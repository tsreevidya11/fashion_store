<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>

<html>
<head>

<title>Login</title>

<link rel="stylesheet"
      href="${pageContext.request.contextPath}/assets/css/global.css">

<style>

.login-container{
    max-width:500px;
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

.error{
    color:red;
    margin-bottom:15px;
}

</style>

</head>
<body>

<jsp:include page="partials/header.jsp"/>

<div class="login-container">

<h2>Login</h2>

<%
String error =
(String) request.getAttribute("error");

if(error != null){
%>

<div class="error">
    <%=error%>
</div>

<%
}
%>

<form action="${pageContext.request.contextPath}/login"
      method="post">

    <div class="form-group">

        <label>Email</label>

        <input type="email"
               name="email"
               required>

    </div>

    <div class="form-group">

        <label>Password</label>

        <input type="password"
               name="password"
               required>

    </div>

    <button type="submit"
            class="btn-primary">

        Login

    </button>

</form>

</div>

</body>
</html>
