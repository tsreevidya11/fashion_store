<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.fashionstore.model.Product" %>

<!DOCTYPE html>

<html>
<head>
    <title>All Products</title>

<link rel="stylesheet"
href="${pageContext.request.contextPath}/assets/css/global.css">

<link rel="stylesheet"
href="${pageContext.request.contextPath}/assets/css/products.css">
</head>
<body>

<jsp:include page="partials/header.jsp"/>
<jsp:include page="partials/category-nav.jsp"/>

<section class="products-section">

<div class="container">

    <h2 class="section-title">
        All Products
    </h2>

    <div class="products-layout">

        <!-- LEFT SIDEBAR -->

        <aside class="sidebar">

            <h3>Filters</h3>

            <form action="${pageContext.request.contextPath}/products"
                  method="get">

                <input type="text"
                       name="keyword"
                       placeholder="Search Product">

                <select name="categoryId">
                    <option value="">All Categories</option>
                    <option value="1">Men</option>
                    <option value="2">Women</option>
                    <option value="3">Kids</option>
                    <option value="4">Shoes</option>
                    <option value="5">Accessories</option>
                </select>

                <select name="brand">
                    <option value="">All Brands</option>
                    <option value="Nike">Nike</option>
                    <option value="Adidas">Adidas</option>
                    <option value="Puma">Puma</option>
                    <option value="Fastrack">Fastrack</option>
                </select>

                <input type="number"
                       name="minPrice"
                       placeholder="Min Price">

                <input type="number"
                       name="maxPrice"
                       placeholder="Max Price">

                <select name="sort">
                    <option value="">Sort By</option>
                    <option value="lowToHigh">
                        Price Low To High
                    </option>
                    <option value="highToLow">
                        Price High To Low
                    </option>
                </select>

                <button type="submit"
                        class="btn-primary">
                    Apply Filters
                </button>

            </form>

        </aside>

        <!-- PRODUCTS AREA -->

        <div class="products-content">

            <div class="product-grid">


<%
List<Product> products =
(List<Product>) request.getAttribute("products");

if(products != null){
for(Product product : products){
%>

                <div class="product-card">

                    <img src="<%=request.getContextPath()%>/<%=product.getImageUrl()%>"
                         alt="<%=product.getProductName()%>">

                    <h3>
                        <%=product.getProductName()%>
                    </h3>

                    <p>
                        Brand:
                        <strong>
                            <%=product.getBrand()%>
                        </strong>
                    </p>

                    <p class="price">
                        ₹<%=product.getPrice()%>
                    </p>

                    <a href="${pageContext.request.contextPath}/product-details?id=<%=product.getProductId()%>"
                       class="btn-primary">
                        View Details
                    </a>

                </div>

<%
}
}
%>

            </div>

        </div>

    </div>

</div>

</section>

<jsp:include page="partials/footer.jsp"/>

</body>
</html>
