package com.vending.repository;

import com.vending.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

/**
 * Created by Lloyd on 01/11/2017.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
    /**
     * Find products in a vending machine
     *
     * @param name the vending machine name
     * @return if the machine exists
     */
    Collection<Product> findByMachineName(String name);
}
