package com.fashionstore.dao;

import com.fashionstore.model.Product;
import com.fashionstore.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

private static final String GET_ALL_PRODUCTS =
        "SELECT * FROM products ORDER BY product_id";

public List<Product> getAllProducts() {

    List<Product> products = new ArrayList<>();

    try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(GET_ALL_PRODUCTS);
            ResultSet rs = ps.executeQuery()
    ) {

        while (rs.next()) {

            Product product = new Product();

            product.setProductId(rs.getInt("product_id"));
            product.setCategoryId(rs.getInt("category_id"));
            product.setProductName(rs.getString("product_name"));
            product.setBrand(rs.getString("brand"));
            product.setDescription(rs.getString("description"));
            product.setPrice(rs.getDouble("price"));
            product.setImageUrl(rs.getString("image_url"));

            products.add(product);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return products;
}

public Product getProductById(int productId) {

    Product product = null;

    String sql = "SELECT * FROM products WHERE product_id = ?";

    try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
    ) {

        ps.setInt(1, productId);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            product = new Product();

            product.setProductId(rs.getInt("product_id"));
            product.setCategoryId(rs.getInt("category_id"));
            product.setProductName(rs.getString("product_name"));
            product.setBrand(rs.getString("brand"));
            product.setDescription(rs.getString("description"));
            product.setPrice(rs.getDouble("price"));
            product.setImageUrl(rs.getString("image_url"));
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return product;
}

public List<Product> filterProducts(
        String keyword,
        Integer categoryId,
        String brand,
        Double minPrice,
        Double maxPrice,
        String sort) {

    List<Product> products = new ArrayList<>();

    StringBuilder sql =
            new StringBuilder("SELECT * FROM products WHERE 1=1");

    if (keyword != null && !keyword.isBlank()) {
        sql.append(" AND product_name LIKE ?");
    }

    if (categoryId != null) {
        sql.append(" AND category_id = ?");
    }

    if (brand != null && !brand.isBlank()) {
        sql.append(" AND brand = ?");
    }

    if (minPrice != null) {
        sql.append(" AND price >= ?");
    }

    if (maxPrice != null) {
        sql.append(" AND price <= ?");
    }

    if ("lowToHigh".equals(sort)) {
        sql.append(" ORDER BY price ASC");
    } else if ("highToLow".equals(sort)) {
        sql.append(" ORDER BY price DESC");
    }

    try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps =
                    con.prepareStatement(sql.toString())
    ) {

        int index = 1;

        if (keyword != null && !keyword.isBlank()) {
            ps.setString(index++, "%" + keyword + "%");
        }

        if (categoryId != null) {
            ps.setInt(index++, categoryId);
        }

        if (brand != null && !brand.isBlank()) {
            ps.setString(index++, brand);
        }

        if (minPrice != null) {
            ps.setDouble(index++, minPrice);
        }

        if (maxPrice != null) {
            ps.setDouble(index++, maxPrice);
        }

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            Product product = new Product();

            product.setProductId(rs.getInt("product_id"));
            product.setCategoryId(rs.getInt("category_id"));
            product.setProductName(rs.getString("product_name"));
            product.setBrand(rs.getString("brand"));
            product.setDescription(rs.getString("description"));
            product.setPrice(rs.getDouble("price"));
            product.setImageUrl(rs.getString("image_url"));

            products.add(product);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return products;
}

}
