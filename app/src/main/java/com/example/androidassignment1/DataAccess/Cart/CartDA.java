package com.example.androidassignment1.DataAccess.Cart;

import android.content.SharedPreferences;

import com.example.androidassignment1.DataAccess.Item.Item;
import com.example.androidassignment1.DataAccess.Item.ItemDAFactory;
import com.example.androidassignment1.DataAccess.Item.iItemDA;
import com.google.gson.Gson;

import java.util.List;

public class CartDA implements iCartDA {
    private static final String CART = "CART";

    private final SharedPreferences prefs;
    private final SharedPreferences.Editor editor;
    private final Gson gson;

    public CartDA(SharedPreferences prefs) {
        this.prefs = prefs;
        editor = prefs.edit();
        gson = new Gson();
    }

    public Cart getCart() {
        String cartJson = prefs.getString(CART, null);
        if (cartJson == null)
            return new Cart();

        return gson.fromJson(cartJson, Cart.class);
    }

    public void saveCart(Cart cart) {
        String cartJson = gson.toJson(cart);
        editor.putString(CART, cartJson);
        editor.apply();
    }

    public void addToCart(Item item) {
        Cart cart = getCart();
        cart.addItem(item);
        saveCart(cart);
    }

    public void checkout() throws InsufficientStockException {
        iItemDA itemDA = ItemDAFactory.getInstance(prefs);
        List<Item> items = itemDA.getAllItems();

        Cart cart = getCart();
        for (Item cartItem : cart.getItems())
            checkout(cart, items, cartItem, false);

        cart.getItems().clear();
        saveCart(cart);
        itemDA.saveItems(items);
    }

    public void checkout(Item cartItem) throws InsufficientStockException {
        iItemDA itemDA = ItemDAFactory.getInstance(prefs);
        List<Item> items = itemDA.getAllItems();

        Cart cart = getCart();
        checkout(cart, items, cartItem, true);
        itemDA.saveItems(items);
    }

    public void removeItem(int itemIndex) {
        Cart cart = getCart();
        cart.removeItem(itemIndex);
        saveCart(cart);
    }

    private void checkout(Cart cart, List<Item> items, Item cartItem, boolean changeCart) throws InsufficientStockException {
        Item item = items.get(cartItem.getId() - 1);

        if (item.getAmount() < cartItem.getAmount())
            throw new InsufficientStockException(item, cartItem.getAmount());

        item.decreaseAmount(cartItem.getAmount());

        if (changeCart) {
            cart.removeItem(cartItem);
            saveCart(cart);
        }
    }
}
