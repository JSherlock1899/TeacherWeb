<%@ page import="java.sql.*" language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
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
    <center>
        <h1 style="color:red">登录</h1>
            <form id="indexform" name="indexForm" action="LoginServlet" method="post">
                <table border="0">
                    <tr>
                        <td>用户名：</td>
                        <td><input type="text" name="username"></td>
                    </tr>
                    <tr>
                        <td>密码：</td>
                        <td><input type="password" name="password">
                        </td>
                    </tr>
                </table>
            <br>
                <input type="submit" value="登录" style="color:#BC8F8F">
            </form>
    </center>
</body>
</html>