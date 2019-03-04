<%@page import="dao.impl.BaseDaoImpl"%>
<%@page import="dao.IBaseDao"%>
<%@page import="model.Pager"%>
<%@page import="java.util.List"%>
<%@page import="model.Patent"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="dao.impl.PatentDaoImpl"%>
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
<script type="text/javascript" src="/TeacherWeb/UI/JS/ajaxPatentData.js"></script>
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
						<input type="hidden" id="Proname" value="<%=datalist.get(0).getPatname()%>"/>
						<input type="hidden" id="majorKey" value="<%= datalist.get(0).getPatsn()%>"/>
				</div>
					<table border="2" id="table" class="table table-striped table-bordered table-condensed">
							<tr class="info">
								<td>名称</td>
								<td class="Patname edit text-center" colspan="3"><%=datalist.get(0).getPatname()%></td>
							</tr>
							<tr>
								<td>第一作者</td>
								<td class="Pleader edit" ><%=datalist.get(0).getPleader()%></td>
								<td style="width:100px">授权号</td>
								<td class="Patsn edit" colspan="3"><%= datalist.get(0).getPatsn()%></td>
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
								<td>附件</td>
								<td colspan="3"><a href="../servlet/DownloadFileServlet" multiple class="btn btn-primary" value="上传">下载附件</a></td>	
							</tr>
							<tr>
								<td>备注</td>
								<td class="Patremarks edit" colspan="3"><%=datalist.get(0).getPatremarks()%></td>
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
              <h4 class="modal-title" id="myModalLabel">新建专利信息</h4>
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">×</span>
							</button>
						</div>
						<div class="modal-body">
							<div class="form-group">
								<label for="Patname">名称</label> <input type="text" name="Patname" value="<%=datalist.get(0).getPatname()%>"
									class="form-control" id="Patname" placeholder="名称">
							</div>
							<div class="form-group">
								<label for="Pleader">第一作者</label> <input type="text" name="Pleader" value="<%=datalist.get(0).getPleader()%>"
									class="form-control" id="Pleader" placeholder="第一作者">
							</div>
							<div class="form-group">
								<label for="Patsn">授权号</label> <input type="text" value="<%=datalist.get(0).getPatsn()%>"
									name="Patsn" class="form-control" id="Patsn"
									placeholder="授权号">
							</div>
							<div class="form-group">
								<label for="Patapdate">申请时间</label> <input type="date" value="<%=datalist.get(0).getPatapdate()%>"
									name="Patapdate" class="form-control" id="Patapdate"
									placeholder="申请时间">
							</div>
							<div class="form-group">
								<label for="Patemdate">授权时间</label> <input type="date" name="Patemdate" value="<%=datalist.get(0).getPatemdate()%>"
									class="form-control" id="Patemdate" placeholder="授权时间">
							</div>
							<div class="form-group">
								<label for="Patgrad">级别</label> <input type="text" name="Patgrad" value="<%=datalist.get(0).getPatname()%>"
									class="form-control" id="Patgrad" placeholder="级别">
							</div>
							<div class="form-group">
								<label for="Patremarks">备注</label> <input type="text" value="<%=datalist.get(0).getPatremarks()%>"
									name="Patremarks" class="form-control" id="Patremarks"
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
		$(document).on("change","#pageSize",function(){			//根据下拉框值的改变改变每页显示的记录条数
			var pageSizeSelect = $("#pageSize option:selected").val();
			var href = "";
			var a = "../servlet/PageServlet?option=Patent&currentPage=<%=currentPage%>&pageSizeSelect=";
			href = href + a + pageSizeSelect + "&teacher=teacher"
			window.location.href = href;
		})    
		
		//新建按钮的事件
		 $("#btn_add").click(function () {
		 $("#myModalLabel").text("新建专利");
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
	            uploadUrl: "../servlet/UploadFileServlet?Proname=<%=datalist.get(0).getPatname()%>&majorKey=<%= datalist.get(0).getPatsn()%>", //上传的地址
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
			console.log(event);
			console.log(data); 
			console.log(previewId); 
			console.log(index); 
			});
		
		

	</script>
</body>
</html>