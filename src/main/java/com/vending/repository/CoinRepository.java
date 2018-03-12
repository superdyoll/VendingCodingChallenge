package com.vending.repository;

import com.vending.entity.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

/**
 * Created by Lloyd on 01/11/2017.
 */
public interface CoinRepository extends JpaRepository<Coin, Long> {
    /**
     * Find all coins in a given machine
     *
     * @param name
     * @return
     */
    Collection<Coin> findByMachineName(String name);
}
