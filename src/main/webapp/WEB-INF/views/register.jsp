<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>

<html>
<head>

<title>Register</title>

<link rel="stylesheet"
      href="${pageContext.request.contextPath}/assets/css/global.css">

<style>

.register-container{
    max-width:600px;
    margin:40px auto;
    background:#fff;
    padding:30px;
    border-radius:12px;
    box-shadow:0 2px 10px rgba(0,0,0,0.1);
}

.form-group{
    margin-bottom:15px;
}

.form-group label{
    display:block;
    margin-bottom:5px;
    font-weight:bold;
}

.form-group input{
    width:100%;
    padding:10px;
    border:1px solid #ccc;
    border-radius:6px;
}

.btn-register{
    width:100%;
    padding:12px;
    border:none;
    cursor:pointer;
}

</style>

</head>
<body>

<jsp:include page="partials/header.jsp"/>

<div class="register-container">

<h2>Create Account</h2>

<form action="${pageContext.request.contextPath}/register"
      method="post">

    <div class="form-group">
        <label>Full Name</label>
        <input type="text"
               name="fullName"
               required>
    </div>

    <div class="form-group">
        <label>Email</label>
        <input type="email"
               name="email"
               required>
    </div>

    <div class="form-group">
        <label>Phone</label>
        <input type="text"
               name="phone"
               required>
    </div>

    <div class="form-group">
        <label>Password</label>
        <input type="password"
               name="password"
               required>
    </div>

    <div class="form-group">
        <label>Address</label>
        <input type="text"
               name="address"
               required>
    </div>

    <div class="form-group">
        <label>City</label>
        <input type="text"
               name="city"
               required>
    </div>

    <div class="form-group">
        <label>State</label>
        <input type="text"
               name="state"
               required>
    </div>

    <div class="form-group">
        <label>Pincode</label>
        <input type="text"
               name="pincode"
               required>
    </div>

    <button type="submit"
            class="btn-primary btn-register">

        Register

    </button>

</form>

</div>

</body>
</html>
