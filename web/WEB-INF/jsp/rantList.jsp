
<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/4/17
  Time: 22:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>所有Rant</title>
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
          crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap-validator/0.5.3/css/bootstrapValidator.css">

</head>
<body style="background-color: #e3e3e3;">

<header>
    <%@include file="header.jsp"%><!--静态包含-->
</header>

<div class="col-lg-6 col-lg-offset-3">
    <%--主要--%>
    <div class="panel panel-default">
        <div class="panel-heading">全部的Rant</div>
        <div class="panel-body">
            <ul>
                <c:forEach items="${rantList}" var="rant">
                    <li><a href="${pageContext.request.contextPath}/rant.action?rantId=${rant.rantId}"><h5>${rant.rantContent}</h5></a> </li>
                </c:forEach>
            </ul>
        </div>
    </div>




</div>


<%--Dialogs--%>
<div id="pleaseLogin" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body">
                <button class="close" data-dismiss="modal">
                    <span>&times;</span>
                </button>
            </div>
            <div class="modal-title">
                <h1 class="text-center">抱歉😥
                </h1>
            </div>
            <div class="modal-body">
                <h4 style="text-align: center;">请先登录</h4>
            </div>
        </div>
    </div>
</div>
<div id="pleaseDoNotSendNull" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body">
                <button class="close" data-dismiss="modal">
                    <span>&times;</span>
                </button>
            </div>
            <div class="modal-title">
                <h1 class="text-center">抱歉😥
                </h1>
            </div>
            <div class="modal-body">
                <h4 style="text-align: center;">发送内容不能为空</h4>
            </div>
        </div>
    </div>
</div>



<script src="https://cdn.bootcss.com/bootstrap-validator/0.5.3/js/bootstrapValidator.js"></script>
<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"
        integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
        crossorigin="anonymous"></script>
</body>
</html>
