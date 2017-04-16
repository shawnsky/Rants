<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/4/16
  Time: 14:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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


<header class="site-header" style="background-color: #27AE60;">
    <nav class="navbar navbar-static-top main-navbar" id="top">
        <div class="container">
            <div class="navbar-header">
                <a href="/" class="navbar-brand brand-bootcdn" onclick="">Rants</a>
            </div>

            <nav id="bs-navbar" class="collapse navbar-collapse">
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="/api/" onclick="" target="_blank">API</a></li>
                    <li><a href="http://blog.bootcdn.cn/" onclick="" target="_blank">博客</a></li>
                    <li><a href="#about" onclick="">关于</a></li>
                </ul>
            </nav>
        </div>
    </nav>

    <div class="container jumbotron" style="background-color: #27AE60;">
        <div class="row">
            <div class="col-xs-12">
                <h1 style="color: white">你好，世界</h1>
                <p style="color: white">忘了我就没有痛，将往事留在风中<br><span class="package-amount">我是不是该安静地走开</span></p>

                <div class="col-lg-6">
                    <div class="input-group">
                        <input type="text" class="form-control" placeholder="Search for...">
                        <span class="input-group-addon">
                            <input type="checkbox" aria-label="...">
                        </span>
                        <span class="input-group-btn">
                            <button class="btn btn-default" type="button"><span class="glyphicon glyphicon-send"></span> </button>
                        </span>
                    </div><!-- /input-group -->
                </div><!-- /.col-lg-6 -->
            </div>
        </div>
    </div>
</header>

    <!-- 发送内容 -->
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <div class="input-group">

                    <input type="text" class="form-control" aria-label="...">
      <span class="input-group-addon">
        <input type="checkbox" aria-label="...">匿名
      </span>

      <span class="input-group-btn">
        <button class="btn btn-default" type="button">Go!</button>
      </span>
                </div><!-- /input-group -->
            </div><!-- /.col-lg-6 -->
        </div><!-- /.row -->
    </div>

    <!-- 列表 -->
    <div class="container">
        <ul class="list-group">
            <a href="#" class="package list-group-item" target="_blank" onclick="">
                <div class="row">
                    <div class="col-md-1">
                        <div style="background-color: gray; width: 50px; height: 50px;"></div>
                    </div>

                    <div class="col-md-2">
                        <h4 class="package-name">欧阳若雨</h4>
                    </div>
                    <div class="col-md-9 hidden-xs">
                        <p class="package-description">The most popular front-end framework for developing responsive, mobile first projects on the web.</p>
                    </div>
                    <div class="package-extra-info col-md-9 col-md-offset-3 col-xs-12">
                        <span class="badge" style="float: right;">712</span>
                    </div>


                </div>
            </a>

            <a href="#" class="package list-group-item" target="_blank" onclick="">
                <div class="row">
                    <div class="col-md-1"></div>


                    <div class="col-md-2">
                        <h4 class="package-name">神秘人</h4>
                    </div>
                    <div class="col-md-9 hidden-xs">
                        <p class="package-description">The most popular front-end framework for developing responsive, mobile first projects on the web.</p>
                    </div>
                    <div class="package-extra-info col-md-9 col-md-offset-3 col-xs-12">
                        <span class="badge" style="float: right;">7</span>
                    </div>
                </div>
            </a>

            <a href="#" class="package list-group-item" target="_blank" onclick="">
                <div class="row">
                    <div class="col-md-1">

                    </div>

                    <div class="col-md-2">
                        <h4 class="package-name">杨涛</h4>
                    </div>
                    <div class="col-md-9 hidden-xs">
                        <p class="package-description">The most popular front-end framework for developing responsive, mobile first projects on the web.</p>
                    </div>
                    <div class="package-extra-info col-md-9 col-md-offset-3 col-xs-12">
                        <span class="badge" style="float: right;">14468</span>
                    </div>
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