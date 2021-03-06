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
    <%--<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap-validator/0.5.3/css/bootstrapValidator.css">--%>

    <script>
        function PushNewRant(){
            var content = document.getElementById("txtContent").value;

            if(${sessionScope.username==null}){
                $('#pleaseLogin').modal('toggle');
                return false;
            }

            if(content == ""){
                $('#pleaseDoNotSendNull').modal('toggle');
                return false;
            }
            document.getElementById("newRantForm").submit();
        }

//        function getDateDiff(dateTimeStamp){
//            var minute = 1000 * 60;
//            var hour = minute * 60;
//            var day = hour * 24;
//            var halfamonth = day * 15;
//            var month = day * 30;
//            var now = new Date().getTime();
//            var diffValue = now - dateTimeStamp;
//            if(diffValue < 0){return;}
//            var monthC =diffValue/month;
//            var weekC =diffValue/(7*day);
//            var dayC =diffValue/day;
//            var hourC =diffValue/hour;
//            var minC =diffValue/minute;
//            if(monthC>=1){
//                result="" + parseInt(monthC) + "月前";
//            }
//            else if(weekC>=1){
//                result="" + parseInt(weekC) + "周前";
//            }
//            else if(dayC>=1){
//                result=""+ parseInt(dayC) +"天前";
//            }
//            else if(hourC>=1){
//                result=""+ parseInt(hourC) +"小时前";
//            }
//            else if(minC>=1){
//                result=""+ parseInt(minC) +"分钟前";
//            }else
//                result="刚刚";
//            return result;
//        }

    </script>
</head>
<body style="background-color: #e3e3e3;">


<header class="site-header" >
    <%@include file="header.jsp"%><!--静态包含-->
</header>


<div class="container">
    <div class="row">
        <ul class="col-lg-9 list-group">
            <!-- 发送 -->
            <div class="package list-group-item" style="background-color: #F5F5F5;padding-top: 0;">
                <div class="row" >
                    <h3><span class="col-md-1 glyphicon glyphicon-pencil hidden-sm hidden-xs" style="padding: 0 0 0 0;text-align: center;"></span></h3>

                    <form id="newRantForm" method="post" action="${pageContext.request.contextPath }/sendRant.action">
                        <div class="col-xs-9" style="padding: 0">
                            <input type="text" id="txtContent" name="newRantContent" class="form-control search clearable" placeholder="说点有趣的..." autocomplete="off">
                        </div>
                        <div class="col-xs-2">

                                <div class="btn-group">
                                    <button type="button" class="btn btn-danger" style="background-color: white;color: black;" onclick="PushNewRant();">快捷发送</button>
                                    <button style="height: 34px;background-color: #D55161;" type="button" class="btn btn-info dropdown-toggle hidden-xs hidden-sm" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                        <span class="caret"></span>
                                    </button>
                                    <ul class="dropdown-menu">
                                        <li style="padding: 5px;"><input type="checkbox" name="newRantCheckBox" value="yes">匿名发送</li>
                                    </ul>
                                </div>


                        </div>
                    </form>
                </div>
            </div>
            <%--列表--%>
            <%--如果不多于10条--%>
            <c:if test="${rantList.size()<=10}">
                <c:forEach items="${rantList}" var="rant">
                    <a href="${pageContext.request.contextPath }/rant.action?rantId=${rant.rantId}" class="package list-group-item">
                        <div class="row">
                            <div class="col-md-1">
                                <img style=" width:50px;height:50px;border-radius:4px;" src="${rant.rantAvatar}" alt="..." height="50px" width="50px">
                            </div>

                            <div class="col-md-11">
                                <span class="badge" style="float: right;">${rantId2ComCntMap[rant.rantId]}</span>
                                <h4 class="package-description">${rant.rantContent}</h4>
                                <c:if test="${rant.rantHidden==0}"><p class="package-name"><strong>${userId2NameMap[rant.userId]}</strong> 发表于 ${rant.rantDate}</p></c:if>
                                <c:if test="${rant.rantHidden==1}"><p class="package-name"><strong>神秘人</strong> 发表于 ${rant.rantDate}</p></c:if>
                            </div>
                        </div>
                    </a>
                </c:forEach>
            </c:if>
            <%--如果多于10条--%>
            <c:if test="${rantList.size()>10}">
                <c:forEach items="${rantList}" var="rant" end="10">
                    <a href="${pageContext.request.contextPath }/rant.action?rantId=${rant.rantId}" class="package list-group-item">
                        <div class="row">
                            <div class="col-md-1">
                                <img style=" width:50px;height:50px;border-radius:4px;" src="${rant.rantAvatar}" alt="..." height="50px" width="50px">
                            </div>

                            <div class="col-md-11">
                                <span class="badge" style="float: right;">${rantId2ComCntMap[rant.rantId]}</span>
                                <h4 class="package-description">${rant.rantContent}</h4>
                                <c:if test="${rant.rantHidden==0}"><p class="package-name"><strong>${userId2NameMap[rant.userId]}</strong> 发表于 ${rant.rantDate}</p></c:if>
                                <c:if test="${rant.rantHidden==1}"><p class="package-name"><strong>神秘人</strong> 发表于 ${rant.rantDate}</p></c:if>
                            </div>
                        </div>
                    </a>
                </c:forEach>
                <a href="${pageContext.request.contextPath}/allRant.action" class="package list-group-item" >
                    <div class="row">
                        <h4 style="text-align: center;">查看更多</h4>
                    </div>
                </a>
            </c:if>
        </ul>

        <%--右边栏--%>
        <div class="col-lg-3">
            <div class="panel panel-default">
                <img src="http://i1.piimg.com/567571/f7b682078d8eb6ae.png" height="200px" width="200px">
                <div class="panel-body">
                    <h3>全新App可用！</h3>
                </div>
            </div>

            <div class="panel panel-default">
                <div class="panel-heading">三日内热门</div>
                <div class="panel-body">
                    <c:forEach items="${recentHotRantList}" var="recentHotRant">
                        <h5><a href="${pageContext.request.contextPath}/rant.action?rantId=${recentHotRant.rantId}" style="color: black">${recentHotRant.rantContent}</a></h5>
                    </c:forEach>


                </div>
            </div>

            <div class="panel panel-default">
                <div class="panel-heading">精华内容</div>
                <div class="panel-body">
                    <c:forEach items="${starRantList}" var="starRant">
                        <h5><a href="${pageContext.request.contextPath}/rant.action?rantId=${starRant.rantId}" style="color: black">${starRant.rantContent}</a></h5>
                    </c:forEach>

                </div>
            </div>



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


<%@include file="footer.jsp"%><!--静态包含-->

<%--<script src="https://cdn.bootcss.com/bootstrap-validator/0.5.3/js/bootstrapValidator.js"></script>--%>
<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"
        integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
        crossorigin="anonymous"></script>
</body>
</html>