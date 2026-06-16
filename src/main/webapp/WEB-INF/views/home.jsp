<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.fashionstore.model.Product" %>

<%
List<Product> products =
(List<Product>) request.getAttribute("products");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Fashion Store - Home</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css/global.css">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css/home.css">
</head>

<body>

<jsp:include page="partials/header.jsp"/>
<jsp:include page="partials/category-nav.jsp"/>

<!-- HERO SECTION -->
<section class="hero">
    <div class="container hero-content">

        <div class="hero-text">
  <h1>Discover Trends. Define Your Style.</h1>

<p>
Explore premium fashion collections for men, women, and kids.
Find the latest styles, footwear, and accessories all in one place.
</p>

            <a href="${pageContext.request.contextPath}/products"
               class="btn-primary">
                Shop Now
            </a>
        </div>

        <div class="hero-image">
            <img src="${pageContext.request.contextPath}/assets/images/hero-banner.jpg"
                 alt="Fashion Banner">
        </div>

    </div>
</section>


<!-- FEATURED PRODUCTS -->
<section class="products-section">
    <div class="container">

        <h2 class="section-title">Featured Products</h2>

        <div class="product-grid">

            <%
            if(products != null){
                for(Product p : products){
            %>

            <div class="product-card">

                <a href="${pageContext.request.contextPath}/product-details?id=<%=p.getProductId()%>">

                    <img src="<%=request.getContextPath()%>/<%=p.getImageUrl()%>"
                         alt="<%=p.getProductName()%>">

                    <h3><%=p.getProductName()%></h3>

                    <p class="price">₹<%=p.getPrice()%></p>

                </a>

            </div>

            <%
                }
            }
            %>

        </div>

    </div>
</section>

<jsp:include page="partials/footer.jsp"/>

</body>
</html>