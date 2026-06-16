package com.fashionstore.controller;

import com.fashionstore.dao.CartDAO;
import com.fashionstore.model.CartItem;
import com.fashionstore.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

private CartDAO cartDAO;

@Override
public void init() {
    cartDAO = new CartDAO();
}

@Override
protected void doGet(HttpServletRequest request,
                     HttpServletResponse response)
        throws ServletException, IOException {

    HttpSession session =
            request.getSession(false);

    if(session == null){

        response.sendRedirect(
                request.getContextPath()
                + "/login");

        return;
    }

    User user =
            (User) session.getAttribute("loggedInUser");

    if(user == null){

        response.sendRedirect(
                request.getContextPath()
                + "/login");

        return;
    }

    int cartId =
            cartDAO.getCartIdByUserId(
                    user.getUserId());

    List<CartItem> cartItems =
            cartDAO.getCartItems(cartId);

    double total = 0;

    for(CartItem item : cartItems){

        total += item.getSubtotal();
    }

    request.setAttribute(
            "cartItems",
            cartItems);

    request.setAttribute(
            "cartTotal",
            total);

    request.getRequestDispatcher(
            "/WEB-INF/views/cart.jsp")
            .forward(request, response);
}

}
