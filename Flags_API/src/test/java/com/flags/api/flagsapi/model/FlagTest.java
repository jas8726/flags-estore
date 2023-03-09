package com.flags.api.flagsapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model")
public class FlagTest {
    @Test
    public void newFlagTest() {
        int id = 99;
        String name = "Hungary";
        int price = 10;
        int quantity = 1;

        Flag flag = new Flag(id, name, price, quantity);

        assertNotNull(flag);

        assertEquals(id,flag.getId());
        assertEquals(name,flag.getName());
        assertEquals(quantity, flag.getQuantity());
        assertEquals(price, flag.getPrice());
    }

    @Test
    public void testFlagName() {
        int id = 99;
        String name = "Hungary";
        int price = 10;
        int quantity = 1;

        Flag flag = new Flag(id, name, price, quantity);

        String newName = "Poland";

        flag.setName(newName);

        assertEquals(newName, flag.getName(), "Name not Changed");
    }

    @Test
    public void testToString() {
        int id = 99;
        String name = "Hungary";
        int price = 10;
        int quantity = 1;

        Flag flag = new Flag(id, name, price, quantity);
        String tostring = String.format(Flag.STRING_FORMAT, id, name, price, quantity);

        assertEquals(tostring, flag.toString(), "toString incorrect");
    }
    
}
