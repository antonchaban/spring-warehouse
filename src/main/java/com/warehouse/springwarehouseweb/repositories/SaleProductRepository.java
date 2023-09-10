package com.warehouse.springwarehouseweb.repositories;

import com.warehouse.springwarehouseweb.models.SaleProduct;
import com.warehouse.springwarehouseweb.models.SaleProductPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleProductRepository extends JpaRepository<SaleProduct, SaleProductPK> {
}
