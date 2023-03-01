package com.flags.api.flagsapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the Flag class
 * 
 * @author SWEN Faculty
 */
@Tag("Model-tier")
public class FlagTest {
    @Test
    public void testCtor() {
        // Setup
        int expected_id = 99;
        String expected_name = "Wi-Fire";
        int expected_price = 1;
        int expected_quantity = 1;

        // Invoke
        Flag flag = new Flag(expected_id, expected_name, expected_price, expected_quantity);

        // Analyze
        assertEquals(expected_id,flag.getId());
        assertEquals(expected_name,flag.getName());
    }

    @Test
    public void testName() {
        // Setup
        int id = 99;
        String name = "Wi-Fire";
        int price = 1;
        int quantity = 1;
        Flag flag = new Flag(id, name, price, quantity);

        String expected_name = "Galactic Agent";

        // Invoke
        flag.setName(expected_name);

        // Analyze
        assertEquals(expected_name,flag.getName());
    }

    @Test
    public void testToString() {
        // Setup
        int id = 99;
        String name = "Wi-Fire";
        int price = 1;
        int quantity = 1;
        String expected_string = String.format(Flag.STRING_FORMAT,id,name);
        Flag flag = new Flag(id, name, price, quantity);

        // Invoke
        String actual_string = flag.toString();

        // Analyze
        assertEquals(expected_string,actual_string);
    }
}