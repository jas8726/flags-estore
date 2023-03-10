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

import com.flags.api.flagsapi.persistence.FlagDAO;
import com.flags.api.flagsapi.model.Flag;

/**
 * Handles the REST API requests for the Flag resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author SWEN Faculty
 */

@RestController
@RequestMapping("flags")
public class FlagController {
    private static final Logger LOG = Logger.getLogger(FlagController.class.getName());
    private FlagDAO flagDao;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param flagDao The {@link FlagDAO Flag Data Access Object} to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public FlagController(FlagDAO flagDao) {
        this.flagDao = flagDao;
    }

    /**
     * Responds to the GET request for a {@linkplain Flag flag} for the given id
     * 
     * @param id The id used to locate the {@link Flag flag}
     * 
     * @return ResponseEntity with {@link Flag flag} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Flag> getFlag(@PathVariable int id) {
        LOG.info("GET /flags/" + id);
        try {
            Flag flag = flagDao.getFlag(id);
            if (flag != null)
                return new ResponseEntity<Flag>(flag,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Flag flags}
     * 
     * @return ResponseEntity with array of {@link Flag flag} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<Flag[]> getFlags() {
        LOG.info("GET /flags");

        try {
            Flag[] flagArray = flagDao.getFlags();
            if (flagArray != null)
                return new ResponseEntity<Flag[]>(flagArray,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Flag flags} whose name contains
     * the text in name
     * 
     * @param name The name parameter which contains the text used to find the {@link Flag flags}
     * 
     * @return ResponseEntity with array of {@link Flag flag} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * <p>
     * Example: Find all flags that contain the text "ma"
     * GET http://localhost:8080/flags/?name=ma
     */
    @GetMapping("/")
    public ResponseEntity<Flag[]> searchFlags(@RequestParam String name) {
        LOG.info("GET /flags/?name="+name);

        try {
            Flag[] flag = flagDao.findFlags(name);
            if (flag != null)
                return new ResponseEntity<Flag[]>(flag,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain Flag flag} with the provided flag object
     * 
     * @param flag - The {@link Flag flag} to create
     * 
     * @return ResponseEntity with created {@link Flag flag} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link Flag flag} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Flag> createFlag(@RequestBody Flag flag) {
        LOG.info("POST /flags " + flag);

        try {
            if (flagDao.findFlags(flag.getName()).length > 0) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            
            Flag newFlag = flagDao.createFlag( flag );
            return new ResponseEntity<Flag>(newFlag,HttpStatus.CREATED);
                
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Updates the {@linkplain Flag flag} with the provided {@linkplain Flag flag} object, if it exists
     * 
     * @param flag The {@link Flag flag} to update
     * 
     * @return ResponseEntity with updated {@link Flag flag} object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Flag> updateFlag(@RequestBody Flag flag) {
        LOG.info("PUT /flags " + flag);

        try {
            Flag newFlag = flagDao.updateFlag(flag);
            if (newFlag != null)
                return new ResponseEntity<Flag>(newFlag,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain Flag flag} with the given id
     * 
     * @param id The id of the {@link Flag flag} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Flag> deleteFlag(@PathVariable int id) {
        LOG.info("DELETE /flags/" + id);

        try {
            if ( flagDao.deleteFlag(id) )
                return new ResponseEntity<Flag>(HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
