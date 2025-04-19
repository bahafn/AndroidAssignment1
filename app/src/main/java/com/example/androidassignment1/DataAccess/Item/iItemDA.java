package com.example.androidassignment1.DataAccess.Item;

import java.util.List;

public interface iItemDA {
    List<Item> getAllAvailableItems();
    List<Item> getAllItems();
    List<Item> searchItems(String name, String category, SortBy sortBy, boolean showUnavailable);
    List<String> getCategories();
    void saveItems(List<Item> items);
}
