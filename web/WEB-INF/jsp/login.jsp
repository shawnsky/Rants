<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/4/16
  Time: 23:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>登录</title>
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
          crossorigin="anonymous">
</head>
<body>




<div class="row" style="height: 100%">
    <div class="container" style="background-color: #f5f5f5;height: 500px;width: 400px;">

        <div class="row col-xs-8 col-xs-offset-2"><h1>登录</h1></div>
        <form action="${pageContext.request.contextPath }/loginSubmit.action">

            <div class="row col-xs-8 col-xs-offset-2" ><input type="text" name="username" class="form-control" placeholder="用户名" aria-describedby="basic-addon1"></div>

            <div class="row col-xs-8 col-xs-offset-2"><input type="text" name="password" class="form-control" placeholder="密码" aria-describedby="basic-addon1"></div>


            <div class="row col-xs-8 col-xs-offset-2"> <input type="submit" class="btn btn-default"></div>

        </form>
    </div>


</div>


<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"
        integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
        crossorigin="anonymous"></script>
</body>
</html>