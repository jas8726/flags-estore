package com.flags.api.flagsapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.beans.Transient;
import java.io.IOException;

import com.flags.api.flagsapi.persistence.FlagDAO;
import com.flags.api.flagsapi.model.Flag;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class FlagControllerTest {

    private FlagController flagController;
    private FlagDAO mockFlagDAO;

    /*
     * create mock FlagDAO class for testing
     */
    @BeforeEach
    public void setupFlagController() {
        mockFlagDAO = mock(FlagDAO.class);
        flagController = new FlagController(mockFlagDAO);
    }

    /*
     * testing getFlag function when it is found, check status
     */
    @Test 
    public void testGetFlagFound() throws IOException {  
        
        Flag flag = new Flag(99,"Hungary", 10, 1);
        when(mockFlagDAO.getFlag(flag.getId())).thenReturn(flag);
        ResponseEntity<Flag> response = flagController.getFlag(flag.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(flag, response.getBody());
    }

    /*
     * testing getFlag function when it is not found, check status
     */
    @Test
    public void testGetFlagNotFound() throws Exception { 
        
        int flagId = 99; 
        when(mockFlagDAO.getFlag(flagId)).thenReturn(null);
        ResponseEntity<Flag> response = flagController.getFlag(flagId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /*
     * check exception with getFlag function (internal_server_error)
     */
    @Test
    public void testGetFlagHandleException() throws Exception { 
        
        int flagId = 99;
        doThrow(new IOException()).when(mockFlagDAO).getFlag(flagId);
        ResponseEntity<Flag> response = flagController.getFlag(flagId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    /*
     * test createFlag function, check status
     */
    @Test
    public void testCreateFlag() throws IOException {  
       
        Flag flag = new Flag(98,"Mexico", 10, 3);
        when(mockFlagDAO.createFlag(flag)).thenReturn(flag);
        ResponseEntity<Flag> response = flagController.createFlag(flag);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(flag, response.getBody());
    }

    /*
     * test createFlag function when their is a conflict, status CONFLICT
     */
    @Test
    public void testCreateFlagFail() throws IOException {  

        Flag flag = new Flag(98,"Mexico", 10, 3); 
        when(mockFlagDAO.createFlag(flag)).thenReturn(null);
        ResponseEntity<Flag> response = flagController.createFlag(flag);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    /*
     * check exception with createFlag function (internal_server_error)
     */
    @Test
    public void testCreateFlagHandle() throws IOException {  
    
        Flag flag = new Flag(97,"UK", 15, 1);
        doThrow(new IOException()).when(mockFlagDAO).createFlag(flag);
        ResponseEntity<Flag> response = flagController.createFlag(flag);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    /*
     * test updateFlag, check status
     */
    @Test
    public void testUpdateFlag() throws IOException { 
      
        Flag flag = new Flag(97,"UK", 15, 1);
        when(mockFlagDAO.updateFlag(flag)).thenReturn(flag);
        ResponseEntity<Flag> response = flagController.updateFlag(flag);
        flag.setName("Germany");

        response = flagController.updateFlag(flag);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(flag,response.getBody());
    }

    /*
     * test updateFlag when fails, status NOT_FOUND
     */
    @Test
    public void testUpdateFlagFailed() throws IOException { 
       
        Flag flag = new Flag(97,"UK", 15, 1);
        when(mockFlagDAO.updateFlag(flag)).thenReturn(null);
        ResponseEntity<Flag> response = flagController.updateFlag(flag);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /*
     * check exception with updateFlag function (internal_server_error)
     */
    @Test
    public void testUpdateFlagHandle() throws IOException { 
   
        Flag flag = new Flag(96,"Germany", 50, 2);
        doThrow(new IOException()).when(mockFlagDAO).updateFlag(flag);
        ResponseEntity<Flag> response = flagController.updateFlag(flag);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    /*
     * test getFlags function, check status
     */
    @Test
    public void testGetFlags() throws IOException { 

        Flag[] flags = new Flag[2];
        flags[0] = new Flag(99,"Hungary", 10, 1);
        flags[1] = new Flag(100,"Cambodia", 30, 10);
        when(mockFlagDAO.getFlags()).thenReturn(flags);
        ResponseEntity<Flag[]> response = flagController.getFlags();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(flags, response.getBody());
    }

    /*
     * check exception with getFlags function (internal_server_error)
     */
    @Test
    public void testGetFlagsHandle() throws IOException { 
       
        doThrow(new IOException()).when(mockFlagDAO).getFlags();
        ResponseEntity<Flag[]> response = flagController.getFlags();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    /*
     * test searchFlags function, check status
     */
    @Test
    public void testSearchFlags() throws IOException { 
    
        String search = "r";
        Flag[] flags = new Flag[2];
        flags[0] = new Flag(99,"Hungary", 10, 1);
        flags[1] = new Flag(100,"Germany", 30, 10);

        when(mockFlagDAO.findFlags(search)).thenReturn(flags);
        ResponseEntity<Flag[]> response = flagController.searchFlags(search);

  
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(flags, response.getBody());
    }

    /*
     * check exception with searchFlags function (internal_server_error)
     */
    @Test
    public void testSearchFlagsHandle() throws IOException { 
   
        String search = "an";
        doThrow(new IOException()).when(mockFlagDAO).findFlags(search);
        ResponseEntity<Flag[]> response = flagController.searchFlags(search);


        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    /*
     * tests deleteFlag function, check status
     */
    @Test
    public void testDeleteFlag() throws IOException { 
    
        int flagId = 99;
        when(mockFlagDAO.deleteFlag(flagId)).thenReturn(true);
        ResponseEntity<Flag> response = flagController.deleteFlag(flagId);

        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    /*
     * tests deleteFlag function when flag isn't found, status NOT_FOUND
     */
    @Test
    public void testDeleteFlagNotFound() throws IOException { 
  
        int flagId = 99;
        when(mockFlagDAO.deleteFlag(flagId)).thenReturn(false);
        ResponseEntity<Flag> response = flagController.deleteFlag(flagId);

        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    /*
     * check exception with deleteFlag function (internal_server_error)
     */
    @Test
    public void testDeleteFlagHandle() throws IOException { 
   
        int flagId = 99;
        doThrow(new IOException()).when(mockFlagDAO).deleteFlag(flagId);
        ResponseEntity<Flag> response = flagController.deleteFlag(flagId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

}
