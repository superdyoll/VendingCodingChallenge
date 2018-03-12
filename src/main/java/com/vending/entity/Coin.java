package com.vending.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by Lloyd on 02/11/2017.
 */
@Entity
public class Coin {

    /**
     * All the possible values of coin 50 = 50p, 200 = £2
     */
    @JsonIgnore
    public static final int [] POSSIBLE_VALUES = {200,100,50,20,10,5,2,1};

    @JsonIgnore
    @ManyToOne
    private Machine machine;

    @Id
    @GeneratedValue
    private Long id;

    /** The unique id of the coin
     * @return the unique id
     */
    public Long getId() {
        return id;
    }

    public int value;
    public int amount;

    /** Get the value of the type of coin
     * @return the value (e.g. 50 = 50p , 200 = £2)
     */
    public int getValue() {
        return value;
    }

    /** Get the number of coins available
     * @return the number of coins
     */
    public int getAmount() {
        return amount;
    }

    /** Coin constructor
     *
     * @param machine the vending machine being used
     * @param value how much the coin is worth
     * @param amount how many coins you have
     */
    public Coin(Machine machine, int value, int amount) {
        this.machine = machine;
        this.value = value;
        this.amount = amount;
    }

    /** Coin constructor
     *
     * @param value how much the coin is worth
     * @param amount how many coins you have
     */
    public Coin(int value, int amount){
        this.value = value;
        this.amount = amount;
    }

    public Coin(){
        //jpa only
    }
}
