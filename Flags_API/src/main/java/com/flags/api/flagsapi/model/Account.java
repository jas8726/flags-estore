package com.flags.api.flagsapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents an Account entity
 */
public class Account {
    private static final Logger LOG = Logger.getLogger(Account.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "Account [username=%s, password=%s]";

    @JsonProperty("username") private String username;
    @JsonProperty("password") private String password;

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
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,username,password);
    }
}