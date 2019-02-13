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
//新建表格行
$(document).on("click","#addMsg",function(){
		var tr = "<tr><td><input type='checkbox' name = 'select'></td>"+
		"<td class=Hsn><input type='text' class='Hsn' style='width:50px'></td>" +
		"<td class=Hname><input type='text' class='Hname' style='width:50px'></td>" +
		"<td class=Hwinner><input type='text' class='Hwinner' style='width:50px'></td>" +
		"<td class=Hdate><input type='date' class='Hdate' style='width:50px'></td>" +
		"<td class=Hcompany><input type='text' class='Hcompany' style='width:50px'></td>"+ 
		"<td class=Hgrad><input type='text' class='Hgrad' style='width:50px'></td>"+ 
		"<td class=Hreward><input type='text' class='Hreward' style='width:50px'></td>" +
		"<td class=Hremarks><input type='text' class='Hremarks' style='width:50px'></td>"+
		"<td><a class='delete'>删除</a><a class='saveNewMsg' >保存</a></td>"
		"</tr>";
		$("table").append(tr);//向table中追加tr
	});

//获取新建行当前每个表格单元的信息
$(document).on("click",".saveNewMsg",function(){
	var Hsn = $(this).closest("tr").find("input[class='Hsn']").val();
	var Hname = $(this).closest("tr").find("input[class='Hname']").val();
	var Hwinner = $(this).closest("tr").find("input[class='Hwinner']").val();
	var Hdate = $(this).closest("tr").find("input[class='Hdate']").val();
	var Hcompany = $(this).closest("tr").find("input[class='Hcompany']").val();
	var Hgrad = $(this).closest("tr").find("input[class='Hgrad']").val();
	var Hreward = $(this).closest("tr").find("input[class='Hreward']").val();
	var Hremarks = $(this).closest("tr").find("input[class='Hremarks']").val();
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
