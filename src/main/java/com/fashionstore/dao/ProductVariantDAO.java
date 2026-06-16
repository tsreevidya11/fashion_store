package com.fashionstore.dao;

import com.fashionstore.model.ProductVariant;
import com.fashionstore.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductVariantDAO {

    public List<ProductVariant> getVariantsByProductId(int productId) {

        List<ProductVariant> variants =
                new ArrayList<>();

        String sql =
                "SELECT * FROM product_variants WHERE product_id=?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(1, productId);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                ProductVariant variant =
                        new ProductVariant();

                variant.setVariantId(
                        rs.getInt("variant_id"));

                variant.setProductId(
                        rs.getInt("product_id"));

                variant.setSize(
                        rs.getString("size"));

                variant.setStockQuantity(
                        rs.getInt("stock_quantity"));

                variants.add(variant);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return variants;
    }
}