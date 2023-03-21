package com.flags.api.flagsapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.flags.api.flagsapi.persistence.AccountDAO;
import com.flags.api.flagsapi.model.Account;

/**
 * Handles the REST API requests for the Account resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 */

@RestController
@RequestMapping("accounts")
public class AccountController {
    private static final Logger LOG = Logger.getLogger(AccountController.class.getName());
    private AccountDAO accountDao;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param accountDao The {@link AccountDAO Account Data Access Object} to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public AccountController(AccountDAO accountDao) {
        this.accountDao = accountDao;
    }

    /**
     * Responds to the GET request for an {@linkplain Account account} for the given username
     * 
     * @param username The username used to locate the {@link Account account}
     * 
     * @return ResponseEntity with {@link Account account} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{username}")
    public ResponseEntity<Account> getAccount(@PathVariable String username) {
        LOG.info("GET /accounts/" + username);
        try {
            Account account = accountDao.getAccount(username);
            if (account != null)
                return new ResponseEntity<Account>(account,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Account accounts}
     * 
     * @return ResponseEntity with array of {@link Account account} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<Account[]> getAccounts() {
        LOG.info("GET /accounts");

        try {
            Account[] accountArray = accountDao.getAccounts();
            if (accountArray != null)
                return new ResponseEntity<Account[]>(accountArray,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for an {@linkplain Account account} for the given username and password
     * 
     * @param username The username used to login to the {@link Account account}
     * @param password The username used to login to the {@link Account account}
     * 
     * @return ResponseEntity with {@link Account account} object and HTTP status of OK if logged in<br>
     * ResponseEntity with HTTP status of UNAUTHORIZED if not logged in<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping(path = "/{username}", params = "password")
    public ResponseEntity<Account> loginAccount(@PathVariable String username, @RequestParam String password) {
        LOG.info("GET /accounts/" + username + "?password="+ password);
        try {
            Account account = accountDao.loginAccount(username, password);
            if (account != null)
                return new ResponseEntity<Account>(account,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for an {@linkplain Account account} for a given flag id
     * 
     * @param username The username used to get the cart of the {@link Account account}
     * @param id The id of the {@linkplain Flag flag} to get the count of
     * 
     * @return ResponseEntity with Integer count and HTTP status of OK if retrieved count<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping(path = "/{username}/cart", params = "id")
    public ResponseEntity<Integer> getCartCount(@PathVariable String username, @RequestParam int id) {
        LOG.info("GET /accounts/" + username + "/cart?id="+ id);
        try {
            return new ResponseEntity<Integer>(accountDao.getFlagCountCart(username, id),HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates an {@linkplain Account account} with the provided account object
     * 
     * @param account - The {@link Account account} to create
     * 
     * @return ResponseEntity with created {@link Account account} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link Account account} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        LOG.info("POST /accounts " + account);

        try {
            if (accountDao.getAccount(account.getUsername()) != null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            
            Account newAccount = accountDao.createAccount(account);
            return new ResponseEntity<Account>(newAccount,HttpStatus.CREATED);
                
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Adds the id of a {@linkplain Flag flag} to an {@linkplain Account account}'s shopping cart
     * 
     * @param username The username used to add to the cart of the {@link Account account}
     * @param id The id of the {@linkplain Flag flag} to add
     * 
     * @return ResponseEntity with Integer count and HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping(path = "/{username}/cart", params = "id")
    public ResponseEntity<Integer> addFlagCart(@PathVariable String username, @RequestParam int id) {
        LOG.info("POST /accounts/" + username + "/cart?id=" + id);

        try {
            return new ResponseEntity<Integer>(accountDao.addFlagCart(username, id),HttpStatus.OK);
                
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Updates the {@linkplain Account account} with the provided {@linkplain Account account} object, if it exists
     * 
     * @param account The {@link Account account} to update
     * 
     * @return ResponseEntity with updated {@link Account account} object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Account> updateAccount(@RequestBody Account account) {
        LOG.info("PUT /accounts " + account);

        try {
            Account newAccount = accountDao.updateAccount(account);
            if (newAccount != null)
                return new ResponseEntity<Account>(newAccount,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes an {@linkplain Account account} with the given username
     * 
     * @param username The username of the {@link Account account} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{username}")
    public ResponseEntity<Account> deleteAccount(@PathVariable String username) {
        LOG.info("DELETE /accounts/" + username);

        try {
            if ( accountDao.deleteAccount(username) )
                return new ResponseEntity<Account>(HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Adds the id of a {@linkplain Flag flag} from an {@linkplain Account account}'s shopping cart
     * 
     * @param username The username used to remove from the cart of the {@link Account account}
     * @param id The id of the {@linkplain Flag flag} to remove
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping(path = "/{username}/cart", params = "id")
    public ResponseEntity<Integer> deleteFlagCart(@PathVariable String username, @RequestParam int id) {
        LOG.info("DELETE /accounts/" + username + "/cart?id=" + id);

        try {
            if (accountDao.deleteFlagCart(username, id))
                return new ResponseEntity<Integer>(HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
