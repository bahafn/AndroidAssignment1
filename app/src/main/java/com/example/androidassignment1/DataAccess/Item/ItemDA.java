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
        if (!name.isBlank()) {
            String lowerCaseSearch = name.toLowerCase();
            itemsStream = itemsStream.filter(item -> item.getName().toLowerCase().contains(lowerCaseSearch)
                    || item.getDescription().toLowerCase().contains(lowerCaseSearch));
        }
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
        items.add(new Item(1, "Dell XPS 8940", "Intel i7-11700, 16GB RAM, 512GB SSD, NVIDIA GTX 1660", "PC", R.drawable.item_image1, 1200, 10));
        items.add(new Item(2, "HP Z4 G5 Workstation", "Intel Xeon W3-2423, 32GB ECC RAM, 1TB SSD, NVIDIA RTX A2000", "Work Station", R.drawable.item_image2, 2100, 5));
        items.add(new Item(3, "Lenovo ThinkPad X1 Carbon Gen 11", "Intel i7-1355U, 16GB RAM, 1TB SSD, 14\" WUXGA Display", "Laptop", R.drawable.item_image3, 1600, 8));
        items.add(new Item(4, "Apple Mac Mini M2", "Apple M2 Chip, 8GB Unified RAM, 512GB SSD", "Mini computer", R.drawable.item_image4, 700, 12));
        items.add(new Item(5, "Acer Predator Orion 7000", "Intel i9-13900KF, 32GB RAM, 2TB SSD, NVIDIA RTX 4090", "PC", R.drawable.item_image5, 3500, 3));
        items.add(new Item(6, "Dell Precision 7865 Tower", "AMD Ryzen Threadripper PRO 5965WX, 64GB ECC RAM, 2TB NVMe SSD", "Work Station", R.drawable.item_image6, 4800, 2));
        items.add(new Item(7, "Asus ROG Zephyrus G14", "AMD Ryzen 9 7940HS, 32GB RAM, 1TB SSD, RTX 4060, 14\" QHD", "Laptop", R.drawable.item_image7, 1900, 6));
        items.add(new Item(8, "Intel NUC 13 Pro", "Intel Core i7-1360P, 16GB RAM, 512GB SSD", "Mini computer", R.drawable.item_image8, 900, 7));
        items.add(new Item(9, "MSI MEG Trident X2", "Intel i9-13900KF, 32GB RAM, 2TB SSD, NVIDIA RTX 4080", "PC", R.drawable.item_image9, 3100, 4));
        items.add(new Item(10, "HP Z2 Mini G9", "Intel i7-12700, 16GB RAM, 512GB SSD, NVIDIA T1000", "Mini computer", R.drawable.item_image10, 1250, 5));
        items.add(new Item(11, "Apple MacBook Pro 16 M3 Max", "Apple M3 Max, 36GB Unified RAM, 1TB SSD, 16\" Liquid Retina XDR", "Laptop", R.drawable.item_image11, 3900, 3));
        items.add(new Item(12, "Lenovo ThinkStation P360 Ultra", "Intel i9-12900K, 32GB ECC RAM, 1TB SSD, NVIDIA RTX A4000", "Work Station", R.drawable.item_image12, 2700, 4));
        items.add(new Item(13, "Alienware Aurora R15", "Intel i9-13900KF, 32GB RAM, 1TB SSD + 2TB HDD, RTX 4080", "PC", R.drawable.item_image13, 3200, 6));
        items.add(new Item(14, "Framework Laptop 13 (13th Gen)", "Intel i7-1360P, 16GB RAM, 1TB SSD, Modular Build", "Laptop", R.drawable.item_image14, 1450, 10));
        items.add(new Item(15, "Beelink SER6 Pro Mini PC", "AMD Ryzen 7 7735HS, 32GB RAM, 1TB SSD, Radeon 680M", "Mini computer", R.drawable.item_image15, 800, 8));

        saveItems(items);

        return items;
    }

    public void saveItems(List<Item> items) {
        String itemsString = gson.toJson(items);
        editor.putString(ITEMS, itemsString);
        editor.apply();
    }
}
