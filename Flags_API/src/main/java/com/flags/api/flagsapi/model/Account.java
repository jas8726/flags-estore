package com.flags.api.flagsapi.model;

import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents an Account entity
 */
public class Account {
    private static final Logger LOG = Logger.getLogger(Account.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "Account [username=%s, password=%s, cart=%s]";

    @JsonProperty("username") private String username;
    @JsonProperty("password") private String password;
    @JsonProperty("shoppingCart") private Map<Integer, Integer> shoppingCart;
    //try to make a map with key as product id and object as the quantity

    /**
     * Create an account with the given username and password
     * @param username The username of the account
     * @param password The password of the account
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Account(@JsonProperty("username") String username, @JsonProperty("password") String password) {
        this.username = username;
        this.password = password;
        this.shoppingCart = new TreeMap<>();
    }

    /**
     * Retrieves the username of the account
     * @return The username of the account
     */
    public String getUsername() {return username;}

    /**
     * Retrieves the password of the account
     * @return The password of the account
     */
    public String getPassword() {return password;}

    /**
     * Retrieves the password of the account
     * @return The password of the account
     */
    public Map<Integer, Integer> getShoppingCart() {return shoppingCart;}
    
    /**
     * Retrieves boolean for if account is an admin
     * and, therefore, can manage the inventory
     * @return true if account is admin account
     */
    public boolean isAdmin() {return username.equals("admin");}

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,username,password,shoppingCart);
    }
}