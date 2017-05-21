<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/4/16
  Time: 14:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Home</title>
</head>

<body>
<h1>HTTP500 ERROR</h1>
<hr>
<h3>数据库连接异常，请刷新页面</h3>
<em>Mysql的"wait_timeout"时长为8小时，因此数据库8小时空闲就会自动的断开连接。<br>
由于本项目使用了dbcp连接池，而dbcp没有校验连接的有效性，所以会造成服务器空闲8小时后第一次访问出错。</em>
</body>
</html>