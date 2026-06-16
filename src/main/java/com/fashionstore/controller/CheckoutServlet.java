package com.fashionstore.controller;

import com.fashionstore.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

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

    request.getRequestDispatcher(
            "/WEB-INF/views/checkout.jsp")
            .forward(request, response);
}

}
