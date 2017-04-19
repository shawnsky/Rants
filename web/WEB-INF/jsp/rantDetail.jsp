
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

    <title>Rant</title>
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
          crossorigin="anonymous">
    <%--<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap-validator/0.5.3/css/bootstrapValidator.css">--%>

    <script>
        function PushNewComment(){
            var content = document.getElementById("txtCommentContent").value;

            if(${sessionScope.username==null}){
                $('#pleaseLogin').modal('toggle');
                return false;
            }

            if(content == ""){
                $('#pleaseDoNotSendNull').modal('toggle');
                return false;
            }
            document.getElementById("newCommentForm").submit();
        }

        function thumbsUp(){

            if(${sessionScope.username==null}){
                $('#pleaseLogin').modal('toggle');
                return false;
            }

            document.getElementById("thumbsUpForm").submit();
        }

        function thumbsDown(){

            if(${sessionScope.username==null}){
                $('#pleaseLogin').modal('toggle');
                return false;
            }

            document.getElementById("thumbsDownForm").submit();
        }
    </script>

</head>
<body style="background-color: white;;">

<header>
    <%@include file="header.jsp"%><!--静态包含-->
</header>

<div class="row col-lg-6 col-lg-offset-3">
    <%--主要--%>
    <div class="panel panel-default">
        <div class="panel-body">
            <!-- 个人信息 -->
            <div class="page-header">
                <img style=" width:100px;height:100px;border-radius:8px;" src="${rant.rantAvatar}" alt="..." height="100px" width="100px">

                <c:if test="${rant.rantHidden==0}">
                    <h4 class="package-name">
                        <a href="${pageContext.request.contextPath}/user.action?userName=${user.userName}"><h4>${user.userName}</h4></a>
                    </h4>
                </c:if>
                <c:if test="${rant.rantHidden==1}"><h4 class="package-name">神秘人</h4></c:if>
                <small>${rant.rantDate}</small>
                <c:if test="${sessionScope.username == user.userName}">
                    <form id="deleteForm" method="post" action="${pageContext.request.contextPath}/deleteRant.action">
                        <input type="hidden" name="rantId" value="${rant.rantId}"/>
                        <a onclick="document.getElementById('deleteForm').submit();">删除</a>
                    </form>
                </c:if>
            </div>


            <!-- 内容 -->
            <div class="row">
                <div class="col-lg-1">
                    <div class="btn-group-vertical" role="group" aria-label="...">
                        <%--赞--%>
                        <form id="thumbsUpForm" action="${pageContext.request.contextPath}/thumbsUp.action" method="post">
                            <input type="hidden" name="rantId" value="${rant.rantId}"/>
                            <button type="button" class="btn btn-default" onclick="thumbsUp();"><span class="glyphicon glyphicon-thumbs-up"></span></button>
                        </form>
                        <h5>${rant.rantValue}</h5>
                        <%--踩--%>
                        <form id="thumbsDownForm" action="${pageContext.request.contextPath}/thumbsDown.action" method="post">
                            <input type="hidden" name="rantId" value="${rant.rantId}"/>
                            <button type="button" class="btn btn-default" onclick="thumbsDown();"><span class="glyphicon glyphicon-thumbs-down"></span></button>
                        </form>
                    </div>
                </div>
                <div class="col-lg-11">${rant.rantContent}</div>
            </div>
        </div>
    </div>

        <div class="panel panel-default">
            <div class="panel-heading">评论</div>
            <div class="panel-body">
                <ul class="list-group">
                    <c:if test="${commentList.size()==0}">
                        <h3 style="color: gray;">还没有评论哦，抢沙发</h3>
                    </c:if>

                    <div class="row">
                        <form id="newCommentForm" method="post" action="${pageContext.request.contextPath }/sendComment.action">
                            <div class="col-md-10">
                                <input type="text" id="txtCommentContent" name="newCommentContent" class="form-control search clearable" placeholder="写点评..." autocomplete="off">
                            </div>

                            <div class="col-md-2">
                                <input type="hidden" name="rantId" value="${rant.rantId}"/>
                                <button type="button"  class="btn btn-default" onclick="PushNewComment();">发送</button>
                            </div>
                        </form>
                    </div>
                    <h1></h1>
                    <c:forEach items="${commentList}" var="comment">
                        <li class="list-group-item"><strong>${userId2userNameMap[comment.userId]}</strong>：${comment.commentContent}<span class="glyphicon glyphicon-thumbs-up" style="float: right;"></span></li>
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



<%--<script src="https://cdn.bootcss.com/bootstrap-validator/0.5.3/js/bootstrapValidator.js"></script>--%>
<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"
        integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
        crossorigin="anonymous"></script>


</body>
</html>
