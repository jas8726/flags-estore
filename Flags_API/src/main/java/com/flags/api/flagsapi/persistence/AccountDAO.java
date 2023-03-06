package com.flags.api.flagsapi.persistence;

import java.io.IOException;
import com.flags.api.flagsapi.model.Account;

/**
 * Defines the interface for Account object persistence
 */
public interface AccountDAO {
    /**
     * Retrieves all {@linkplain Account accounts}
     * 
     * @return An array of {@link Account accounts} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Account[] getAccounts() throws IOException;

    /**
     * Retrieves an {@linkplain Account account} with the given username
     * 
     * @param username The username of the {@link Account account} to get
     * 
     * @return an {@link Account account} object with the matching username
     * <br>
     * null if no {@link Account account} with a matching username is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Account getAccount(String username) throws IOException;

        /**
     * Logins to an {@linkplain Account account} with the given username and password
     * 
     * @param username The username of the {@link Account account} to login with
     * @param password The password of the {@link Account account} to login with
     * 
     * @return an {@link Account account} object with the matching username and password
     * <br>
     * null if no {@link Account account} with a matching username and password is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Account loginAccount(String username, String password) throws IOException;

    /**
     * Creates and saves an {@linkplain Account account}
     * 
     * @param account {@linkplain Account account} object to be created and saved
     * <br>
     *
     * @return new {@link Account account} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    Account createAccount(Account account) throws IOException;

    /**
     * Updates and saves an {@linkplain Account account}
     * 
     * @param {@link Account account} object to be updated and saved
     * 
     * @return updated {@link Account account} if successful, null if
     * {@link Account account} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Account updateAccount(Account account) throws IOException;

    /**
     * Deletes an {@linkplain Account account} with the given username
     * 
     * @param username The username of the {@link Account account}
     * 
     * @return true if the {@link Account account} was deleted
     * <br>
     * false if account with the given username does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteAccount(String username) throws IOException;
}
