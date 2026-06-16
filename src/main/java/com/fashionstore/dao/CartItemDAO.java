package com.fashionstore.dao;

import java.util.List;

import com.fashionstore.model.CartItem;

public interface CartItemDAO {

    boolean addCartItem(CartItem cartItem);

    CartItem getCartItemById(int cartItemId);

    CartItem getCartItemByCartAndVariant(int cartId, int variantId);

    List<CartItem> getCartItemsByCartId(int cartId);

    boolean updateQuantity(int cartItemId, int quantity);

    boolean removeCartItem(int cartItemId);

    boolean removeCartItemByCartAndVariant(int cartId, int variantId);

    boolean clearCartItems(int cartId);
}