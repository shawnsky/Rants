<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/4/18
  Time: 15:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- 顶部导航 -->

<nav class="navbar navbar-static-top main-navbar navbar-inverse" id="top" >
    <div class="container">
        <div class="navbar-header">
            <a href="${pageContext.request.contextPath}/home.action" class="navbar-brand brand-bootcdn" onclick="">Rants</a>
        </div>

        <nav id="bs-navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav navbar-right">
                <%--已登陆--%>
                <c:if test="${sessionScope.username != null}">
                    <li><a href="${pageContext.request.contextPath}/user.action?userName=${sessionScope.username}">${sessionScope.username}</a></li>
                    <li><a href="${pageContext.request.contextPath }/logout.action" target="_blank">退出</a></li>
                </c:if>

                    <%--未登录--%>
                    <c:if test="${sessionScope.username == null}">
                        <li><a data-toggle="modal" data-target="#register" href=""><span class="glyphicon glyphicon-user"></span> 注册</a></li>
                        <li><a id="loginButton" data-toggle="modal" data-target="#login" href=""><span class="glyphicon glyphicon-log-in"></span> 登录</a></li>
                    </c:if>

                <li><a href="/api/" onclick="" target="_blank">API</a></li>
                <li><a href="http://xtlog.com/" onclick="" target="_blank">博客</a></li>
                <li><a href="#about" onclick="">关于</a></li>
            </ul>
        </nav>
    </div>

</nav>



<!-- 注册窗口 -->
<div id="register" class="modal fade" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body">
                <button class="close" data-dismiss="modal">
                    <span>&times;</span>
                </button>
            </div>
            <div class="modal-title">
                <h1 class="text-center">注册</h1>
            </div>
            <div class="modal-body">
                <form class="form-group" action="${pageContext.request.contextPath}/register.action" method="post">
                    <div class="form-group">
                        <label for="">用户名</label>
                        <input name="username" class="form-control" type="text" placeholder=" ">
                    </div>
                    <div class="form-group">
                        <label for="">密码</label>
                        <input name="password" class="form-control" type="password" placeholder=" ">
                    </div>
                    <div class="form-group">
                        <label for="">再次输入密码</label>
                        <input class="form-control" type="password" placeholder=" ">
                    </div>
                    <div class="text-right">
                        <button class="btn btn-primary" type="submit">提交</button>
                        <button class="btn btn-danger" data-dismiss="modal">取消</button>
                    </div>
                    <a href="" data-toggle="modal" data-dismiss="modal" data-target="#login">已有账号？点我登录</a>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- 登录窗口 -->
<div id="login" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body">
                <button class="close" data-dismiss="modal">
                    <span>&times;</span>
                </button>
            </div>
            <div class="modal-title">
                <h1 class="text-center">登录</h1>
            </div>
            <div class="modal-body">
                <form class="form-group" action="${pageContext.request.contextPath}/login.action" method="post">
                    <div class="form-group">
                        <label for="">用户名</label>
                        <input name="username" class="form-control" type="text" placeholder="">
                    </div>
                    <div class="form-group">
                        <label for="">密码</label>
                        <input name="password" class="form-control" type="password" placeholder="">
                    </div>
                    <div class="text-right">
                        <button class="btn btn-primary" type="submit">登录</button>
                        <button class="btn btn-danger" data-dismiss="modal">取消</button>
                    </div>
                    <a href="" data-toggle="modal" data-dismiss="modal" data-target="#register">点我注册</a>
                </form>
            </div>
        </div>
    </div>
</div>


<%--<script type="text/javascript">--%>
    <%--$(document).ready(function() {--%>
        <%--/**--%>
         <%--* 下面是进行插件初始化--%>
         <%--* 你只需传入相应的键值对--%>
         <%--* */--%>
        <%--$('#defaultForm').bootstrapValidator({--%>
            <%--message: 'This value is not valid',--%>
            <%--feedbackIcons: {/*输入框不同状态，显示图片的样式*/--%>
                <%--valid: 'glyphicon glyphicon-ok',--%>
                <%--invalid: 'glyphicon glyphicon-remove',--%>
                <%--validating: 'glyphicon glyphicon-refresh'--%>
            <%--},--%>
            <%--fields: {/*验证*/--%>
                <%--username: {/*键名username和input name值对应*/--%>
                    <%--message: 'The username is not valid',--%>
                    <%--validators: {--%>
                        <%--notEmpty: {/*非空提示*/--%>
                            <%--message: '用户名不能为空'--%>
                        <%--},--%>
                        <%--stringLength: {/*长度提示*/--%>
                            <%--min: 6,--%>
                            <%--max: 30,--%>
                            <%--message: '用户名长度必须在6到30之间'--%>
                        <%--}/*最后一个没有逗号*/--%>
                    <%--}--%>
                <%--},--%>
                <%--password: {--%>
                    <%--message:'密码无效',--%>
                    <%--validators: {--%>
                        <%--notEmpty: {--%>
                            <%--message: '密码不能为空'--%>
                        <%--},--%>
                        <%--stringLength: {--%>
                            <%--min: 6,--%>
                            <%--max: 30,--%>
                            <%--message: '用户名长度必须在6到30之间'--%>
                        <%--}--%>
                    <%--}--%>
                <%--},--%>
                <%--email: {--%>
                    <%--validators: {--%>
                        <%--notEmpty: {--%>
                            <%--message: 'The email address is required and can\'t be empty'--%>
                        <%--},--%>
                        <%--emailAddress: {--%>
                            <%--message: 'The input is not a valid email address'--%>
                        <%--}--%>
                    <%--}--%>
                <%--}--%>
            <%--}--%>
        <%--});--%>
    <%--});--%>
<%--</script>--%>
