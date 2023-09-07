package com.warehouse.springwarehouseweb.repositories;

import com.warehouse.springwarehouseweb.models.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesRepository extends JpaRepository<Sales, Long> {
}
