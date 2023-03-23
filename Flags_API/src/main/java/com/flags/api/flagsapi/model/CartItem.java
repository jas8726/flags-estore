package com.flags.api.flagsapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CartItem {
    private static final Logger LOG = Logger.getLogger(CartItem.class.getName());

    @JsonProperty("flagID") private int flagID;
    @JsonProperty("quantity") private int quantity;

    /**
     * Create a cart item with a given Flag and quanitity
     */
    public CartItem(@JsonProperty("flagID") int flagID, @JsonProperty("quantity") int quantity) {
        this.flagID = flagID;
        this.quantity = quantity;
    }

    /**
     * Retrieves the id of the flag in the cart
     * @return The flag ID as an int
     */
    public int getFlagID() {return flagID;}

    /**
     * Retrieves the quantity of the flag in the cart
     * @return The quantity as an int
     */
    public int getQuantity() {return quantity;}

    /**
     * Sets the quantity of the flag in the cart
     * @param quantity the new quantity to set
     */
    public void setQuantity(int quantity) {this.quantity = quantity;}
}
