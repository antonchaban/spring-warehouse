package com.warehouse.springwarehouseweb.services.impl;

import com.warehouse.springwarehouseweb.models.Sales;
import com.warehouse.springwarehouseweb.models.User;
import com.warehouse.springwarehouseweb.repositories.ProductRepository;
import com.warehouse.springwarehouseweb.repositories.SalesRepository;
import com.warehouse.springwarehouseweb.repositories.UserRepository;
import com.warehouse.springwarehouseweb.services.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService { // todo implement
    private final UserRepository userRepository;
    private final SalesRepository salesRepository;
    private final ProductRepository productRepository;

    @Override
    public List<Sales> findAll() {
        return salesRepository.findAll();
    }

    @Override
    public Sales findById(Long id) {
        return salesRepository.findById(id).get();
    }

    @Override
    public void save(Sales sales) {
        salesRepository.save(sales);
    }

    @Override
    public void deleteById(Long id) {
        salesRepository.findById(id).ifPresent(salesRepository::delete);
    }

    @Override
    public Sales createSale(Sales sales, Principal principal) {
//        List<Product> products = sales.getProducts();

        if (principal == null) sales.setUser(new User());
        else sales.setUser(userRepository.findUserByLogin(principal.getName()));
        sales.setSaleDate(LocalDate.now());
        salesRepository.save(sales);
        return sales;
    }

    public void update(Sales sales) {
        salesRepository.save(sales);
    }

    @Override
    public void deleteSales(User user, Long id) {

    }

    public List<Sales> getLast3Sales() {
        List<Sales> sortedSales = salesRepository.findAll().stream()
                .sorted(Comparator.comparing(Sales::getId).reversed())
                .collect(Collectors.toList());

        return sortedSales.stream()
                .limit(3)
                .collect(Collectors.toList());
    }
}
