package com.example.androidassignment1.DataAccess.Item;

import java.util.List;

public interface iItemDA {
    List<Item> getAllAvailableItems();
    List<Item> searchItems(String name, String category, SortBy sortBy, boolean showUnavailable);
    Item getItemById(int id);
    void saveItem(int itemIndex, Item item);
    List<String> getCategories();
}
