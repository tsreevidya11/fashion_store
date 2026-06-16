package com.fashionstore.dao;

import com.fashionstore.model.CartItem;
import com.fashionstore.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {

public boolean addToCart(
        int cartId,
        int variantId,
        int quantity) {

    String sql =
            "INSERT INTO cart_items(cart_id, variant_id, quantity) VALUES(?,?,?)";

    try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
    ) {

        ps.setInt(1, cartId);
        ps.setInt(2, variantId);
        ps.setInt(3, quantity);

        return ps.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
    }

    return false;
}

public int createCart(int userId){

    String sql =
            "INSERT INTO cart(user_id) VALUES(?)";

    try(
            Connection con = DBConnection.getConnection();
            PreparedStatement ps =
                    con.prepareStatement(
                            sql,
                            PreparedStatement.RETURN_GENERATED_KEYS)
    ){

        ps.setInt(1, userId);

        ps.executeUpdate();

        ResultSet rs =
                ps.getGeneratedKeys();

        if(rs.next()){
            return rs.getInt(1);
        }

    }catch(Exception e){
        e.printStackTrace();
    }

    return 0;
}

public int getCartIdByUserId(int userId) {

    String sql =
            "SELECT cart_id FROM cart WHERE user_id=?";

    try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
    ) {

        ps.setInt(1, userId);

        ResultSet rs = ps.executeQuery();

        if(rs.next()) {
            return rs.getInt("cart_id");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return createCart(userId);
}

public List<CartItem> getCartItems(int cartId) {

    List<CartItem> items =
            new ArrayList<>();

    String sql =
            "SELECT ci.cart_item_id, ci.cart_id, ci.variant_id, ci.quantity, " +
            "p.product_name, p.brand, p.price, p.image_url, " +
            "pv.size " +
            "FROM cart_items ci " +
            "JOIN product_variants pv ON ci.variant_id = pv.variant_id " +
            "JOIN products p ON pv.product_id = p.product_id " +
            "WHERE ci.cart_id = ?";

    try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
    ) {

        ps.setInt(1, cartId);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            CartItem item = new CartItem();

            item.setCartItemId(rs.getInt("cart_item_id"));
            item.setCartId(rs.getInt("cart_id"));
            item.setVariantId(rs.getInt("variant_id"));
            item.setQuantity(rs.getInt("quantity"));

            item.setProductName(rs.getString("product_name"));
            item.setBrand(rs.getString("brand"));
            item.setImageUrl(rs.getString("image_url"));
            item.setSize(rs.getString("size"));

            item.setPrice(rs.getDouble("price"));

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

public boolean removeCartItem(int cartItemId) {

    String sql =
            "DELETE FROM cart_items WHERE cart_item_id=?";

    try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
    ) {

        ps.setInt(1, cartItemId);

        return ps.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
    }

    return false;
}

}
