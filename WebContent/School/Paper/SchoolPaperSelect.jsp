<%@page import="model.Pager"%>
<%@page import="java.util.List"%>
<%@page import="model.Paper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="dao.impl.PaperDaoImpl"%>
<%@page import="dao.impl.BaseDaoImpl"%>
<%@page import="dao.IBaseDao"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>荣誉查询</title>
<link rel="stylesheet" href="/TeacherWeb/UI/CSS/bootstrap.css">
<script type="text/javascript" src="/TeacherWeb/UI/JS/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="/TeacherWeb/UI/JS/ajaxPaperData.js"></script>
<script type="text/javascript" src="/TeacherWeb/UI/JS/commonUse.js"></script>
<script type="text/javascript" src="/TeacherWeb/UI/JS/bootstrap-table.js"></script>
<script type="text/javascript" src="/TeacherWeb/UI/JS/ajaxSelect.js"></script>
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
							String college = (String) request.getAttribute("college");	//搜索的条件
							String sdept = (String) request.getAttribute("sdept");
							String starttime = (String) request.getAttribute("starttime");
							String endtime = (String) request.getAttribute("endtime");
							String Tname = (String) request.getAttribute("Tname");
							int[] pageArr = (int[]) request.getAttribute("pageArr"); 
							int grade = (int) session.getAttribute("grade");	//获取用户的权限等级
				%>
	<div class="table-main col-md-12">
		<div class="col-md-4" >
		  <ol class="breadcrumb">
		    <li><a href="#">主页</a></li>
		    <li><a href="#">查询</a></li>
		    <li class="active">论文查询</li>
		  </ol>
		</div>
		<div class="row">
			<div class="col-md-11 col-md-offset-1 ">
				<div class="col-md-10 button-div form-inline">
					<form method="post" enctype="multipart/form-data" action="../servlet/UploadFileServlet?tally=1&count=2&grade=<%=grade %>" class="form-group importform">
						<a href="../servlet/DownloadTemplate?count=2" class="btn btn-success">下载模板</a>
						<input type="file" id="myfile" name="myfile" class="btn btn-info" style="display: none" onchange="$('.importform').submit()"> 
						<input type="button" name="" value="导入"  class="btn btn-info" id="importButton">
					</form>			
					<form action="../servlet/ExportServlet?sdept=<%=sdept %>&college=<%=college %>&starttime=<%=starttime %>&endtime=<%=endtime %>&Tname=<%=Tname %>&totalRecord=<%=totalRecord %>&count=2"  method="post" id="PatentForm" class="form-group">
						<input type="hidden" name="count" value="2">
						<input type="submit" value="导出" id="submitChecked" class="btn btn-info">
						<input type="hidden" id="college" value="<%=college %>"/>
						<input type="hidden" id="sdept" value="<%=sdept %>"/>
						<input type="hidden" id="starttime" value="<%=starttime %>"/>
						<input type="hidden" id="endtime" value="<%=endtime %>"/>
						<input type="hidden" id="Tname" value="<%=Tname %>"/>
						<input type="hidden" id="grade" value="<%=grade %>"/>
						<input type="hidden" id="totalPage" value="<%=totalPage %>"/>
						<input type="hidden" id="currentPage" value="<%=currentPage %>"/>
						<input type="hidden" id="totalRecord" value="<%=totalRecord %>"/>
				</div>
					<table border="1" id="table" class="table table-striped table-bordered table-hover table-condensed">
							<tr class="info">
								<th>检索号</th>
								<th>名称</th>
								<th>第一作者</th>
								<th>发表期刊</th>
								<th>期/卷/页</th>
								<th>发表时间</th>
								<th>级别</th>
								<th>备注</th>
								<th>附件</th>
								<th>操作</th>
							</tr>
							<%	
								for(int i=0; i<datalist.size(); i++){
									String Pasearchnum = datalist.get(i).getPasearchnum();
									
							%>
							<tr>
								<td class="Pasearchnum edit"><%=Pasearchnum%></td>
								<td class="Paname edit"><%=datalist.get(i).getPaname()%></td>
								<td class="Pawriter edit"><%=datalist.get(i).getPawriter()%></td>
								<td class="Papublish edit"><%=datalist.get(i).getPapublish()%></td>
								<td class="Pdisvol edit"><%=datalist.get(i).getPdisvol()%></td>
								<td class="Padate edit"><%=datalist.get(i).getPadate()%></td>
								<td class="Pagrad edit"><%=datalist.get(i).getPagrad()%></td>
								<td class="Paremarks edit"><%=datalist.get(i).getParemarks()%></td>
								<td class="Paccessory edit">
									<a href="<%=datalist.get(i).getPaccessory()%>">查看附件</a>
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
						<a href="../servlet/PageServlet?option=Paper&currentPage=1&teacher=admin" id="homePage">首页</a>
					</li>
					<li>
						<a aria-label="Previous" id="pre" class="prenextpage" href="../servlet/PageServlet?option=Paper&currentPage=<%=currentPage - 1%>&pageSize=<%=pageSize%>
					&collegevalue=<%=college%>&sdeptValue=<%=sdept%>&endtime=<%=endtime%>&teacher=admin
					&selectByNameVal=<%=Tname%>"> 
							<span >&laquo;</span>
						</a>
					</li>
					<li id="page1"><a class="page" href="../servlet/PageServlet?option=Paper&currentPage=<%=pageArr[0]%>&pageSize=<%=pageSize%>
					&collegevalue=<%=college%>&sdeptValue=<%=sdept%>&endtime=<%=endtime%>&teacher=admin
					&selectByNameVal=<%=Tname%>"><%=pageArr[0]%></a></li>
					<li id="page2"><a class="page" href="../servlet/PageServlet?option=Paper&currentPage=<%=pageArr[1]%>&pageSize=<%=pageSize%>
					&collegevalue=<%=college%>&sdeptValue=<%=sdept%>&endtime=<%=endtime%>&teacher=admin
					&selectByNameVal=<%=Tname%>"><%=pageArr[1]%></a></li>
					<li id="page3"><a class="page" href="../servlet/PageServlet?option=Paper&currentPage=<%=pageArr[2]%>&pageSize=<%=pageSize%>
					&collegevalue=<%=college%>&sdeptValue=<%=sdept%>&endtime=<%=endtime%>&teacher=admin
					&selectByNameVal=<%=Tname%>"><%=pageArr[2]%></a></li>
					<li>
						<a id="next" aria-label="Next" class="prenextpage" href="../servlet/PageServlet?option=Paper&currentPage=<%=currentPage + 1%>&pageSize=<%=pageSize%>
					&collegevalue=<%=college%>&sdeptValue=<%=sdept%>&endtime=<%=endtime%>&teacher=admin
					&selectByNameVal=<%=Tname%>"> 
							<span>&raquo;</span>
						</a>
					</li>
					<li><a href="../servlet/PageServlet?option=Paper&currentPage=<%=totalPage %>&teacher=admin" id="endPage" >尾页</a></li>
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
	 </div>
	</div>
	<script type="text/javascript">

		
	$(document).on("change","#pageSize",function(){			//根据下拉框值的改变改变每页显示的记录条数
		var pageSizeSelect = $("#pageSize option:selected").val();
		var href = "";
		var a = "../servlet/PageServlet?option=Patent&currentPage=<%=currentPage%>&pageSizeSelect=";
		var b = "&collegevalue=<%=college%>&sdeptValue=<%=sdept%>&starttime=<%=starttime%>&endtime=<%=endtime%>&selectByNameVal=<%=Tname%>&teacher=admin"
		href = href + a + pageSizeSelect + b;
		window.location.href = href;
	})
		
		function skipPage(){		//输入页码跳转页面
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
			var a = "../servlet/PageServlet?option=Paper&currentPage=";
			var b = "&pageSizeSelect=<%=pageSize%>&collegevalue=<%=college%>&sdeptValue=<%=sdept%>&endtime=<%=endtime%>&selectByNameVal=<%=Tname%>&teacher=admin"
			path = path + a + pageVal + b;
			window.location.href = path;
		}
		
		//点击上传文件时打开文件上传选择窗口
	    $(function(){
	    	$('#imporFileButton').on("click",function(){
	    		$('#file').click();
	    	})
	    })
	    
	    function submitFile(){
		    $('#PaperForm').attr("action","../servlet/UploadFileServlet?&count=4&grade=<%=grade%>");
		    $('#PaperForm').attr("enctype","multipart/form-data");
		    $('#PaperForm').submit();
	    }
	</script>
</body>
</html>