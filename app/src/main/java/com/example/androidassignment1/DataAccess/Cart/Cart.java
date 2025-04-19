package com.example.androidassignment1.DataAccess.Cart;

import com.example.androidassignment1.DataAccess.Item.Item;

import java.util.ArrayList;

public class Cart {
    private final ArrayList<Item> items;
    private float totalPrice;

    public Cart() {
        items = new ArrayList<>();
        totalPrice = 0;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        items.add(item);
        totalPrice += item.getPrice() * item.getAmount();
    }

    public void removeItem(int itemIndex) {
        Item item = items.get(itemIndex);
        items.remove(itemIndex);
        totalPrice -= item.getPrice() * item.getAmount();
    }

    public void removeItem(Item item) {
        if (items.remove(item))
            totalPrice -= item.getPrice() * item.getAmount();
    }

    public float getTotalPrice() {
        return totalPrice;
    }
}
