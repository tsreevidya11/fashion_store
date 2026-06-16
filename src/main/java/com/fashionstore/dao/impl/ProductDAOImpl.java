package com.fashionstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.fashionstore.dao.ProductDAO;
import com.fashionstore.model.Product;
import com.fashionstore.util.DBConnection;

public class ProductDAOImpl implements ProductDAO {

    private static final String INSERT_PRODUCT_SQL = """
            INSERT INTO products (
                category_id, product_name, description, price, image_url
            ) VALUES (?, ?, ?, ?, ?)
            """;

    private static final String GET_PRODUCT_BY_ID_SQL = """
            SELECT * FROM products
            WHERE product_id = ?
            """;

    private static final String GET_ALL_PRODUCTS_SQL = """
            SELECT * FROM products
            ORDER BY created_at DESC
            """;

    private static final String GET_PRODUCTS_BY_CATEGORY_SQL = """
            SELECT * FROM products
            WHERE category_id = ?
            ORDER BY created_at DESC
            """;

    private static final String SEARCH_PRODUCTS_SQL = """
            SELECT * FROM products
            WHERE product_name LIKE ? OR description LIKE ?
            ORDER BY created_at DESC
            """;

    private static final String GET_NEW_ARRIVALS_SQL = """
            SELECT * FROM products
            ORDER BY created_at DESC
            LIMIT 8
            """;

    private static final String GET_FEATURED_PRODUCTS_SQL = """
            SELECT * FROM products
            ORDER BY product_id DESC
            LIMIT 8
            """;

    private static final String UPDATE_PRODUCT_SQL = """
            UPDATE products
            SET category_id = ?, product_name = ?, description = ?,
                price = ?, image_url = ?
            WHERE product_id = ?
            """;

    private static final String DELETE_PRODUCT_SQL = """
            DELETE FROM products
            WHERE product_id = ?
            """;

    @Override
    public boolean addProduct(Product product) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT_SQL)) {

            preparedStatement.setInt(1, product.getCategoryId());
            preparedStatement.setString(2, product.getProductName());
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.setDouble(4, product.getPrice());
            preparedStatement.setString(5, product.getImageUrl());

            return preparedStatement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Product getProductById(int productId) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_PRODUCT_BY_ID_SQL)) {

            preparedStatement.setInt(1, productId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToProduct(resultSet);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_PRODUCTS_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                products.add(mapResultSetToProduct(resultSet));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public List<Product> getProductsByCategory(int categoryId) {
        List<Product> products = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_PRODUCTS_BY_CATEGORY_SQL)) {

            preparedStatement.setInt(1, categoryId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    products.add(mapResultSetToProduct(resultSet));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public List<Product> searchProducts(String keyword) {
        List<Product> products = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_PRODUCTS_SQL)) {

            String searchKeyword = "%" + keyword + "%";

            preparedStatement.setString(1, searchKeyword);
            preparedStatement.setString(2, searchKeyword);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    products.add(mapResultSetToProduct(resultSet));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public List<Product> filterProducts(Integer categoryId, String size, Double minPrice, Double maxPrice, String sortBy) {
        List<Product> products = new ArrayList<>();

        StringBuilder sql = new StringBuilder("""
                SELECT DISTINCT p.* FROM products p
                LEFT JOIN product_variants pv ON p.product_id = pv.product_id
                WHERE 1 = 1
                """);

        List<Object> parameters = new ArrayList<>();

        if (categoryId != null) {
            sql.append(" AND p.category_id = ?");
            parameters.add(categoryId);
        }

        if (size != null && !size.trim().isEmpty()) {
            sql.append(" AND pv.size = ?");
            parameters.add(size);
        }

        if (minPrice != null) {
            sql.append(" AND p.price >= ?");
            parameters.add(minPrice);
        }

        if (maxPrice != null) {
            sql.append(" AND p.price <= ?");
            parameters.add(maxPrice);
        }

        if ("price_low_high".equalsIgnoreCase(sortBy)) {
            sql.append(" ORDER BY p.price ASC");
        } else if ("price_high_low".equalsIgnoreCase(sortBy)) {
            sql.append(" ORDER BY p.price DESC");
        } else if ("newest".equalsIgnoreCase(sortBy)) {
            sql.append(" ORDER BY p.created_at DESC");
        } else {
            sql.append(" ORDER BY p.product_id DESC");
        }

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {

            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i + 1, parameters.get(i));
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    products.add(mapResultSetToProduct(resultSet));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public List<Product> getNewArrivals() {
        List<Product> products = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_NEW_ARRIVALS_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                products.add(mapResultSetToProduct(resultSet));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public List<Product> getFeaturedProducts() {
        List<Product> products = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_FEATURED_PRODUCTS_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                products.add(mapResultSetToProduct(resultSet));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public boolean updateProduct(Product product) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT_SQL)) {

            preparedStatement.setInt(1, product.getCategoryId());
            preparedStatement.setString(2, product.getProductName());
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.setDouble(4, product.getPrice());
            preparedStatement.setString(5, product.getImageUrl());
            preparedStatement.setInt(6, product.getProductId());

            return preparedStatement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deleteProduct(int productId) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT_SQL)) {

            preparedStatement.setInt(1, productId);

            return preparedStatement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private Product mapResultSetToProduct(ResultSet resultSet) throws Exception {
        Product product = new Product();

        product.setProductId(resultSet.getInt("product_id"));
        product.setCategoryId(resultSet.getInt("category_id"));
        product.setProductName(resultSet.getString("product_name"));
        product.setDescription(resultSet.getString("description"));
        product.setPrice(resultSet.getDouble("price"));
        product.setImageUrl(resultSet.getString("image_url"));
        product.setCreatedAt(resultSet.getTimestamp("created_at"));

        return product;
    }
}