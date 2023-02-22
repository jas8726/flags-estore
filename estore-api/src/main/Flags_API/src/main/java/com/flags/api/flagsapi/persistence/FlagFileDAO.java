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

import com.flags.api.flagsapi.model.Flag;

/**
 * Implements the functionality for JSON file-based peristance for Flags
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 * 
 * @author SWEN Faculty
 */
@Component
public class FlagFileDAO implements FlagDAO {
    private static final Logger LOG = Logger.getLogger(FlagFileDAO.class.getName());
    Map<Integer,Flag> flags;   // Provides a local cache of the flag objects
                                // so that we don't need to read from the file
                                // each time
    private ObjectMapper objectMapper;  // Provides conversion between Flag
                                        // objects and JSON text format written
                                        // to the file
    private static int nextId;  // The next Id to assign to a new flag
    private String filename;    // Filename to read from and write to

    /**
     * Creates a Flag File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public FlagFileDAO(@Value("${flags.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the flags from the file
    }

    /**
     * Generates the next id for a new {@linkplain Flag flag}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Generates an array of {@linkplain Flag flags} from the tree map
     * 
     * @return  The array of {@link Flag flags}, may be empty
     */
    private Flag[] getFlagsArray() {
        return getFlagsArray(null);
    }

    /**
     * Generates an array of {@linkplain Flag flags} from the tree map for any
     * {@linkplain Flag flags} that contains the text specified by containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain Flag flags}
     * in the tree map
     * 
     * @return  The array of {@link Flag flags}, may be empty
     */
    private Flag[] getFlagsArray(String containsText) { // if containsText == null, no filter
        ArrayList<Flag> flagArrayList = new ArrayList<>();

        for (Flag flag : flags.values()) {
            if (containsText == null || flag.getName().contains(containsText)) {
                flagArrayList.add(flag);
            }
        }

        Flag[] flagArray = new Flag[flagArrayList.size()];
        flagArrayList.toArray(flagArray);
        return flagArray;
    }

    /**
     * Saves the {@linkplain Flag flags} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Flag flags} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Flag[] flagArray = getFlagsArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),flagArray);
        return true;
    }

    /**
     * Loads {@linkplain Flag flags} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        flags = new TreeMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of flags
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Flag[] flagArray = objectMapper.readValue(new File(filename),Flag[].class);

        // Add each flag to the tree map and keep track of the greatest id
        for (Flag flag : flagArray) {
            flags.put(flag.getId(),flag);
            if (flag.getId() > nextId)
                nextId = flag.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Flag[] getFlags() {
        synchronized(flags) {
            return getFlagsArray();
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Flag[] findFlags(String containsText) {
        synchronized(flags) {
            return getFlagsArray(containsText);
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Flag getFlag(int id) {
        synchronized(flags) {
            if (flags.containsKey(id))
                return flags.get(id);
            else
                return null;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Flag createFlag(Flag flag) throws IOException {
        synchronized(flags) {
            // We create a new flag object because the id field is immutable
            // and we need to assign the next unique id
            Flag newFlag = new Flag(nextId(),flag.getName(),flag.getPrice(),flag.getQuantity());
            flags.put(newFlag.getId(),newFlag);
            save(); // may throw an IOException
            return newFlag;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Flag updateFlag(Flag flag) throws IOException {
        synchronized(flags) {
            if (flags.containsKey(flag.getId()) == false)
                return null;  // flag does not exist

            flags.put(flag.getId(),flag);
            save(); // may throw an IOException
            return flag;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public boolean deleteFlag(int id) throws IOException {
        synchronized(flags) {
            if (flags.containsKey(id)) {
                flags.remove(id);
                return save();
            }
            else
                return false;
        }
    }
}
