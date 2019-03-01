/**
 * 用ajax对数据进行增删改
 */


//删
$(document).on("click",".delete",function(e,url){
        var Hsn = $(this).closest("tr").find(".Hsn").text();
        $.ajax({
        	url:"../servlet/HonorServlet?value=1",
            type:"post",
            datatype:"json",
            data:{
                "Hsn" : Hsn
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
//获取该行当前每个表格单元的信息并提交到servlet进行修改
$(document).on("click",".save",function(){
	var Hsn = $(this).closest("tr").find(".Hsn").text();
	var Hname = $(this).closest("tr").find(".Hname").text();
	var Hwinner = $(this).closest("tr").find(".Hwinner").text();
	var Hdate = $(this).closest("tr").find(".Hdate").text();
	var Hcompany = $(this).closest("tr").find(".Hcompany").text();
	var Hgrad = $(this).closest("tr").find(".Hgrad").text();
	var Hreward = $(this).closest("tr").find(".Hreward").text();
	var Hremarks = $(this).closest("tr").find(".Hremarks").text();
	$.ajax({
        url:"../servlet/HonorServlet?value=2",
        type:"post",
        datatype:"json",
        data:{
            "Hsn" : Hsn,
            "Hname" : Hname,
            "Hwinner" :Hwinner,
            "Hdate" : Hdate,
            "Hcompany" : Hcompany,
            "Hgrad" : Hgrad,
            "Hreward" :Hreward,
            "Hremarks" :Hremarks,
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
	var Hsn = $('#Hsn').val();
	var Hname = $('#Hname').val();
	var Hwinner = $('#Hwinner').val();
	var Hdate = $('#Hdate').val();
	var Hcompany = $('#Hcompany').val();
	var Hgrad = $('#Hgrad').val();
	var Hreward = $('#Hreward').val();
	var Hremarks = $('#Hremarks').val();
	$.ajax({
        url:"../servlet/HonorServlet?value=3",
        type:"post",
        datatype:"json",
        data:{
            "Hsn" : Hsn,
            "Hname" : Hname,
            "Hwinner" :Hwinner,
            "Hdate" : Hdate,
            "Hcompany" : Hcompany,
            "Hgrad" : Hgrad,
            "Hreward" :Hreward,
            "Hremarks" :Hremarks,
        },
        success : function(result){
                alert("修改成功");
                location.reload();
                //window.location.href = "";
        },
        error:function(result){  
            alert('请求出现错误...');  
        }
    });
});


//判断当前状态是编辑还是保存
$(document).on("click",".updata",function(e){
	var content = $(this).text();
	if(content == "编辑"){
		$(this).removeClass('save');
	}else{
		$(this).addClass('save');
	}
})

	//审核通过
	$(document).on("click","#pass",function(){
		var Hsn = $(this).closest("tr").find(".Hsn").text();
		$.ajax({
			url:"../servlet/HonorServlet?value=4",
	        type:"post",
	        datatype:"json",
	        data:{
	        	"Hsn" : Hsn,
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
		var Hsn = $(this).closest("tr").find(".Hsn").text();
		$.ajax({
			url:"../servlet/HonorServlet?value=5",
	        type:"post",
	        datatype:"json",
	        data:{
	        	"Hsn" : Hsn,
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
		 $("#myModalLabel").text("新建荣誉");
		 $('#myModal').modal();
		 });

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
		var path = "";
		var a = "../servlet/PageServlet?option=Honor&currentPage=";
		var b = "&pageSizeSelect=" + pageSize + "&teacher=teacher"
		path = path + a + pageVal + b;
		window.location.href = path;
	}
