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
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
          crossorigin="anonymous">
</head>
<body>


<header class="site-header" style="background-color: white;">
    <!-- 顶部导航 -->
    <nav class="navbar navbar-static-top main-navbar" id="top" style="background-color: #F5F5F5;">
        <div class="container">
            <div class="navbar-header">
                <a href="${pageContext.request.contextPath}/home.action" class="navbar-brand brand-bootcdn" onclick="">Rants</a>
            </div>

            <nav id="bs-navbar" class="collapse navbar-collapse">
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="#" target="_blank">${username}</a></li>
                    <li><a href="${pageContext.request.contextPath }/logoutSubmit.action" target="_blank">退出</a></li>
                    <li><a href="/api/" onclick="" target="_blank">API</a></li>
                    <li><a href="http://blog.bootcdn.cn/" onclick="" target="_blank">博客</a></li>
                    <li><a href="#about" onclick="">关于</a></li>
                </ul>
            </nav>
        </div>
    </nav>
    <!--巨幕 -->
    <div class="container jumbotron" style="background-color: white;">
        <div class="row">
            <div class="col-xs-12">
                <h1>你好，世界</h1>
                <p>忘了我就没有痛，将往事留在风中<br><span class="package-amount">我是不是该安静地走开</span></p>

                <div class="col-lg-12">
                    <div class="input-group">
                        <input type="text" class="form-control search clearable" placeholder="搜索" autocomplete="off">
                    </div>
                </div>
            </div>
        </div>
    </div>
</header>


<!-- 列表 -->
<div class="container">
    <ul class="list-group">
        <!-- 发送 -->
        <div class="package list-group-item" style="background-color: #F5F5F5;">
            <div class="row" >

                <div class="col-md-11">
                    <input type="text" class="form-control search clearable" placeholder="说点有趣的..." autocomplete="off">
                </div>

                <div class="col-md-1">
                    <button type="button" class="btn btn-default">发布</button>
                </div>
            </div>
        </div>

        <c:forEach items="${rantList}" var="rant">
        <a href="${pageContext.request.contextPath }/rant.action?rantId=${rant.rantId}" class="package list-group-item" target="_blank" onclick="">
            <div class="row">
                <div class="col-md-1">
                    <div style="width:50px; height:50px; border-radius:50%; overflow:hidden;">
                        <img class="media-object" src="${rant.rantAvatar}" alt="..." height="50px" width="50px">
                    </div>
                </div>

                <div class="col-md-2">
                    <h4 class="package-name">${userId2NameMap[rant.userId]}</h4>
                </div>
                <div class="col-md-9 hidden-xs">
                    <p class="package-description">${rant.rantContent}</p>
                </div>
                <div class="package-extra-info col-md-9 col-md-offset-3 col-xs-12">
                    <span class="badge" style="float: right;">${rantId2ComCntMap[rant.rantId]}</span>
                </div>


            </div>
        </a>

        </c:forEach>


        <a href="#" class="package list-group-item" target="_blank" onclick="">
            <div class="row">
                <h4 style="text-align: center;">查看更多</h4>
            </div>
        </a>

    </ul>
</div>




<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"
        integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
        crossorigin="anonymous"></script>
</body>
</html>