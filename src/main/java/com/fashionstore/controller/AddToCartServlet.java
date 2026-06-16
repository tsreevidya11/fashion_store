package com.fashionstore.controller;

import com.fashionstore.dao.CartDAO;
import com.fashionstore.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/add-to-cart")
public class AddToCartServlet extends HttpServlet {

private CartDAO cartDAO;

@Override
public void init() {
    cartDAO = new CartDAO();
}

@Override
protected void doPost(HttpServletRequest request,
                      HttpServletResponse response)
        throws ServletException, IOException {

    HttpSession session =
            request.getSession(false);

    if(session == null) {

        response.sendRedirect(
                request.getContextPath()
                + "/login");
        return;
    }

    User user =
            (User) session.getAttribute("loggedInUser");

    if(user == null) {

        response.sendRedirect(
                request.getContextPath()
                + "/login");
        return;
    }

    int variantId =
            Integer.parseInt(
                    request.getParameter("variantId"));

    int quantity =
            Integer.parseInt(
                    request.getParameter("quantity"));

    int cartId =
            cartDAO.getCartIdByUserId(
                    user.getUserId());

    boolean added =
            cartDAO.addToCart(
                    cartId,
                    variantId,
                    quantity);

    if(added) {

        response.sendRedirect(
                request.getContextPath()
                + "/cart");

    } else {

        response.getWriter().println(
                "Failed To Add Item To Cart");
    }
}

}
