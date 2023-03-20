package com.flags.api.flagsapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
public class Cart {
    private static final Logger LOG = Logger.getLogger(Cart.class.getName());

    static final String STRING_FORMAT = "Cart [owner=%s]";

    @JsonProperty("owner") private String owner;

    /**
     * Create a shopping cart with the given username owner
     * @param owner The username of the account
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Cart(@JsonProperty("owner") String owner){
        this.owner = owner;
        //set owner to an account
    }

    
    /**
     * Retrieves the username of the account
     * @return The username of the account
     */
    public String getUsername() {return owner;}

    /**
     * Retrieves the password of the account
     * @return The password of the account
     */
    public String getPassword() {return owner;}

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,owner);
    }
}
