package com.fashionstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.fashionstore.dao.OrderItemDAO;
import com.fashionstore.model.OrderItem;
import com.fashionstore.util.DBConnection;

public class OrderItemDAOImpl implements OrderItemDAO {

    private static final String INSERT_ORDER_ITEM_SQL = """
            INSERT INTO order_items (
                order_id, variant_id, quantity, price
            ) VALUES (?, ?, ?, ?)
            """;

    private static final String GET_ORDER_ITEM_BY_ID_SQL = """
            SELECT * FROM order_items
            WHERE order_item_id = ?
            """;

    private static final String GET_ORDER_ITEMS_BY_ORDER_ID_SQL = """
            SELECT * FROM order_items
            WHERE order_id = ?
            """;

    @Override
    public boolean addOrderItem(OrderItem orderItem) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER_ITEM_SQL)) {

            preparedStatement.setInt(1, orderItem.getOrderId());
            preparedStatement.setInt(2, orderItem.getVariantId());
            preparedStatement.setInt(3, orderItem.getQuantity());
            preparedStatement.setDouble(4, orderItem.getPrice());

            return preparedStatement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean addOrderItems(List<OrderItem> orderItems) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER_ITEM_SQL)) {

            for (OrderItem orderItem : orderItems) {
                preparedStatement.setInt(1, orderItem.getOrderId());
                preparedStatement.setInt(2, orderItem.getVariantId());
                preparedStatement.setInt(3, orderItem.getQuantity());
                preparedStatement.setDouble(4, orderItem.getPrice());

                preparedStatement.addBatch();
            }

            int[] result = preparedStatement.executeBatch();

            for (int value : result) {
                if (value == 0) {
                    return false;
                }
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public OrderItem getOrderItemById(int orderItemId) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDER_ITEM_BY_ID_SQL)) {

            preparedStatement.setInt(1, orderItemId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToOrderItem(resultSet);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(int orderId) {
        List<OrderItem> orderItems = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDER_ITEMS_BY_ORDER_ID_SQL)) {

            preparedStatement.setInt(1, orderId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    orderItems.add(mapResultSetToOrderItem(resultSet));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return orderItems;
    }

    private OrderItem mapResultSetToOrderItem(ResultSet resultSet) throws Exception {
        OrderItem orderItem = new OrderItem();

        orderItem.setOrderItemId(resultSet.getInt("order_item_id"));
        orderItem.setOrderId(resultSet.getInt("order_id"));
        orderItem.setVariantId(resultSet.getInt("variant_id"));
        orderItem.setQuantity(resultSet.getInt("quantity"));
        orderItem.setPrice(resultSet.getDouble("price"));

        return orderItem;
    }
}