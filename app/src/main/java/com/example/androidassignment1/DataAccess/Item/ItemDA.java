package com.example.androidassignment1.DataAccess.Item;

import android.content.SharedPreferences;

import com.example.androidassignment1.R;
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
    public List<Item> getItemsByName(String name) {
        List<Item> items = getAllItems();
        return items.stream().filter(item -> item.getName().equals(name)).collect(Collectors.toList());
    }

    /** Creates default items and adds them to SharedPreferences. */
    private List<Item> createItems() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("Item1", "Item1 description", R.drawable.ic_launcher_background, 10, 5));
        items.add(new Item("Item2", "Item2 description", R.drawable.logo, 20, 15));
        items.add(new Item("Item3", "Item3 description", R.drawable.ic_launcher_background, 30, 2));
        items.add(new Item("Item4", "Item4 description", R.drawable.ic_launcher_background, 5, 50));
        items.add(new Item("Item5", "Item5 description", R.drawable.ic_launcher_background, 100, 10));

        String itemsString = gson.toJson(items);
        editor.putString(ITEMS, itemsString);

        return items;
    }
}
