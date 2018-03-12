package com.vending.controller;

import com.vending.entity.Machine;
import com.vending.repository.CoinRepository;
import com.vending.repository.MachineRepository;
import com.vending.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Created by Lloyd on 02/11/2017.
 */
@RestController
@RequestMapping("")
public class MachineRestController {
    private final ProductRepository productRepository;
    private final MachineRepository machineRepository;
    private final CoinRepository coinRepository;

    /**
     * Machine Rest Controller constructor
     *
     * @param productRepository product repository
     * @param machineRepository machine repository
     * @param coinRepository    coin repository
     */
    @Autowired
    MachineRestController(ProductRepository productRepository,
                          MachineRepository machineRepository,
                          CoinRepository coinRepository) {
        this.productRepository = productRepository;
        this.machineRepository = machineRepository;
        this.coinRepository = coinRepository;
    }

    /**
     * Get all machines
     *
     * @return all machines
     */
    @RequestMapping(method = RequestMethod.GET)
    List<Machine> getMachines() {
        return this.machineRepository.findAll();
    }

    /**
     * Add a new machine to the system
     *
     * @param input the machine you want to add
     * @return the added machine
     */
    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> add(@RequestBody Machine input) {

        Machine result = this.machineRepository.save(new Machine(input.name, input.currentAmount));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getID()).toUri();

        return ResponseEntity.created(location).build();

    }

    /**
     * Get the details of a specific vending machine
     *
     * @param machineId the machine name
     * @return the machine details
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{machineId}")
    Machine readMachine(@PathVariable String machineId) {
        this.validateMachine(machineId);
        return this.machineRepository.findOneByName(machineId);
    }

    /**
     * Check that a given machine exists
     *
     * @param machineId the machine name
     */
    private void validateMachine(String machineId) {
        this.machineRepository.findByName(machineId).orElseThrow(
                () -> new MachineNotFoundException(machineId));
    }
}
