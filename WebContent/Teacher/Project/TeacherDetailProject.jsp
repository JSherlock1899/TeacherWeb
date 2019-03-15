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
<script type="text/javascript" src="/TeacherWeb/UI/JS/ajaxProjectData.js"></script>
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
							String Tname = (String) request.getAttribute("Tname");
							int[] pageArr = (int[]) request.getAttribute("pageArr"); 
							int grade = (int) session.getAttribute("grade");	//获取用户的权限等级
				%>
	<div class="table-main col-md-12" >
		<div class="col-md-4" >
		  <ol class="breadcrumb" style="margin-left:5em;margin-top:2em">
		    <li><a href="#">主页</a></li>
		    <li><a href="#">查询</a></li>
		    <li><a href="#">项目查询</a></li>
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
						<input type="hidden" id="Proname" value="<%=datalist.get(0).getPname()%>"/>
						<input type="hidden" id="majorKey" value="<%= datalist.get(0).getPsn()%>"/>
				</div>
					<table border="2" id="table" class="table table-striped table-bordered table-condensed">
							<tr class="info">
								<td>项目名称</td>
								<td class="Pname edit text-center" colspan="3"><%=datalist.get(0).getPname()%></td>
							</tr>
							<tr>
								<td>项目编号</td>
								<td class="Psn edit " ><%=datalist.get(0).getPsn()%></td>
								<td>负责人</td>
								<td class="Pleader edit"><%=datalist.get(0).getPleader()%></td>
							</tr>
							<tr>
								<td>级别</td>
								<td class="Pgrad edit"><%=datalist.get(0).getPgrad()%></td>
								<td>类型</td>
								<td class="Pkind edit"><%=datalist.get(0).getPkind()%></td>
							</tr>
							<tr>
								<td>科研状态</td>
								<td class="Pcondition edit"><%=datalist.get(0).getPcondition()%></td>
								<td>成员</td>
								<td class="Pmember edit"><%=datalist.get(0).getPmember()%></td>
							</tr>
							<tr>
								<td>经费</td>
								<td class="Pmoney edit"><%=datalist.get(0).getPmoney()%></td>
								<td>立项时间</td>
								<td class="Pstatime edit"><%=datalist.get(0).getPstatime()%></td>
							</tr>
							<tr>
								<td>结题时间</td>
								<td class="Pendtime edit"><%=datalist.get(0).getPendtime()%></td>
								<td>附件</td>
								<td colspan="3"><a href="../servlet/DownloadFileServlet?mainKey=<%=datalist.get(0).getPsn() %>
								&option=project&Proname=<%=datalist.get(0).getPname() %>"  class="btn btn-primary Download" value="上传">下载附件</a></td>	
								<input type="hidden" class="accessoryPath" value="<%=datalist.get(0).getPaccessory() %>"/>
								<input type="hidden" class="key" value="<%=datalist.get(0).getPsn() %>"/>
							</tr>
							<tr>
								<td>备注</td>
								<td class="Patremarks edit" colspan="3"><%=datalist.get(0).getPremarks()%></td>
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
									<input type="text" name="Psn" id="Psn" onfocus="showTips('Psn','项目编号为1-20位的数字')" value="<%=datalist.get(0).getPsn() %>"
								onblur="checkPsn('Psn','请按要求输入项目编号')" class="form-control" id="Psn" placeholder="项目编号">
								<div id="Psndiv" style="display:none">
									<span id="Psnspan" ></span><br>
								</div>
							</div>
							<div class="form-group">
								<label for="Pname">项目名称</label> <input type="text" name="Pname"  value="<%=datalist.get(0).getPname()%>"
									class="form-control" id="Pname" placeholder="项目名称">
							</div>
							<div class="form-group">
								<label for="Pleader">负责人</label> <input type="text"    value="<%=datalist.get(0).getPleader() %>"
									name="Pleader" class="form-control" id="Pleader"
									placeholder="负责人">
							</div>
							<div class="form-group">
								<label for="Pmember">成员</label> 
									<input type="text" name="Pmember" id="Pmember" onfocus="showTips('Pmember','项目成员不能超过50个字符')" 
									onblur="checkPmember('Pmember','请按要求输入项目成员')" class="form-control" placeholder="成员"
									value="<%=datalist.get(0).getPmember() %>">
									<div id="Pmembersdiv" style="display:none">
										<span id="Pmemberspan" ></span><br>
									</div>
							</div>
							<div class="form-group">
								<label for="Pgrad">级别</label>
									<select name="Pgrad"  value="<%=datalist.get(0).getPgrad() %>"
									class="form-control" id="Pgrad">
									<option value="校级">校级</option>
									<option value="市级">市级</option>
									<option value="省级">省级</option>
									<option value="部级">部级</option>
								</select>
							</div>
							<div class="form-group">
								<label for="Pkind">类型</label>  
								<select name="Pkind"  value="<%=datalist.get(0).getPkind() %>"
									class="form-control" id="Pkind">
									<option value="横向">横向</option>
									<option value="纵向">纵向</option>
								</select>
							</div>
							<div class="form-group">
								<label for="Pcondition">科研状态</label> 
								<select name="Pcondition"  value="<%=datalist.get(0).getPcondition() %>"
									class="form-control" id="Pcondition">
									<option value="未结题">未结题</option>
									<option value="已结题">已结题</option>
								</select>
							</div>
							<div class="form-group">
								<label for="Pmoney">经费 </label>
									<input type="text" name="Pmoney"   value="<%=datalist.get(0).getPmoney() %>"
									class="form-control" id="Pmoney" placeholder="经费"
									onfocus="showTips('Pmoney','项目经费为数字')" 
									onblur="checkPmoney('Pmoney','请按要求输入项目经费')">
									<div id="Pmoneydiv" style="display:none">
										<span id="Pmoneyspan" ></span><br>
									</div>
							</div>
							<div class="form-group">
								<label for="Pstatime">立项时间 </label> <input type="date"  value="<%=datalist.get(0).getPstatime()%>"
									name="Pstatime" class="form-control" id="Pstatime"
									placeholder="立项时间	">
							</div>
							<div class="form-group">
								<label for="Pendtime">结题时间</label> <input type="date"  value="<%=datalist.get(0).getPendtime()%>"
									name="Pendtime" class="form-control" id="Pendtime"
									placeholder="结题时间">
							</div>
							<div class="form-group">
								<label for="Premarks">备注</label> <input type="text"  value="<%=datalist.get(0).getPremarks()%>"
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
				</div>
	 		</div>
	</div>
	<script type="text/javascript">
		//新建按钮的事件
		 $("#btn_add").click(function () {
		 $("#myModalLabel").text("修改项目信息");
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
	            uploadUrl: "../servlet/UploadFileServlet?Proname=<%=datalist.get(0).getPname()%>&key=<%= datalist.get(0).getPsn()%>&grade=3&option=project", //上传的地址
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
			return check;
            }
	</script>
</body>
</html>