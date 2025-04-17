package com.example.androidassignment1.DataAccess.Cart;

import com.example.androidassignment1.DataAccess.Item.Item;

public interface iCartDA {
    Cart getCart();
    void addToCart(Item item);
    void saveCart(Cart cart);
    /** Finalizes purchase of all items in cart. */
    void checkout();
    /** Finalizes purchase of a cart item. */
    void checkout(int itemIndex);
    void removeItem(int itemIndex);
}
