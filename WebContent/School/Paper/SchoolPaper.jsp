<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="dao.BaseDao"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
	//查询所有论文信息
		String sql = "select * from Paper p LEFT join TEACHER t on p.Tsn = t.Tsn";
		BaseDao baseDao = new BaseDao();
		ResultSet PaperRs = baseDao.select(sql);
	%>
	<table border="1">
	      <tr>
	        <th>名称</th><th>第一作者</th><th>期刊</th><th>级别</th><th>备注</th><th>附件</th><th>操作</th>
	      </tr>
	    
	 <%
	 	while(PaperRs.next()){								//取出论文表各字段
	 		String Panum = PaperRs.getString("Panum");		//序号，用来唯一标识
	 		String Paname = PaperRs.getString("Paname");
	 		String Pleader = PaperRs.getString("Tname");			//论文第一作者
	 		String Papublish = PaperRs.getString("Papublish");		//期刊
	 		String Pagrad = PaperRs.getString("Pagrad");
	 		String Paremarks = PaperRs.getString("Paremarks");
	 %>
	 <tr>
        <td><%=Paname%></td><td><%=Pleader %></td><td><%=Papublish %></td><td><%=Pagrad %></td><td><%=Paremarks %></td><td><a href="">查看附件</a></td><td><a class="delete">编辑</a>&nbsp<a class="updata">修改</a></td>
      </tr>
      <%} %>
      </table>
</body>
</html>