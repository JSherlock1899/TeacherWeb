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
		    <li><a href="#">论文审核</a></li>
		    <li><a class="active">详细信息</a></li>
		  </ol>
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
									String Pasearchnum = datalist.get(0).getPasearchnum();
									
							%>
							<tr>
								<td class="Pasearchnum edit"><%=Pasearchnum%></td>
								<td class="Paname edit"><%=datalist.get(0).getPaname()%></td>
								<td class="Pawriter edit"><%=datalist.get(0).getPawriter()%></td>
								<td class="Papublish edit"><%=datalist.get(0).getPapublish()%></td>
								<td class="Pdisvol edit"><%=datalist.get(0).getPdisvol()%></td>
								<td class="Padate edit"><%=datalist.get(0).getPadate()%></td>
								<td class="Pagrad edit"><%=datalist.get(0).getPagrad()%></td>
								<td class="Paremarks edit"><%=datalist.get(0).getParemarks()%></td>
								<td class=""><a id="pass">通过</a>&nbsp<a id="nopass">不通过</a></td>
							</tr>
							
							<%  
					      basedao.closeCon();%>
						</table>
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
			var a = "../servlet/AuditServlet?option=Paper&currentPage=<%=currentPage%>&pageSizeSelect=";
			href = href + a + pageSizeSelect;
			window.location.href = href;
		})
		
		function skipPage(){								//输入页码跳转页面
			var pageVal = $('.pageVal').val();
			var path = "";
			var a = "../servlet/AuditServlet?option=Paper&currentPage=";
			var b = "&pageSizeSelect=<%=pageSize%>"
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