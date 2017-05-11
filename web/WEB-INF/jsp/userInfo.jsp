<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/4/18
  Time: 19:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>${user.userName}的资料</title>
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
          crossorigin="anonymous">
</head>
<body style="background-color:#e3e3e3;">

<%@include file="header.jsp"%><!--静态包含-->


<div class="container">
  <div class="row">
      <div class="col-lg-10 col-lg-offset-1">
          <%--头像和简单信息--%>
          <div class="col-lg-3">
              <div class="media">
                  <img class="media-object" src="${user.userAvatar}" alt="..." height="200px" width="200px">
              </div>

              <h3>${user.userName}</h3>
              <h5>${user.userBio}</h5>
              <h5><span class="glyphicon glyphicon-grain"></span> 声望 ${user.userValue}</h5>
              <h5><span class="glyphicon glyphicon-map-marker"></span> ${user.userLocation} </h5>
              <%--如果是本人可以修改资料--%>
              <c:if test="${user.userName.equals(sessionScope.username)}"><button type="button" class="btn btn-default">修改资料</button></c:if>

          </div>

          <%--列表--%>
          <div class="col-lg-9">

              <div class="page-header">
                  <h1>RANTS</h1>
              </div>
              <c:if test="${rantList.size()==0}"><h3 style="color: gray">暂无</h3></c:if>
              <ul class="list-group">
                  <c:forEach items="${rantList}" var="rant">
                  <li class="list-group-item">
                      <span class="badge">${rantId2ComCntMap[rant.rantId]}</span>
                      <a href="${pageContext.request.contextPath}/rant.action?rantId=${rant.rantId}">${rant.rantContent}</a>
                      <%--<c:if test="${user.userName.equals(sessionScope.username)}">--%>
                      <%--<form id="deleteRantForm" method="post" action="${pageContext.request.contextPath}/deleteRant.action">--%>
                          <%--<input type="hidden" name="rantId" value="${rant.rantId}"/>--%>
                          <%--<a onclick="document.getElementById('deleteRantForm').submit();">删除</a>--%>
                      <%--</form>--%>
                      <%--</c:if>--%>
                  </li>
                  </c:forEach>
              </ul>
                <%--评论--%>
              <div class="page-header">
                  <h1>点评</h1>
              </div>
              <c:if test="${commentList.size()==0}"><h3 style="color: gray">暂无</h3></c:if>
              <ul class="list-group">
                  <c:forEach items="${commentList}" var="comment">
                      <li class="list-group-item">
                      评论  <a href="${pageContext.request.contextPath}/rant.action?rantId=${comment.rantId}"><em>RANT</em></a> ${comment.commentContent}
                      </li>
                  </c:forEach>
              </ul>
          </div>
      </div>
  </div>


</div>



<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"
        integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
        crossorigin="anonymous"></script>
</body>
