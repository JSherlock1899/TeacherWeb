<%@page import="model.Pager"%>
<%@page import="java.util.List"%>
<%@page import="model.Project"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="dao.impl.BaseDaoImpl"%>
<%@page import="dao.IBaseDao"%>
<%@page import="dao.impl.ProjectDaoImpl"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>项目审核</title>
<link rel="stylesheet" href="/TeacherWeb/UI/CSS/bootstrap.css">
<script type="text/javascript" src="/TeacherWeb/UI/JS/jquery-3.3.1.min.js"></script>
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
							int[] pageArr = (int[]) request.getAttribute("pageArr"); 
							String college = (String) request.getAttribute("college"); 
							int grade = (int) session.getAttribute("grade");	//获取用户的权限等级
				%>
	<div class="table-main col-md-12">
		<div class="col-md-4" >
		  <ol class="breadcrumb">
		    <li><a>主页</a></li>
		    <li><a>审核</a></li>
		    <li><a href="../servlet/AuditServlet?option=Project&college=<%=college%>">项目审核</a></li>
		    <li class="active">详细信息</li>
		  </ol>
		</div>
		<div class="row">
			<div class="col-md-11 col-md-offset-1 ">
			<input type="hidden" value="<%=datalist.get(0).getPaccessory()%>" />
				<div class="col-md-10 button-div form-inline">
					<table border="1" id="table" class="table table-striped table-bordered table-hover table-condensed">
							<tr class="info">
								<td>项目编号</td>
								<td class="Psn edit text-center" colspan="3"><%=datalist.get(0).getPsn()%></td>
							</tr>
							<tr>
								<td>项目名称</td>
								<td class="Pname edit"><%=datalist.get(0).getPname()%></td>
								<td>负责人</td>
								<td class="Pleader edit" colspan="3"><%=datalist.get(0).getPleader()%></td>
							</tr>
							<tr>
								<td>成员</td>
								<td class="Pmember edit"><%=datalist.get(0).getPmember()%></td>
								<td>级别</td>
								<td class="Pgrad edit"><%=datalist.get(0).getPgrad()%></td>
							</tr>
							<tr>
								<td>类型</td>		
								<td class="Pmoney edit"><%=datalist.get(0).getPkind()%></td>
								<td>经费</td>
								<td class="Pmoney edit"><%=datalist.get(0).getPmoney()%></td>
							</tr>
							<tr>
								<td>类型</td>		
								<td class="Pmoney edit"><%=datalist.get(0).getPkind()%></td>
								<td>经费</td>
								<td class="Pmoney edit"><%=datalist.get(0).getPmoney()%></td>
							</tr>
							<tr>
								<td>立项时间</td>
								<td class="Pstatime edit"><%=datalist.get(0).getPstatime()%></td>
								<td>结题时间</td>
								<td class="Pendtime edit"><%=datalist.get(0).getPendtime()%></td>
							</tr>
							<tr>
								<td>科研状态</td>
								<td class="Pcondition edit"><%=datalist.get(0).getPcondition()%></td>
								<td>备注</td>
								<td class="Patremarks edit"><%=datalist.get(0).getPremarks()%></td>
							</tr>
							<tr>
								<td>附件</td>
								<td class="Paccessory edit"  colspan="3" ><a href="../servlet/DownloadFileServlet?mainKey=<%=datalist.get(0).getPsn() %>
								&option=project&Proname=<%=datalist.get(0).getPname() %>" 
								class=" btn btn-primary Download">查看附件</a></td>
								<input type="hidden" class="accessoryPath" value="<%=datalist.get(0).getPaccessory() %>"/>
								<input type="hidden" class="key" value="<%=datalist.get(0).getPsn() %>"/>
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
		    $('#ProjectForm').attr("action","../servlet/UploadFileServlet?&count=4&grade=<%=grade%>");
		    $('#ProjectForm').attr("enctype","multipart/form-data");
		    $('#ProjectForm').submit();
	    }
	</script>
</body>
</html>