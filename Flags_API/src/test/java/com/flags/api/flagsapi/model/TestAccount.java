package com.flags.api.flagsapi.model;

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
     }

     /*
     * test toString override function, assert it prints correctly
     */
     @Test
     public void testToString() {
        String username = "testaccount";
        String password = "testpassword";

        Account account = new Account(username, password);
        String tostring = String.format(Account.STRING_FORMAT, username, password);

        assertEquals(tostring, account.toString(), "toString() incorrect");
     }

 }