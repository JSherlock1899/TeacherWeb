<%@page import="org.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/TeacherWeb/UI/CSS/bootstrap.css">
<script type="text/javascript" src="/TeacherWeb/UI/JS/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="/TeacherWeb/UI/JS/echarts.min.js"></script>
<script type="text/javascript" src="/TeacherWeb/UI/JS/bootstrap-table.js"></script>
<script type="text/javascript" src="/TeacherWeb/UI/JS/statisticsProject.js"></script>
</head>
<body>
	<%
		JSONObject json = (JSONObject)request.getAttribute("json");
		JSONObject Pmoney = (JSONObject)request.getAttribute("Pmoney");

	%>
	<input id="json" value=<%=json%> style="display:none">
	<input id="Pmoneyjson" value=<%=Pmoney%> style="display:none">
	<div id="main" style="width: 600px;height:400px;"></div>
	<div id="Pmoney" style="width: 600px;height:400px;"></div>
	
</body>
</html>