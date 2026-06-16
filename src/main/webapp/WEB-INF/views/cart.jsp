<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.fashionstore.model.CartItem" %>

<%
List<CartItem> cartItems =
(List<CartItem>) request.getAttribute("cartItems");

Double cartTotal =
(Double) request.getAttribute("cartTotal");

if(cartTotal == null){
cartTotal = 0.0;
}
%>

<!DOCTYPE html>

<html>
<head>
    <title>My Cart</title>


<link rel="stylesheet"
      href="${pageContext.request.contextPath}/assets/css/global.css">

<link rel="stylesheet"
      href="${pageContext.request.contextPath}/assets/css/cart.css">


</head>
<body>

<jsp:include page="partials/header.jsp"/>
<jsp:include page="partials/category-nav.jsp"/>

<div class="container">

<h1 class="section-title">
    Shopping Cart
</h1>

<%
if(cartItems != null && !cartItems.isEmpty()){
%>


<div class="cart-layout">

    <div class="cart-items">


<%
for(CartItem item : cartItems){
%>


        <div class="cart-item-card">

            <div class="cart-image">

                <img src="<%=request.getContextPath()%>/<%=item.getImageUrl()%>"
                     alt="<%=item.getProductName()%>">

            </div>

            <div class="cart-info">

                <h3>
                    <%=item.getProductName()%>
                </h3>

                <p>
                    Brand:
                    <strong>
                        <%=item.getBrand()%>
                    </strong>
                </p>

                <p>
                    Size:
                    <strong>
                        <%=item.getSize()%>
                    </strong>
                </p>

                <p>
                    Price:
                    ₹<%=item.getPrice()%>
                </p>

                <p>
                    Quantity:
                    <%=item.getQuantity()%>
                </p>

                <a href="${pageContext.request.contextPath}/remove-cart-item?id=<%=item.getCartItemId()%>"
                   class="remove-btn">
                    Remove
                </a>

            </div>

            <div class="cart-subtotal">

                ₹<%=item.getSubtotal()%>

            </div>

        </div>


<%
}
%>


    </div>

    <div class="cart-summary">

        <h2>Order Summary</h2>

        <hr>

        <br>

        <h3>
            Total: ₹<%=cartTotal%>
        </h3>

        <br>

        <a href="${pageContext.request.contextPath}/checkout"
           class="btn-primary">
            Proceed To Checkout
        </a>

    </div>

</div>


<%
}else{
%>


<div class="cart-card">

    <h3>
        Your Cart Is Empty
    </h3>

    <br>

    <a href="${pageContext.request.contextPath}/products"
       class="btn-primary">
        Continue Shopping
    </a>

</div>


<%
}
%>

</div>

<jsp:include page="partials/footer.jsp"/>

</body>
</html>
