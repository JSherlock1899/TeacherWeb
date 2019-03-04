<%@page import="model.Pager"%>
<%@page import="java.util.List"%>
<%@page import="model.Teacher"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="dao.impl.BaseDaoImpl"%>
<%@page import="dao.IBaseDao"%>
<%@page import="dao.impl.TeacherDaoImpl"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>教师查询</title>
<link rel="stylesheet" href="/TeacherWeb/UI/CSS/bootstrap.css">
<script type="text/javascript" src="/TeacherWeb/UI/JS/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="/TeacherWeb/UI/JS/ajaxTeacherData.js"></script>
<script type="text/javascript" src="/TeacherWeb/UI/JS/commonUse.js"></script>
<script type="text/javascript" src="/TeacherWeb/UI/JS/bootstrap-table.js"></script>
<script type="text/javascript" src="/TeacherWeb/UI/JS/ajaxSelect.js"></script>
<link rel="stylesheet" href="/TeacherWeb/UI/CSS/style.css">
</head>
<body>
				<%
							IBaseDao basedao = new BaseDaoImpl();
							Pager pager = (Pager) request.getAttribute("pager");
							List<Teacher> datalist =  pager.getDataList();	//要显示的数据集合
							int currentPage =  pager.getCurrentPage();		//获取当前页码
							int pageSize = pager.getPageSize();			//获取每页显示多少条数据
							int totalRecord = pager.getTotalRecord();		//获取数据总条数
							int totalPage = pager.getTotalPage();			//总页数
							String college = (String) request.getAttribute("college");	//搜索的条件
							String sdept = (String) request.getAttribute("sdept");
							String Tname = (String) request.getAttribute("Tname");
							int[] pageArr = (int[]) request.getAttribute("pageArr"); 
							int grade = (int) session.getAttribute("grade");	//获取用户的权限等级
				%>
	<div class="table-main col-md-12">
		<input type="hidden" id="college" value="<%=college %>"/>
		<input type="hidden" id="sdept" value="<%=sdept %>"/>
		<input type="hidden" id="Tname" value="<%=Tname %>"/>
		<input type="hidden" id="grade" value="<%=grade %>"/>
		<input type="hidden" id="totalPage" value="<%=totalPage %>"/>
		<input type="hidden" id="currentPage" value="<%=currentPage %>"/>
		<input type="hidden" id="totalRecord" value="<%=totalRecord %>"/>
		<div class="col-md-4" >
		  <ol class="breadcrumb">
		    <li><a href="#">主页</a></li>
		    <li><a href="#">其他</a></li>
		    <li class="active">教师管理</li>
		  </ol>
		</div>
		<div class="row">
			<div class="col-md-11 col-md-offset-1 ">
				<div class="col-md-10 button-div form-inline">
					<input type="button" value="新建记录" id="addMsg" class="btn btn-success"> 
					<input type="button" name="" value="导入"  class="btn btn-info" id="imporFileButton">		
					<form action="../servlet/ExportServlet?sdept=<%=sdept %>&college=<%=college %>&Tname=<%=Tname %>&totalRecord=<%=totalRecord %>&count=1"  method="post" id="PatentForm" class="form-group">						<input type="hidden" name="count" value="1">
						<input type="submit" value="导出" id="submitChecked" class="btn btn-info">
						<input type="file" id="file" name="file"  class="btn btn-info" style="display: none" onchange="submitFile()"> 
				</div>
					<table border="1" id="table" class="table table-striped table-bordered table-hover table-condensed">
							<tr class="info">
								<th>教师号</th>
								<th>教师名</th>
								<th>性别</th>
								<th>联系电话</th>
								<th>邮箱</th>
								<th>身份证号</th>
								<th>所属学院</th>
								<th>所属系</th>							
								<th>操作</th>							
							</tr>
							<%	
								for(int i=0; i<datalist.size(); i++){
									String Tsn = datalist.get(i).getTsn();
									
							%>
							<tr>
								<td class="Tsn edit"><%=Tsn%></td>
								<td class="Tname edit"><%=datalist.get(i).getTname()%></td>
								<td class="Tsex edit"><%=datalist.get(i).getTsex()%></td>
								<td class="Ttel edit"><%=datalist.get(i).getTtel()%></td>
								<td class="Tmail edit"><%=datalist.get(i).getTmail()%></td>
								<td class="TID edit"><%=datalist.get(i).getTID()%></td>								
								<td class="Cname edit"><%=datalist.get(i).getCname()%></td>
								<td class="Dname edit"><%=datalist.get(i).getDname()%></td>
								<td class=""><a class="delete">删除</a>&nbsp<a class="Updata">编辑</a></td>
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
						<a href="../servlet/PageServlet?option=Teacher&currentPage=1&teacher=admin" id="homePage">首页</a>
					</li>
					<li>
						<a aria-label="Previous" id="pre" class="prenextpage" href="../servlet/PageServlet?option=Teacher&currentPage=<%=currentPage - 1%>&pageSize=5
					&collegevalue=<%=college%>&sdeptValue=<%=sdept%>&teacher=admin
					&selectByNameVal=<%=Tname%>"> 
							<span >&laquo;</span>
						</a>
					</li>
					<li id="page1"><a class="page" href="../servlet/PageServlet?option=Teacher&currentPage=<%=pageArr[0]%>&pageSize=5
					&collegevalue=<%=college%>&sdeptValue=<%=sdept%>&teacher=admin
					&selectByNameVal=<%=Tname%>"><%=pageArr[0]%></a></li>
					<li id="page2"><a class="page" href="../servlet/PageServlet?option=Teacher&currentPage=<%=pageArr[1]%>&pageSize=5
					&collegevalue=<%=college%>&sdeptValue=<%=sdept%>&teacher=admin
					&selectByNameVal=<%=Tname%>"><%=pageArr[1]%></a></li>
					<li id="page3"><a class="page" href="../servlet/PageServlet?option=Teacher&currentPage=<%=pageArr[2]%>&pageSize=5
					&collegevalue=<%=college%>&sdeptValue=<%=sdept%>&teacher=admin
					&selectByNameVal=<%=Tname%>"><%=pageArr[2]%></a></li>
					<li>
						<a id="next" aria-label="Next" class="prenextpage" href="../servlet/PageServlet?option=Teacher&currentPage=<%=currentPage + 1%>&pageSize=5
					&collegevalue=<%=college%>&sdeptValue=<%=sdept%>&teacher=admin
					&selectByNameVal=<%=Tname%>"> 
							<span>&raquo;</span>
						</a>
					</li>
					<li><a href="../servlet/PageServlet?option=Teacher&currentPage=<%=totalPage %>&teacher=admin" id="endPage" >尾页</a></li>
					<li id="totalPage" value="<%=totalPage %>"><span>当前第<%=currentPage %>页，共<%=totalRecord %>条记录</span></li>
				</ul>
				</nav>
			</form>
			<div class="form-group pull-right">
			  	共<%=totalPage %>页
			  <input type="text" class="pageVal" style="width:100px;">
			  <button type="submit" class="btn btn-default " onclick="skipPage()">GO</button>
			</div>
			<!--新建信息的模态框 -->
			<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" >
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
              				<h4 class="modal-title" id="myModalLabel">修改教师信息</h4>
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">×</span>
							</button>
						</div>
						<div class="modal-body">
							<div class="form-group">
								<label for="Tsn">教师号</label> <input type="text" name="Tsn" 
									class="form-control" id="Tsn" placeholder="教师号">
							</div>
							<div class="form-group">
								<label for="Tname">教师名</label> <input type="text" name="Tname"
									class="form-control" id="Tname" placeholder="教师名">
							</div>
							<div class="form-group">
								<label for="Tsex">性别</label> <input type="text"
									name="Tsex" class="form-control" id="Tsex"
									placeholder="性别">
							</div>
							<div class="form-group">
								<label for="Ttel">联系电话</label> <input type="date"
									name="Ttel" class="form-control" id="Ttel"
									placeholder="联系电话">
							</div>
							<div class="form-group">
								<label for="Tmail">邮箱</label> <input type="date" name="Tmail"
									class="form-control" id="Tmail" placeholder="邮箱">
							</div>
							<div class="form-group">
								<label for="TID">身份证号</label> <input type="text" name="TID"
									class="form-control" id="TID" placeholder="身份证号">
							</div>
							<div class="form-group">
								<label for="Patremarks">所属学院</label> <input type="text"
									name="Patremarks" class="form-control" id="Patremarks"
									placeholder="所属学院">
							</div>
							<div class="form-group">
								<label for="Patremarks">所属系</label> <input type="text"
									name="Patremarks" class="form-control" id="Patremarks"
									placeholder="所属系">
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭
							</button>
							<button type="submit" id="btn_submit"
								class="btn btn-primary saveNewMsg">
								<span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>保存
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	 </div>
	</div>
	<script type="text/javascript">
	
		
		$(document).on("change","#pageSize",function(){			//根据下拉框值的改变改变每页显示的记录条数
			var pageSizeSelect = $("#pageSize option:selected").val();
			var href = "";
			var a = "../servlet/PageServlet?option=Teacher&currentPage=<%=currentPage%>&pageSizeSelect=";
			var b = "&collegevalue=<%=college%>&sdeptValue=<%=sdept%>&selectByNameVal=<%=Tname%>&teacher=admin"
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
			if(pageVal > totalPage){
				alert('请输入正确的页码！');
				return
			}
			var path = "";
			var a = "../servlet/PageServlet?option=Teacher&currentPage=";
			var b = "&pageSizeSelect=<%=pageSize%>&collegevalue=<%=college%>&sdeptValue=<%=sdept%>&selectByNameVal=<%=Tname%>&teacher=admin"
			path = path + a + pageVal + b;
			window.location.href = path;
		}
		
		//新建按钮的事件
		 $(".Update").click(function () {
		 $("#myModalLabel").text("新建专利");
		 $('#myModal').modal();
		 });
	
	</script>
</body>
</html>