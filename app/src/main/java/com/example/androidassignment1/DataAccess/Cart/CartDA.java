package com.example.androidassignment1.DataAccess.Cart;

import android.content.SharedPreferences;

import com.example.androidassignment1.DataAccess.Item.Item;
import com.example.androidassignment1.DataAccess.Item.ItemDAFactory;
import com.example.androidassignment1.DataAccess.Item.iItemDA;
import com.google.gson.Gson;

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

    public void checkout() {
        Cart cart = getCart();
        for (int i = 0; i < cart.getItems().size(); i++)
            checkout(cart, i, false);

        cart.getItems().clear();
        saveCart(cart);
    }

    public void checkout(int itemIndex) {
        Cart cart = getCart();
        checkout(cart, itemIndex, true);
    }

    private void checkout(Cart cart, int itemIndex, boolean changeCart) {
        Item cartItem = cart.getItems().get(itemIndex);

        iItemDA itemDA = ItemDAFactory.getInstance(prefs);
        Item item = itemDA.getItemById(cartItem.getId());
        item.decreaseAmount(cartItem.getAmount());
        itemDA.saveItem(itemIndex, item);

        if (changeCart) {
            cart.removeItem(itemIndex);
            saveCart(cart);
        }
    }
}
