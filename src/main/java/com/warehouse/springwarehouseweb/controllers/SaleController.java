package com.warehouse.springwarehouseweb.controllers;

import com.warehouse.springwarehouseweb.dto.SaleProductDto;
import com.warehouse.springwarehouseweb.models.Product;
import com.warehouse.springwarehouseweb.models.SaleProduct;
import com.warehouse.springwarehouseweb.models.Sales;
import com.warehouse.springwarehouseweb.models.enums.Category;
import com.warehouse.springwarehouseweb.services.impl.ProductServiceImpl;
import com.warehouse.springwarehouseweb.services.impl.SaleProductServiceImpl;
import com.warehouse.springwarehouseweb.services.impl.SaleServiceImpl;
import com.warehouse.springwarehouseweb.services.impl.UserServiceImpl;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class SaleController {
    private final ProductServiceImpl productService;
    private final SaleServiceImpl saleService;
    private final SaleProductServiceImpl saleProductService;
    private final UserServiceImpl userService;

    @GetMapping(value = "/sales")
    public String getAllSales(Model model, Principal principal) {
        model.addAttribute("username", userService.getUserByPrincipal(principal));
        model.addAttribute("sales", saleService.findAll());
        return "sales";
    }

    @GetMapping(value = "/sales/create")
    public String createSale(Model model, Principal principal) {
        List<Product> products = productService.findAll();
        model.addAttribute("username", userService.getUserByPrincipal(principal));

        // Check if products is null or empty before passing it to SalesForm
        if (products != null && !products.isEmpty()) {
            model.addAttribute("products", products);
            model.addAttribute("salesForm", new SalesForm(products));
        } else {
            model.addAttribute("products", new ArrayList<Product>());
            model.addAttribute("salesForm", new SalesForm(new ArrayList<Product>()));
        }

        return "sales-create";
    }


    @PostMapping(value = "/sales/create")
    public String createSalePost(SalesForm form, Principal principal) {
        List<SaleProductDto> formDtos = form.getProductSales();
        formDtos.removeIf(saleProductDto -> saleProductDto.getQuantity() == null || saleProductDto.getQuantity() < 1);
        formDtos.forEach(saleProductDto -> saleProductDto.setProduct(productService.findById(saleProductDto
                .getProduct()
                .getId())));
        Sales sale = new Sales();
        sale = saleService.createSale(sale, principal);
        List<SaleProduct> saleProducts = new ArrayList<>();
        for (SaleProductDto dto : formDtos) {
            saleProducts.add(saleProductService.create(new SaleProduct(sale, productService.getProduct(dto
                    .getProduct()
                    .getId()), dto.getQuantity())));
        }
        sale.setSaleProducts(saleProducts);
        for (SaleProductDto dto : formDtos) {
            productService.updateAmount(dto.getProduct(), dto.getQuantity());
        }
        saleService.update(sale);
        return "redirect:/";
    }

    @PostMapping(value = "/sale/{id}/delete")
    public String deleteSale(@PathVariable Long id) {
        saleService.deleteById(id);
        return "redirect:/sales";
    }


    // SalesForm class for form validation
    public static class SalesForm {

        private List<SaleProductDto> productSales;

        public SalesForm(List<Product> products) {
            if (products != null && !products.isEmpty()) {
                this.productSales = products.stream()
                        .map(product -> new SaleProductDto(product, 0)) // Initialize quantity to 0
                        .collect(Collectors.toList());
            } else {
                this.productSales = new ArrayList<>();
            }
        }

        public List<SaleProductDto> getProductSales() {
            return productSales;
        }

        public void setProductSales(List<SaleProductDto> productSales) {
            this.productSales = productSales;
        }
    }
}
