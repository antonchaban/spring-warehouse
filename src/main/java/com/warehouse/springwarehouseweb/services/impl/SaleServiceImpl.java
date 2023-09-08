package com.warehouse.springwarehouseweb.services.impl;

import com.warehouse.springwarehouseweb.models.Sales;
import com.warehouse.springwarehouseweb.models.User;
import com.warehouse.springwarehouseweb.services.SaleService;

import java.security.Principal;
import java.util.List;

public class SaleServiceImpl implements SaleService { // todo implement
    @Override
    public List<Sales> findAll() {
        return null;
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
