package com.warehouse.springwarehouseweb.services.impl;

import com.warehouse.springwarehouseweb.models.SaleProduct;
import com.warehouse.springwarehouseweb.repositories.SaleProductRepository;
import com.warehouse.springwarehouseweb.services.SaleProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaleProductServiceImpl implements SaleProductService {
    private final SaleProductRepository saleProductRepository;
    @Override
    public SaleProduct create(SaleProduct saleProduct) {
        return saleProductRepository.save(saleProduct);
    }
}
