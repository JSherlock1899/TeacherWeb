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
		String Psn = request.getParameter("Psn");	//获取对应的项目编号
		String sql = "select * from Project as p LEFT join TEACHER t on p.Tsn = t.Tsn where Psn = '" + Psn + "'";
		BaseDao baseDao = new BaseDao();
		ResultSet ProjectRs = baseDao.select(sql);
	%>
	<table border="1">
	      <tr>
	        <th>项目编号</th><th>项目名称</th><th>负责人</th><th>级别</th><th>类型</th><th>科研状态</th>
	      </tr>
	    
	 <%
	 	if(ProjectRs.next()){								//取出项目表各字段
	 		String Pname = ProjectRs.getString("Pname");
	 		String Pleader = ProjectRs.getString("Tname");			//项目负责人名
	 		String Pgrad = ProjectRs.getString("Pgrad");
	 		String Pkind = ProjectRs.getString("Pkind");
	 		String Pcondition = ProjectRs.getString("Pcondition");
	 		
	 %>
	 <tr>
        <td><%=Psn %></td><td><%=Pname %></td><td><%=Pleader %></td><td><%=Pgrad %></td><td><%=Pkind %></td><td><%=Pcondition %></td>
      </tr>
      <tr>
        <th>成员</th><th>经费</th><th>立项时间</th><th>结题时间</th><th colspan=2>备注</th>
      </tr>
      <%
      		String Pmember = ProjectRs.getString("Pmember");
      		String Pmoney = ProjectRs.getString("Pmoney");
      		String Pstatime = ProjectRs.getString("Pstatime");			//立项时间
      		String Pendtime = ProjectRs.getString("Pendtime");		//结题时间
      		String Premarks = ProjectRs.getString("Premarks");			//项目备注
      		if(Pendtime == null){
      			Pendtime = "未结题";
      		}
      		
      %>
      <tr>
        <td><%=Pmember%></td><td><%=Pmoney %></td><td><%=Pstatime %></td><td><%=Pendtime %></td><td colspan=2><%=Premarks %></td>
      </tr>
      <%} %>
      </table>
</body>
</html>