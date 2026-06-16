package com.fashionstore.dao;

import com.fashionstore.model.Order;
import com.fashionstore.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

	public int createOrder(
			int userId,
			double totalAmount,
			String address,
			String city,
			String state,
			String pincode,
			String paymentMethod) {

	
			int orderId = 0;

			String sql =
			        "INSERT INTO orders " +
			        "(user_id,total_amount,shipping_address,shipping_city,shipping_state,shipping_pincode,payment_method,order_status) " +
			        "VALUES(?,?,?,?,?,?,?,?)";

			try (
			        Connection con =
			                DBConnection.getConnection();

			        PreparedStatement ps =
			                con.prepareStatement(
			                        sql,
			                        PreparedStatement.RETURN_GENERATED_KEYS)
			) {

			    ps.setInt(1, userId);
			    ps.setDouble(2, totalAmount);
			    ps.setString(3, address);
			    ps.setString(4, city);
			    ps.setString(5, state);
			    ps.setString(6, pincode);
			    ps.setString(7, paymentMethod);
			    ps.setString(8, "PLACED");

			    int rows = ps.executeUpdate();

			    System.out.println("Rows Inserted = " + rows);

			    ResultSet rs =
			            ps.getGeneratedKeys();

			    if(rs.next()) {

			        orderId =
			                rs.getInt(1);

			        System.out.println("Order ID = " + orderId);
			    }

			} catch (Exception e) {

			    System.out.println("ORDER INSERT ERROR");

			    e.printStackTrace();
			}

			return orderId;

			}

public void addOrderItem(
        int orderId,
        int variantId,
        int quantity,
        double price) {

    String sql =
            "INSERT INTO order_items " +
            "(order_id, variant_id, quantity, price) " +
            "VALUES(?,?,?,?)";

    try (
            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql)
    ) {

        ps.setInt(1, orderId);
        ps.setInt(2, variantId);
        ps.setInt(3, quantity);
        ps.setDouble(4, price);

        ps.executeUpdate();

    } catch (Exception e) {
        e.printStackTrace();
    }
}

public void clearCart(int cartId) {

    String sql =
            "DELETE FROM cart_items WHERE cart_id=?";

    try (
            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql)
    ) {

        ps.setInt(1, cartId);

        ps.executeUpdate();

    } catch (Exception e) {
        e.printStackTrace();
    }
}

public List<Order> getOrdersByUserId(int userId) {

    List<Order> orders =
            new ArrayList<>();

    String sql =
            "SELECT * FROM orders " +
            "WHERE user_id=? " +
            "ORDER BY order_date DESC";

    try (
            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql)
    ) {

        ps.setInt(1, userId);

        ResultSet rs =
                ps.executeQuery();

        while (rs.next()) {

            Order order =
                    new Order();

            order.setOrderId(
                    rs.getInt("order_id"));

            order.setUserId(
                    rs.getInt("user_id"));

            order.setTotalAmount(
                    rs.getDouble("total_amount"));

            order.setShippingAddress(
                    rs.getString("shipping_address"));

            order.setShippingCity(
                    rs.getString("shipping_city"));

            order.setShippingState(
                    rs.getString("shipping_state"));

            order.setShippingPincode(
                    rs.getString("shipping_pincode"));

            order.setPaymentMethod(
                    rs.getString("payment_method"));

            order.setOrderStatus(
                    rs.getString("order_status"));

            order.setOrderDate(
                    rs.getTimestamp("order_date"));

            orders.add(order);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return orders;
}
public java.util.List<com.fashionstore.model.OrderItemDisplay>
getOrderItemsByOrderId(int orderId) {

java.util.List<com.fashionstore.model.OrderItemDisplay>
        items = new java.util.ArrayList<>();

String sql =
        "SELECT p.product_name, p.brand, p.image_url, " +
        "pv.size, oi.quantity, oi.price " +
        "FROM order_items oi " +
        "JOIN product_variants pv ON oi.variant_id = pv.variant_id " +
        "JOIN products p ON pv.product_id = p.product_id " +
        "WHERE oi.order_id = ?";

try (
        Connection con =
                DBConnection.getConnection();

        PreparedStatement ps =
                con.prepareStatement(sql)
) {

    ps.setInt(1, orderId);

    ResultSet rs =
            ps.executeQuery();

    while (rs.next()) {

        com.fashionstore.model.OrderItemDisplay item =
                new com.fashionstore.model.OrderItemDisplay();

        item.setProductName(
                rs.getString("product_name"));

        item.setBrand(
                rs.getString("brand"));

        item.setImageUrl(
                rs.getString("image_url"));

        item.setSize(
                rs.getString("size"));

        item.setQuantity(
                rs.getInt("quantity"));

        item.setPrice(
                rs.getDouble("price"));

        item.setSubtotal(
                rs.getDouble("price")
                * rs.getInt("quantity"));

        items.add(item);
    }

} catch (Exception e) {
    e.printStackTrace();
}

return items;

}


}
