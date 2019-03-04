/**
 * 用ajax对数据进行增删改
 */


//删
$(document).on("click",".delete",function(e,url){
        var Patsn = $(this).closest("tr").find(".Patsn").text();
        $.ajax({
        	url:"../servlet/PatentServlet?value=1",		//url貌似存在问题，未解决
            type:"post",
            datatype:"json",
            data:{
                "Patsn" : Patsn
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
	var Patname = $(this).closest("tr").find(".Patname").text();
	var Pleader = $(this).closest("tr").find(".Pleader").text();
	var Patsn = $(this).closest("tr").find(".Patsn").text();
	var Patapdate = $(this).closest("tr").find(".Patapdate").text();
	var Patemdate = $(this).closest("tr").find(".Patemdate").text();
	var Patgrad = $(this).closest("tr").find(".Patgrad").text();
	var Patremarks = $(this).closest("tr").find(".Patremarks").text();
	$.ajax({
        url:"../servlet/PatentServlet?value=2",
        type:"post",
        datatype:"json",
        data:{
            "Patname" : Patname,
            "Pleader" : Pleader,
            "Patsn" :Patsn,
            "Patapdate" : Patapdate,
            "Patemdate" : Patemdate,
            "Patgrad" : Patgrad,
            "Patremarks" :Patremarks,
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
	var Patname = $('#Patname').val();
	var Pleader = $('#Pleader').val();
	var Patsn = $('#Patsn').val();
	var Patapdate = $('#Patapdate').val();
	var Patemdate = $('#Patemdate').val();
	var Patgrad = $('#Patgrad').val();
	var Patremarks = $('#Patremarks').val();
	$.ajax({
        url:"../servlet/PatentServlet?value=3",
        type:"post",
        datatype:"json",
        data:{
            "Patname" : Patname,
            "Pleader" : Pleader,
            "Patsn" :Patsn,
            "Patapdate" : Patapdate,
            "Patemdate" : Patemdate,
            "Patgrad" : Patgrad,
            "Patremarks" :Patremarks,
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

//审核通过
$(document).on("click","#pass",function(){
	var Patsn = $('.Patsn').text();
	var message  = $('#message').val();
	$.ajax({
		url:"../servlet/PatentServlet?value=4",
        type:"post",
        datatype:"json",
        data:{
        	"Patsn" : Patsn,
        	"message" : message,
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
	var Patsn = $('.Patsn').text();
	var message  = $('#message').val();
	$.ajax({
		url:"../servlet/PatentServlet?value=5",
        type:"post",
        datatype:"json",
        data:{
        	"Patsn" : Patsn,
        	"message" : message,
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

	
	   
	    
	    
		//输入页码跳转页面
		$(document).on("click","#pageGo",function(){
			//页码输入框输入的数
			var pageVal = $('.pageVal').val();
			//总页数
			var totalPage = $('#totalPage').val();
			//一页显示的条数
			var pageSize = $('#pageSize').val();
			console.log(pageVal)
			console.log(totalPage)
			console.log(pageSize)
			var college = $('#college').val();
			if(pageVal > totalPage){
				alert('请输入正确的页码！');
				return
			}
			var path = "";
			var a = "../servlet/PageServlet?option=Patent&currentPage=";
			var b = "&pageSizeSelect=" + pageSize + "&teacher=teacher&count=0&college=" + college
			path = path + a + pageVal + b;
			alert(path)
			window.location.href = path;
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
		