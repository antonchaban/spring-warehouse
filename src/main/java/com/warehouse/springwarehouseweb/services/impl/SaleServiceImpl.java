package com.warehouse.springwarehouseweb.services.impl;

import com.warehouse.springwarehouseweb.models.Sales;
import com.warehouse.springwarehouseweb.models.User;
import com.warehouse.springwarehouseweb.repositories.SalesRepository;
import com.warehouse.springwarehouseweb.repositories.UserRepository;
import com.warehouse.springwarehouseweb.services.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService { // todo implement
    private final UserRepository userRepository;
    private final SalesRepository salesRepository;

    @Override
    public List<Sales> findAll() {
        return salesRepository.findAll();
    }

    @Override
    public Sales findById(Long id) {
        return null;
    }

    @Override
    public void save(Sales sales) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void createSale(Sales sales, Principal principal) {

    }

    @Override
    public void deleteSales(User user, Long id) {

    }
}
