package com.flags.api.flagsapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.flags.api.flagsapi.persistence.FlagDAO;
import com.flags.api.flagsapi.model.Flag;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test the Flag Controller class
 * 
 * @author SWEN Faculty
 */
@Tag("Controller-tier")
public class FlagControllerTest {
    private FlagController flagController;
    private FlagDAO mockFlagDAO;

    /**
     * Before each test, create a new FlagController object and inject
     * a mock Flag DAO
     */
    @BeforeEach
    public void setupFlagController() {
        mockFlagDAO = mock(FlagDAO.class);
        flagController = new FlagController(mockFlagDAO);
    }

    @Test
    public void testGetFlag() throws IOException {  // getFlag may throw IOException
        // Setup
        Flag flag = new Flag(99,"Galactic Agent", 100, 2);
        // When the same id is passed in, our mock Flag DAO will return the Flag object
        when(mockFlagDAO.getFlag(flag.getId())).thenReturn(flag);

        // Invoke
        ResponseEntity<Flag> response = flagController.getFlag(flag.getId());

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(flag,response.getBody());
    }

    @Test
    public void testGetFlagNotFound() throws Exception { // createFlag may throw IOException
        // Setup
        int flagId = 99;
        // When the same id is passed in, our mock Flag DAO will return null, simulating
        // no flag found
        when(mockFlagDAO.getFlag(flagId)).thenReturn(null);

        // Invoke
        ResponseEntity<Flag> response = flagController.getFlag(flagId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetFlagHandleException() throws Exception { // createFlag may throw IOException
        // Setup
        int flagId = 99;
        // When getFlag is called on the Mock Flag DAO, throw an IOException
        doThrow(new IOException()).when(mockFlagDAO).getFlag(flagId);

        // Invoke
        ResponseEntity<Flag> response = flagController.getFlag(flagId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    /*****************************************************************
     * The following tests will fail until all FlagController methods
     * are implemented.
     ****************************************************************/

    @Test
    public void testCreateFlag() throws IOException {  // createFlag may throw IOException
        // Setup
        Flag flag = new Flag(99,"Wi-Fire", 20, 11);
        // when createFlag is called, return true simulating successful
        // creation and save
        when(mockFlagDAO.createFlag(flag)).thenReturn(flag);

        // Invoke
        ResponseEntity<Flag> response = flagController.createFlag(flag);

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(flag,response.getBody());
    }

    @Test
    public void testCreateFlagFailed() throws IOException {  // createFlag may throw IOException
        // Setup
        Flag flag = new Flag(99,"Bolt", 40, 12);
        // when createFlag is called, return false simulating failed
        // creation and save
        when(mockFlagDAO.createFlag(flag)).thenReturn(null);

        // Invoke
        ResponseEntity<Flag> response = flagController.createFlag(flag);

        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    @Test
    public void testCreateFlagHandleException() throws IOException {  // createFlag may throw IOException
        // Setup
        Flag flag = new Flag(99,"Ice Gladiator", 15, 1);

        // When createFlag is called on the Mock Flag DAO, throw an IOException
        doThrow(new IOException()).when(mockFlagDAO).createFlag(flag);

        // Invoke
        ResponseEntity<Flag> response = flagController.createFlag(flag);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testUpdateFlag() throws IOException { // updateFlag may throw IOException
        // Setup
        Flag flag = new Flag(99,"Wi-Fire", 2, 5);
        // when updateFlag is called, return true simulating successful
        // update and save
        when(mockFlagDAO.updateFlag(flag)).thenReturn(flag);
        ResponseEntity<Flag> response = flagController.updateFlag(flag);
        flag.setName("Bolt");

        // Invoke
        response = flagController.updateFlag(flag);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(flag,response.getBody());
    }

    @Test
    public void testUpdateFlagFailed() throws IOException { // updateFlag may throw IOException
        // Setup
        Flag flag = new Flag(99,"Galactic Agent", 66, 9);
        // when updateFlag is called, return true simulating successful
        // update and save
        when(mockFlagDAO.updateFlag(flag)).thenReturn(null);

        // Invoke
        ResponseEntity<Flag> response = flagController.updateFlag(flag);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testUpdateFlagHandleException() throws IOException { // updateFlag may throw IOException
        // Setup
        Flag flag = new Flag(99,"Galactic Agent", 99, 12);
        // When updateFlag is called on the Mock Flag DAO, throw an IOException
        doThrow(new IOException()).when(mockFlagDAO).updateFlag(flag);

        // Invoke
        ResponseEntity<Flag> response = flagController.updateFlag(flag);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetFlags() throws IOException { // getFlags may throw IOException
        // Setup
        Flag[] flags = new Flag[2];
        flags[0] = new Flag(99,"Bolt", 30, 10);
        flags[1] = new Flag(100,"The Great Iguana", 30, 10);
        // When getFlags is called return the flags created above
        when(mockFlagDAO.getFlags()).thenReturn(flags);

        // Invoke
        ResponseEntity<Flag[]> response = flagController.getFlags();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(flags,response.getBody());
    }

    @Test
    public void testGetFlagsHandleException() throws IOException { // getFlags may throw IOException
        // Setup
        // When getFlags is called on the Mock Flag DAO, throw an IOException
        doThrow(new IOException()).when(mockFlagDAO).getFlags();

        // Invoke
        ResponseEntity<Flag[]> response = flagController.getFlags();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testSearchFlags() throws IOException { // findFlags may throw IOException
        // Setup
        String searchString = "la";
        Flag[] flags = new Flag[2];
        flags[0] = new Flag(99,"Galactic Agent", 30, 10);
        flags[1] = new Flag(100,"Ice Gladiator", 30, 10);
        // When findFlags is called with the search string, return the two
        /// flags above
        when(mockFlagDAO.findFlags(searchString)).thenReturn(flags);

        // Invoke
        ResponseEntity<Flag[]> response = flagController.searchFlags(searchString);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(flags,response.getBody());
    }

    @Test
    public void testSearchFlagsHandleException() throws IOException { // findFlags may throw IOException
        // Setup
        String searchString = "an";
        // When createFlag is called on the Mock Flag DAO, throw an IOException
        doThrow(new IOException()).when(mockFlagDAO).findFlags(searchString);

        // Invoke
        ResponseEntity<Flag[]> response = flagController.searchFlags(searchString);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testDeleteFlag() throws IOException { // deleteFlag may throw IOException
        // Setup
        int flagId = 99;
        // when deleteFlag is called return true, simulating successful deletion
        when(mockFlagDAO.deleteFlag(flagId)).thenReturn(true);

        // Invoke
        ResponseEntity<Flag> response = flagController.deleteFlag(flagId);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteFlagNotFound() throws IOException { // deleteFlag may throw IOException
        // Setup
        int flagId = 99;
        // when deleteFlag is called return false, simulating failed deletion
        when(mockFlagDAO.deleteFlag(flagId)).thenReturn(false);

        // Invoke
        ResponseEntity<Flag> response = flagController.deleteFlag(flagId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteFlagHandleException() throws IOException { // deleteFlag may throw IOException
        // Setup
        int flagId = 99;
        // When deleteFlag is called on the Mock Flag DAO, throw an IOException
        doThrow(new IOException()).when(mockFlagDAO).deleteFlag(flagId);

        // Invoke
        ResponseEntity<Flag> response = flagController.deleteFlag(flagId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}
