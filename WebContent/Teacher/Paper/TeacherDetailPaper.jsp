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
<title>详细信息</title>
<link rel="stylesheet" href="/TeacherWeb/UI/CSS/bootstrap.css">
<link rel="stylesheet" href="/TeacherWeb/UI/CSS/fileinput.min.css">
<script type="text/javascript" src="/TeacherWeb/UI/JS/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="/TeacherWeb/UI/JS/bootstrap.min.js"></script>
<script type="text/javascript" src="/TeacherWeb/UI/JS/commonUse.js"></script>
<script type="text/javascript" src="/TeacherWeb/UI/JS/bootstrap-table.js"></script>
<script type="text/javascript" src="/TeacherWeb/UI/JS/fileinput.min.js"></script>
<script type="text/javascript" src="/TeacherWeb/UI/JS/zh.js"></script>
<link rel="stylesheet" href="/TeacherWeb/UI/CSS/style.css">
<script type="text/javascript" src="/TeacherWeb/UI/JS/ajaxPaperData.js"></script>
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
				%>
	<div class="table-main col-md-12" >
		<div class="col-md-4" >
		  <ol class="breadcrumb" style="margin-left:5em;margin-top:2em">
		    <li><a href="#">主页</a></li>
		    <li><a href="#">查询</a></li>
		    <li><a href="#">论文查询</a></li>
		    <li class="active">详细信息</li>
		  </ol>
		</div>
		<div class="row">
			<div class="col-md-11 col-md-offset-1 ">
				<div class="col-md-10 button-div form-inline">
						<input type="hidden" name="count" value="4">
						<input type="file" id="file"style="display: none"> 
						<input type="hidden" id="totalPage" value="<%=totalPage %>"/>
						<input type="hidden" id="currentPage" value="<%=currentPage %>"/>
						<input type="hidden" id="pageSize" value="<%=pageSize %>"/>
						<input type="hidden" id="totalRecord" value="<%=totalRecord %>"/>
						<input type="hidden" id="grade" value="<%=grade %>"/>
						<input type="hidden" id="Proname" value="<%=datalist.get(0).getPaname()%>"/>
						<input type="hidden" id="majorKey" value="<%= datalist.get(0).getPasearchnum()%>"/>
				</div>
					<table border="2" id="table" class="table table-striped table-bordered table-condensed">
							<tr class="info">
								<td>名称</td>
								<td class="Paname edit text-center" colspan="3"><%=datalist.get(0).getPaname()%></td>
							</tr>
							<tr>
								<td>检索号</td>
								<td class="Pasearchnum edit "><%=datalist.get(0).getPasearchnum()%></td>
								<td>第一作者</td>
								<td class="Pawriter edit"><%=datalist.get(0).getPawriter()%></td>
							</tr>
							<tr>
								<td>发表期刊</td>
								<td class="Papublish edit"><%=datalist.get(0).getPapublish()%></td>
								<td>期/卷/页</td>
								<td class="Pdisvol edit"><%=datalist.get(0).getPdisvol()%></td>
							</tr>
							<tr>
								<td>发表时间</td>
								<td class="Padate edit"><%=datalist.get(0).getPadate()%></td>
								<td>级别</td>
								<td class="Pagrad edit"><%=datalist.get(0).getPagrad()%></td>
							</tr>
							<tr>
								<td>附件</td>
								<td colspan="3"><a href="../servlet/DownloadFileServlet?mainKey=<%=datalist.get(0).getPasearchnum() %>
								&option=paper&Proname=<%=datalist.get(0).getPaname() %>"  class="btn btn-primary Download" value="上传">下载附件</a></td>	
								<input type="hidden" class="accessoryPath" value="<%=datalist.get(0).getPaccessory() %>"/>
								<input type="hidden" class="key" value="<%=datalist.get(0).getPasearchnum()%>"/>
							</tr>
							<tr>
								<td>备注</td>
								<td class="Patremarks edit" colspan="3"><%=datalist.get(0).getParemarks()%></td>
							</tr>
							<tr>
								<td>审核情况</td>
								<td><%=datalist.get(0).getPaudit()%></td>
								<td>审核意见</td>
								<td><%=datalist.get(0).getMessage()%></td>
							</tr>
							
							<% 
					      basedao.closeCon();%>
						</table>
						<button class="btn btn-primary form-group" style="width:100%;margin-bottom:10px" id="btn_add">重新编辑</button>
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
									<input type="text" name="Pasearchnum" value="<%=datalist.get(0).getPasearchnum() %>"
									class="form-control" id="Pasearchnum" placeholder="检索号"
									onfocus="showTips('Pasearchnum','查询编号为1-10位的数字')" 
									onblur="checkPasearchnum('Pasearchnum','请按要求输入查询编号')">
									<div id="Pasearchnumdiv" style="display:none">
										<span id="Pasearchnumspan" ></span><br>
									</div>
							</div>
							<div class="form-group">
								<label for="Paname">论文名</label> 
									<input type="text" name="Paname" value="<%=datalist.get(0).getPaname() %>"
									class="form-control" id="Paname" placeholder="论文名"
									onfocus="showTips('Paname','论文名称不能超过15个字符')" 
									onblur="checkPaname('Paname','请按要求输入论文名称')">
									<div id="Panamediv" style="display:none">
										<span id="Panamespan" ></span><br>
									</div>
							</div>
							<div class="form-group">
								<label for="Pawriter">第一作者</label> <input type="text"  value="<%=datalist.get(0).getPawriter() %>"
									name="Pawriter" class="form-control" id="Pawriter"
									placeholder="第一作者">
							</div>
							<div class="form-group">
								<label for="Papublish">发表期刊</label> <input type="text"  value="<%=datalist.get(0).getPapublish() %>"
									name="Papublish" class="form-control" id="Papublish"
									placeholder="发表期刊">
							</div>
							<div class="form-group">
								<label for="Pdisvol">期/卷/页</label> <input type="text" name="Pdisvol" value="<%=datalist.get(0).getPdisvol() %>"
									class="form-control" id="Pdisvol" placeholder="期/卷/页">
							</div>
							<div class="form-group">
								<label for="Padate">发表时间</label> <input type="date" name="Padate"  value="<%=datalist.get(0).getPadate() %>"
									class="form-control" id="Padate" placeholder="发表时间">
							</div>
             				 <div class="form-group">
								<label for="Pagrad">级别</label><select name="Pagrad"  value="<%=datalist.get(0).getPagrad() %>"
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
								<label for="Paremarks">备注</label> <input type="text" value="<%=datalist.get(0).getParemarks() %>"
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
								class="btn btn-primary save">
								<span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>保存
							</button>
						</div>
					</div>
				</div>
			</div>
			<div class="container-fluid">
				    <form id="form" action="" method="post" enctype="multipart/form-data">
				      <div class="row form-group">
				         <div class="panel panel-primary">
				           <div class="panel-heading" align="center">
				             <label style="text-align: center;font-size: 18px;">文 件 上 传</label>
				           </div>
				         <div class="panel-body">
				           <div class="col-sm-12">
				              <input id="uploadfile" name="file" multiple type="file" data-show-caption="true">
				           </div>
				         </div>
				        </div>
				      </div>
				    </form>
				</div>
	<script type="text/javascript">
		//新建按钮的事件
		 $("#btn_add").click(function () {
		 $("#myModalLabel").text("修改论文信息");
		 $('#myModal').modal();
		 });
		
		//上传文件
			$(function () {
		        initFileInput("uploadfile");
		    })
	 
		    function initFileInput(ctrlName) {
		        var control = $('#' + ctrlName);
		        control.fileinput({
		            language: 'zh', //设置语言
		            uploadUrl: "../servlet/UploadFileServlet?Proname=<%=datalist.get(0).getPaname()%>&key=<%= datalist.get(0).getPasearchnum()%>&grade=3&option=paper", //上传的地址
		            allowedFileExtensions: ['jpg', 'gif', 'png','doc','docx','pdf','ppt','pptx','txt'],//接收的文件后缀
		            maxFilesNum : 5,//上传最大的文件数量
		            //uploadExtraData:{"id": 1, "fileName":'123.mp3'},
		            uploadAsync: true, //默认异步上传
		            showUpload: true, //是否显示上传按钮
		            showRemove : true, //显示移除按钮
		            showPreview : true, //是否显示预览
		            showCaption: false,//是否显示标题
		            browseClass: "btn btn-primary", //按钮样式
		            //dropZoneEnabled: true,//是否显示拖拽区域
		            //minImageWidth: 50, //图片的最小宽度
		            //minImageHeight: 50,//图片的最小高度
		            //maxImageWidth: 1000,//图片的最大宽度
		            //maxImageHeight: 1000,//图片的最大高度
		            maxFileSize: 0,//单位为kb，如果为0表示不限制文件大小
		            //minFileCount: 0,
		            //maxFileCount: 10, //表示允许同时上传的最大文件个数
		            enctype: 'multipart/form-data',
		            validateInitialCount:true,
		            previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
		            msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
	 
		        })
				}
				
			$('#uploadfile').on('filebatchuploadsuccess', function(event, data, previewId, index) { 

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