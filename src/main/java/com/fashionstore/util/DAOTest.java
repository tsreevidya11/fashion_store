package com.fashionstore.util;

import java.util.List;

import com.fashionstore.dao.CategoryDAO;
import com.fashionstore.dao.ProductDAO;
import com.fashionstore.dao.ProductVariantDAO;
import com.fashionstore.dao.UserDAO;
import com.fashionstore.dao.impl.CategoryDAOImpl;
import com.fashionstore.dao.impl.ProductDAOImpl;
import com.fashionstore.dao.impl.ProductVariantDAOImpl;
import com.fashionstore.dao.impl.UserDAOImpl;
import com.fashionstore.model.Category;
import com.fashionstore.model.Product;
import com.fashionstore.model.ProductVariant;
import com.fashionstore.model.User;

public class DAOTest {

    public static void main(String[] args) {

        testUsers();
        testCategories();
        testProducts();
        testProductVariants();
    }

    private static void testUsers() {
        UserDAO userDAO = new UserDAOImpl();

        System.out.println("----- USERS TEST -----");

        User user = userDAO.loginUser("rahul@example.com", "rahul123");

        if (user != null) {
            System.out.println("Login successful");
            System.out.println("User ID: " + user.getUserId());
            System.out.println("Name: " + user.getFullName());
            System.out.println("Email: " + user.getEmail());
        } else {
            System.out.println("Login failed");
        }

        System.out.println("Email exists: " + userDAO.emailExists("rahul@example.com"));
        System.out.println();
    }

    private static void testCategories() {
        CategoryDAO categoryDAO = new CategoryDAOImpl();

        System.out.println("----- CATEGORIES TEST -----");

        List<Category> categories = categoryDAO.getAllCategories();

        for (Category category : categories) {
            System.out.println(category.getCategoryId() + " - " + category.getCategoryName());
        }

        System.out.println();
    }

    private static void testProducts() {
        ProductDAO productDAO = new ProductDAOImpl();

        System.out.println("----- PRODUCTS TEST -----");

        List<Product> products = productDAO.getAllProducts();

        for (Product product : products) {
            System.out.println(product.getProductId() + " - " + product.getProductName()
                    + " - Rs." + product.getPrice());
        }

        System.out.println();

        System.out.println("----- MEN CATEGORY PRODUCTS -----");

        List<Product> menProducts = productDAO.getProductsByCategory(1);

        for (Product product : menProducts) {
            System.out.println(product.getProductId() + " - " + product.getProductName());
        }

        System.out.println();

        System.out.println("----- SEARCH PRODUCTS: shoes -----");

        List<Product> searchedProducts = productDAO.searchProducts("shoes");

        for (Product product : searchedProducts) {
            System.out.println(product.getProductId() + " - " + product.getProductName());
        }

        System.out.println();

        System.out.println("----- FILTER PRODUCTS: size M, price 400 to 1500 -----");

        List<Product> filteredProducts = productDAO.filterProducts(null, "M", 400.00, 1500.00, "price_low_high");

        for (Product product : filteredProducts) {
            System.out.println(product.getProductId() + " - " + product.getProductName()
                    + " - Rs." + product.getPrice());
        }

        System.out.println();
    }

    private static void testProductVariants() {
        ProductVariantDAO productVariantDAO = new ProductVariantDAOImpl();

        System.out.println("----- PRODUCT VARIANTS TEST -----");

        List<ProductVariant> variants = productVariantDAO.getVariantsByProductId(1);

        for (ProductVariant variant : variants) {
            System.out.println("Variant ID: " + variant.getVariantId()
                    + ", Product ID: " + variant.getProductId()
                    + ", Size: " + variant.getSize()
                    + ", Stock: " + variant.getStockQuantity());
        }

        System.out.println();
    }
}