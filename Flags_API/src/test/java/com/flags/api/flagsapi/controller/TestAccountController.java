package com.flags.api.flagsapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

// import java.beans.Transient;
import java.io.IOException;

import com.flags.api.flagsapi.persistence.AccountDAO;
import com.flags.api.flagsapi.model.Account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Tag("Controller")
public class TestAccountController {

    private AccountController accountController;
    private AccountDAO mockAccountDAO;

    /*
     * create mock AccountDAO class for testing
     */
    @BeforeEach
    public void setupAccountController() {
        mockAccountDAO = mock(AccountDAO.class);
        accountController = new AccountController(mockAccountDAO);
    }

    /*
     * testing getAccount function when it is found, check status
     */
    @Test 
    public void testGetAccountFound() throws IOException {  
        
        Account account = new Account("user", "password");
        when(mockAccountDAO.getAccount(account.getUsername())).thenReturn(account);
        ResponseEntity<Account> response = accountController.getAccount(account.getUsername());

        assertEquals(HttpStatus.OK, response.getStatusCode(), "getAccount() HTTP status incorrect");
        assertEquals(account, response.getBody(), "getAccount() body incorrect");
    }

    /*
     * testing getAccount function when it is not found, check status
     */
    @Test
    public void testGetAccountNotFound() throws Exception { 
        
        String accountUsername = "incorrectusername"; 
        when(mockAccountDAO.getAccount(accountUsername)).thenReturn(null);
        ResponseEntity<Account> response = accountController.getAccount(accountUsername);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "getAccount() HTTP status incorrect when not found");
    }

    /*
     * check exception with getAccount function (internal_server_error)
     */
    @Test
    public void testGetAccountException() throws Exception { 
        
        String accountUsername = "incorrectusername";
        doThrow(new IOException()).when(mockAccountDAO).getAccount(accountUsername);
        ResponseEntity<Account> response = accountController.getAccount(accountUsername);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode(), "getAccount() HTTP status incorrect when exception");
    }

    /*
     * testing getAccount function when it is found, check status
     */
    @Test 
    public void testLoginAccount() throws IOException {  
        
        Account account = new Account("user", "password");
        when(mockAccountDAO.loginAccount(account.getUsername(), account.getPassword())).thenReturn(account);
        ResponseEntity<Account> response = accountController.loginAccount(account.getUsername(), account.getPassword());

        assertEquals(HttpStatus.OK, response.getStatusCode(), "loginAccount() HTTP status incorrect");
        assertEquals(account, response.getBody(), "loginAccount() body incorrect");
    }

    /*
     * testing getAccount function when it is not found, check status
     */
    @Test
    public void testLoginAccountUnauthorized() throws Exception { 
        
        Account account = new Account("testusername", "incorrectpassword");
        when(mockAccountDAO.loginAccount(account.getUsername(), account.getPassword())).thenReturn(null);
        ResponseEntity<Account> response = accountController.loginAccount(account.getUsername(), account.getPassword());

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode(), "loginAccount() HTTP status incorrect when unauthorized");
    }

    /*
     * check exception with getAccount function (internal_server_error)
     */
    @Test
    public void testLoginAccountException() throws Exception { 
        
        Account account = new Account("testusername", "password");
        doThrow(new IOException()).when(mockAccountDAO).loginAccount(account.getUsername(), account.getPassword());
        ResponseEntity<Account> response = accountController.loginAccount(account.getUsername(), account.getPassword());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode(), "loginAccount() HTTP status incorrect when exception");
    }

    /*
     * test createAccount function, check status
     */
    @Test
    public void testCreateAccount() throws IOException {  
       
        Account account = new Account("user", "password");
        when(mockAccountDAO.createAccount(account)).thenReturn(account);
        ResponseEntity<Account> response = accountController.createAccount(account);

        assertEquals(HttpStatus.CREATED, response.getStatusCode(), "createAccount() HTTP status incorrect");
        assertEquals(account, response.getBody(), "createAccount() body incorrect");
    }

    /*
     * test createAccount function when there is a conflict, status CONFLICT
     */
    @Test
    public void testCreateAccountFail() throws IOException {  

        Account account = new Account("user", "password"); 
        when(mockAccountDAO.getAccount(account.getUsername())).thenReturn(account);
        ResponseEntity<Account> response = accountController.createAccount(account);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode(), "createAccount() HTTP status incorrect when conflict");
    }

    /*
     * check exception with createAccount function (internal_server_error)
     */
    @Test
    public void testCreateAccountException() throws IOException {  
    
        Account account = new Account("user", "password");
        doThrow(new IOException()).when(mockAccountDAO).createAccount(account);
        ResponseEntity<Account> response = accountController.createAccount(account);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode(), "createAccount() HTTP status incorrect when exception");
    }

    /*
     * test updateAccount, check status
     */
    @Test
    public void testUpdateAccount() throws IOException { 
      
        Account account = new Account("user", "testpassword");
        when(mockAccountDAO.updateAccount(account)).thenReturn(account);
        ResponseEntity<Account> response = accountController.updateAccount(account);

        assertEquals(HttpStatus.OK, response.getStatusCode(), "updateAccount() HTTP status incorrect");
        assertEquals(account, response.getBody(), "updateAccount() body incorrect");
    }

    /*
     * test updateAccount when fails, status NOT_FOUND
     */
    @Test
    public void testUpdateAccountFailed() throws IOException { 
       
        Account account = new Account("testusername", "testpassword");
        when(mockAccountDAO.updateAccount(account)).thenReturn(null);
        ResponseEntity<Account> response = accountController.updateAccount(account);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "updateAccount() HTTP status incorrect when not found");
    }

    /*
     * check exception with updateAccount function (internal_server_error)
     */
    @Test
    public void testUpdateAccountException() throws IOException { 
   
        Account account = new Account("testusername", "testpassword");
        doThrow(new IOException()).when(mockAccountDAO).updateAccount(account);
        ResponseEntity<Account> response = accountController.updateAccount(account);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode(), "updateAccount() HTTP status incorrect when exception");
    }

    /*
     * test getAccounts function, check status
     */
    @Test
    public void testGetAccounts() throws IOException { 

        Account[] accounts = new Account[2];
        accounts[0] = new Account("user", "password");
        accounts[1] = new Account("admin", "admin");
        when(mockAccountDAO.getAccounts()).thenReturn(accounts);
        ResponseEntity<Account[]> response = accountController.getAccounts();

        assertEquals(HttpStatus.OK, response.getStatusCode(), "getAccounts() HTTP status incorrect");
        assertEquals(accounts, response.getBody(), "getAccounts() body incorrect");
    }

    /*
     * check exception with getAccounts function (internal_server_error)
     */
    @Test
    public void testGetAccountsException() throws IOException { 
       
        doThrow(new IOException()).when(mockAccountDAO).getAccounts();
        ResponseEntity<Account[]> response = accountController.getAccounts();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode(), "getAccounts() HTTP status incorrect when exception");
    }

    /*
     * tests deleteAccount function, check status
     */
    @Test
    public void testDeleteAccount() throws IOException { 
    
        String accountUsername = "user";
        when(mockAccountDAO.deleteAccount(accountUsername)).thenReturn(true);
        ResponseEntity<Account> response = accountController.deleteAccount(accountUsername);

        assertEquals(HttpStatus.OK,response.getStatusCode(), "deleteAccount() HTTP status incorrect");
    }

    /*
     * tests deleteAccount function when account isn't found, status NOT_FOUND
     */
    @Test
    public void testDeleteAccountNotFound() throws IOException { 
  
        String accountUsername = "incorrectusername";
        when(mockAccountDAO.deleteAccount(accountUsername)).thenReturn(false);
        ResponseEntity<Account> response = accountController.deleteAccount(accountUsername);

        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode(), "deleteAccount() HTTP status incorrect when not found");
    }

    /*
     * check exception with deleteAccount function (internal_server_error)
     */
    @Test
    public void testDeleteAccountException() throws IOException { 
   
        String accountUsername = "testusername";
        doThrow(new IOException()).when(mockAccountDAO).deleteAccount(accountUsername);
        ResponseEntity<Account> response = accountController.deleteAccount(accountUsername);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode(), "deleteAccount() HTTP status incorrect when exception");
    }

}