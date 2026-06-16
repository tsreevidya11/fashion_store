package com.fashionstore.controller;

import com.fashionstore.dao.ProductDAO;
import com.fashionstore.model.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

private ProductDAO productDAO;

@Override
public void init() {
    productDAO = new ProductDAO();
}

@Override
protected void doGet(HttpServletRequest request,
                     HttpServletResponse response)
        throws ServletException, IOException {

    List<Product> products = productDAO.getAllProducts();

    System.out.println("Total Products = " + products.size());

    request.setAttribute("products", products);

    request.getRequestDispatcher("/WEB-INF/views/home.jsp")
            .forward(request, response);
}

}
