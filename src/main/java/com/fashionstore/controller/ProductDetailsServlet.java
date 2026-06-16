package com.fashionstore.controller;

import com.fashionstore.dao.ProductDAO;
import com.fashionstore.dao.ProductVariantDAO;
import com.fashionstore.model.Product;
import com.fashionstore.model.ProductVariant;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/product-details")
public class ProductDetailsServlet extends HttpServlet {

    private ProductDAO productDAO;
    private ProductVariantDAO variantDAO;

    @Override
    public void init() {

        productDAO = new ProductDAO();
        variantDAO = new ProductVariantDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        int productId =
                Integer.parseInt(request.getParameter("id"));

        Product product =
                productDAO.getProductById(productId);

        List<ProductVariant> variants =
                variantDAO.getVariantsByProductId(productId);

        request.setAttribute("product", product);
        request.setAttribute("variants", variants);

        request.getRequestDispatcher(
                "/WEB-INF/views/product-details.jsp")
                .forward(request, response);
    }
}