package com.example.androidassignment1.DataAccess.Item;

import java.util.List;

public interface iItemDA {
    List<Item> getAllItems();
    List<Item> getItemsByName(String name);
}
