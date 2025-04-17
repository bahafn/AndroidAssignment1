package com.example.androidassignment1.DataAccess.Item;

import android.content.SharedPreferences;

import com.example.androidassignment1.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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
    public List<Item> getAllItems() {
        String itemsString = prefs.getString(ITEMS, null);
        if (itemsString == null)
            return createItems();

        Type listType = new TypeToken<List<Item>>() {}.getType();
        return gson.fromJson(itemsString, listType);
    }

    @Override
    public List<Item> getItemsByName(String name) {
        return getAllItems().stream().filter(item -> item.getName().equals(name)).collect(Collectors.toList());
    }

    @Override
    public Item getItemById(int id) {
        return getAllItems().stream().filter(item -> item.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void saveItem(int itemIndex, Item item) {
        List<Item> items = getAllItems();
        items.set(itemIndex, item);
        saveItems(items);
    }

    /** Creates default items and adds them to SharedPreferences. */
    private List<Item> createItems() {
        List<Item> items = new ArrayList<>();
        items.add(new Item(1, "Item1", "Item1 description", R.drawable.ic_launcher_background, 10, 5));
        items.add(new Item(2, "Item2", "Item2 description", R.drawable.logo, 20, 15));
        items.add(new Item(3, "Item3", "Item3 description", R.drawable.ic_launcher_background, 30, 2));
        items.add(new Item(4, "Item4", "Item4 description", R.drawable.ic_launcher_background, 5, 50));
        items.add(new Item(5, "Item5", "Item5 description", R.drawable.ic_launcher_background, 100, 10));

        saveItems(items);

        return items;
    }

    private void saveItems(List<Item> items) {
        String itemsString = gson.toJson(items);
        editor.putString(ITEMS, itemsString);
        editor.apply();
    }
}
