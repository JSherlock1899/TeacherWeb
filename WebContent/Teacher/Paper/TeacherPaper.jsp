<%@page import="dao.impl.BaseDaoImpl"%>
<%@page import="dao.IBaseDao"%>
<%@page import="model.Pager"%>
<%@page import="java.util.List"%>
<%@page import="model.Paper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="dao.impl.PaperDaoImpl"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>论文查询</title>
<link rel="stylesheet" href="/TeacherWeb/UI/CSS/bootstrap.css">
<script type="text/javascript" src="/TeacherWeb/UI/JS/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="/TeacherWeb/UI/JS/bootstrap.min.js"></script>
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
		    <li class="active">论文查询</li>
		  </ol>
		</div>
		<div class="row">
			<div class="col-md-11 col-md-offset-1 ">
				<div class="col-md-10 button-div form-inline">
					<input type="button" value="新建记录" id="btn_add" class="btn btn-success">
					<form action="../servlet/SelectExport"  method="post" id="PaperForm" class="form-group">
						<input type="hidden" name="count" value="4">
						<input type="hidden" id="totalPage" value="<%=totalPage %>"/>
						<input type="hidden" id="currentPage" value="<%=currentPage %>"/>
						<input type="hidden" id="pageSize" value="<%=pageSize %>"/>
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
							</tr>
							<%	
								for(int i=0; i<datalist.size(); i++){
									String Pasearchnum = datalist.get(i).getPasearchnum();
									
							%>
							<tr>
								<td class="Pasearchnum edit"><%=Pasearchnum%></td>
								<td class="Paname edit"><a href="../servlet/PageServlet?option=Paper&teacher=teacher&count=1&Proname=
								<%=datalist.get(i).getPaname()%>&Patsn=<%=Pasearchnum%>&order=<%=i%>&pageSize=<%=pageSize%>&currentPage=<%=currentPage%>"><%=datalist.get(i).getPaname()%></a></td>
								<td class="Pawriter edit"><%=datalist.get(i).getPawriter()%></td>
								<td class="Papublish edit"><%=datalist.get(i).getPapublish()%></td>
								<td class="Pdisvol edit"><%=datalist.get(i).getPdisvol()%></td>
								<td class="Padate edit"><%=datalist.get(i).getPadate()%></td>
								<td class="Pagrad edit"><%=datalist.get(i).getPagrad()%></td>
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
						<a href="../servlet/PageServlet?option=Paper&currentPage=1&teacher=teacher&count=0" id="homePage">首页</a>
					</li>
					<li>
						<a aria-label="Previous" id="pre" class="prenextpage" href="../servlet/PageServlet?option=Paper&currentPage=<%=currentPage - 1%>&pageSize=<%=pageSize %>&count=0
					&teacher=teacher"> 
							<span >&laquo;</span>
						</a>
					</li>
					<li id="page1"><a class="page" href="../servlet/PageServlet?option=Paper&currentPage=<%=pageArr[0]%>&pageSize=<%=pageSize%>&teacher=teacher&count=0
					"><%=pageArr[0]%></a></li>
					<li id="page2"><a class="page" href="../servlet/PageServlet?option=Paper&currentPage=<%=pageArr[1]%>&pageSize=<%=pageSize%>&teacher=teacher&count=0
					"><%=pageArr[1]%></a></li>
					<li id="page3"><a class="page" href="../servlet/PageServlet?option=Paper&currentPage=<%=pageArr[2]%>&pageSize=<%=pageSize%>&teacher=teacher&count=0
					"><%=pageArr[2]%></a></li>
					<li>
						<a id="next" aria-label="Next" class="prenextpage" href="../servlet/PageServlet?option=Paper&currentPage=<%=currentPage + 1%>&pageSize=<%=pageSize %>&count=0
					&teacher=teacher"> 
							<span>&raquo;</span>
						</a>
					</li>
					<li><a href="../servlet/PageServlet?option=Paper&currentPage=<%=totalPage %>&teacher=teacher&count=0" id="endPage" >尾页</a></li>
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
              <h4 class="modal-title" id="myModalLabel">新建论文信息</h4>
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">×</span>
							</button>
						</div>
						<div class="modal-body">
							<div class="form-group">
								<label for="Pasearchnum">检索号</label> 
								<input type="text" name="Pasearchnum"
									class="form-control" id="Pasearchnum" placeholder="检索号"
									onfocus="showTips('Pasearchnum','查询编号为1-10位的数字')" 
									onblur="checkPasearchnum('Pasearchnum','请按要求输入查询编号')">
									<div id="Pasearchnumdiv" style="display:none">
										<span id="Pasearchnumspan" ></span><br>
									</div>
							</div>
							<div class="form-group">
								<label for="Paname">论文名</label> <input type="text" name="Paname"
									class="form-control" id="Paname" placeholder="论文名"
									onfocus="showTips('Paname','论文名称不能超过15个字符')" 
									onblur="checkPaname('Paname','请按要求输入论文名称')">
									<div id="Panamediv" style="display:none">
										<span id="Panamespan" ></span><br>
									</div>
							</div>
							<div class="form-group">
								<label for="Pawriter">第一作者</label> <input type="text"  value="<%=user %>"
									name="Pawriter" class="form-control" id="Pawriter"
									placeholder="第一作者">
									<div id="Pawriterdiv" style="display:none">
										<span id="Pawriterspan" ></span><br>
									</div>
							</div>
							<div class="form-group">
								<label for="Papublish">发表期刊</label> <input type="text"
									name="Papublish" class="form-control" id="Papublish"
									placeholder="发表期刊">
							</div>
							<div class="form-group">
								<label for="Pdisvol">期/卷/页</label> <input type="text" name="Pdisvol"
									class="form-control" id="Pdisvol" placeholder="期/卷/页">
							</div>
							<div class="form-group">
								<label for="Padate">发表时间</label> <input type="date" name="Padate"
									class="form-control" id="Padate" placeholder="发表时间">
							</div>
             				 <div class="form-group">
								<label for="Pagrad">级别</label> 
								<select name="Pagrad"
									class="form-control" id="Pagrad">
									<option value="T类">T类</option>
									<option value="A类">A类</option>
									<option value=B类>B类</option>
									<option value="C类">C类</option>
									<option value="D类">D类</option>
									<option value="E类">E类</option>
								</select>
							</div>
							<div class="form-group">
								<label for="Paremarks">备注</label> <input type="text"
									name="Paremarks" class="form-control" id="Paremarks"
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
		var a = "../servlet/PageServlet?option=Paper&currentPage=<%=currentPage%>&pageSizeSelect=";
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
		var a = "../servlet/PageServlet?option=Paper&currentPage=";
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
		    $('#PaperForm').attr("action","../servlet/UploadFileServlet?&count=4&grade=<%=grade%>");
		    $('#PaperForm').attr("enctype","multipart/form-data");
		    $('#PaperForm').submit();
	    }
		
	  //新建按钮的事件
		 $("#btn_add").click(function () {
		 $("#myModalLabel").text("新建论文");
		 $('#myModal').modal();
		 });

	  
		 function checkPasearchnum(id,info){
				var uValue = document.getElementById(id).value;
				if(!/^\d{1,10}$/.test(uValue)){
					document.getElementById(id+"span").innerHTML="<font color='red' size='2'>"+info+"</font>";
					document.getElementById(id+"div").style.display="block";
				}else{
					document.getElementById(id+"span").innerHTML="<font color='green' size='3'> √</font>";
				}
			}
			function checkPaname(id,info){
				var uValue = document.getElementById(id).value;
				if(!/^.{1,15}$/.test(uValue)){
					document.getElementById(id+"span").innerHTML="<font color='red' size='2'>"+info+"</font>";
					document.getElementById(id+"div").style.display="block";
				}else{
					document.getElementById(id+"span").innerHTML="<font color='green' size='3'> √</font>";
				}
			}
			function checkPawriter(id,info){
				var uValue = document.getElementById(id).value;
				if(!/^.{1,15}$/.test(uValue)){
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