package com.warehouse.springwarehouseweb.services.impl;

import com.warehouse.springwarehouseweb.models.Product;
import com.warehouse.springwarehouseweb.models.User;
import com.warehouse.springwarehouseweb.models.enums.Category;
import com.warehouse.springwarehouseweb.repositories.ProductRepository;
import com.warehouse.springwarehouseweb.repositories.UserRepository;
import com.warehouse.springwarehouseweb.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).get();
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.findById(id).ifPresent(productRepository::delete);
    }

    @Override
    public List<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public void createProduct(Product product, Principal principal) {
        if (principal == null) product.setUser(new User());
        else product.setUser(userRepository.findUserByLogin(principal.getName()));
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(User user, Long id) {
        Product product = productRepository.findById(id)
                .orElse(null);
        if (product != null) {
            if (product.getUser().getId().equals(user.getId()) || user.isAdmin()) {
                productRepository.delete(product);
            } else System.err.println("User: " + user.getLogin() + " hasn't product " + id);
        } else System.err.println("Product " + id + " is not found");
    }

    @Override
    public void editProduct(Product updProduct, Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            System.err.println("Product " + id + " is not found");
        } else {
            product.setName(updProduct.getName());
            product.setPrice(updProduct.getPrice());
            product.setQuantity(updProduct.getQuantity());
            product.setDescription(updProduct.getDescription());
            try {
                product.getCategory().clear();
                String category = updProduct.getCategory().toArray()[0].toString();
                product.getCategory().add(Category.valueOf(category));
            } catch (NullPointerException e) {
                System.out.println("Category is null, setting other value");
                product.getCategory().clear();
                product.getCategory().add(Category.OTHER);
            }
            productRepository.save(product);
        }
    }

}
