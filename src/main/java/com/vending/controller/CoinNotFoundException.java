package com.vending.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Lloyd on 02/11/2017.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CoinNotFoundException extends RuntimeException {
    /**
     * Coin not found exception
     *
     * @param coinValue the value of the coin
     */
    public CoinNotFoundException(int coinValue) {
        super("could not find coin '" + coinValue + "'.");
    }
}