package com.example.androidassignment1.DataAccess.Item;

import androidx.annotation.NonNull;

public class Item {
    private String name;
    private String description;
    private int imageID;
    private float price;
    private int amount;

    public Item(String name, String description, int imageID, float price, int amount) {
        setName(name);
        setDescription(description);
        setImageID(imageID);
        setPrice(price);
        setAmount(amount);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    @NonNull
    @Override
    public String toString() {
        return getName();
    }
}
