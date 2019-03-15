<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>论文统计</title>
<link rel="stylesheet" href="/TeacherWeb/UI/CSS/bootstrap.css">
<link rel="stylesheet" href="/TeacherWeb/UI/CSS/style.css">
<script type="text/javascript" src="/TeacherWeb/UI/JS/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="/TeacherWeb/UI/JS/echarts.min.js"></script>
<script type="text/javascript" src="/TeacherWeb/UI/JS/bootstrap-table.js"></script>
<script type="text/javascript" src="/TeacherWeb/UI/JS/statisticsPaper.js"></script>
</head>
<body>
	<%
		JSONObject json = (JSONObject)request.getAttribute("json");
		JSONArray jsonList = (JSONArray)request.getAttribute("jsonList");
		String grade = (String)request.getAttribute("grade");	//获取用户的权限等级	
	%>
	<ol class="breadcrumb">
		    <li><a href="#">主页</a></li>
		    <li><a href="#">统计</a></li>
		    <li class="active">论文统计</li>
		  </ol>
	<input id="json" value=<%=json%> style="display:none">
	<input id="grade" value=<%=grade%> style="display:none">
	<input id="jsonList" value=<%=jsonList%> style="display:none">
	<div class="container-fluid">
	  <div class="row">
	    <div id="paper_bar" class="col-md-6" style="height:400px"></div>
	    <div id="paper_pie" class="col-md-5 col-md-offset-1" style="height:400px"></div>
	  </div>
	</div>
	
	<div class="container-fluid">
	  <div class="row">
	    <div id="paper_broken_line" class="col-md-12" style="height:400px"></div>
	  </div>
	</div>
	
</body>
</html>