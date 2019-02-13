<%@page import="java.sql.ResultSet"%>
<%@page import="dao.BaseDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="/TeacherWeb/UI/JS/jquery.js"></script>
<script type="text/javascript" src="/TeacherWeb/UI/JS/ajaxSelect.js"></script>
</head>
<body>
	<!-- 查询下拉框及按要求进行查询  -->
	<select id="college" name="college" onchange="ajaxSelect()" >
		<option value="0">请选择所在学院</option>
		<%
			BaseDao baseDao = new BaseDao();
			String sql = "select * from College";
			ResultSet CollegeRs = baseDao.select(sql);
			while (CollegeRs.next()) {
				String CollegeName = CollegeRs.getString("Cname"); //获取学院名
		%>
		<option value="<%=CollegeName%>">
			<%=CollegeName%>
		</option>
		<%} %>
	</select>
</body>
</html>