package com.fashionstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.fashionstore.dao.CartDAO;
import com.fashionstore.model.Cart;
import com.fashionstore.util.DBConnection;

public class CartDAOImpl implements CartDAO {

    private static final String INSERT_CART_SQL = """
            INSERT INTO cart (
                user_id
            ) VALUES (?)
            """;

    private static final String GET_CART_BY_ID_SQL = """
            SELECT * FROM cart
            WHERE cart_id = ?
            """;

    private static final String GET_CART_BY_USER_ID_SQL = """
            SELECT * FROM cart
            WHERE user_id = ?
            """;

    private static final String CLEAR_CART_SQL = """
            DELETE FROM cart_items
            WHERE cart_id = ?
            """;

    private static final String DELETE_CART_SQL = """
            DELETE FROM cart
            WHERE cart_id = ?
            """;

    private static final String DELETE_CART_BY_USER_ID_SQL = """
            DELETE FROM cart
            WHERE user_id = ?
            """;

    @Override
    public int createCart(int userId) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     INSERT_CART_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, userId);

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public Cart getCartById(int cartId) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_CART_BY_ID_SQL)) {

            preparedStatement.setInt(1, cartId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToCart(resultSet);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Cart getCartByUserId(int userId) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_CART_BY_USER_ID_SQL)) {

            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToCart(resultSet);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean clearCart(int cartId) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CLEAR_CART_SQL)) {

            preparedStatement.setInt(1, cartId);

            preparedStatement.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deleteCart(int cartId) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CART_SQL)) {

            preparedStatement.setInt(1, cartId);

            return preparedStatement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deleteCartByUserId(int userId) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CART_BY_USER_ID_SQL)) {

            preparedStatement.setInt(1, userId);

            return preparedStatement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private Cart mapResultSetToCart(ResultSet resultSet) throws Exception {
        Cart cart = new Cart();

        cart.setCartId(resultSet.getInt("cart_id"));
        cart.setUserId(resultSet.getInt("user_id"));
        cart.setCreatedAt(resultSet.getTimestamp("created_at"));

        return cart;
    }
}