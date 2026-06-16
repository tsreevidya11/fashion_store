<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.fashionstore.model.User" %>

<%
User loggedInUser =
(User) session.getAttribute("loggedInUser");
%>

<header class="header">
<head><link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700;800&display=swap"
      rel="stylesheet"></head>

<div class="container nav-container">

    <div class="logo">
        <a href="${pageContext.request.contextPath}/home">
            🛍️ Fashion Store
        </a>
    </div>

  <form action="${pageContext.request.contextPath}/products"
      method="get"
      class="search-box">

    <input type="text"
           name="keyword"
           placeholder="Search products...">

    

</form>

    <nav class="nav-links">

        <a href="${pageContext.request.contextPath}/home">
            Home
        </a>

        <a href="${pageContext.request.contextPath}/products">
            Products
        </a>

<%
if(loggedInUser == null){
%>

        <a href="${pageContext.request.contextPath}/register">
            Register
        </a>

        <a href="${pageContext.request.contextPath}/login">
            Login
        </a>

<%
}else{
%>

        <a href="${pageContext.request.contextPath}/orders">
            My Orders
        </a>

        <a href="${pageContext.request.contextPath}/cart">
            Cart
        </a>

        <span style="font-weight:bold;">
            Welcome <%=loggedInUser.getFullName()%>
        </span>

        <a href="${pageContext.request.contextPath}/logout">
            Logout
        </a>

<%
}
%>

    </nav>

</div>

</header>
