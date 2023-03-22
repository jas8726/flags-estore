package com.flags.api.flagsapi.model;

import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

public class ShoppingCart {
    private static final Logger LOG = Logger.getLogger(ShoppingCart.class.getName());

    private Map<Integer, Integer> cartMap;

    /**
     * Create an empty shopping cart
     */
    public ShoppingCart() {
        this.cartMap = new TreeMap<>();
    }

    /**
     * Retrieves the shopping cart as a tree map
     * @return The shopping cart as a tree map
     */
    public Map<Integer,Integer> getCartMap() {return cartMap;}

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return cartMap.toString();
    }
}
