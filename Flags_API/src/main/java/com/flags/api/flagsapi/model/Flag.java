package com.flags.api.flagsapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents a Flag entity
 * 
 * @author SWEN Faculty
 */
public class Flag {
    private static final Logger LOG = Logger.getLogger(Flag.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "Flag [id=%d, name=%s, price=%d, quantity=%d, tags=%s, image=%s]";

    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("price") private int price;
    @JsonProperty("quantity") private int quantity;
    @JsonProperty("tags") private String[] tags;
    @JsonProperty("image") private String image;

    /**
     * Create a flag with the given id and name
     * @param id The id of the flag
     * @param name The name of the flag
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Flag(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("price") int price, @JsonProperty("quantity") int quantity, @JsonProperty("tags") String[] tags, @JsonProperty("image") String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.tags = tags;
        this.image = image;
    }

    /**
     * Retrieves the id of the flag
     * @return The id of the flag
     */
    public int getId() {return id;}

    /**
     * Retrieves the price of the flag
     * @return The price of the flag
     */
    public int getPrice() {return price;}

    /**
     * Retrieves the quantity of the flag
     * @return The quantity of the flag
     */
    public int getQuantity() {return quantity;}

    /**
     * Sets the name of the flag - necessary for JSON object to Java object deserialization
     * @param name The name of the flag
     */
    public void setName(String name) {this.name = name;}

    /**
     * Retrieves the name of the flag
     * @return The name of the flag
     */
    public String getName() {return name;}

    /**
     * Retrieves the tags of the flag
     * @return A flag's tags, as a string array
     */
    public String[] getTags() {return tags;}

    /**
     * Retrieves the image of the flag
     * @return The URL image of the flag, as string
     */
    public String getImage() {return image;}

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id,name,price,quantity,tags,image);
    }
}