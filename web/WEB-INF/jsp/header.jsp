<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/4/18
  Time: 15:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- 顶部导航 -->

<nav class="navbar navbar-static-top main-navbar" id="top" style="background-color: #F5F5F5;">
    <div class="container">
        <div class="navbar-header">
            <a href="${pageContext.request.contextPath}/home.action" class="navbar-brand brand-bootcdn" onclick="">Rants</a>
        </div>

        <nav id="bs-navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav navbar-right">
                <%--已登陆--%>
                <c:if test="${sessionScope.username != null}">
                    <li><a href="#">${sessionScope.username}</a></li>
                    <li><a href="${pageContext.request.contextPath }/logout.action" target="_blank">退出</a></li>
                </c:if>

                    <%--未登录--%>
                    <c:if test="${sessionScope.username == null}">
                        <li><a href="${pageContext.request.contextPath }/login.action">登录</a></li>
                    </c:if>

                <li><a href="/api/" onclick="" target="_blank">API</a></li>
                <li><a href="http://blog.bootcdn.cn/" onclick="" target="_blank">博客</a></li>
                <li><a href="#about" onclick="">关于</a></li>
            </ul>
        </nav>
    </div>
</nav>
