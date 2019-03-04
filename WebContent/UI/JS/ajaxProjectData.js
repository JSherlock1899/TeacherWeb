/**
 * 用ajax对数据进行增删改
 */


//删
$(document).on("click",".delete",function(e,url){
        var Psn = $(this).closest("tr").find(".Psn").text();
        $.ajax({
        	url:"../servlet/ProjectServlet?value=1",		//url存在问题，未解决
            type:"post",
            datatype:"json",
            data:{
                "Psn" : Psn
            },
            success : function(msg){              
                    alert("删除成功");
                    $(e.target).closest("tr").fadeOut();
                    window.location.reload();
            },
            error:function(msg){  
                alert('请求出现错误...');  
            }
        });
    });


//改
//判断当前状态是编辑还是保存
$(document).on("click",".updata",function(e){
	var content = $(this).text();
	if(content == "编辑"){
		$(this).removeClass('save');
	}else{
		$(this).addClass('save');
	}
})




//获取该行当前每个表格单元的信息并提交到servlet进行修改
$(document).on("click",".save",function(){
	var Psn = $(this).closest("tr").find(".Psn").text();
	var Pleader = $(this).closest("tr").find(".Pname").text();
	var Pname = $(this).closest("tr").find(".Pleader").text();
	var Pmember = $(this).closest("tr").find(".Pmember").text();
	var Pgrad = $(this).closest("tr").find(".Pgrad").text();
	var Pkind = $(this).closest("tr").find(".Pkind").text();
	var Pmoney = $(this).closest("tr").find(".Pmoney").text();
	var Pstatime = $(this).closest("tr").find(".Pstatime").text();
	var Pcondition = $(this).closest("tr").find(".Pcondition").text();
	var Pendtime = $(this).closest("tr").find(".Pendtime").text();
	var Premarks = $(this).closest("tr").find(".Premarks").text();
	var Paccessory = $(this).closest("tr").find(".Paccessory").text();
	$.ajax({
        url:"../servlet/ProjectServlet?value=2",
        type:"post",
        datatype:"json",
        data:{
            "Psn" : Psn,
            "Pleader" : Pleader,
            "Pname" :Pname,
            "Pmember" : Pmember,
            "Pgrad" : Pgrad,
            "Pkind" : Pkind,
            "Pmoney" :Pmoney,
            "Pstatime" : Pstatime,
            "Pcondition" : Pcondition,
            "Pendtime" : Pendtime,
            "Premarks" :Premarks,
            "Paccessory" : Paccessory,
        },
        
        success : function(result){
            
                alert("修改成功");
        },
        error:function(result){  
            alert('请求出现错误...');  
        }
    });
});

//新建信息
//获取新建行当前每个表格单元的信息
$(document).on("click",".saveNewMsg",function(){
	var Psn = $('#Psn').val();
	var Pleader = $('#Pleader').val();
	var Pname = $('#Pname').val();
	var Pmember = $('#Pmember').val();
	var Pgrad = $('#Pgrad option:selected').val();
	var Pkind = $('#Pkind option:selected').val();
	var Pmoney = $('#Pmoney').val();
	var Pstatime = $('#Pstatime').val();
	var Pcondition = $('#Pcondition option:selected').val();
	var Pendtime = $('#Pendtime').val();
	var Premarks = $('#Premarks').val();
	$.ajax({
        url:"../servlet/ProjectServlet?value=3",
        type:"post",
        datatype:"json",
        data:{
        	"Psn" : Psn,
            "Pleader" : Pleader,
            "Pname" :Pname,
            "Pmember" : Pmember,
            "Pgrad" : Pgrad,
            "Pkind" : Pkind,
            "Pmoney" :Pmoney,
            "Pstatime" : Pstatime,
            "Pcondition" : Pcondition,
            "Pendtime" : Pendtime,
            "Premarks" :Premarks,
        },
        success : function(result){
                alert("提交成功");
                location.reload();
                //window.location.href = "";
        },
        error:function(result){  
            alert('请求出现错误...');  
        }
    });
});


	//审核通过
	$(document).on("click","#pass",function(){
		var Psn = $(this).closest("tr").find(".Psn").text();
		$.ajax({
			url:"../servlet/ProjectServlet?value=4",
	        type:"post",
	        datatype:"json",
	        data:{
	        	"Psn" : Psn,
	        },
	        success : function(result){
	                alert("操作成功");
	                location.reload();
	        },
	        error:function(result){  
	            alert('请求出现错误...');  
	        }
		})
	})
	
	
	//审核不通过
	$(document).on("click","#nopass",function(){
		var Psn = $(this).closest("tr").find(".Psn").text();
		$.ajax({
			url:"../servlet/ProjectServlet?value=5",
	        type:"post",
	        datatype:"json",
	        data:{
	        	"Psn" : Psn,
	        },
	        success : function(result){
	                alert("操作成功");
	                location.reload();
	        },
	        error:function(result){  
	            alert('请求出现错误...');  
	        }
		})
	})


	 //新建按钮的事件
		$("#btn_add").click(function () {
		$("#myModalLabel").text("新建项目信息");
		$('#myModal').modal();
		});
	
	function skipPage(){								//输入页码跳转页面
		//页码输入框输入的数
		var pageVal = $('.pageVal').val();
		//总页数
		var totalPage = $('#totalPage').val();
		//一页显示的条数
		var pageSize = $('#pageSize').val();
		var college = $('#college').val();
		alert(college)
		if(pageVal > totalPage){
			alert('请输入正确的页码！');
			return
		}
		var path = "";
		var a = "../servlet/PageServlet?option=Project&currentPage=";
		var b = "&pageSizeSelect=" + pageSize + "&teacher=teacher&count=0&college=" + college
		path = path + a + pageVal + b;
		window.location.href = path;
	}

	$(document).on("change","#pageSize",function(){			//根据下拉框值的改变改变每页显示的记录条数
		var pageSizeSelect = $("#pageSize option:selected").val();
		var pageSize = $('#pageSize').val();
		var currentPage = $('#currentPage').val();
		//数据总条数
		var totalRecord = $('#totalRecord').val();
		if(totalRecord < pageSize){
			alert('数据总数小于' + pageSize + "!");
		}
		var href = "";
		var a = "../servlet/PageServlet?option=Project&currentPage=" + currentPage + "&pageSizeSelect=";
		href = href + a + pageSizeSelect + "&teacher=teacher"
		window.location.href = href;
	})

	$(function(){
		var totalPage = $('#totalPage').val();
		var currentPage = $('#currentPage').val();
		if(currentPage == 1){					//首页和尾页时分别隐藏对应按钮
			$('#pre').css("display","none");
		}
		
		if(currentPage == totalPage ){
			$('#next').css("display","none");
		}
	})
	