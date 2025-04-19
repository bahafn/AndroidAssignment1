package com.example.androidassignment1.DataAccess.Cart;

import com.example.androidassignment1.DataAccess.Item.Item;

import java.util.Locale;

public class InsufficientStockException extends Exception {
    private final Item item;

    public InsufficientStockException(Item item, int orderedAmount) {
        super(String.format(Locale.getDefault(),
                "Can't purchase %d of %s. Only %d available.", orderedAmount, item.getName(), item.getAmount()));
        this.item = item;
    }

    public Item getItem() {
        return item;
    }
}
