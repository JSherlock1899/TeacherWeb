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
<script type="text/javascript" src="/TeacherWeb/UI/JS/ajaxHonorData.js"></script>
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
				%>
	<div class="table-main col-md-12" >
		<div class="col-md-4" >
		  <ol class="breadcrumb" style="margin-left:5em;margin-top:2em">
		    <li><a href="#">主页</a></li>
		    <li><a href="#">查询</a></li>
		    <li><a href="#">专利查询</a></li>
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
						<input type="hidden" id="Proname" value="<%=datalist.get(0).getHname()%>"/>
						<input type="hidden" id="majorKey" value="<%= datalist.get(0).getHsn()%>"/>
				</div>
					<table border="2" id="table" class="table table-striped table-bordered table-condensed">
							<tr class="info">
								<td>名称</td>
								<td class="Hname edit text-center"  colspan="3"><%=datalist.get(0).getHname()%></td>
							</tr>
							<tr>
								<td>编号</td>
								<td class="Hsn edit"><%=datalist.get(0).getHsn()%></td>
								<td>获奖者</td>
								<td class="Hwinner edit"><%=datalist.get(0).getHwinner()%></td>
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
								<td class="Patremarks edit"><%=datalist.get(0).getHremarks()%></td>
								<td>附件</td>
								<td><a href="../servlet/DownloadFileServlet?mainKey=<%=datalist.get(0).getHsn() %>
								&option=honor&Proname=<%=datalist.get(0).getHname() %>"  class="btn btn-primary Download" value="上传">下载附件</a></td>	
								<input type="hidden" class="accessoryPath" value="<%=datalist.get(0).getHaccessory() %>"/>
								<input type="hidden" class="key" value="<%=datalist.get(0).getHsn() %>"/>
							</tr>
							<tr>
								<td>审核情况</td>
								<td><%=datalist.get(0).getHaudit()%></td>
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
              				<h4 class="modal-title" id="myModalLabel">新建荣誉信息</h4>
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">×</span>
							</button>
						</div>
						<div class="modal-body">
							<div class="form-group">
								<label for="Hsn">荣誉编号</label> 
									<input type="text" name="Hsn" value="<%=datalist.get(0).getHsn()%>"
									class="form-control" id="Hsn" placeholder="荣誉编号"
									onfocus="showTips('Hsn','荣誉编号为1-20位的数字')" 
									onblur="checkHsn('Hsn','请按要求输入荣誉编号')">
									<div id="Hsndiv" style="display:none">
										<span id="Hsnspan" ></span><br>
									</div>
							</div>
							<div class="form-group">
								<label for="Hname">荣誉名称</label>
									<input type="text" name="Hname" value="<%=datalist.get(0).getHname()%>"
									class="form-control" id="Hname" placeholder="荣誉名称"
									onfocus="showTips('Hname','荣誉名称不能超过15个字符')" 
									onblur="checkHname('Hname','请按要求输入荣誉名称')">
									<div id="Hnamediv" style="display:none">
										<span id="Hnamespan" ></span><br>
									</div>
							</div>
							<div class="form-group">
								<label for="Hwinner">获奖者</label> 
									<input type="text" value="<%=datalist.get(0).getHwinner()%>"
									name="Hwinner" class="form-control" id="Hwinner"
									placeholder="获奖者" onfocus="showTips('Hwinner','获奖者不能超过50个字符')" 
									onblur="checkHwinner('Hwinner','请按要求输入获奖者')">
									<div id="Hwinnerdiv" style="display:none">
										<span id="Hwinnerspan" ></span><br>
									</div>
							</div>
							<div class="form-group">
								<label for="Hdate">获奖时间</label> <input type="date"  value="<%=datalist.get(0).getHdate()%>"
									name="Hdate" class="form-control" id="Hdate"
									placeholder="获奖时间">
							</div>
							<div class="form-group">
								<label for="Hcompany">颁奖单位</label> 
									<input type="text" name="Hcompany" value="<%=datalist.get(0).getHcompany()%>"
									class="form-control" id="Hcompany" placeholder="颁奖单位"
									onfocus="showTips('Hcompany','颁发单位不能超过16个字符')" 
									onblur="checkHcompany('Hcompany','请按要求输入颁发单位')">
									<div id="Hcompanydiv" style="display:none">
										<span id="Hcompanyspan" ></span><br>
									</div>
							</div>
							<div class="form-group">
								<label for="Hgrad">级别</label> 
									<select name="Hgrad"  value="<%=datalist.get(0).getHgrad()%>"
									class="form-control" id="Hgrad">
									<option value="校级">校级</option>
									<option value="市级">市级</option>
									<option value="省级">省级</option>
									<option value="国家级">国家级</option>
								</select>
							</div>
             			 <div class="form-group">
								<label for="Hreward">奖金</label> 
									<input type="text"  value="<%=datalist.get(0).getHreward()%>"
									name="Hreward" class="form-control" id="Hreward"
									placeholder="奖金" onfocus="showTips('Hreward','奖金为数字')" 
									onblur="checkHreward('Hreward','请按要求输入奖金')">
									<div id="Hrewarddiv" style="display:none">
										<span id="Hrewardspan" ></span><br>
									</div>
							</div>
							<div class="form-group">
								<label for="Hremarks">备注</label> <input type="text"  value="<%=datalist.get(0).getHremarks()%>"
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
		 $("#myModalLabel").text("修改荣誉信息");
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
	            uploadUrl: "../servlet/UploadFileServlet?Proname=<%=datalist.get(0).getHname()%>&key=<%= datalist.get(0).getHsn()%>&grade=3&option=honor", //上传的地址
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