<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>

<html>
<head>

<title>Order Success</title>

<link rel="stylesheet"
      href="${pageContext.request.contextPath}/assets/css/global.css">

</head>
<body>

<jsp:include page="partials/header.jsp"/>

<div class="container">

<div style="
    max-width:700px;
    margin:60px auto;
    text-align:center;
    background:#fff;
    padding:40px;
    border-radius:15px;
    box-shadow:0 2px 10px rgba(0,0,0,0.08);">

    <h1>
        ✅ Order Placed Successfully
    </h1>

    <br>

    <p>
        Thank you for shopping with us.
    </p>

    <br>

    <a href="${pageContext.request.contextPath}/products"
       class="btn-primary">

        Continue Shopping

    </a>

</div>

</div>

<jsp:include page="partials/footer.jsp"/>

</body>
</html>
