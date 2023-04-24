package com.flags.api.flagsapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model")
public class FlagTest {
    /*
     * test newFlag function, assert it creates correctly
     */
    @Test
    public void newFlagTest() {
        int id = 99;
        String name = "Hungary";
        int price = 10;
        int quantity = 1;
        String[] tags = new String[] {"red", "green"};
        String image = "";

        Flag flag = new Flag(id, name, price, quantity, tags, image);

        assertNotNull(flag);

        assertEquals(id,flag.getId());
        assertEquals(name,flag.getName());
        assertEquals(quantity, flag.getQuantity());
        assertEquals(price, flag.getPrice());
        assertEquals(tags, flag.getTags());
    }

    /*
     * test getName function, assert it return correctly
     */
    @Test
    public void testFlagName() {
        int id = 99;
        String name = "Hungary";
        int price = 10;
        int quantity = 1;
        String[] tags = new String[] {"red", "green"};
        String image = "";

        Flag flag = new Flag(id, name, price, quantity, tags, image);

        String newName = "Poland";

        flag.setName(newName);

        assertEquals(newName, flag.getName(), "Name not Changed");
    }

    /*
     * test toString override function, assert it prints correctly
     */
    @Test
    public void testToString() {
        int id = 99;
        String name = "Hungary";
        int price = 10;
        int quantity = 1;
        String[] tags = new String[] {"red", "green"};
        String image = "";

        Flag flag = new Flag(id, name, price, quantity, tags, image);
        String tostring = String.format(Flag.STRING_FORMAT, id, name, price, quantity, tags, image);

        assertEquals(tostring, flag.toString(), "toString incorrect");
    }
    
}
