<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.fashionstore.model.Order" %>

<%
List<Order> orders =
(List<Order>) request.getAttribute("orders");
%>

<!DOCTYPE html>

<html>
<head>

<title>My Orders</title>

<link rel="stylesheet"
      href="${pageContext.request.contextPath}/assets/css/global.css">

</head>
<body>

<jsp:include page="partials/header.jsp"/>

<div class="container">

<h1 class="section-title">
    My Orders
</h1>


<%
if(orders != null && !orders.isEmpty()){
%>

<table class="cart-table">

<tr>
    <th>Order ID</th>
    <th>Date</th>
    <th>Total Amount</th>
    <th>Status</th>
    <th>Action</th>
</tr>


<%
for(Order order : orders){
%>

<tr>

    <td>
        <%=order.getOrderId()%>
    </td>

    <td>
        <%=order.getOrderDate()%>
    </td>

    <td>
        ₹<%=order.getTotalAmount()%>
    </td>

    <td>
    <%=order.getOrderStatus()%>
</td>

<td>

    <a href="${pageContext.request.contextPath}/order-details?id=<%=order.getOrderId()%>"
       class="btn-primary">

        View Details

    </a>

</td>

</tr>

<%
}
%>

</table>

<%
}else{
%>

<h3>No Orders Found</h3>

<%
}
%>

</div>

<jsp:include page="partials/footer.jsp"/>

</body>
</html>
