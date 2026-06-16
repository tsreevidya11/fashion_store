package com.fashionstore.controller;

import com.fashionstore.dao.CartDAO;
import com.fashionstore.dao.OrderDAO;
import com.fashionstore.model.CartItem;
import com.fashionstore.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/place-order")
public class PlaceOrderServlet extends HttpServlet {

private OrderDAO orderDAO;
private CartDAO cartDAO;

@Override
public void init() {
    orderDAO = new OrderDAO();
    cartDAO = new CartDAO();
}

@Override
protected void doPost(HttpServletRequest request,
                      HttpServletResponse response)
        throws ServletException, IOException {

    HttpSession session =
            request.getSession(false);

    User user = null;

    if(session != null){
        user =
                (User) session.getAttribute("loggedInUser");
    }

    if(user == null){

        response.sendRedirect(
                request.getContextPath()
                + "/login");

        return;
    }

    int userId =
            user.getUserId();

    int cartId =
            cartDAO.getCartIdByUserId(
                    user.getUserId());

    String address =
            request.getParameter("address");
    String city =
            request.getParameter("city");

    String state =
            request.getParameter("state");

    String pincode =
            request.getParameter("pincode");

    String paymentMethod =
            request.getParameter("paymentMethod");

    List<CartItem> cartItems =
            cartDAO.getCartItems(cartId);

    double totalAmount = 0;

    for(CartItem item : cartItems){

        totalAmount += item.getSubtotal();
    }
    System.out.println("User ID = " + userId);
    System.out.println("Cart ID = " + cartId);
    System.out.println("Total = " + totalAmount);
    System.out.println("Address = " + address);
    System.out.println("Payment = " + paymentMethod);
    int orderId =
    		orderDAO.createOrder(
    		        userId,
    		        totalAmount,
    		        address,
    		        city,
    		        state,
    		        pincode,
    		        paymentMethod);
    System.out.println("Created Order ID = " + orderId);

    for(CartItem item : cartItems){

        orderDAO.addOrderItem(
                orderId,
                item.getVariantId(),
                item.getQuantity(),
                item.getPrice());
    }

    orderDAO.clearCart(cartId);

    response.sendRedirect(
            request.getContextPath()
            + "/order-success");
}

}
