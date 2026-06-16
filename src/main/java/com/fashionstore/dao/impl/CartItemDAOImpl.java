package com.fashionstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.fashionstore.dao.CartItemDAO;
import com.fashionstore.model.CartItem;
import com.fashionstore.util.DBConnection;

public class CartItemDAOImpl implements CartItemDAO {

    private static final String INSERT_CART_ITEM_SQL = """
            INSERT INTO cart_items (
                cart_id, variant_id, quantity
            ) VALUES (?, ?, ?)
            """;

    private static final String GET_CART_ITEM_BY_ID_SQL = """
            SELECT * FROM cart_items
            WHERE cart_item_id = ?
            """;

    private static final String GET_CART_ITEM_BY_CART_AND_VARIANT_SQL = """
            SELECT * FROM cart_items
            WHERE cart_id = ? AND variant_id = ?
            """;

    private static final String GET_CART_ITEMS_BY_CART_ID_SQL = """
            SELECT * FROM cart_items
            WHERE cart_id = ?
            ORDER BY added_at DESC
            """;

    private static final String UPDATE_QUANTITY_SQL = """
            UPDATE cart_items
            SET quantity = ?
            WHERE cart_item_id = ?
            """;

    private static final String REMOVE_CART_ITEM_SQL = """
            DELETE FROM cart_items
            WHERE cart_item_id = ?
            """;

    private static final String REMOVE_CART_ITEM_BY_CART_AND_VARIANT_SQL = """
            DELETE FROM cart_items
            WHERE cart_id = ? AND variant_id = ?
            """;

    private static final String CLEAR_CART_ITEMS_SQL = """
            DELETE FROM cart_items
            WHERE cart_id = ?
            """;

    @Override
    public boolean addCartItem(CartItem cartItem) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CART_ITEM_SQL)) {

            preparedStatement.setInt(1, cartItem.getCartId());
            preparedStatement.setInt(2, cartItem.getVariantId());
            preparedStatement.setInt(3, cartItem.getQuantity());

            return preparedStatement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public CartItem getCartItemById(int cartItemId) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_CART_ITEM_BY_ID_SQL)) {

            preparedStatement.setInt(1, cartItemId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToCartItem(resultSet);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public CartItem getCartItemByCartAndVariant(int cartId, int variantId) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_CART_ITEM_BY_CART_AND_VARIANT_SQL)) {

            preparedStatement.setInt(1, cartId);
            preparedStatement.setInt(2, variantId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToCartItem(resultSet);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<CartItem> getCartItemsByCartId(int cartId) {
        List<CartItem> cartItems = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_CART_ITEMS_BY_CART_ID_SQL)) {

            preparedStatement.setInt(1, cartId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    cartItems.add(mapResultSetToCartItem(resultSet));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return cartItems;
    }

    @Override
    public boolean updateQuantity(int cartItemId, int quantity) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUANTITY_SQL)) {

            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, cartItemId);

            return preparedStatement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean removeCartItem(int cartItemId) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_CART_ITEM_SQL)) {

            preparedStatement.setInt(1, cartItemId);

            return preparedStatement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean removeCartItemByCartAndVariant(int cartId, int variantId) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_CART_ITEM_BY_CART_AND_VARIANT_SQL)) {

            preparedStatement.setInt(1, cartId);
            preparedStatement.setInt(2, variantId);

            return preparedStatement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean clearCartItems(int cartId) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CLEAR_CART_ITEMS_SQL)) {

            preparedStatement.setInt(1, cartId);

            preparedStatement.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private CartItem mapResultSetToCartItem(ResultSet resultSet) throws Exception {
        CartItem cartItem = new CartItem();

        cartItem.setCartItemId(resultSet.getInt("cart_item_id"));
        cartItem.setCartId(resultSet.getInt("cart_id"));
        cartItem.setVariantId(resultSet.getInt("variant_id"));
        cartItem.setQuantity(resultSet.getInt("quantity"));
        cartItem.setAddedAt(resultSet.getTimestamp("