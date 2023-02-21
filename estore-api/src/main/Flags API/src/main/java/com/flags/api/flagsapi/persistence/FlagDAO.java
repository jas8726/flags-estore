package com.flags.api.flagsapi.persistence;

import java.io.IOException;
import com.flags.api.flagsapi.model.Flag;

/**
 * Defines the interface for Flag object persistence
 * 
 * @author SWEN Faculty
 */
public interface FlagDAO {
    /**
     * Retrieves all {@linkplain Flag flags}
     * 
     * @return An array of {@link Flag flag} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Flag[] getFlages() throws IOException;

    /**
     * Finds all {@linkplain Flag flags} whose name contains the given text
     * 
     * @param containsText The text to match against
     * 
     * @return An array of {@link Flag flags} whose nemes contains the given text, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Flag[] findFlages(String containsText) throws IOException;

    /**
     * Retrieves a {@linkplain Flag flag} with the given id
     * 
     * @param id The id of the {@link Flag flag} to get
     * 
     * @return a {@link Flag flag} object with the matching id
     * <br>
     * null if no {@link Flag flag} with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Flag getFlag(int id) throws IOException;

    /**
     * Creates and saves a {@linkplain Flag flag}
     * 
     * @param flag {@linkplain Flag flag} object to be created and saved
     * <br>
     * The id of the flag object is ignored and a new uniqe id is assigned
     *
     * @return new {@link Flag flag} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    Flag createFlag(Flag flag) throws IOException;

    /**
     * Updates and saves a {@linkplain Flag flag}
     * 
     * @param {@link Flag flag} object to be updated and saved
     * 
     * @return updated {@link Flag flag} if successful, null if
     * {@link Flag flag} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Flag updateFlag(Flag flag) throws IOException;

    /**
     * Deletes a {@linkplain Flag flag} with the given id
     * 
     * @param id The id of the {@link Flag flag}
     * 
     * @return true if the {@link Flag flag} was deleted
     * <br>
     * false if flag with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteFlag(int id) throws IOException;
}
