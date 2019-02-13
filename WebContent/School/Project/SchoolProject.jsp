<%@page import="java.sql.ResultSet"%>
<%@page import="dao.BaseDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
	//查询所有项目信息
		String sql = "select * from Project as p LEFT join TEACHER t on p.Tsn = t.Tsn";
		BaseDao baseDao = new BaseDao();
		ResultSet ProjectRs = baseDao.select(sql);
	%>
	<table border="1">
	      <tr>
	        <th>编号</th><th>名称</th><th>负责人</th><th>级别</th><th>类型</th><th>科研状态</th><th>操作</th>
	      </tr>
	    
	 <%
	 	while(ProjectRs.next()){								//取出项目表各字段
	 		String Psn = ProjectRs.getString("Psn");
	 		String Pname = ProjectRs.getString("Pname");
	 		String Pleader = ProjectRs.getString("Tname");			//项目负责人名
	 		String Pgrad = ProjectRs.getString("Pgrad");
	 		String Pkind = ProjectRs.getString("Pkind");
	 		String Pcondition = ProjectRs.getString("Pcondition");
	 %>
	 <tr>
        <td><%=Psn%></td><td><%=Pname %></td><td><%=Pleader %></td><td><%=Pgrad %></td><td><%=Pkind %></td><td><%=Pcondition %></td><td><a href="/TeacherWeb/School/Project/ShowProjectInformation.jsp?Psn=<%= Psn%>" target="select_frame">查看详细信息</a></td>
      </tr>
      <%} %>
      </table>
</body>
</html>