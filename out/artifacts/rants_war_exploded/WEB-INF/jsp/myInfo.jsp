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

    <title>个人中心</title>
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
          crossorigin="anonymous">
</head>
<body style="background-color: white;">

<%@include file="header.jsp"%><!--静态包含-->


<div class="container">
  <div class="row">
      <div class="col-lg-10 col-lg-offset-1">
          <%--头像和简单信息--%>
          <div class="col-lg-3">
              <div class="panel panel-default">
                  <div class="panel-heading">我的资料</div>
                  <div class="panel-body">
                      <div style="position: relative; width: 175px; height: 175px;">
                          <img src="${user.userAvatar}" width="175px" height="175px" alt="">
                          <a style="position: absolute; top: 100px; left: 70px;">更换头像</a>
                      </div>
                      <h3>${user.userName}</h3>
                      <h5>${user.userBio}</h5>
                      <h5><span class="glyphicon glyphicon-grain"></span> 声望 ${user.userValue}</h5>
                      <h5><span class="glyphicon glyphicon-map-marker"></span> ${user.userLocation} </h5>
                      <button type="button" class="btn btn-default" data-toggle="modal" data-target="#editInfo">编辑资料</button>

                  </div>
              </div>


          </div>

          <%--列表--%>
          <div class="col-lg-9">
              <div class="panel panel-default">
                  <div class="panel-heading">我的记录</div>
                  <div class="panel-body">
                      <div class="alert alert-info alert-dismissible" role="alert">
                          <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                          <strong>先等等！</strong>最好先完善一下自己的信息，这样有利于和别人更好的互动哦
                      </div>

                      <ul id="myTab" class="nav nav-tabs">
                          <li class="active">
                              <a href="#Rants" data-toggle="tab">我的Rants</a>
                          </li>
                          <li>
                              <a href="#Comments" data-toggle="tab">我的评论</a>
                          </li>
                          <li>
                              <a href="#ThumbsUp" data-toggle="tab">我赞过的</a>
                          </li>
                          <li>
                              <a href="#ThumbsDown" data-toggle="tab">我踩过的</a>
                          </li>
                      </ul>

                      <h1></h1>

                      <div id="myTabContent" class="tab-content">
                          <div class="tab-pane fade in active" id="Rants">
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
                          </div>
                          <div class="tab-pane fade" id="Comments">
                              <c:if test="${commentList.size()==0}"><h3 style="color: gray">暂无</h3></c:if>
                              <ul class="list-group">
                                  <c:forEach items="${commentList}" var="comment">
                                      <li class="list-group-item">
                                          评论  <a href="${pageContext.request.contextPath}/rant.action?rantId=${comment.rantId}"><em>RANT</em></a> ${comment.commentContent}
                                      </li>
                                  </c:forEach>
                              </ul>
                          </div>

                          <div class="tab-pane fade" id="ThumbsUp">
                              <c:if test="${myThumbsUpRantList.size()==0}"><h3 style="color: gray">暂无</h3></c:if>
                              <ul class="list-group">
                                  <c:forEach items="${myThumbsUpRantList}" var="rant">
                                      <li class="list-group-item">
                                          <span class="badge">${rantId2ComCntMap[rant.rantId]}</span>
                                          <a href="${pageContext.request.contextPath}/rant.action?rantId=${rant.rantId}">${rant.rantContent}</a>
                                      </li>
                                  </c:forEach>
                              </ul>
                          </div>

                          <div class="tab-pane fade" id="ThumbsDown">
                              <c:if test="${myThumbsDownRantList.size()==0}"><h3 style="color: gray">暂无</h3></c:if>
                              <ul class="list-group">
                                  <c:forEach items="${myThumbsDownRantList}" var="rant">
                                      <li class="list-group-item">
                                          <span class="badge">${rantId2ComCntMap[rant.rantId]}</span>
                                          <a href="${pageContext.request.contextPath}/rant.action?rantId=${rant.rantId}">${rant.rantContent}</a>
                                      </li>
                                  </c:forEach>
                              </ul>
                          </div>
                      </div>
                  </div>
              </div>
          </div>
  </div>


</div>
</div>

<%--修改信息模态框--%>
<div id="editInfo" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body">
                <button class="close" data-dismiss="modal">
                    <span>&times;</span>
                </button>
            </div>
            <div class="modal-title">
                <h1 class="text-center">编辑资料</h1>
            </div>
            <div class="modal-body">
                <form class="form-group" action="${pageContext.request.contextPath}/editInfo.action" method="post">
                    <div class="form-group">
                        <label for="">地区</label>
                        <input name="location" class="form-control" type="text" value="${user.userLocation}">
                    </div>
                    <div class="form-group">
                        <label for="">简介</label>
                        <input name="bio" class="form-control" type="text" value="${user.userBio}">
                    </div>
                    <div class="text-right">
                        <button class="btn btn-primary" type="submit">修改</button>
                        <button class="btn btn-danger" data-dismiss="modal">取消</button>
                    </div>
                    <input type="hidden" name="userId" value="${user.userId}">
                    <a href="" data-toggle="modal" data-dismiss="modal" data-target="#editPwd">修改密码</a>
                </form>
            </div>
        </div>
    </div>
</div>
<%--修改密码模态框--%>
<div id="editPwd" class="modal fade" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body">
                <button class="close" data-dismiss="modal">
                    <span>&times;</span>
                </button>
            </div>
            <div class="modal-title">
                <h1 class="text-center">修改密码</h1>
            </div>
            <div class="modal-body">
                <form class="form-group" action="${pageContext.request.contextPath}/editPwd.action" method="post">
                    <div class="form-group">
                        <label for="">新密码</label>
                        <input name="password" class="form-control" type="password" >
                    </div>
                    <div class="form-group">
                        <label for="">再次输入</label>
                        <input name="passwordAgain" class="form-control" type="password" >
                    </div>
                    <div class="text-right">
                        <button class="btn btn-primary" type="submit">修改</button>
                        <button class="btn btn-danger" data-dismiss="modal">取消</button>
                    </div>
                    <input type="hidden" name="userId" value="${user.userId}">
                    <a href="" data-toggle="modal" data-dismiss="modal" data-target="#editInfo">修改信息</a>
                </form>
            </div>
        </div>
    </div>
</div>


<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"
        integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
        crossorigin="anonymous"></script>
</body>

</html>
