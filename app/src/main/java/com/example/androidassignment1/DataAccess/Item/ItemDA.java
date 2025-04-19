package com.example.androidassignment1.DataAccess.Item;

import android.content.SharedPreferences;

import com.example.androidassignment1.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public List<Item> getAllAvailableItems() {
        return getAllItems().stream().filter(item -> item.getAmount() > 0).collect(Collectors.toList());
    }

    @Override
    public List<Item> searchItems(String name, String category, SortBy sortBy, boolean showUnavailable) {
        Stream<Item> itemsStream = getAllItems().stream();

        if (!showUnavailable)
            itemsStream = itemsStream.filter(item -> item.getAmount() > 0);
        if (!name.isBlank())
            itemsStream = itemsStream.filter(item -> item.getName().toLowerCase().contains(name.toLowerCase()));
        if (category == null || !category.equals("Any"))
            itemsStream = itemsStream.filter(item -> item.getCategory().equalsIgnoreCase(category));

        List<Item> items = itemsStream.collect(Collectors.toList());

        if (sortBy == SortBy.PRICE_INCREASING)
            items.sort(Comparator.comparingDouble(Item::getPrice));
        else if (sortBy == SortBy.PRICE_DECREASING)
            items.sort(Comparator.comparingDouble(Item::getPrice).reversed());

        return items;
    }

    @Override
    public List<String> getCategories() {
        return getAllItems().stream().map(Item::getCategory).distinct().collect(Collectors.toList());
    }

    /**
     * Creates default items and adds them to SharedPreferences.
     */
    private List<Item> createItems() {
        List<Item> items = new ArrayList<>();
        items.add(new Item(1, "Item1", "Item1 description", "category1", R.drawable.ic_launcher_background, 10, 5));
        items.add(new Item(2, "Item2", "Item2 description", "category1", R.drawable.logo, 20, 15));
        items.add(new Item(3, "Item3", "Item3 description", "category2", R.drawable.ic_launcher_background, 30, 2));
        items.add(new Item(4, "Item4", "Item4 description", "category2", R.drawable.ic_launcher_background, 5, 50));
        items.add(new Item(5, "Item5", "Item5 description", "category3", R.drawable.ic_launcher_background, 100, 10));
        items.add(new Item(6, "Item6", "Item6 Description", "category3", R.drawable.ic_launcher_background, 6, 0));

        saveItems(items);

        return items;
    }

    public void saveItems(List<Item> items) {
        String itemsString = gson.toJson(items);
        editor.putString(ITEMS, itemsString);
        editor.apply();
    }
}
