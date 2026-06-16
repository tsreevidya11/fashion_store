package com.fashionstore.controller;

import com.fashionstore.dao.CartDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/remove-cart-item")
public class RemoveCartItemServlet extends HttpServlet {

private CartDAO cartDAO;

@Override
public void init() {
    cartDAO = new CartDAO();
}

@Override
protected void doGet(HttpServletRequest request,
                     HttpServletResponse response)
        throws ServletException, IOException {

    int cartItemId =
            Integer.parseInt(
                    request.getParameter("id"));

    cartDAO.removeCartItem(cartItemId);

    response.sendRedirect(
            request.getContextPath() + "/cart");
}

}
