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
import com.flags.api.flagsapi.model.Flag;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Flag File DAO class
 * 
 */
@Tag("Persistence-tier")
public class FlagFileDAOTest {
    FlagFileDAO flagFileDAO;
    Flag[] testFlags;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupFlagFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testFlags = new Flag[3];
        testFlags[0] = new Flag(99,"Canada",30,12,new String[0], "");
        testFlags[1] = new Flag(100,"Mexico",30,12,new String[0], "");
        testFlags[2] = new Flag(101,"Belize",30,12,new String[0], "");

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the flag array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Flag[].class))
                .thenReturn(testFlags);
        flagFileDAO = new FlagFileDAO("doesnt_matter.txt",mockObjectMapper);
    }

    @Test
    public void testGetFlags() {
        // Invoke
        Flag[] flags = flagFileDAO.getFlags();

        // Analyze
        assertEquals(flags.length,testFlags.length);
        for (int i = 0; i < testFlags.length;++i)
            assertEquals(flags[i],testFlags[i]);
    }

    @Test
    public void testFindFlags() {
        // Invoke
        Flag[] flags = flagFileDAO.findFlags("i");

        // Analyze
        assertEquals(flags.length,2);
        assertEquals(flags[0],testFlags[1]);
        assertEquals(flags[1],testFlags[2]);
    }

    @Test
    public void testGetFlag() {
        // Invoke
        Flag flag = flagFileDAO.getFlag(99);

        // Analzye
        assertEquals(flag,testFlags[0]);
    }

    @Test
    public void testDeleteFlag() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> flagFileDAO.deleteFlag(99),
                            "Unexpected exception thrown");

        // Analzye
        assertEquals(result,true);
        // We check the internal tree map size against the length
        // of the test flags array - 1 (because of the delete)
        // Because flags attribute of FlagFileDAO is package private
        // we can access it directly
        assertEquals(flagFileDAO.flags.size(),testFlags.length-1);
    }

    @Test
    public void testCreateFlag() {
        // Setup
        Flag flag = new Flag(102,"Japan",30,12, new String[0], "");

        // Invoke
        Flag result = assertDoesNotThrow(() -> flagFileDAO.createFlag(flag),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Flag actual = flagFileDAO.getFlag(flag.getId());
        assertEquals(actual.getId(),flag.getId());
        assertEquals(actual.getName(),flag.getName());
    }

    @Test
    public void testUpdateFlag() {
        // Setup
        Flag flag = new Flag(101,"Italy",25,20,new String[0], "");

        // Invoke
        Flag result = assertDoesNotThrow(() -> flagFileDAO.updateFlag(flag),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Flag actual = flagFileDAO.getFlag(flag.getId());
        assertEquals(actual,flag);
    }

    @Test
    public void testSaveException() throws IOException{
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class),any(Flag[].class));

        Flag flag = new Flag(102,"Thailand",20,10, new String[0], "");

        assertThrows(IOException.class,
                        () -> flagFileDAO.createFlag(flag),
                        "IOException not thrown");
    }

    @Test
    public void testGetFlagNotFound() {
        // Invoke
        Flag flag = flagFileDAO.getFlag(98);

        // Analyze
        assertEquals(flag,null);
    }

    @Test
    public void testDeleteFlagNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> flagFileDAO.deleteFlag(98),
                                                "Unexpected exception thrown");

        // Analyze
        assertEquals(result,false);
        assertEquals(flagFileDAO.flags.size(),testFlags.length);
    }

    @Test
    public void testUpdateFlagNotFound() {
        // Setup
        Flag flag = new Flag(98,"Uganda",1,1,new String[0], "");

        // Invoke
        Flag result = assertDoesNotThrow(() -> flagFileDAO.updateFlag(flag),
                                                "Unexpected exception thrown");

        // Analyze
        assertNull(result);
    }

    @Test
    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        // We want to simulate with a Mock Object Mapper that an
        // exception was raised during JSON object deseerialization
        // into Java objects
        // When the Mock Object Mapper readValue method is called
        // from the FlagFileDAO load method, an IOException is
        // raised
        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"),Flag[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new FlagFileDAO("doesnt_matter.txt",mockObjectMapper),
                        "IOException not thrown");
    }
}
