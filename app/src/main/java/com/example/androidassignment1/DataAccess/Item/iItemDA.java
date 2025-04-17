package com.example.androidassignment1.DataAccess.Item;

import java.util.List;

public interface iItemDA {
    List<Item> getAllAvailableItems();
    List<Item> getItemsByName(String name);
    Item getItemById(int id);
    void saveItem(int itemIndex, Item item);
}
