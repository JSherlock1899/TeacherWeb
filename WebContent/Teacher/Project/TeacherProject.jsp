<%@page import="dao.impl.BaseDaoImpl"%>
<%@page import="dao.IBaseDao"%>
<%@page import="model.Pager"%>
<%@page import="java.util.List"%>
<%@page import="model.Project"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="dao.impl.ProjectDaoImpl"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>项目查询</title>
<link rel="stylesheet" href="/TeacherWeb/UI/CSS/bootstrap.css">
<script type="text/javascript" src="/TeacherWeb/UI/JS/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="/TeacherWeb/UI/JS/bootstrap.min.js"></script>
<script type="text/javascript" src="/TeacherWeb/UI/JS/ajaxProjectData.js"></script>
<script type="text/javascript" src="/TeacherWeb/UI/JS/commonUse.js"></script>
<script type="text/javascript" src="/TeacherWeb/UI/JS/bootstrap-table.js"></script>
<script type="text/javascript" src="/TeacherWeb/UI/JS/ajaxSelect.js"></script>
<link rel="stylesheet" href="/TeacherWeb/UI/CSS/style.css">
</head>
<body>
				<%
							IBaseDao basedao = new BaseDaoImpl();
							Pager pager = (Pager) request.getAttribute("pager");
							List<Project> datalist =  pager.getDataList();	//要显示的数据集合
							int currentPage =  pager.getCurrentPage();		//获取当前页码
							int pageSize = pager.getPageSize();			//获取每页显示多少条数据
							int totalRecord = pager.getTotalRecord();		//获取数据总条数
							int totalPage = pager.getTotalPage();			//总页数
							String Tname = (String) request.getAttribute("Tname");
							int[] pageArr = (int[]) request.getAttribute("pageArr"); 
							int grade = (int) session.getAttribute("grade");	//获取用户的权限等级
				%>
	<div class="table-main col-md-12" >
		<div class="col-md-4" >
		  <ol class="breadcrumb" style="margin-left:5em;margin-top:2em">
		    <li><a href="#">主页</a></li>
		    <li><a href="#">查询</a></li>
		    <li class="active">项目查询</li>
		  </ol>
		</div>
		<div class="row" style="margin-left:col-md-2">
			<div class="col-md-11 col-md-offset-1 ">
				<div class="col-md-10 button-div form-inline">
					<input type="button" value="新建记录" id="btn_add" class="btn btn-success"> 
					<a href="../servlet/DownloadTemplate?count=4" class="btn btn-success">下载模板</a>
					<form method="post" enctype="multipart/form-data" action="../servlet/UploadFileServlet?tally=1&count=4&grade=<%=grade %>" class="form-group importform">
						<input type="file" id="myfile" name="myfile" class="btn btn-info" style="display: none" onchange="$('.importform').submit()"> 
						<input type="button" name="" value="导入"  class="btn btn-info" id="importButton">
					</form>			
					<form action="../servlet/SelectExport"  method="post" id="ProjectForm" class="form-group">
						<input type="hidden" name="count" value="4">
						<input type="submit" value="导出" id="submitChecked" class="btn btn-info">
						<a class="btn btn-warning" href="../servlet/SelectExport?all=all&count=4">导出全部数据</a>
						<input type="file" id="file" name="file"  class="btn btn-info" style="display: none" onchange="submitFile()"> 
						<input type="button" name="" value="上传文件"  class="btn btn-warning" id="imporFileButton">
						<input type="hidden" id="Cname" />
				</div>
					<table border="1" id="table" class="table table-striped table-bordered table-hover table-condensed">
							<tr class="info">
								<th><input type="checkbox" id="checkAll" /></th>
								<th>项目编号</th>
								<th>项目名称</th>
								<th>负责人</th>
								<th>级别</th>
								<th>类型</th>
								<th>科研状态</th>
								<th>成员</th>
								<th>经费</th>
								<th>立项时间</th>
								<th>结题时间</th>
								<th colspan=2>备注</th>
								<th>操作</th>
							</tr>
							<%	
								for(int i=0; i<datalist.size(); i++){
									String Psn = datalist.get(i).getPsn();
									
							%>
							<tr>
								<% //导出为excel时的单选框，Pastn用于唯一标识各项目信息
								out.print("<td><input type='checkbox' value = " + Psn + " name='select'  class='select'></td>"); %>
								<td class="Psn edit"><%=Psn%></td>
								<td class="Pname edit"><%=datalist.get(i).getPname()%></td>
								<td class="Pleader edit"><%=datalist.get(i).getPleader()%></td>
								<td class="Pgrad edit"><%=datalist.get(i).getPgrad()%></td>
								<td class="Pkind edit"><%=datalist.get(i).getPkind()%></td>
								<td class="Pcondition edit"><%=datalist.get(i).getPcondition()%></td>
								<td class="Pmember edit"><%=datalist.get(i).getPmember()%></td>
								<td class="Pmoney edit"><%=datalist.get(i).getPmoney()%></td>
								<td class="Pstatime edit"><%=datalist.get(i).getPstatime()%></td>
								<td class="Pendtime edit"><%=datalist.get(i).getPendtime()%></td>
								<td class="Patremarks edit"><%=datalist.get(i).getPremarks()%></td>
								<td class="Paccessory edit"><a href="<%=datalist.get(i).getPaccessory()%>">查看附件</a></td>
								<td class=""><a class="delete">删除</a>&nbsp<a class="updata">编辑</a></td>
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
						<a href="../servlet/PageServlet?option=Project&currentPage=1&teacher=teacher" id="homePage">首页</a>
					</li>
					<li>
						<a aria-label="Previous" id="pre" class="prenextpage" href="../servlet/PageServlet?option=Project&currentPage=<%=currentPage - 1%>&pageSize=5
					&teacher=teacher"> 
							<span >&laquo;</span>
						</a>
					</li>
					<li><a class="page" href="../servlet/PageServlet?option=Project&currentPage=<%=pageArr[0]%>&pageSize=5&teacher=teacher
					"><%=pageArr[0]%></a></li>
					<li><a class="page" href="../servlet/PageServlet?option=Project&currentPage=<%=pageArr[1]%>&pageSize=5&teacher=teacher
					"><%=pageArr[1]%></a></li>
					<li><a class="page" href="../servlet/PageServlet?option=Project&currentPage=<%=pageArr[2]%>&pageSize=5&teacher=teacher
					"><%=pageArr[2]%></a></li>
					<li><a class="page" href="../servlet/PageServlet?option=Project&currentPage=<%=pageArr[3]%>&pageSize=5&teacher=teacher
					"><%=pageArr[3]%></a></li>
					<li><a class="page" href="../servlet/PageServlet?option=Project&currentPage=<%=pageArr[4]%>&pageSize=5&teacher=teacher
					"><%=pageArr[4]%></a></li>
					<li>
						<a id="next" aria-label="Next" class="prenextpage" href="../servlet/PageServlet?option=Project&currentPage=<%=currentPage + 1%>&pageSize=5
					"> 
							<span>&raquo;</span>
						</a>
					</li>
					<li><a href="../servlet/PageServlet?option=Project&currentPage=<%=totalPage %>&teacher=teacher" id="endPage" >尾页</a></li>
					<li><span>当前第<%=currentPage %>页，共<%=totalRecord %>条记录</span></li>
				</ul>
				</nav>
			</form>
			<div class="form-group pull-right">
			  	共<%=totalPage %>页
			  <input type="text" class="pageVal" style="width:100px;">
			  <button type="submit" class="btn btn-default " onclick="skipPage()">GO</button>
			</div>
		</div>
							<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
					<div class="modal-dialog" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">×</span>
								</button>
								<h4 class="modal-title" id="myModalLabel">新建项目</h4>
							</div>
							<form class="" action="index.html" method="post">
								<div class="modal-body">
									<div class="form-group">
										<label for="Psn">项目编号</label> <input type="text"
											name="Psn" class="form-control" id="Psn"
											placeholder="项目编号">
									</div>
									<div class="form-group">
										<label for="Pname">项目名称</label> <input type="text"
											name="Pname" class="form-control" id="Pname"
											placeholder="项目名称">
									</div>
									<div class="form-group">
										<label for="Pleader">负责人</label> <input type="text"
											name="Pleader" class="form-control" id="Pleader"
											placeholder="负责人">
									</div>
									<div class="form-group">
										<label for="Pmember">成员</label> <input type="text"
											name="Pmember" class="form-control" id="Pmember"
											placeholder="成员">
									</div>
									<div class="form-group">
										<label for="Pgrad">级别</label> <input type="text"
											name="Pgrad" class="form-control" id="Pgrad"
											placeholder="级别">
									</div>
									<div class="form-group">
										<label for="Pkind">类型</label> <input type="text"
											name="Pkind" class="form-control" id="Pkind"
											placeholder="类型">
									</div>
									<div class="form-group">
										<label for="Pcondition">科研状态</label> <select name="Pcondition"
											class="form-control" id="Pcondition">
											<option value="请选择科研状态">请选择科研状态</option>
											<option value="未结题">未结题</option>
											<option value="已结题">已结题</option>
										</select>
									</div>
									<div class="form-group">
										<label for="Pmoney">经费 </label> <input type="text"
											name="Pmoney" class="form-control" id="Pmoney"
											placeholder="经费">
									</div>
									<div class="form-group">
										<label for="Pstatime">立项时间 </label> <input type="date"
											name="Pstatime" class="form-control" id="Pstatime"
											placeholder="立项时间	">
									</div>
									<div class="form-group">
										<label for="Pendtime">结题时间</label> <input type="date"
											name="Pendtime" class="form-control" id="Pendtime"
											placeholder="结题时间">
									</div>
									<div class="form-group">
										<label for="Premarks">备注</label> <input type="text"
											name="Premarks" class="form-control" id="Premarks"
											placeholder="备注">
									</div>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-default"
										data-dismiss="modal">
										<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭
									</button>
									<button type="submit" id="btn_submit" class="btn btn-primary saveNewMsg">
										<span class="glyphicon glyphicon-floppy-disk"
											aria-hidden="true"></span>保存
									</button>
								</div>
							</form>
						</div>
					</div>
				</div>
		 </div>
	 </div>
	</div>
	<script type="text/javascript">
		if(<%=currentPage %> == 1){					//首页和尾页时分别隐藏对应按钮
			$('#pre').css("display","none");
		}
		
		if(<%=currentPage %> == <%=totalPage %>){
			$('#next').css("display","none");
		}
		
		
		function skipPage(){								//输入页码跳转页面
			var pageVal = $('.pageVal').val();
			var path = "";
			var a = "../servlet/PageServlet?option=Project&currentPage=";
			var b = "&pageSizeSelect=<%=pageSize%>&teacher=teacher"
			path = path + a + pageVal + b;
			window.location.href = path;
		}
		
		
		$(document).on("change","#pageSize",function(){			//根据下拉框值的改变改变每页显示的记录条数
			var pageSizeSelect = $("#pageSize option:selected").val();
			var href = "";
			var a = "../servlet/PageServlet?option=Project&currentPage=<%=currentPage%>&pageSizeSelect=";
			href = href + a + pageSizeSelect + "&teacher=teacher"
			window.location.href = href;
		})
		
		//点击上传文件时打开文件上传选择窗口
	    $(function(){
	    	$('#imporFileButton').on("click",function(){
	    		$('#file').click();
	    	})
	    })
	    
	    function submitFile(){
		    $('#ProjectForm').attr("action","../servlet/UploadFileServlet?&count=4&grade=3");
		    $('#ProjectForm').attr("enctype","multipart/form-data");
		    $('#ProjectForm').submit();
	    }
		
		 //新建按钮的事件
		$("#btn_add").click(function () {
		$("#myModalLabel").text("新建项目");
		$('#myModal').modal();
		});
	</script>
</body>
</html>