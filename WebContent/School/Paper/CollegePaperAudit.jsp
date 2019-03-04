<%@page import="model.Pager"%>
<%@page import="java.util.List"%>
<%@page import="model.Paper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="dao.impl.BaseDaoImpl"%>
<%@page import="dao.IBaseDao"%>
<%@page import="dao.impl.PaperDaoImpl"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>项目审核</title>
<link rel="stylesheet" href="/TeacherWeb/UI/CSS/bootstrap.css">
<script type="text/javascript" src="/TeacherWeb/UI/JS/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="/TeacherWeb/UI/JS/ajaxPaperData.js"></script>
<script type="text/javascript" src="/TeacherWeb/UI/JS/bootstrap-table.js"></script>
<script type="text/javascript" src="/TeacherWeb/UI/JS/ajaxSelect.js"></script>
<script type="text/javascript" src="/TeacherWeb/UI/JS/commonUse.js"></script>
<link rel="stylesheet" href="/TeacherWeb/UI/CSS/style.css">
</head>
<body>
				<%
							IBaseDao basedao = new BaseDaoImpl();
							Pager pager = (Pager) request.getAttribute("pager");
							List<Paper> datalist =  pager.getDataList();	//要显示的数据集合
							int currentPage =  pager.getCurrentPage();		//获取当前页码
							int pageSize = pager.getPageSize();			//获取每页显示多少条数据
							int totalRecord = pager.getTotalRecord();		//获取数据总条数
							int totalPage = pager.getTotalPage();			//总页数
							int[] pageArr = (int[]) request.getAttribute("pageArr");
							String college = (String) request.getAttribute("college"); 
							int grade = (int) session.getAttribute("grade");	//获取用户的权限等级
				%>
	<div class="table-main col-md-12">
		<div class="col-md-4" >
		  <ol class="breadcrumb">
		    <li><a href="#">主页</a></li>
		    <li><a href="#">审核</a></li>
		    <li class="active">论文审核</li>
		  </ol>
		   <input id="college" value=<%=college%> type="hidden">
		   <input id="currentPage" value=<%=currentPage%> type="hidden">
		   <input id="totalPage" value=<%=totalPage%> type="hidden">
		   <input id="pageSize" value=<%=pageSize%> type="hidden">
		</div>
		<div class="row">
			<div class="col-md-11 col-md-offset-1 ">
				<div class="col-md-10 button-div form-inline">
					<table border="1" id="table" class="table table-striped table-bordered table-hover table-condensed">
							<tr class="info">
								<th>检索号</th>
								<th>名称</th>
								<th>第一作者</th>
								<th>发表期刊</th>
								<th>发表期刊</th>
								<th>发表时间</th>
								<th>级别</th>
								<th>备注</th>
								<th>操作</th>
							</tr>
							<%	
								for(int i=0; i<datalist.size(); i++){
									String Pasearchnum = datalist.get(i).getPasearchnum();
									
							%>
							<tr>
								<td class="Pasearchnum edit"><a href="../servlet/AuditServlet?count=1&order=<%=i %>&option=Paper&college=<%=college%>">
								<%=Pasearchnum%></a></td>
								<td class="Paname edit"><%=datalist.get(i).getPaname()%></td>
								<td class="Pawriter edit"><%=datalist.get(i).getPawriter()%></td>
								<td class="Papublish edit"><%=datalist.get(i).getPapublish()%></td>
								<td class="Pdisvol edit"><%=datalist.get(i).getPdisvol()%></td>
								<td class="Padate edit"><%=datalist.get(i).getPadate()%></td>
								<td class="Pagrad edit"><%=datalist.get(i).getPagrad()%></td>
								<td class="Paremarks edit"><%=datalist.get(i).getParemarks()%></td>
								<td class=""><a id="pass">通过</a>&nbsp<a id="nopass">不通过</a></td>
							</tr>
							
							<% } 
					      basedao.closeCon();%>
						</table>
				<nav aria-label="Page navigation">
				<ul class="pagination" style="display:block">
					<li>
						<span >
							 <select name="pageSize" id="pageSize">
										 <option value="<%=pageSize %>"><%=pageSize %></option>
										<option value="5">5</option>
										<option value="10">10</option>
										<option value="15">15</option>
										<option value="25">25</option>
								</select>
					</span>
					</li>
					<li>
						<a href="../servlet/AuditServlet?option=Paper&currentPage=1&pageSize=<%=pageSize %>" id="homePage">首页</a>
					</li>
					<li>
						<a aria-label="Previous" id="pre" class="prenextpage" href="../servlet/AuditServlet?option=Paper&currentPage=<%=currentPage - 1%>&pageSize=<%=pageSize %>
					"> 
							<span >&laquo;</span>
						</a>
					</li>
					<li id="page1"><a class="page" href="../servlet/AuditServlet?option=Paper&currentPage=<%=pageArr[0]%>&pageSize=<%=pageSize %>
					"><%=pageArr[0]%></a></li>
					<li id="page2"><a class="page" href="../servlet/AuditServlet?option=Paper&currentPage=<%=pageArr[1]%>&pageSize=<%=pageSize %>
					"><%=pageArr[1]%></a></li>
					<li id="page3"><a class="page" href="../servlet/AuditServlet?option=Paper&currentPage=<%=pageArr[2]%>&pageSize=<%=pageSize %>
					"><%=pageArr[2]%></a></li>
					<li>
						<a id="next" aria-label="Next" class="prenextpage" href="../servlet/AuditServlet?option=Paper&currentPage=<%=currentPage + 1%>&pageSize=<%=pageSize %>
					"> 
							<span>&raquo;</span>
						</a>
					</li>
					<li><a href="../servlet/AuditServlet?option=Paper&currentPage=<%=totalPage %>&pageSize=<%=pageSize %>" id="endPage" >尾页</a></li>
					<li><span>当前第<%=currentPage %>页，共<%=totalRecord %>条记录</span></li>
				</ul>
				</nav>
			</form>
			<div class="form-group pull-right">
			  	共<%=totalPage %>页
			  <input type="text" class="pageVal" style="width:100px;">
			  <button type="submit" class="btn btn-default " id="pageGo">GO</button>
			</div>
		</div>
	 </div>
	</div>
	<script type="text/javascript">

		$(document).on("change","#pageSize",function(){			//根据下拉框值的改变改变每页显示的记录条数
			var pageSizeSelect = $("#pageSize option:selected").val();
			var href = "";
			var a = "../servlet/AuditServlet?option=Paper&currentPage=<%=currentPage%>&pageSizeSelect=";
			href = href + a + pageSizeSelect;
			window.location.href = href;
		})
		
		
		

	</script>
</body>
</html>