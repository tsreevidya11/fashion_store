<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.fashionstore.model.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="com.fashionstore.model.ProductVariant" %>

<%
Product product =
(Product) request.getAttribute("product");

List<ProductVariant> variants =
(List<ProductVariant>) request.getAttribute("variants");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Product Details</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css/global.css">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css/product-details.css">
</head>
<body>

<jsp:include page="partials/header.jsp"/>
<jsp:include page="partials/category-nav.jsp"/>

<div class="container">

    <div class="details-container">

        <!-- PRODUCT IMAGE -->

        <div class="details-image">

            <img src="<%=request.getContextPath()%>/<%=product.getImageUrl()%>"
                 alt="<%=product.getProductName()%>">

        </div>

        <!-- PRODUCT INFO -->

        <div class="details-info">

            <h1>
                <%=product.getProductName()%>
            </h1>

            <h3>
                Brand:
                <%=product.getBrand()%>
            </h3>

            <p class="details-price">
                ₹<%=product.getPrice()%>
            </p>

            <p>
                <%=product.getDescription()%>
            </p>

            <form action="${pageContext.request.contextPath}/add-to-cart"
                  method="post">

                <!-- SIZE -->

                <div class="quantity-box">

                    <label>Size:</label>

                    <select name="variantId" required>

                        <%
                        if(variants != null){
                            for(ProductVariant variant : variants){
                        %>

                        <option value="<%=variant.getVariantId()%>">
                            <%=variant.getSize()%>
                        </option>

                        <%
                            }
                        }
                        %>

                    </select>

                </div>

                <br>

                <!-- QUANTITY -->

                <div class="quantity-box">

                    <label>Quantity:</label>

                    <input type="number"
                           name="quantity"
                           value="1"
                           min="1"
                           required>

                </div>

                <br>

                <!-- BUTTONS -->

                <div class="details-actions">

                    <button type="submit"
                            class="btn-primary">
                        Add To Cart
                    </button>

                    <a href="${pageContext.request.contextPath}/products"
                       class="btn-secondary">
                        Back To Products
                    </a>

                </div>

            </form>

        </div>

    </div>

</div>

<jsp:include page="partials/footer.jsp"/>

</body>
</html>