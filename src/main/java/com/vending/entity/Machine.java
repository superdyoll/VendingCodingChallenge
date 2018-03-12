package com.vending.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Lloyd on 01/11/2017.
 */
@Entity
public class Machine {

    @OneToMany(mappedBy = "machine")
    private Set<Product> productList = new HashSet<>();

    @OneToMany(mappedBy = "machine")
    private Set<Coin> coinsList = new HashSet<>();

    public String name;
    public int currentAmount;

    @Id
    @GeneratedValue
    private Long id;

    public Long getID() {
        return id;
    }

    /** Get the products available
     * @return all the products in the system
     */
    public Set<Product> getProducts() {
        return productList;
    }

    /** Machine constructor
     * @param name the name of the vending machine
     * @param currentAmount the amount of money in the machine
     */
    public Machine(String name, int currentAmount) {
        this.name = name;
        this.currentAmount = currentAmount;
    }

    public Machine() { //jpa only
    }

    /** Get the name of the vending machine
     * @return the name of the vending machine
     */
    public String getName() {
        return name;
    }

    /** Get the list of coins in the system
     * @return the list of coins and how many there are
     */
    public Set<Coin> getCoinsList() {
        return coinsList;
    }

    /** Get the current amount of money stored by the vending machine that can be used to purchase things
     * @return the current spending money amount
     */
    public int getCurrentAmount() {
        return currentAmount;
    }
}
