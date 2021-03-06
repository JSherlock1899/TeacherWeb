<%@ page import="java.sql.*" language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="/TeacherWeb/UI/CSS/login.css">
<script type="text/javascript" src="/TeacherWeb/UI/JS/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="/TeacherWeb/UI/JS/layui.js"></script>
<script type="text/javascript" src="/TeacherWeb/UI/JS/login.js"></script>
<title>登录界面</title>
</head>
<body>
	<%
		//检验登录状态
		String  status = (String) request.getAttribute("msg");
		if(status == "fail"){
			out.print("<script>alert('用户名或密码错误！')</script>");
		}
	%>
            <form id="indexform" name="indexForm" action="LoginServlet" method="post">
			<div class="layui-canvs"></div>
			<div class="logo">
				<a href="http://www.slxy.cn/"><img src="UI/images/logo.png"></a>
			</div>
			<div class="layui-layout layui-layout-login">
				<h1>
					<strong>教师科研信息管理系统</strong> <em>Teacher Management System</em>
				</h1>
				<div class="layui-user-icon larry-login">
					<input type="text" placeholder="账号" class="login_txtbx"
						name="username" />
				</div>
				<div class="layui-pwd-icon larry-login">
					<input type="password" placeholder="密码" class="login_txtbx"
						name="password" />
				</div>
				<div class="layui-submit larry-login">
					<input type="submit" value="立即登陆"
						class="submit_btn" /></a>
				</div>
				<div class="layui-login-text">
					<p>© 2018-2019&nbsp;商洛学院812实验室&nbsp;版权所有</p>
				</div>
			</div>
			<div class="screenbg">
				<ul>
					<li><a href="javascript:;"><img src="UI/images/2.png"></a></li>
					<li><a href="javascript:;"><img src="UI/images/3.png"></a></li>
					<li><a href="javascript:;"><img src="UI/images/4.png"></a></li>
				</ul>
			</div>
		</form>
</body>
</html>