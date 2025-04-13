package com.example.androidassignment1.DataAccess;

import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ItemDA implements iItemDA {
    private static final String ITEMS = "ITEMS";

    private final SharedPreferences prefs;
    private final SharedPreferences.Editor editor;
    private final Gson gson;

    public ItemDA(SharedPreferences prefs) {
        this.prefs = prefs;
        editor = prefs.edit();
        gson = new Gson();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Item> getAllItems() {
        String itemsString = prefs.getString(ITEMS, null);
        if (itemsString == null)
            return createItems();

        return gson.fromJson(itemsString, List.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Item> getItemsByName(String name) {
        String itemsString = prefs.getString(ITEMS, null);
        if (itemsString == null)
            return createItems();

        List<Item> items = gson.fromJson(itemsString, List.class);
        return items.stream().filter(item -> item.getName().equals(name)).collect(Collectors.toList());
    }

    /** Creates default items and adds them to SharedPreferences. */
    private List<Item> createItems() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("Item1", 10, 5));
        items.add(new Item("Item2", 20, 15));
        items.add(new Item("Item3", 30, 2));
        items.add(new Item("Item4", 5, 50));
        items.add(new Item("Item5", 100, 10));

        String itemsString = gson.toJson(items);
        editor.putString(ITEMS, itemsString);

        return items;
    }
}
