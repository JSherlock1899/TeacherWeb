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
							int[] pageArr = (int[]) request.getAttribute("pageArr"); 
							String college = (String) request.getAttribute("college"); 
							int grade = (int) session.getAttribute("grade");	//获取用户的权限等级
				%>
	<div class="table-main col-md-12">
		<div class="col-md-4" >
		  <ol class="breadcrumb">
		    <li><a>主页</a></li>
		    <li><a>审核</a></li>
		    <li><a href="../servlet/AuditServlet?option=Honor&college=<%=college%>">荣誉审核</a></li>
		    <li class="active">详细信息</li>
		  </ol>
		</div>
		<div class="row">
			<div class="col-md-11 col-md-offset-1 ">
			<input type="hidden" value="<%=datalist.get(0).getHaccessory()%>" />
				<div class="col-md-10 button-div form-inline">
					<table border="1" id="table" class="table table-striped table-bordered table-hover table-condensed">
							<tr class="info">
								<td>编号</td>
								<td class="Hsn edit text-center" colspan="3"><%=datalist.get(0).getHsn()%></td>
							</tr>
							<%	
							String Hsn = datalist.get(0).getHsn();
									
							%>
							<tr>
								<td>名称</td>
								<td class="Hname edit"><%=datalist.get(0).getHname()%></td>
								<td>获奖者</td>
								<td class="Hwinner edit" colspan="3"><%=datalist.get(0).getHwinner()%></td>
							</tr>
							<tr>
								<td>时间</td>
								<td class="Hdate edit"><%=datalist.get(0).getHdate()%></td>
								<td>颁奖单位</td>
								<td class="Hcompany edit"><%=datalist.get(0).getHcompany()%></td>
							</tr>
							<tr>
								<td>级别</td>
								<td class="Hgrad edit"><%=datalist.get(0).getHgrad()%></td>
								<td>奖金</td>
								<td class="Hreward edit"><%=datalist.get(0).getHreward()%></td>
							</tr>
							<tr>
								<td>备注</td>
								<td class="Hremarks edit"><%=datalist.get(0).getHremarks()%></td>
							</tr>
							<tr>
								<td>附件</td>
								<td class="Paccessory edit"  colspan="3" ><a href="../servlet/DownloadFileServlet?mainKey=<%=datalist.get(0).getHsn() %>
								&option=honor&Proname=<%=datalist.get(0).getHname() %>" 
								class=" btn btn-primary Download">查看附件</a></td>
								<input type="hidden" class="accessoryPath" value="<%=datalist.get(0).getHaccessory() %>"/>
								<input type="hidden" class="key" value="<%=datalist.get(0).getHsn() %>"/>
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