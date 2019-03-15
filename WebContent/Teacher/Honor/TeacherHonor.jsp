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
							String user = (String) session.getAttribute("user");	//获取用户名
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
						<input type="hidden" id="totalPage" value="<%=totalPage %>"/>
						<input type="hidden" id="currentPage" value="<%=currentPage %>"/>
						<input type="hidden" id="pageSize" value="<%=pageSize %>"/>
						<input type="hidden" id="totalRecord" value="<%=totalRecord %>"/>
				</div>
					<table border="1" id="table" class="table table-striped table-bordered table-hover table-condensed">
							<tr class="info">
								<th>编号</th>
								<th>名称</th>
								<th>获奖者</th>
								<th>时间</th>
								<th>颁奖单位</th>
								<th>级别</th>
								<th>奖金</th>
								<th>备注</th>
							</tr>
							<%	
								for(int i=0; i<datalist.size(); i++){
									String Hsn = datalist.get(i).getHsn();
									
							%>
							<tr>
								<td class="Hsn edit"><%=Hsn%></td>
								<td class="Hname edit"><a href="../servlet/PageServlet?option=Honor&teacher=teacher&count=1&Proname=
								<%=datalist.get(i).getHname()%>&Patsn=<%=Hsn%>&order=<%=i%>&pageSize=<%=pageSize%>&currentPage=<%=currentPage%>"><%=datalist.get(i).getHname()%></a></td>
								<td class="Hwinner edit"><%=datalist.get(i).getHwinner()%></td>
								<td class="Hdate edit"><%=datalist.get(i).getHdate()%></td>
								<td class="Hcompany edit"><%=datalist.get(i).getHcompany()%></td>
								<td class="Hgrad edit"><%=datalist.get(i).getHgrad()%></td>
								<td class="Hreward edit"><%=datalist.get(i).getHreward()%></td>
								<td class="Hremarks edit"><%=datalist.get(i).getHremarks()%></td>					
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
						<a href="../servlet/PageServlet?option=Honor&currentPage=1&teacher=teacher&count=0" id="homePage">首页</a>
					</li>
					<li>
						<a aria-label="Previous" id="pre" class="prenextpage" href="../servlet/PageServlet?option=Honor&currentPage=<%=currentPage - 1%>&pageSize=<%=pageSize %>&count=0
					&teacher=teacher"> 
							<span >&laquo;</span>
						</a>
					</li>
					<li id="page1"><a class="page" href="../servlet/PageServlet?option=Honor&currentPage=<%=pageArr[0]%>&pageSize=<%=pageSize%>&teacher=teacher&count=0
					"><%=pageArr[0]%></a></li>
					<li id="page2"><a class="page" href="../servlet/PageServlet?option=Honor&currentPage=<%=pageArr[1]%>&pageSize=<%=pageSize%>&teacher=teacher&count=0
					"><%=pageArr[1]%></a></li>
					<li id="page3"><a class="page" href="../servlet/PageServlet?option=Honor&currentPage=<%=pageArr[2]%>&pageSize=<%=pageSize%>&teacher=teacher&count=0
					"><%=pageArr[2]%></a></li>
					<li>
						<a id="next" aria-label="Next" class="prenextpage" href="../servlet/PageServlet?option=Honor&currentPage=<%=currentPage + 1%>&pageSize=<%=pageSize %>&count=0
					&teacher=teacher"> 
							<span>&raquo;</span>
						</a>
					</li>
					<li><a href="../servlet/PageServlet?option=Honor&currentPage=<%=totalPage %>&teacher=teacher&count=0" id="endPage" >尾页</a></li>
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
									class="form-control" id="Hsn" placeholder="荣誉编号"
									onfocus="showTips('Hsn','荣誉编号为1-20位的数字')" 
									onblur="checkHsn('Hsn','请按要求输入荣誉编号')">
									<div id="Hsndiv" style="display:none">
										<span id="Hsnspan" ></span><br>
									</div>
							</div>
							<div class="form-group">
								<label for="Hname">荣誉名称</label> <input type="text" name="Hname"
									class="form-control" id="Hname" placeholder="荣誉名称"
									onfocus="showTips('Hname','荣誉名称不能超过15个字符')" 
									onblur="checkHname('Hname','请按要求输入荣誉名称')">
									<div id="Hnamediv" style="display:none">
										<span id="Hnamespan" ></span><br>
									</div>
							</div>
							<div class="form-group">
								<label for="Hwinner">获奖者</label> <input type="text"  value="<%=user %>"
									name="Hwinner" class="form-control" id="Hwinner"
									placeholder="获奖者" onfocus="showTips('Hwinner','获奖者不能超过50个字符')" 
									onblur="checkHwinner('Hwinner','请按要求输入获奖者')">
									<div id="Hwinnerdiv" style="display:none">
										<span id="Hwinnerspan" ></span><br>
									</div>
							</div>
							<div class="form-group">
								<label for="Hdate">获奖时间</label> <input type="date"
									name="Hdate" class="form-control" id="Hdate"
									placeholder="获奖时间" >
							</div>
							<div class="form-group">
								<label for="Hcompany">颁奖单位</label> <input type="text" name="Hcompany"
									class="form-control" id="Hcompany" placeholder="颁奖单位"
									onfocus="showTips('Hcompany','颁发单位不能超过16个字符')" 
									onblur="checkHcompany('Hcompany','请按要求输入颁发单位')">
									<div id="Hcompanydiv" style="display:none">
										<span id="Hcompanyspan" ></span><br>
									</div>
							</div>
							<div class="form-group">
								<label for="Hgrad">级别</label>
									<select name="Hgrad"
									class="form-control" id="Hgrad">
									<option value="校级">校级</option>
									<option value="市级">市级</option>
									<option value="省级">省级</option>
									<option value="国家级">国家级</option>
								</select>
							</div>
             			 <div class="form-group">
								<label for="Hreward">奖金</label> <input type="text"
									name="Hreward" class="form-control" id="Hreward"
									placeholder="奖金" onfocus="showTips('Hreward','奖金为数字')" 
									onblur="checkHreward('Hreward','请按要求输入奖金')">
									<div id="Hrewarddiv" style="display:none">
										<span id="Hrewardspan" ></span><br>
									</div>
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
		
		$(document).on("change","#pageSize",function(){			//根据下拉框值的改变改变每页显示的记录条数
			var pageSizeSelect = $("#pageSize option:selected").val();
			var href = "";
			var a = "../servlet/PageServlet?option=Honor&currentPage=<%=currentPage%>&pageSizeSelect=";
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
			var a = "../servlet/PageServlet?option=Honor&currentPage=";
			var b = "&pageSizeSelect=<%=pageSize%>&teacher=teacher&count=0"
			path = path + a + pageVal + b;
			window.location.href = path;
		}	 
		
		//新建按钮的事件
		 $("#btn_add").click(function () {
		 $("#myModalLabel").text("新建荣誉");
		 $('#myModal').modal();
		 });
		
		 function checkHsn(id,info){
				var uValue = document.getElementById(id).value;
				if(!/^\d{1,20}$/.test(uValue)){
					document.getElementById(id+"span").innerHTML="<font color='red' size='2'>"+info+"</font>";
					document.getElementById(id+"div").style.display="block";
				}else{
					document.getElementById(id+"span").innerHTML="<font color='green' size='3'> √</font>";
				}
			}
			function checkHname(id,info){
				var uValue = document.getElementById(id).value;
				if(!/^.{1,15}$/.test(uValue)){
					document.getElementById(id+"span").innerHTML="<font color='red' size='2'>"+info+"</font>";
					document.getElementById(id+"div").style.display="block";
				}else{
					document.getElementById(id+"span").innerHTML="<font color='green' size='3'> √</font>";
				}
			}
			function checkHwinner(id,info){
				var uValue = document.getElementById(id).value;
				if(!/^.{1,50}$/.test(uValue)){
					document.getElementById(id+"span").innerHTML="<font color='red' size='2'>"+info+"</font>";
					document.getElementById(id+"div").style.display="block";
				}else{
					document.getElementById(id+"span").innerHTML="<font color='green' size='3'> √</font>";
				}
			}
			function checkHcompany(id,info){
				var uValue = document.getElementById(id).value;
				if(!/^.{1,16}$/.test(uValue)){
					document.getElementById(id+"span").innerHTML="<font color='red' size='2'>"+info+"</font>";
					document.getElementById(id+"div").style.display="block";
				}else{
					document.getElementById(id+"span").innerHTML="<font color='green' size='3'> √</font>";
				}
			}
			function checkHreward(id,info){
				var uValue = document.getElementById(id).value;
				if(!/^\d{1,}$/.test(uValue)){
					document.getElementById(id+"span").innerHTML="<font color='red' size='2'>"+info+"</font>";
					document.getElementById(id+"div").style.display="block";
				}else{
					document.getElementById(id+"span").innerHTML="<font color='green' size='3'> √</font>";
				}
			}
			
			
			function showTips(id,info){
				document.getElementById(id+"span").innerHTML="<font color='gray' size='2'>"+info+"</font>";
			}
	</script>
</body>
</html>