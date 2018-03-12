package com.vending.controller;

import com.vending.entity.Product;
import com.vending.repository.MachineRepository;
import com.vending.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

/**
 * Created by Lloyd on 02/11/2017.
 */
@RestController
@RequestMapping("/{machineId}/products")
public class ProductRestController {

    private final ProductRepository productRepository;
    private final MachineRepository machineRepository;

    /**
     * Product Rest Controller  constructor
     *
     * @param productRepository the repository for all the products
     * @param machineRepository the repository for all the machines
     */
    @Autowired
    ProductRestController(ProductRepository productRepository,
                          MachineRepository machineRepository) {
        this.productRepository = productRepository;
        this.machineRepository = machineRepository;
    }

    /**
     * Get the products
     *
     * @param machineId the name of the vending machine
     * @return all the products in that vending machine
     */
    @RequestMapping(method = RequestMethod.GET)
    Collection<Product> getProducts(@PathVariable String machineId) {
        this.validateMachine(machineId);
        return this.productRepository.findByMachineName(machineId);
    }

    /**
     * Buy a specific item from a specific machine
     *
     * @param machineId the vending machine
     * @param productId the product
     * @return the product you have purchased
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{productId}/buy")
    Product buy(@PathVariable String machineId, @PathVariable Long productId) {
        this.validateMachine(machineId);

        final Product[] soldProduct = new Product[1];

        this.machineRepository.findByName(machineId).map(machine -> {

            this.productRepository.findByMachineName(machineId)
                    .forEach(product -> {
                        if (product.getId() == productId
                                && product.cost <= machine.currentAmount
                                && product.quantity > 0) {
                            product.quantity--;
                            this.productRepository.saveAndFlush(product);
                            machine.currentAmount -= product.cost;
                            product.quantity = 1;
                            soldProduct[0] = product;
                        }
                    });

            this.machineRepository.saveAndFlush(machine);
            return machine;
        });

        return soldProduct[0];
    }

    /**
     * Add a new product
     *
     * @param machineId the vending machine name
     * @param input     the product
     * @return the newly added product
     */
    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> add(@PathVariable String machineId, @RequestBody Product input) {
        this.validateMachine(machineId);

        return this.machineRepository
                .findByName(machineId)
                .map(machine -> {
                    Product result = productRepository.save(new Product(machine, input.name, input.cost, input.quantity));

                    URI location = ServletUriComponentsBuilder
                            .fromCurrentRequest().path("/{id}")
                            .buildAndExpand(result.getId()).toUri();

                    return ResponseEntity.created(location).build();
                })
                .orElse(ResponseEntity.noContent().build());

    }

    /**
     * Get a specific product
     *
     * @param machineId the vending machine name
     * @param productId the product id
     * @return the product
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{productId}")
    Product getProduct(@PathVariable String machineId, @PathVariable Long productId) {
        this.validateMachine(machineId);
        return this.productRepository.findOne(productId);
    }

    /**
     * Validate that a given vending machine exists
     *
     * @param machineId the machine name.
     */
    private void validateMachine(String machineId) {
        this.machineRepository.findByName(machineId).orElseThrow(
                () -> new MachineNotFoundException(machineId));
    }
}
