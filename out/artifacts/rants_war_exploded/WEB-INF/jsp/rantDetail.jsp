<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/4/17
  Time: 22:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Rant</title>
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
          crossorigin="anonymous">
</head>
<body>

<header>
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
</header>

<div class="row col-lg-8 col-lg-offset-2">
    <!-- 主要 -->
    <div class="row col-lg-9" >
        <!-- 个人信息 -->
        <div class="page-header">
            <div style="width:50px; height:50px; border-radius:50%; overflow:hidden;">
                <img class="media-object" src="${user.userAvatar}" alt="..." height="50px" width="50px">
            </div>
            <h4>${user.userName}</h4>
            <small>${rant.rantDate}</small>
        </div>


        <!-- 内容 -->
        <div class="row">
            <div class="col-lg-1">
                <button type="button" class="btn btn-default"><span class="glyphicon glyphicon-thumbs-up"></span></button>
                <button type="button" class="btn btn-default"><span class="glyphicon glyphicon-thumbs-down"></span></button>
            </div>
            <div class="col-lg-11">${rant.rantContent}</div>
        </div>

        <!-- 评论 -->
        <div class="page-header"><h1>评论</h1></div>
        <ul class="list-group">
            <c:forEach items="${commentList}" var="comment">
            <li class="list-group-item"><strong>${userId2userNameMap[comment.userId]}</strong>：${comment.commentContent}<span class="glyphicon glyphicon-thumbs-up" style="float: right;"></span></li>
            </c:forEach>
        </ul>


    </div>

    <!--右侧边栏 -->
    <div class="col-lg-3" style="height: 300px; background-color: #f5f5f5;">
        <!-- <div class="panel panel-default">
         <div class="panel-heading">相似的内容</div>
             <div class="panel-body">
                 <h4>母猪的存亡</h4>
                 <h4>歌王争霸谁与争锋那个</h4>
                 <h4>实诚的人是最可爱的人</h4>
            </div>
        </div> -->


    </div>
</div>




<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"
        integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
        crossorigin="anonymous"></script>
</body>
</html>
