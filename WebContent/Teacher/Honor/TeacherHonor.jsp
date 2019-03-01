<%@page import="dao.impl.BaseDaoImpl"%>
<%@page import="dao.IBaseDao"%>
<%@page import="model.Pager"%>
<%@page import="java.util.List"%>
<%@page import="model.Honor"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="dao.impl.HonorDaoImpl"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>专利查询</title>
<link rel="stylesheet" href="/TeacherWeb/UI/CSS/bootstrap.css">
<script type="text/javascript" src="/TeacherWeb/UI/JS/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="/TeacherWeb/UI/JS/ajaxHonorData.js"></script>
<script type="text/javascript" src="/TeacherWeb/UI/JS/bootstrap.min.js"></script>
<script type="text/javascript" src="/TeacherWeb/UI/JS/commonUse.js"></script>
<script type="text/javascript" src="/TeacherWeb/UI/JS/bootstrap-table.js"></script>
<script type="text/javascript" src="/TeacherWeb/UI/JS/ajaxSelect.js"></script>
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
							String Tname = (String) request.getAttribute("Tname");
							int[] pageArr = (int[]) request.getAttribute("pageArr"); 
							int grade = (int) session.getAttribute("grade");	//获取用户的权限等级
				%>
	<div class="table-main col-md-12" >
		<div class="col-md-4" >
		  <ol class="breadcrumb" style="margin-left:5em;margin-top:2em">
		    <li><a href="#">主页</a></li>
		    <li><a href="#">查询</a></li>
		    <li class="active">荣誉查询</li>
		  </ol>
		</div>
		<div class="row">
			<div class="col-md-11 col-md-offset-1 ">
				<div class="col-md-10 button-div form-inline">
					<input type="button" value="新建记录" id="btn_add" class="btn btn-success"> 	
					<form action="../servlet/SelectExport"  method="post" id="HonorForm" class="form-group">
						<input type="hidden" name="count" value="4">
						<input type="submit" value="导出" id="submitChecked" class="btn btn-info">
						<a class="btn btn-warning" href="../servlet/SelectExport?all=all&count=4">导出全部数据</a>
						<input type="file" id="file" name="file"  class="btn btn-info" style="display: none" onchange="submitFile()"> 
						<input type="button" name="" value="上传文件"  class="btn btn-warning" id="imporFileButton">
						<input type="hidden" id="totalPage" value="<%=totalPage %>"/>
						<input type="hidden" id="currentPage" value="<%=currentPage %>"/>
						<input type="hidden" id="pageSize" value="<%=pageSize %>"/>
						<input type="hidden" id="totalRecord" value="<%=totalRecord %>"/>
				</div>
					<table border="1" id="table" class="table table-striped table-bordered table-hover table-condensed">
							<tr class="info">
								<th><input type="checkbox" id="checkAll" /></th>
								<th>编号</th>
								<th>名称</th>
								<th>获奖者</th>
								<th>时间</th>
								<th>颁奖单位</th>
								<th>级别</th>
								<th>奖金</th>
								<th>备注</th>
								<th>附件</th>
								<th>操作</th>
							</tr>
							<%	
								for(int i=0; i<datalist.size(); i++){
									String Hsn = datalist.get(i).getHsn();
									
							%>
							<tr>
								<% //导出为excel时的单选框，Hsn用于唯一标识各荣誉信息
								out.print("<td><input type='checkbox' value = " + Hsn + " name='select'  class='select'></td>"); %>
								<td class="Hsn edit"><%=Hsn%></td>
								<td class="Hname edit"><%=datalist.get(i).getHname()%></td>
								<td class="Hwinner edit"><%=datalist.get(i).getHwinner()%></td>
								<td class="Hdate edit"><%=datalist.get(i).getHdate()%></td>
								<td class="Hcompany edit"><%=datalist.get(i).getHcompany()%></td>
								<td class="Hgrad edit"><%=datalist.get(i).getHgrad()%></td>
								<td class="Hreward edit"><%=datalist.get(i).getHreward()%></td>
								<td class="Hremarks edit"><%=datalist.get(i).getHremarks()%></td>	
								<td class="Paccessory edit">
									<a href="<%=datalist.get(i).getHaccessory()%>">查看附件</a>
									<a name="" class="imporFileButton" >上传文件</a>
								</td>						
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
						<a href="../servlet/PageServlet?option=Honor&currentPage=1&teacher=teacher" id="homePage">首页</a>
					</li>
					<li>
						<a aria-label="Previous" id="pre" class="prenextpage" href="../servlet/PageServlet?option=Honor&currentPage=<%=currentPage - 1%>&pageSize=5
					&teacher=teacher"> 
							<span >&laquo;</span>
						</a>
					</li>
					<li id="page1"><a class="page" href="../servlet/PageServlet?option=Honor&currentPage=<%=pageArr[0]%>&pageSize=5&teacher=teacher
					"><%=pageArr[0]%></a></li>
					<li id="page2"><a class="page" href="../servlet/PageServlet?option=Honor&currentPage=<%=pageArr[1]%>&pageSize=5&teacher=teacher
					"><%=pageArr[1]%></a></li>
					<li id="page3"><a class="page" href="../servlet/PageServlet?option=Honor&currentPage=<%=pageArr[2]%>&pageSize=5&teacher=teacher
					"><%=pageArr[2]%></a></li>
					<li>
						<a id="next" aria-label="Next" class="prenextpage" href="../servlet/PageServlet?option=Honor&currentPage=<%=currentPage + 1%>&pageSize=5
					"> 
							<span>&raquo;</span>
						</a>
					</li>
					<li><a href="../servlet/PageServlet?option=Honor&currentPage=<%=totalPage %>&teacher=teacher" id="endPage" >尾页</a></li>
					<li id="totalPage" value="<%=totalPage %>"><span>当前第<%=currentPage %>页，共<%=totalRecord %>条记录</span></li>
				</ul>
				</nav>
			</form>
			<div class="form-group pull-right">
			  	共<%=totalPage %>页
			  <input type="text" class="pageVal" style="width:100px;">
			  <button type="submit" class="btn btn-default " onclick="skipPage()">GO</button>
			</div>
		</div>
		<!--新建信息的模态框 -->
			<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" >
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
              				<h4 class="modal-title" id="myModalLabel">新建荣誉信息</h4>
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">×</span>
							</button>
						</div>
						<div class="modal-body">
							<div class="form-group">
								<label for="Hsn">荣誉编号</label> <input type="text" name="Hsn"
									class="form-control" id="Hsn" placeholder="荣誉编号">
							</div>
							<div class="form-group">
								<label for="Hname">荣誉名称</label> <input type="text" name="Hname"
									class="form-control" id="Hname" placeholder="荣誉名称">
							</div>
							<div class="form-group">
								<label for="Hwinner">获奖者</label> <input type="text"
									name="Hwinner" class="form-control" id="Hwinner"
									placeholder="获奖者">
							</div>
							<div class="form-group">
								<label for="Hdate">获奖时间</label> <input type="date"
									name="Hdate" class="form-control" id="Hdate"
									placeholder="获奖时间">
							</div>
							<div class="form-group">
								<label for="Hcompany">颁奖单位</label> <input type="text" name="Hcompany"
									class="form-control" id="Hcompany" placeholder="颁奖单位">
							</div>
							<div class="form-group">
								<label for="Hgrad">级别</label> <input type="text" name="Hgrad"
									class="form-control" id="Hgrad" placeholder="级别">
							</div>
             			 <div class="form-group">
								<label for="Hreward">奖金</label> <input type="text"
									name="Hreward" class="form-control" id="Hreward"
									placeholder="奖金">
							</div>
							<div class="form-group">
								<label for="Hremarks">备注</label> <input type="text"
									name="Hremarks" class="form-control" id="Hremarks"
									placeholder="备注">
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
	<script type="text/javascript">
		
		$(document).on("change","#pageSize",function(){			//根据下拉框值的改变改变每页显示的记录条数
			var pageSizeSelect = $("#pageSize option:selected").val();
			var href = "";
			var a = "../servlet/PageServlet?option=Honor&currentPage=<%=currentPage%>&pageSizeSelect=";
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
		    $('#HonorForm').attr("action","../servlet/UploadFileServlet?&count=4&grade=<%=grade%>");
		    $('#HonorForm').attr("enctype","multipart/form-data");
		    $('#HonorForm').submit();
	    }
		
		 
	</script>
</body>
</html>