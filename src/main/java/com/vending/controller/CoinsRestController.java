package com.vending.controller;

import com.vending.entity.Coin;
import com.vending.repository.CoinRepository;
import com.vending.repository.MachineRepository;
import com.vending.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * Created by Lloyd on 02/11/2017.
 */
@RestController
@RequestMapping("/{machineId}/coins")
public class CoinsRestController {

    private final ProductRepository productRepository;
    private final MachineRepository machineRepository;
    private final CoinRepository coinRepository;

    /**
     * Coins Rest Controller constructor
     *
     * @param productRepository product repository
     * @param machineRepository machine repository
     * @param coinRepository    coin repository
     */
    @Autowired
    CoinsRestController(ProductRepository productRepository,
                        MachineRepository machineRepository,
                        CoinRepository coinRepository) {
        this.productRepository = productRepository;
        this.machineRepository = machineRepository;
        this.coinRepository = coinRepository;
    }

    /**
     * Get list of all coins in the system
     *
     * @param machineId the machine name
     * @return the list of the coins
     */
    @RequestMapping(method = RequestMethod.GET)
    Collection<Coin> getCoins(@PathVariable String machineId) {
        this.validateMachine(machineId);
        return this.coinRepository.findByMachineName(machineId);
    }

    /**
     * Add a new coin into the vending machine
     *
     * @param machineId the vending machine name
     * @param coin      the coin you want to add
     * @return the machine that the coin was added
     */
    @RequestMapping(method = RequestMethod.POST)
    Optional<?> addCoin(@PathVariable String machineId, @RequestBody Coin coin) {
        this.validateMachine(machineId);

        return this.machineRepository
                .findByName(machineId)
                .map(machine -> {
                    final boolean[] coinFound = {false};

                    // Increase the amount of coins if the machine already has seen that coin
                    this.coinRepository.findByMachineName(machineId).forEach(machineCoin -> {
                        if (coin.value == machineCoin.value) {
                            coin.amount++;
                            coinFound[0] = true;
                        }
                    });

                    // Else add the coin to the repository
                    if (!coinFound[0] && IntStream.of(coin.POSSIBLE_VALUES).anyMatch(x -> x == coin.value)) {
                        this.coinRepository.saveAndFlush(new Coin(machine, coin.value, 1));
                        coinFound[0] = true;
                    }

                    if (coinFound[0]) {
                        machine.currentAmount += coin.value;
                    }

                    this.machineRepository.saveAndFlush(machine);

                    return this.machineRepository.findByName(machineId);
                });
    }

    /**
     * Return all the money that has not been spent in the system
     *
     * @param machineId machine name
     * @return the list of coins that have been returned
     */
    @RequestMapping(method = RequestMethod.GET, value = "/refund")
    List<Coin> refund(@PathVariable String machineId) {
        this.validateMachine(machineId);

        final int[] refundTotal = new int[1];

        this.machineRepository.findByName(machineId).map(machine -> {
            refundTotal[0] = machine.currentAmount;
            return refundTotal[0];
        });

        List<Coin> refundCoins = new ArrayList<>();

        for (int value : Coin.POSSIBLE_VALUES) {
            this.coinRepository.findByMachineName(machineId).forEach(coin -> {
                if (value == coin.value && coin.amount > 0 && coin.value <= refundTotal[0]) {
                    double max_coins = Math.min(Math.floor(refundTotal[0] / coin.value), coin.amount);
                    coin.amount -= max_coins;
                    this.coinRepository.saveAndFlush(coin);
                    refundCoins.add(new Coin(coin.value, (int) max_coins));
                    refundTotal[0] -= (int) max_coins * coin.value;
                }
            });
        }

        return refundCoins;
    }

    /**
     * Get a specific coin
     *
     * @param machineId machine name
     * @param coinValue coin value
     * @return the details of coins in that vending machine
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{coinValue}")
    Coin getCoin(@PathVariable String machineId, @PathVariable int coinValue) {
        this.validateMachine(machineId);
        final Coin[] foundCoin = {null};
        this.coinRepository.findByMachineName(machineId).forEach((Coin coin) -> {
            if (coin.value == coinValue) {
                foundCoin[0] = coin;
            }
        });
        if (foundCoin[0] != null) {
            return foundCoin[0];
        } else {
            throw new CoinNotFoundException(coinValue);
        }
    }

    /**
     * Check if a given machine name exists
     *
     * @param machineId the machine name
     */
    private void validateMachine(String machineId) {
        this.machineRepository.findByName(machineId).orElseThrow(
                () -> new MachineNotFoundException(machineId));
    }
}
