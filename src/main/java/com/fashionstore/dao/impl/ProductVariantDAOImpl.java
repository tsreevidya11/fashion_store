package com.fashionstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.fashionstore.dao.ProductVariantDAO;
import com.fashionstore.model.ProductVariant;
import com.fashionstore.util.DBConnection;

public class ProductVariantDAOImpl implements ProductVariantDAO {

    private static final String INSERT_PRODUCT_VARIANT_SQL = """
            INSERT INTO product_variants (
                product_id, size, stock_quantity
            ) VALUES (?, ?, ?)
            """;

    private static final String GET_VARIANT_BY_ID_SQL = """
            SELECT * FROM product_variants
            WHERE variant_id = ?
            """;

    private static final String GET_VARIANT_BY_PRODUCT_AND_SIZE_SQL = """
            SELECT * FROM product_variants
            WHERE product_id = ? AND size = ?
            """;

    private static final String GET_VARIANTS_BY_PRODUCT_ID_SQL = """
            SELECT * FROM product_variants
            WHERE product_id = ?
            ORDER BY variant_id ASC
            """;

    private static final String UPDATE_PRODUCT_VARIANT_SQL = """
            UPDATE product_variants
            SET product_id = ?, size = ?, stock_quantity = ?
            WHERE variant_id = ?
            """;

    private static final String UPDATE_STOCK_SQL = """
            UPDATE product_variants
            SET stock_quantity = ?
            WHERE variant_id = ?
            """;

    private static final String REDUCE_STOCK_SQL = """
            UPDATE product_variants
            SET stock_quantity = stock_quantity - ?
            WHERE variant_id = ? AND stock_quantity >= ?
            """;

    private static final String DELETE_VARIANT_SQL = """
            DELETE FROM product_variants
            WHERE variant_id = ?
            """;

    @Override
    public boolean addProductVariant(ProductVariant productVariant) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT_VARIANT_SQL)) {

            preparedStatement.setInt(1, productVariant.getProductId());
            preparedStatement.setString(2, productVariant.getSize());
            preparedStatement.setInt(3, productVariant.getStockQuantity());

            return preparedStatement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public ProductVariant getVariantById(int variantId) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_VARIANT_BY_ID_SQL)) {

            preparedStatement.setInt(1, variantId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToProductVariant(resultSet);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ProductVariant getVariantByProductAndSize(int productId, String size) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_VARIANT_BY_PRODUCT_AND_SIZE_SQL)) {

            preparedStatement.setInt(1, productId);
            preparedStatement.setString(2, size);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToProductVariant(resultSet);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<ProductVariant> getVariantsByProductId(int productId) {
        List<ProductVariant> productVariants = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_VARIANTS_BY_PRODUCT_ID_SQL)) {

            preparedStatement.setInt(1, productId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    productVariants.add(mapResultSetToProductVariant(resultSet));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return productVariants;
    }

    @Override
    public boolean updateProductVariant(ProductVariant productVariant) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT_VARIANT_SQL)) {

            preparedStatement.setInt(1, productVariant.getProductId());
            preparedStatement.setString(2, productVariant.getSize());
            preparedStatement.setInt(3, productVariant.getStockQuantity());
            preparedStatement.setInt(4, productVariant.getVariantId());

            return preparedStatement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean updateStock(int variantId, int stockQuantity) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STOCK_SQL)) {

            preparedStatement.setInt(1, stockQuantity);
            preparedStatement.setInt(2, variantId);

            return preparedStatement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean reduceStock(int variantId, int quantity) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(REDUCE_STOCK_SQL)) {

            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, variantId);
            preparedStatement.setInt(3, quantity);

            return preparedStatement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deleteVariant(int variantId) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_VARIANT_SQL)) {

            preparedStatement.setInt(1, variantId);

            return preparedStatement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private ProductVariant mapResultSetToProductVariant(ResultSet resultSet) throws Exception {
        ProductVariant productVariant = new ProductVariant();

        productVariant.setVariantId(resultSet.getInt("variant_id"));
        productVariant.setProductId(resultSet.getInt("product_id"));
        productVariant.setSize(resultSet.getString("size"));
        productVariant.setStockQuantity(resultSet.getInt("stock_quantity"));

        return productVariant;
    }
}