<%@page import="model.Pager"%>
<%@page import="java.util.List"%>
<%@page import="model.Honor"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="dao.BaseDao"%>
<%@page import="dao.impl.HonorDaoImpl"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>荣誉查询</title>
<link rel="stylesheet" href="/TeacherWeb/UI/CSS/bootstrap.css">
<script type="text/javascript" src="/TeacherWeb/UI/JS/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="/TeacherWeb/UI/JS/ajaxHonorData.js"></script>
<script type="text/javascript" src="/TeacherWeb/UI/JS/commonUse.js"></script>
<script type="text/javascript" src="/TeacherWeb/UI/JS/bootstrap-table.js"></script>
<script type="text/javascript" src="/TeacherWeb/UI/JS/ajaxSelect.js"></script>
<link rel="stylesheet" href="/TeacherWeb/UI/CSS/style.css">
</head>
<body>
				<%
							BaseDao basedao = new BaseDao();
							Pager pager = (Pager) request.getAttribute("pager");
							List<Honor> datalist =  pager.getDataList();	//要显示的数据集合
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
		    <li class="active">荣誉查询</li>
		  </ol>
		</div>
		<div class="row">
			<div class="col-md-11 col-md-offset-1 ">
				<div class="col-md-10 button-div form-inline">
					<input type="button" value="新建记录" id="addMsg" class="btn btn-success"> 
					<a href="../servlet/DownloadTemplate?count=3" class="btn btn-success">下载模板</a>
					<form method="post" enctype="multipart/form-data" action="../servlet/UploadFileServlet?tally=1&count=3&grade=<%=grade %>" class="form-group importform">
						<input type="file" id="myfile" name="myfile" class="btn btn-info" style="display: none" onchange="$('.importform').submit()"> 
						<input type="button" name="" value="导入"  class="btn btn-info" id="importButton">
					</form>			
					<form action="../servlet/SelectExport"  method="post" id="HonorForm" class="form-group">
						<input type="hidden" name="count" value="3">
						<input type="submit" value="导出" id="submitChecked" class="btn btn-info">
						<a class="btn btn-warning" href="../servlet/SelectExport?all=all&count=3">导出全部数据</a>
						<input type="file" id="file" name="file"  class="btn btn-info" style="display: none" onchange="submitFile()"> 
						<input type="button" name="" value="上传文件"  class="btn btn-warning" id="imporFileButton">
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
						<a href="../servlet/PageServlet?option=Honor&currentPage=1" id="homePage">首页</a>
					</li>
					<li>
						<a aria-label="Previous" id="pre" class="prenextpage" href="../servlet/PageServlet?option=Honor&currentPage=<%=currentPage - 1%>&pageSize=5
					&collegevalue=<%=college%>&sdeptValue=<%=sdept%>&endtime=<%=endtime%>
					&selectByNameVal=<%=Tname%>"> 
							<span >&laquo;</span>
						</a>
					</li>
					<li><a class="page" href="../servlet/PageServlet?option=Honor&currentPage=<%=pageArr[0]%>&pageSize=5
					&collegevalue=<%=college%>&sdeptValue=<%=sdept%>&endtime=<%=endtime%>
					&selectByNameVal=<%=Tname%>"><%=pageArr[0]%></a></li>
					<li><a class="page" href="../servlet/PageServlet?option=Honor&currentPage=<%=pageArr[1]%>&pageSize=5
					&collegevalue=<%=college%>&sdeptValue=<%=sdept%>&endtime=<%=endtime%>
					&selectByNameVal=<%=Tname%>"><%=pageArr[1]%></a></li>
					<li><a class="page" href="../servlet/PageServlet?option=Honor&currentPage=<%=pageArr[2]%>&pageSize=5
					&collegevalue=<%=college%>&sdeptValue=<%=sdept%>&endtime=<%=endtime%>
					&selectByNameVal=<%=Tname%>"><%=pageArr[2]%></a></li>
					<li><a class="page" href="../servlet/PageServlet?option=Honor&currentPage=<%=pageArr[3]%>&pageSize=5
					&collegevalue=<%=college%>&sdeptValue=<%=sdept%>&endtime=<%=endtime%>
					&selectByNameVal=<%=Tname%>"><%=pageArr[3]%></a></li>
					<li><a class="page" href="../servlet/PageServlet?option=Honor&currentPage=<%=pageArr[4]%>&pageSize=5
					&collegevalue=<%=college%>&sdeptValue=<%=sdept%>&endtime=<%=endtime%>
					&selectByNameVal=<%=Tname%>"><%=pageArr[4]%></a></li>
					<li>
						<a id="next" aria-label="Next" class="prenextpage" href="../servlet/PageServlet?option=Honor&currentPage=<%=currentPage + 1%>&pageSize=5
					&collegevalue=<%=college%>&sdeptValue=<%=sdept%>&endtime=<%=endtime%>
					&selectByNameVal=<%=Tname%>"> 
							<span>&raquo;</span>
						</a>
					</li>
					<li><a href="../servlet/PageServlet?option=Honor&currentPage=<%=totalPage %>" id="endPage" >尾页</a></li>
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
	 </div>
	</div>
	<script type="text/javascript">
		if(<%=currentPage %> == 1){					//首页和尾页时分别隐藏对应按钮
			$('#pre').css("display","none");
		}
		
		if(<%=currentPage %> == <%=totalPage %>){
			$('#next').css("display","none");
		}
		
		$(document).on("change","#pageSize",function(){			//根据下拉框值的改变改变每页显示的记录条数
			var pageSizeSelect = $("#pageSize option:selected").val();
			var href = "";
			var a = "../servlet/PageServlet?option=Honor&currentPage=<%=currentPage%>&pageSizeSelect=";
			var b = "&collegevalue=<%=college%>&sdeptValue=<%=sdept%>&endtime=<%=endtime%>&selectByNameVal=<%=Tname%>"
			href = href + a + pageSizeSelect + b;
			window.location.href = href;
		})
		
		function skipPage(){								//输入页码跳转页面
			var pageVal = $('.pageVal').val();
			var path = "";
			var a = "../servlet/PageServlet?option=Honor&currentPage=";
			var b = "&pageSizeSelect=<%=pageSize%>&collegevalue=<%=college%>&sdeptValue=<%=sdept%>&endtime=<%=endtime%>&selectByNameVal=<%=Tname%>"
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
		    $('#HonorForm').attr("action","../servlet/UploadFileServlet?&count=4&grade=<%=grade%>");
		    $('#HonorForm').attr("enctype","multipart/form-data");
		    $('#HonorForm').submit();
	    }
	</script>
</body>
</html>