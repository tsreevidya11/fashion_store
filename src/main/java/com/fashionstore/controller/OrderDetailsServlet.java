package com.fashionstore.controller;

import com.fashionstore.dao.OrderDAO;
import com.fashionstore.model.OrderItemDisplay;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/order-details")
public class OrderDetailsServlet extends HttpServlet {

private OrderDAO orderDAO;

@Override
public void init() {
    orderDAO = new OrderDAO();
}

@Override
protected void doGet(HttpServletRequest request,
                     HttpServletResponse response)
        throws ServletException, IOException {

    int orderId =
            Integer.parseInt(
                    request.getParameter("id"));

    List<OrderItemDisplay> items =
            orderDAO.getOrderItemsByOrderId(orderId);

    request.setAttribute("items", items);

    request.getRequestDispatcher(
            "/WEB-INF/views/order-details.jsp")
            .forward(request, response);
}

}
