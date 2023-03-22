package com.flags.api.flagsapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.flags.api.flagsapi.model.Account;
import com.flags.api.flagsapi.model.ShoppingCart;

/**
 * Implements the functionality for JSON file-based peristance for Accounts
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 */
@Component
public class AccountFileDAO implements AccountDAO {
    private static final Logger LOG = Logger.getLogger(AccountFileDAO.class.getName());
    Map<String,Account> accounts;   // Provides a local cache of the account objects
                                // so that we don't need to read from the file
                                // each time
    private ObjectMapper objectMapper;  // Provides conversion between Account
                                        // objects and JSON text format written
                                        // to the file
    private String filename;    // Filename to read from and write to

    /**
     * Creates a Account File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public AccountFileDAO(@Value("${accounts.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the accounts from the file
    }

    /**
     * Generates an array of {@linkplain Account accounts} from the tree map
     * used to be private, changing to public for testing
     * @return  The array of {@link Account accounts}, may be empty
     */
    public Account[] getAccountsArray() {
        ArrayList<Account> accountArrayList = new ArrayList<>();

        for (Account account : accounts.values()) {
            accountArrayList.add(account);
        }

        Account[] accountArray = new Account[accountArrayList.size()];
        accountArrayList.toArray(accountArray);
        return accountArray;
    }

    /**
     * Saves the {@linkplain Account accounts} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Account accounts} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    boolean save() throws IOException {
        Account[] accountArray = getAccountsArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),accountArray);
        return true;
    }

    /**
     * Loads {@linkplain Account accounts} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    boolean load() throws IOException {
        accounts = new TreeMap<>();

        // Deserializes the JSON objects from the file into an array of accounts
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Account[] accountArray = objectMapper.readValue(new File(filename),Account[].class);

        // Add each account to the tree map
        for (Account account : accountArray) {
            accounts.put(account.getUsername(),account);
        }

        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Account[] getAccounts() {
        synchronized(accounts) {
            return getAccountsArray();
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Account getAccount(String username) {
        synchronized(accounts) {
            if (accounts.containsKey(username))
                return accounts.get(username);
            else
                return null;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Account loginAccount(String username, String password) {
        synchronized(accounts) {
            if (accounts.containsKey(username)) {
                if (accounts.get(username).getPassword().equals(password)) {
                    return accounts.get(username);
                }
            }
            
            return null;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Account createAccount(Account account) throws IOException {
        synchronized(accounts) {
            // We create a new account object because the username field is immutable
            Account newAccount = new Account(account.getUsername(),account.getPassword());
            accounts.put(newAccount.getUsername(),newAccount);
            save(); // may throw an IOException
            return newAccount;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Account updateAccount(Account account) throws IOException {
        synchronized(accounts) {
            if (accounts.containsKey(account.getUsername()) == false)
                return null;  // account does not exist

            accounts.put(account.getUsername(),account);
            save(); // may throw an IOException
            return account;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public boolean deleteAccount(String username) throws IOException {
        synchronized(accounts) {
            if (accounts.containsKey(username)) {
                accounts.remove(username);
                return save();
            }
            else
                return false;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public ShoppingCart getCart(String username) throws IOException {
        synchronized(accounts) {
            if (accounts.containsKey(username))
                return accounts.get(username).getShoppingCart();
            else
                return null;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public int getCountCart(String username, int id) throws IOException {
        synchronized(accounts) {
            Account account = getAccount(username);
            ShoppingCart cart = account.getShoppingCart();

            if (!cart.getCartMap().containsKey(id)) {
                return 0;
            }

            return cart.getCartMap().get(id);
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public int addFlagCart(String username, int id) throws IOException {
        synchronized(accounts) {
            Account account = getAccount(username);
            ShoppingCart cart = account.getShoppingCart();

            if (cart.getCartMap().containsKey(id)) {
                int newCount = cart.getCartMap().get(id) + 1;
                cart.getCartMap().replace(id, newCount);
                save();
                return newCount;
            }

            cart.getCartMap().put(id, 1);
            save();
            return 1;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public boolean deleteFlagCart(String username, int id) throws IOException {
        synchronized(accounts) {
            Account account = getAccount(username);
            ShoppingCart cart = account.getShoppingCart();

            if (!cart.getCartMap().containsKey(id)) {
                return false;
            }

            if (cart.getCartMap().get(id) == 1) {
                cart.getCartMap().remove(id);
                return save();
            }

            int newCount = cart.getCartMap().get(id) - 1;
            cart.getCartMap().replace(id, newCount);
            return save();
        }
    }
}
