package com.vending.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


/**
 * Created by Lloyd on 01/11/2017.
 */
@Entity
public class Product {

    @JsonIgnore
    @ManyToOne
    private Machine machine;

    @Id
    @GeneratedValue
    private Long id;


    /** The unique if of the product
     * @return the unique id
     */
    public Long getId() {
        return id;
    }

    /** The name of the product
     * @return the name
     */
    public String getName() {
        return name;
    }

    /** The cost of the product
     * @return the cost
     */
    public int getCost() {
        return cost;
    }

    public String name;
    public int cost;
    public int quantity;

    /**
     * Product constructor
     *
     * @param machine  the machine that product is in
     * @param name     the name of the product
     * @param cost     the cost of the product
     * @param quantity the quantity of the product in the vending machine
     */
    public Product(Machine machine, String name, int cost, int quantity) {
        this.name = name;
        this.cost = cost;
        this.machine = machine;
        this.quantity = quantity;
    }

    /**
     * Empty constructor for use by jpa
     */
    public Product() {
        // jpa only
    }


    /**
     * Get the quantity of product for a specific machine
     *
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }
}
