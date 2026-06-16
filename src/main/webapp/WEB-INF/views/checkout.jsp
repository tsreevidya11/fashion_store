<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>

<title>Checkout</title>

<link rel="stylesheet"
      href="${pageContext.request.contextPath}/assets/css/global.css">

<link rel="stylesheet"
      href="${pageContext.request.contextPath}/assets/css/checkout.css">

</head>
<body>

<jsp:include page="partials/header.jsp"/>

<div class="container">

    <div class="checkout-card">

        <h1>Checkout</h1>

        <form action="${pageContext.request.contextPath}/place-order"
              method="post">

            <input type="text"
                   name="fullName"
                   placeholder="Full Name"
                   required>

            <input type="text"
                   name="phone"
                   placeholder="Phone Number"
                   required>

            <textarea
                    name="address"
                    placeholder="Shipping Address"
                    required></textarea>

            <input type="text"
                   name="city"
                   placeholder="City"
                   required>

            <input type="text"
                   name="state"
                   placeholder="State"
                   required>

            <input type="text"
                   name="pincode"
                   placeholder="Pincode"
                   required>

            <select name="paymentMethod" required>

                <option value="COD">
                    Cash On Delivery
                </option>

                <option value="UPI">
                    UPI
                </option>

            </select>

            <button type="submit"
                    class="btn-primary">

                Place Order

            </button>

        </form>

    </div>

</div>

<jsp:include page="partials/footer.jsp"/>

</body>
</html>