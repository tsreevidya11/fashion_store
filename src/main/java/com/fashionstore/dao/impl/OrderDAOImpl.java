package com.fashionstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.fashionstore.dao.OrderDAO;
import com.fashionstore.model.Order;
import com.fashionstore.util.DBConnection;

public class OrderDAOImpl implements OrderDAO {

    private static final String INSERT_ORDER_SQL = """
            INSERT INTO orders (
                user_id, total_amount, shipping_address, shipping_city,
                shipping_state, shipping_pincode, payment_method, order_status
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;

    private static final String GET_ORDER_BY_ID_SQL = """
            SELECT * FROM orders
            WHERE order_id = ?
            """;

    private static final String GET_ORDERS_BY_USER_ID_SQL = """
            SELECT * FROM orders
            WHERE user_id = ?
            ORDER BY order_date DESC
            """;

    private static final String UPDATE_ORDER_STATUS_SQL = """
            UPDATE orders
            SET order_status = ?
            WHERE order_id = ?
            """;

    private static final String CANCEL_ORDER_SQL = """
            UPDATE orders
            SET order_status = 'CANCELLED'
            WHERE order_id = ?
            """;

    private static final String GET_ORDER_TOTAL_AMOUNT_SQL = """
            SELECT total_amount FROM orders
            WHERE order_id = ?
            """;

    private static final String GET_ALL_ORDERS_SQL = """
            SELECT * FROM orders
            ORDER BY order_date DESC
            """;

    @Override
    public int createOrder(Order order) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     INSERT_ORDER_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, order.getUserId());
            preparedStatement.setDouble(2, order.getTotalAmount());
            preparedStatement.setString(3, order.getShippingAddress());
            preparedStatement.setString(4, order.getShippingCity());
            preparedStatement.setString(5, order.getShippingState());
            preparedStatement.setString(6, order.getShippingPincode());
            preparedStatement.setString(7, order.getPaymentMethod());
            preparedStatement.setString(8, order.getOrderStatus());

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
    public Order getOrderById(int orderId) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDER_BY_ID_SQL)) {

            preparedStatement.setInt(1, orderId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToOrder(resultSet);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Order> getOrdersByUserId(int userId) {
        List<Order> orders = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDERS_BY_USER_ID_SQL)) {

            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    orders.add(mapResultSetToOrder(resultSet));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }

    @Override
    public boolean updateOrderStatus(int orderId, String orderStatus) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ORDER_STATUS_SQL)) {

            preparedStatement.setString(1, orderStatus);
            preparedStatement.setInt(2, orderId);

            return preparedStatement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean cancelOrder(int orderId) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CANCEL_ORDER_SQL)) {

            preparedStatement.setInt(1, orderId);

            return preparedStatement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public double getOrderTotalAmount(int orderId) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDER_TOTAL_AMOUNT_SQL)) {

            preparedStatement.setInt(1, orderId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("total_amount");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_ORDERS_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                orders.add(mapResultSetToOrder(resultSet));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }

    private Order mapResultSetToOrder(ResultSet resultSet) throws Exception {
        Order order = new Order();

        order.setOrderId(resultSet.getInt("order_id"));
        order.setUserId(resultSet.getInt("user_id"));
        order.setTotalAmount(resultSet.getDouble("total_amount"));
        order.setShippingAddress(resultSet.getString("shipping_address"));
        order.setShippingCity(resultSet.getString("shipping_city"));
        order.setShippingState(resultSet.getString("shipping_state"));
        order.setShippingPincode(resultSet.getString("shipping_pincode"));
        order.setPaymentMethod(resultSet.getString("payment_method"));
        order.setOrderStatus(resultSet.getString("order_status"));
        order.setOrderDate(resultSet.getTimestamp("order_date"));

        return order;
    }
}