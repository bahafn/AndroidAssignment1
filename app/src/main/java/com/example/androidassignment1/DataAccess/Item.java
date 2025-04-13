package com.example.androidassignment1.DataAccess;

public class Item {
    private String name;
    private float price;
    private int amount;

    public Item(String name, float price, int amount) {
        setName(name);
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

    public String toString() {
        return getName();
    }
}
