package com.fashionstore.controller;

import com.fashionstore.dao.OrderDAO;
import com.fashionstore.model.Order;
import com.fashionstore.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/orders")
public class OrdersServlet extends HttpServlet {

private OrderDAO orderDAO;

@Override
public void init() {
    orderDAO = new OrderDAO();
}

@Override
protected void doGet(HttpServletRequest request,
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

    List<Order> orders =
            orderDAO.getOrdersByUserId(userId);

    request.setAttribute(
            "orders",
            orders);

    request.getRequestDispatcher(
            "/WEB-INF/views/orders.jsp")
            .forward(request, response);
}

}
