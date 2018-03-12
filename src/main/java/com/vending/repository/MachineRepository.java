package com.vending.repository;

import com.vending.entity.Machine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Lloyd on 01/11/2017.
 */
public interface MachineRepository extends JpaRepository<Machine, Long> {
    /**
     * Find all machines with a specific name
     *
     * @param name name of the machine
     * @return if the machine exists
     */
    Optional<Machine> findByName(String name);

    /**
     * Find the machine with a a specific name
     *
     * @param name name of the machine
     * @return the machine
     */
    Machine findOneByName(String name);
}
