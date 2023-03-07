package com.flags.api.flagsapi.model;

 import static org.junit.jupiter.api.Assertions.assertEquals;
 import static org.junit.jupiter.api.Assertions.assertNotNull;

 import org.junit.jupiter.api.Tag;
 import org.junit.jupiter.api.Test;

 @Tag("Model")
 public class TestAccount {
    @Test
    public void newFlagTest() {
        String username = "testaccount";
        String password = "testpassword";

        Account account = new Account(username, password);

        assertNotNull(account);

        assertEquals(username, account.getUsername());
        assertEquals(password, account.getUsername());
     }

     @Test
     public void testToString() {
        String username = "testaccount";
        String password = "testpassword";

        Account account = new Account(username, password);
        String tostring = String.format(account.STRING_FORMAT, username, password);

        assertEquals(tostring, account.toString(), "toString() incorrect");
     }

 }