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

@WebServlet("/products")
public class ProductServlet extends HttpServlet {

private ProductDAO productDAO;

@Override
public void init() {
    productDAO = new ProductDAO();
}

@Override
protected void doGet(HttpServletRequest request,
                     HttpServletResponse response)
        throws ServletException, IOException {

    String keyword = request.getParameter("keyword");
    String brand = request.getParameter("brand");
    String categoryParam = request.getParameter("categoryId");
    String sort = request.getParameter("sort");

    Integer categoryId = null;
    Double minPrice = null;
    Double maxPrice = null;

    try {

        if (categoryParam != null &&
                !categoryParam.isBlank()) {

            categoryId =
                    Integer.parseInt(categoryParam);
        }

        String minPriceParam =
                request.getParameter("minPrice");

        String maxPriceParam =
                request.getParameter("maxPrice");

        if (minPriceParam != null &&
                !minPriceParam.isBlank()) {

            minPrice =
                    Double.parseDouble(minPriceParam);
        }

        if (maxPriceParam != null &&
                !maxPriceParam.isBlank()) {

            maxPrice =
                    Double.parseDouble(maxPriceParam);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    List<Product> products;

    boolean filterApplied =
            (keyword != null && !keyword.isBlank())
            || (brand != null && !brand.isBlank())
            || categoryId != null
            || minPrice != null
            || maxPrice != null
            || (sort != null && !sort.isBlank());

    if (filterApplied) {

        products = productDAO.filterProducts(
                keyword,
                categoryId,
                brand,
                minPrice,
                maxPrice,
                sort);

    } else {

        products = productDAO.getAllProducts();
    }

    request.setAttribute("products", products);

    request.getRequestDispatcher(
            "/WEB-INF/views/products.jsp")
            .forward(request, response);
}

}
