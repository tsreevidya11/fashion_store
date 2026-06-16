<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.fashionstore.model.OrderItemDisplay" %>

<%
List<OrderItemDisplay> items =
(List<OrderItemDisplay>) request.getAttribute("items");
%>

<!DOCTYPE html>

<html>
<head>

<title>Order Details</title>

<link rel="stylesheet"
      href="${pageContext.request.contextPath}/assets/css/global.css">

<style>

.order-container{
    max-width:1000px;
    margin:30px auto;
}

.order-card{
    display:flex;
    align-items:center;
    gap:20px;
    background:#fff;
    padding:20px;
    margin-bottom:20px;
    border-radius:15px;
    box-shadow:0 2px 10px rgba(0,0,0,0.08);
}

.order-image img{
    width:120px;
    height:120px;
    object-fit:cover;
    border-radius:10px;
}

.order-info{
    flex:1;
}

.order-subtotal{
    font-size:22px;
    font-weight:bold;
    color:#6C4CF1;
}

.back-btn{
    margin-bottom:20px;
}

</style>

</head>
<body>

<jsp:include page="partials/header.jsp"/>
<jsp:include page="partials/category-nav.jsp"/>

<div class="container order-container">

```
<a href="${pageContext.request.contextPath}/orders"
   class="btn-primary back-btn">

    Back To Orders

</a>

<h1 class="section-title">
    Order Details
</h1>
```

<%
if(items != null && !items.isEmpty()){


double total = 0;

for(OrderItemDisplay item : items){

    total += item.getSubtotal();


%>

<div class="order-card">

    <div class="order-image">

        <img
            src="<%=request.getContextPath()%>/<%=item.getImageUrl()%>"
            alt="<%=item.getProductName()%>">

    </div>

    <div class="order-info">

        <h3>
            <%=item.getProductName()%>
        </h3>

        <p>
            Brand:
            <strong><%=item.getBrand()%></strong>
        </p>

        <p>
            Size:
            <strong><%=item.getSize()%></strong>
        </p>

        <p>
            Quantity:
            <strong><%=item.getQuantity()%></strong>
        </p>

        <p>
            Price:
            ₹<%=item.getPrice()%>
        </p>

    </div>

    <div class="order-subtotal">

        ₹<%=item.getSubtotal()%>

    </div>

</div>


<%
}
%>

<div class="cart-total">

    <h2>
        Order Total :
        ₹<%=total%>
    </h2>

</div>
<%
}else{
%>

<h3>No Items Found</h3>

<%
}
%>

</div>

<jsp:include page="partials/footer.jsp"/>

</body>
</html>
