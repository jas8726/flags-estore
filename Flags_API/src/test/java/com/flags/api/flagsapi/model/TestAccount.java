package com.flags.api.flagsapi.model;

import java.util.TreeMap;

 import static org.junit.jupiter.api.Assertions.assertEquals;
 import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Tag;
 import org.junit.jupiter.api.Test;

 @Tag("Model")
 public class TestAccount {
    /*
     * test newAccount function, assert it creates correctly
     */
    @Test
    public void newAccountTest() {
        String username = "testaccount";
        String password = "testpassword";

        Account account = new Account(username, password);

        assertNotNull(account);

        assertEquals(username, account.getUsername());
        assertEquals(password, account.getPassword());
        assertEquals((new TreeMap<Integer,Integer>()).getClass(), account.getShoppingCart().getClass());
        assertEquals(false, account.isAdmin());
     }

     /*
     * test toString override function, assert it prints correctly
     */
     @Test
     public void testToString() {
        String username = "testaccount";
        String password = "testpassword";

        Account account = new Account(username, password);
        String tostring = String.format(Account.STRING_FORMAT, username, password, (new TreeMap<Integer, Integer>()).toString());

        assertEquals(tostring, account.toString(), "toString() incorrect");
     }

 }