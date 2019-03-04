<%@page import="model.Pager"%>
<%@page import="java.util.List"%>
<%@page import="model.Patent"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="dao.impl.BaseDaoImpl"%>
<%@page import="dao.IBaseDao"%>
<%@page import="dao.impl.PatentDaoImpl"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>项目审核</title>
<link rel="stylesheet" href="/TeacherWeb/UI/CSS/bootstrap.css">
<script type="text/javascript" src="/TeacherWeb/UI/JS/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="/TeacherWeb/UI/JS/ajaxPatentData.js"></script>
<script type="text/javascript" src="/TeacherWeb/UI/JS/commonUse.js"></script>
<script type="text/javascript" src="/TeacherWeb/UI/JS/bootstrap-table.js"></script>
<script type="text/javascript" src="/TeacherWeb/UI/JS/ajaxSelect.js"></script>
<link rel="stylesheet" href="/TeacherWeb/UI/CSS/style.css">
</head>
<body>
				<%
							IBaseDao basedao = new BaseDaoImpl();
							Pager pager = (Pager) request.getAttribute("pager");
							List<Patent> datalist =  pager.getDataList();	//要显示的数据集合
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
		    <li><a>主页</a></li>
		    <li><a>审核</a></li>
		    <li><a href="../servlet/AuditServlet?option=Patent&college=<%=college%>">专利审核</a></li>
		    <li class="active">详细信息</li>
		  </ol>
		</div>
		<div class="row">
			<div class="col-md-11 col-md-offset-1 ">
				<div class="col-md-10 button-div form-inline">
					<table border="1" id="table" class="table table-striped table-bordered table-hover table-condensed">
							<tr class="info">
								<td>名称</td>
								<td class="Patname edit text-center" colspan="3"><%=datalist.get(0).getPatname()%></td>
							</tr>
							<%	
									String Patsn = datalist.get(0).getPatsn();
									
							%>
							<tr>
								<td>第一作者</td>
								<td class="Pleader edit" ><%=datalist.get(0).getPleader()%></td>
								<td style="width:100px">授权号</td>
								<td class="Patsn edit" colspan="3"><%=Patsn%></td>
							</tr>
							<tr>
								<td>申请时间</td>
								<td class="Patapdate edit"><%=datalist.get(0).getPatemdate()%></td>
								<td>授权时间</td>
								<td class="Patemdate edit"><%=datalist.get(0).getPatapdate()%></td>
							</tr>
							<tr>
								<td>级别</td>
								<td class="Patgrad edit"><%=datalist.get(0).getPatgrad()%></td>
								<td>备注</td>
								<td class="Patremarks edit"><%=datalist.get(0).getPatremarks()%></td>
							</tr>
							<tr>
								<td>附件</td>
								<td class="Paccessory edit"  colspan="3"><a href="<%=datalist.get(0).getPaccessory()%>" class="t btn btn-primary">查看附件</a></td>
							</tr>
							<tr>
								<td>审核</td>
								<td>审核意见</td>
								<td><input type="text" id="message" style="width:400px"></td>
								<td class=""><a id="pass" class="btn btn-success">通过</a>&nbsp<a id="nopass" class="btn btn-danger">不通过</a></td>
							</tr>
							<% 
					      basedao.closeCon();%>
						</table>
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
			var a = "../servlet/AuditServlet?option=Patent&currentPage=<%=currentPage%>&pageSizeSelect=";
			href = href + a + pageSizeSelect;
			window.location.href = href;
		})
		
		function skipPage(){								//输入页码跳转页面
			var pageVal = $('.pageVal').val();
			var path = "";
			var a = "../servlet/AuditServlet?option=Patent&currentPage=";
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
		    $('#PatentForm').attr("action","../servlet/UploadFileServlet?&count=4&grade=<%=grade%>");
		    $('#PatentForm').attr("enctype","multipart/form-data");
		    $('#PatentForm').submit();
	    }
	</script>
</body>
</html>