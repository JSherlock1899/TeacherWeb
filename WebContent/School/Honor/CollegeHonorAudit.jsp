<%@page import="model.Pager"%>
<%@page import="java.util.List"%>
<%@page import="model.Honor"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="dao.impl.BaseDaoImpl"%>
<%@page import="dao.IBaseDao"%>
<%@page import="dao.impl.HonorDaoImpl"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>项目审核</title>
<link rel="stylesheet" href="/TeacherWeb/UI/CSS/bootstrap.css">
<script type="text/javascript" src="/TeacherWeb/UI/JS/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="/TeacherWeb/UI/JS/ajaxHonorData.js"></script>
<script type="text/javascript" src="/TeacherWeb/UI/JS/bootstrap-table.js"></script>
<script type="text/javascript" src="/TeacherWeb/UI/JS/ajaxSelect.js"></script>
<script type="text/javascript" src="/TeacherWeb/UI/JS/commonUse.js"></script>
<link rel="stylesheet" href="/TeacherWeb/UI/CSS/style.css">
</head>
<body>
				<%
							IBaseDao basedao = new BaseDaoImpl();
							Pager pager = (Pager) request.getAttribute("pager");
							List<Honor> datalist =  pager.getDataList();	//要显示的数据集合
							int currentPage =  pager.getCurrentPage();		//获取当前页码
							int pageSize = pager.getPageSize();			//获取每页显示多少条数据
							int totalRecord = pager.getTotalRecord();		//获取数据总条数
							int totalPage = pager.getTotalPage();			//总页数
							int[] pageArr = (int[]) request.getAttribute("pageArr"); 
							String college = (String) request.getAttribute("college");	//搜索的条件
							String sdept = (String) request.getAttribute("sdept");
							String starttime = (String) request.getAttribute("starttime");
							String endtime = (String) request.getAttribute("endtime");
							String Tname = (String) request.getAttribute("Tname");
							int grade = (int) session.getAttribute("grade");	//获取用户的权限等级
				%>
	<div class="table-main col-md-12">
		<div class="col-md-4" >
		  <ol class="breadcrumb">
		    <li><a href="#">主页</a></li>
		    <li><a href="#">审核</a></li>
		    <li class="active">荣誉审核</li>
		  </ol>
		   <input id="currentPage" value=<%=currentPage%> type="hidden">
		</div>
		<div class="row">
			<div class="col-md-11 col-md-offset-1 ">
				<div class="col-md-10 button-div form-inline">
					<table border="1" id="table" class="table table-striped table-bordered table-hover table-condensed">
							<tr class="info">
								<th>编号</th>
								<th>名称</th>
								<th>获奖者</th>
								<th>时间</th>
								<th>颁奖单位</th>
								<th>级别</th>
								<th>奖金</th>
								<th>备注</th>
							</tr>
							<%	
								for(int i=0; i<datalist.size(); i++){
									String Hsn = datalist.get(i).getHsn();
									
							%>
							<tr>
								<td class="Hsn edit"><%=Hsn%></td>
								<td class="Hname edit"><a href="../servlet/AuditServlet?count=1&order=<%=i %>&option=Honor&college=<%=college%>
								&pageSize=<%=pageSize%>&currentPage=<%=currentPage%>"><%=datalist.get(i).getHname()%></a></td>
								<td class="Hwinner edit"><%=datalist.get(i).getHwinner()%></td>
								<td class="Hdate edit"><%=datalist.get(i).getHdate()%></td>
								<td class="Hcompany edit"><%=datalist.get(i).getHcompany()%></td>
								<td class="Hgrad edit"><%=datalist.get(i).getHgrad()%></td>
								<td class="Hreward edit"><%=datalist.get(i).getHreward()%></td>
								<td class="Hremarks edit"><%=datalist.get(i).getHremarks()%></td>							
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
						<a href="../servlet/AuditServlet?option=Honor&currentPage=1&college=<%=college %>&pageSize=<%=pageSize%>" id="homePage">首页</a>
					</li>
					<li>
						<a aria-label="Previous" id="pre" class="prenextpage" href="../servlet/AuditServlet?option=Honor&college=<%=college %>&currentPage=<%=currentPage - 1%>&pageSize=<%=pageSize%>
					"> 
							<span >&laquo;</span>
						</a>
					</li>
					<li id="page1"><a class="page" href="../servlet/AuditServlet?option=Honor&currentPage=<%=pageArr[0]%>&pageSize=<%=pageSize%>&college=<%=college %>
					"><%=pageArr[0]%></a></li>
					<li id="page2"><a class="page" href="../servlet/AuditServlet?option=Honor&currentPage=<%=pageArr[1]%>&pageSize=<%=pageSize%>&college=<%=college %>
					"><%=pageArr[1]%></a></li>
					<li id="page3"><a class="page" href="../servlet/AuditServlet?option=Honor&currentPage=<%=pageArr[2]%>&pageSize=<%=pageSize%>&college=<%=college %>
					"><%=pageArr[2]%></a></li>
					<li>
						<a id="next" aria-label="Next" class="prenextpage" href="../servlet/AuditServlet?option=Honor&currentPage=<%=currentPage + 1%>&pageSize=<%=pageSize%>&college=<%=college %>
					"> 
							<span>&raquo;</span>
						</a>
					</li>
					<li><a href="../servlet/AuditServlet?option=Honor&currentPage=<%=totalPage %>&college=<%=college %>&pageSize=<%=pageSize%>" id="endPage" >尾页</a></li>
					<li id="totalPage" value="<%=totalPage %>"><span>当前第<%=currentPage %>页，共<%=totalRecord %>条记录</span></li>
				</ul>
				</nav>
			</form>
			<div class="form-group pull-right">
			  	共<%=totalPage %>页
			  <input type="text" class="pageVal" style="width:100px;">
			  <button type="submit" class="btn btn-default" id="pageGo" onclick="skipPage()">GO</button>
			</div>
		</div>
	 </div>
	</div>
	<script type="text/javascript">
	$(document).on("change","#pageSize",function(){			//根据下拉框值的改变改变每页显示的记录条数
		var pageSizeSelect = $("#pageSize option:selected").val();
		var href = "";
		var a = "../servlet/AuditServlet?option=Honor&currentPage=<%=currentPage%>&pageSizeSelect=";
		var b = "&college=<%=college%>&sdeptValue=<%=sdept%>&starttime=<%=starttime%>&endtime=<%=endtime%>&selectByNameVal=<%=Tname%>&teacher=admin"
		href = href + a + pageSizeSelect + b;
		window.location.href = href;
	})
	
	function skipPage(){								//输入页码跳转页面
		//页码输入框输入的数
		var pageVal = $('.pageVal').val();
		//总页数
		var totalPage = $('#totalPage').val();
		//一页显示的条数
		var pageSize = $('#pageSize').val();
		console.log(pageVal)
		console.log(totalPage)
		if(pageVal > totalPage){
			alert('请输入正确的页码！');
			return
		}
		var path = "";
		var a = "../servlet/AuditServlet?option=Honor&currentPage=";
		var b = "&pageSizeSelect=<%=pageSize%>&college=<%=college%>&sdeptValue=<%=sdept%>&starttime=<%=starttime%>&endtime=<%=endtime%>&selectByNameVal=<%=Tname%>&teacher=admin"
		path = path + a + pageVal + b;
		window.location.href = path;
	}
		
		
	</script>
</body>
</html>