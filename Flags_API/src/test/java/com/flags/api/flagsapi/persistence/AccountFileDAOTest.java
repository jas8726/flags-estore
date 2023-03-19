package com.flags.api.flagsapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flags.api.flagsapi.model.Account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


@Tag("Persistence-tier")
public class AccountFileDAOTest {
    AccountFileDAO accountFileDAO;
    Account[] testAccounts;
    ObjectMapper mockObjectMapper;


    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupAccountFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testAccounts = new Account[3];
        testAccounts[0] = new Account("username", "password");
        testAccounts[1] = new Account("numbers_user", "12345");
        testAccounts[2] = new Account("alphabet_user", "abc");

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the flag array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Account[].class))
                .thenReturn(testAccounts);
                accountFileDAO = new AccountFileDAO("doesnt_matter.txt",mockObjectMapper);
    }

    //not getting the correct order/full order of accounts
    @Test
    public void testGetAccountsArray() {
        // Invoke
        Account[] accounts = accountFileDAO.getAccountsArray();

        // Analyze
        assertEquals(accounts.length,testAccounts.length);
        for (int i = 0; i < testAccounts.length;++i)
            assertEquals(accounts[i],testAccounts[i]);
    }


    @Test
    public void testSaveThrowsException() throws IOException{
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class),any(Account[].class));

        Account user = new Account("new_username", "new_password");

        assertThrows(IOException.class,
                        () -> accountFileDAO.createAccount(user),
                        "IOException not thrown");  //you want it to throw something
    }

    //Need to fix what you compare to : line 89 : the assertEquals says that the hex values of the accounts are different
    //also something weird with the delete - check the contents of the beforesave and aftersave
    @Test
    public void testSave() throws IOException{

        Account user = new Account("new_username", "new_password");
        accountFileDAO.createAccount(user);
        Account[] beforesave = accountFileDAO.getAccountsArray();
        accountFileDAO.save();
        accountFileDAO.load();
        Account[] aftersave = accountFileDAO.getAccountsArray();
        accountFileDAO.deleteAccount("new_username");

        assertEquals(beforesave, aftersave);

    }

    //might just need to fix the assert cuz it says that no exception was thrown
    @Test
    public void testLoadException() throws IOException{
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class),any(Account[].class));


        assertThrows(IOException.class,
                        () -> accountFileDAO.load(),
                        "IOException not thrown");

    }

    //something wrong with how it is getting accounts
    @Test
    public void testGetAccounts() {
        // Invoke
        Account[] accounts = accountFileDAO.getAccounts();

        // Analyze
        assertEquals(accounts.length,testAccounts.length);
        for (int i = 0; i < testAccounts.length;++i)
            assertEquals(accounts[i],testAccounts[i]);
    }

    @Test
    public void testGetAccount() {
        // Invoke
        Account account = accountFileDAO.getAccount("username");

        // Analyze
        assertEquals(account, testAccounts[0]);
    }

    @Test
    public void testGetAccountNotFound() {
        // Invoke
        Account account = accountFileDAO.getAccount("xyz");

        // Analyze
        assertEquals(account, null);
    }

    @Test
    public void testCreateAccount() {
        // Setup
        Account user = new Account("person_name", "person_password");

        // Invoke
        Account result = assertDoesNotThrow(() -> accountFileDAO.createAccount(user),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Account actual = accountFileDAO.getAccount(user.getUsername());
        assertEquals(actual.getUsername(),user.getUsername());
        assertEquals(actual.getPassword(),user.getPassword());
    }

    @Test
    public void testUpdateAccount() {
        // Setup
        Account account = new Account("username", "new_password");

        // Invoke
        Account result = assertDoesNotThrow(() -> accountFileDAO.updateAccount(account),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Account actual = accountFileDAO.getAccount(account.getUsername());
        assertEquals(actual,account);
    }

    @Test
    public void testUpdateAccountNotFound() {
        // Setup
        Account account = new Account("new_username", "new_password");

        // Invoke
        Account result = assertDoesNotThrow(() -> accountFileDAO.updateAccount(account),
                                                "Unexpected exception thrown");

        // Analyze
        assertNull(result);
    }

    @Test
    public void testDeleteAccount() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> accountFileDAO.deleteAccount("username"),
                            "Unexpected exception thrown");

        // Analzye
        assertEquals(result,true);
        // We check the internal tree map size against the length
        // of the test flags array - 1 (because of the delete)
        // Because flags attribute of FlagFileDAO is package private
        // we can access it directly
        assertEquals(accountFileDAO.accounts.size(),testAccounts.length-1);
    }

    @Test
    public void testDeleteAccountNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> accountFileDAO.deleteAccount("wrong_username"),
                                                "Unexpected exception thrown");

        // Analyze
        assertEquals(result,false);
        assertEquals(accountFileDAO.accounts.size(),testAccounts.length);
    }



}
