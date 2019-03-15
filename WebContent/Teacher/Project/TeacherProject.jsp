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
		List<Project> datalist = pager.getDataList(); //要显示的数据集合
		int currentPage = pager.getCurrentPage(); //获取当前页码
		int pageSize = pager.getPageSize(); //获取每页显示多少条数据
		int totalRecord = pager.getTotalRecord(); //获取数据总条数
		int totalPage = pager.getTotalPage(); //总页数
		String Tname = (String) request.getAttribute("Tname");
		int[] pageArr = (int[]) request.getAttribute("pageArr");
		int grade = (int) session.getAttribute("grade"); //获取用户的权限等级
		String user = (String) session.getAttribute("user");	//获取用户名
	%>
	<div class="table-main col-md-12">
		<div class="col-md-4">
			<ol class="breadcrumb" style="margin-left: 5em; margin-top: 2em">
				<li><a href="#">主页</a></li>
				<li><a href="#">查询</a></li>
				<li class="active">项目查询</li>
			</ol>
		</div>
		<div class="row" style="margin-left: col-md-2">
			<div class="col-md-11 col-md-offset-1 ">
				<div class="col-md-10 button-div form-inline">
					<input type="button" value="新建记录" id="btn_add" class="btn btn-success">
						<input type="hidden" id="totalPage" value="<%=totalPage %>"/>
						<input type="hidden" id="currentPage" value="<%=currentPage %>"/>
						<input type="hidden" id="totalRecord" value="<%=totalRecord %>"/>
					<form action="../servlet/SelectExport" method="post" id="ProjectForm" class="form-group">
						<input type="hidden" name="count" value="4"> 
				</div>
				<table border="1" id="table"
					class="table table-striped table-bordered table-hover table-condensed">
					<tr class="info">
						<th>项目编号</th>
						<th>项目名称</th>
						<th>负责人</th>
						<th>级别</th>
						<th>类型</th>
						<th>科研状态</th>
						<th>成员</th>
						<th>经费</th>
					</tr>
					<%
						for (int i = 0; i < datalist.size(); i++) {
							String Psn = datalist.get(i).getPsn();
					%>
					<tr>
						<td class="Psn edit"><%=Psn%></td>
						<td class="Pname edit"><a href="../servlet/PageServlet?option=Project&teacher=teacher&count=1&Proname=
								<%=datalist.get(i).getPname()%>&Patsn=<%=Psn%>&order=<%=i%>&pageSize=<%=pageSize%>&currentPage=<%=currentPage%>"><%=datalist.get(i).getPname()%></a></td>
						<td class="Pleader edit"><%=datalist.get(i).getPleader()%></td>
						<td class="Pgrad edit"><%=datalist.get(i).getPgrad()%></td>
						<td class="Pkind edit"><%=datalist.get(i).getPkind()%></td>
						<td class="Pcondition edit"><%=datalist.get(i).getPcondition()%></td>
						<td class="Pmember edit"><%=datalist.get(i).getPmember()%></td>
						<td class="Pmoney edit"><%=datalist.get(i).getPmoney()%></td>
					</tr>

					<%
						}
						basedao.closeCon();
					%>
				</table>

				<nav aria-label="Page navigation">
				<ul class="pagination" style="display: block">
					<li><span> <select name="pageSize" id="pageSize" value="<%=pageSize %>">
								<option value="<%=pageSize%>"><%=pageSize%></option>
								<option value="5">5</option>
								<option value="10">10</option>
								<option value="15">15</option>
								<option value="25">25</option>
						</select>
					</span></li>
					<li>
						<a href="../servlet/PageServlet?option=Project&currentPage=1&teacher=teacher&count=0" id="homePage">首页</a>
					</li>
					<li>
						<a aria-label="Previous" id="pre" class="prenextpage" href="../servlet/PageServlet?option=Project&currentPage=<%=currentPage - 1%>&pageSize=<%=pageSize %>&count=0
					&teacher=teacher"> 
							<span >&laquo;</span>
						</a>
					</li>
					<li id="page1"><a class="page" href="../servlet/PageServlet?option=Project&currentPage=<%=pageArr[0]%>&pageSize=<%=pageSize%>&teacher=teacher&count=0
					"><%=pageArr[0]%></a></li>
					<li id="page2"><a class="page" href="../servlet/PageServlet?option=Project&currentPage=<%=pageArr[1]%>&pageSize=<%=pageSize%>&teacher=teacher&count=0
					"><%=pageArr[1]%></a></li>
					<li id="page3"><a class="page" href="../servlet/PageServlet?option=Project&currentPage=<%=pageArr[2]%>&pageSize=<%=pageSize%>&teacher=teacher&count=0
					"><%=pageArr[2]%></a></li>
					<li>
						<a id="next" aria-label="Next" class="prenextpage" href="../servlet/PageServlet?option=Project&currentPage=<%=currentPage + 1%>&pageSize=<%=pageSize %>&count=0
					&teacher=teacher"> 
							<span>&raquo;</span>
						</a>
					</li>
					<li><a href="../servlet/PageServlet?option=Project&currentPage=<%=totalPage %>&teacher=teacher&count=0" id="endPage" >尾页</a></li>
					<li id="totalPage" value="<%=totalPage %>"><span>当前第<%=currentPage %>页，共<%=totalRecord %>条记录</span></li>
				</nav>
				</form>
				<div class="form-group pull-right">
					共<%=totalPage%>页 <input type="text" class="pageVal"
						style="width: 100px;">
					<button type="submit" class="btn btn-default " onclick="skipPage()">GO</button>
				</div>
			</div>

			<!--新建信息的模态框 -->
			<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">×</span>
							</button>
							<h4 class="modal-title" id="myModalLabel">新建项目</h4>
						</div>
						<div class="modal-body">
							<div class="form-group">
								<label for="Psn">项目编号</label> 
								<input type="text" name="Psn" id="Psn" onfocus="showTips('Psn','项目编号为1-20位的数字')" 
								onblur="checkPsn('Psn','请按要求输入项目编号')" class="form-control" id="Psn" placeholder="项目编号">
								<div id="Psndiv" style="display:none">
									<span id="Psnspan" ></span><br>
								</div>
							</div>
							<div class="form-group">
								<label for="Pname">项目名称</label> 
								<input type="text" name="Pname" id="Pname" onfocus="showTips('Pname','项目名称不能超过15个字符')" 
								onblur="checkPname('Pname','请按要求输入项目名称')" class="form-control" id="Psn" placeholder="项目名称">
								<div id="Pnamediv" style="display:none">
									<span id="Pnamespan" ></span><br>
								</div>
							</div>
							<div class="form-group">
								<label for="Pleader">负责人</label> <input type="text"  value="<%=user %>"
									name="Pleader" class="form-control" id="Pleader"
									placeholder="负责人">
							</div>
							<div class="form-group">
								<label for="Pmember">成员</label>
									<input type="text" name="Pmember" id="Pmember" onfocus="showTips('Pmember','项目成员不能超过50个字符')" 
									onblur="checkPmember('Pmember','请按要求输入项目成员')" class="form-control" placeholder="成员">
									<div id="Pmembersdiv" style="display:none">
										<span id="Pmemberspan" ></span><br>
									</div>
							</div>
							<div class="form-group">
								<label for="Pgrad">级别</label> 
									<select name="Pgrad"
									class="form-control" id="Pgrad">
									<option value="校级">校级</option>
									<option value="市级">市级</option>
									<option value="省级">省级</option>
									<option value="部级">部级</option>
								</select>
							</div>
							<div class="form-group">
								<label for="Pkind">类型</label> 
								<select name="Pkind"
									class="form-control" id="Pkind">
									<option value="横向">横向</option>
									<option value="纵向">纵向</option>
								</select>
							</div>
							<div class="form-group">
								<label for="Pcondition">科研状态</label> 
								<select name="Pcondition"
									class="form-control" id="Pcondition">
									<option value="未结题">未结题</option>
									<option value="已结题">已结题</option>
								</select>
							</div>
							<div class="form-group">
								<label for="Pmoney">经费 </label> <input type="text" name="Pmoney"
									class="form-control" id="Pmoney" placeholder="经费"
									onfocus="showTips('Pmoney','项目经费为数字')" 
									onblur="checkPmoney('Pmoney','请按要求输入项目经费')">
									<div id="Pmoneydiv" style="display:none">
										<span id="Pmoneyspan" ></span><br>
									</div>
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
		var a = "../servlet/PageServlet?option=Project&currentPage=<%=currentPage%>&pageSizeSelect=";
		var b = "&teacher=teacher&count=0"
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
		if(pageVal == ""){
			alert('页码不能为空！');
			return
		}
		var path = "";
		var a = "../servlet/PageServlet?option=Project&currentPage=";
		var b = "&pageSizeSelect=<%=pageSize%>&teacher=teacher&count=0"
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
		    $('#ProjectForm').attr("action","../servlet/UploadFileServlet?&count=4&grade=3");
		    $('#ProjectForm').attr("enctype","multipart/form-data");
		    $('#ProjectForm').submit();
	    }
		
	  //新建按钮的事件
		 $("#btn_add").click(function () {
		 $("#myModalLabel").text("新建项目");
		 $('#myModal').modal();
		 });
	  
		 function showTips(id,info){
				document.getElementById(id+"span").innerHTML="<font color='gray' size='2'>"+info+"</font>";
			}
			function checkPsn(id,info){
				var uValue = document.getElementById(id).value;
				if(!/^\d{1,20}$/.test(uValue)){
					document.getElementById(id+"span").innerHTML="<font color='red' size='2'>"+info+"</font>";
					document.getElementById(id+"div").style.display="block";
					return true;
					
				}else{
					document.getElementById(id+"span").innerHTML="<font color='green' size='3'> √</font>";
					
				}
			}
			function checkPname(id,info){
				var uValue = document.getElementById(id).value;
				if(!/^.{1,15}$/.test(uValue)){
					document.getElementById(id+"span").innerHTML="<font color='red' size='2'>"+info+"</font>";
					document.getElementById(id+"div").style.display="block";
					return true;
				}else{
					document.getElementById(id+"span").innerHTML="<font color='green' size='3'> √</font>";
				}
			}
			function checkPmember(id,info){
				var uValue = document.getElementById(id).value;
				if(!/^.{1,50}$/.test(uValue)){
					document.getElementById(id+"span").innerHTML="<font color='red' size='2'>"+info+"</font>";
					document.getElementById(id+"div").style.display="block";
					return true;
				}else{
					document.getElementById(id+"span").innerHTML="<font color='green' size='3'> √</font>";
				}
			}
			function checkPmoney(id,info){
				var uValue = document.getElementById(id).value;
				if(!/^\d{1,}$/.test(uValue)){
					document.getElementById(id+"span").innerHTML="<font color='red' size='2'>"+info+"</font>";
					document.getElementById(id+"div").style.display="block";
					return true;
				}else{
					document.getElementById(id+"span").innerHTML="<font color='green' size='3'> √</font>";
				}
			}
			
			function check(){
				var check = checkPsn() && checkPname() && checkPmember() && checkPmoney() 
				alert(check)	
				return check;
	            }
	</script>
</body>
</html>