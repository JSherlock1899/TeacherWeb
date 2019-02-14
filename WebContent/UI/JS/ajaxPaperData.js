/**
 * 用ajax对数据进行增删改
 */


//删
$(document).on("click",".delete",function(e,url){
        var Pasearchnum = $(this).closest("tr").find(".Pasearchnum").text();
        $.ajax({
        	url:"../servlet/PaperServlet?value=1",
            type:"post",
            datatype:"json",
            data:{
                "Pasearchnum" : Pasearchnum
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
	var Pasearchnum = $(this).closest("tr").find(".Pasearchnum").text();
	var Paname = $(this).closest("tr").find(".Paname").text();
	var Pawriter = $(this).closest("tr").find(".Pawriter").text();
	var Papublish = $(this).closest("tr").find(".Papublish").text();
	var Padate = $(this).closest("tr").find(".Padate").text();
	var Pagrad = $(this).closest("tr").find(".Pagrad").text();
	var Paremarks = $(this).closest("tr").find(".Paremarks").text();
	$.ajax({
        url:"../servlet/PaperServlet?value=2",
        type:"post",
        datatype:"json",
        data:{
            "Pasearchnum" : Pasearchnum,
            "Paname" : Paname,
            "Pawriter" :Pawriter,
            "Papublish" : Papublish,
            "Padate" : Padate,
            "Pagrad" : Pagrad,
            "Paremarks" :Paremarks,
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
		"<td class=Pasearchnum><input type='text' class='Pasearchnum' style='width:50px'></td>" +
		"<td class=Paname><input type='text' class='Paname' style='width:50px'></td>" +
		"<td class=Pawriter><input type='text' class='Pawriter' style='width:50px'></td>" +
		"<td class=Papublish><input type='text' class='Papublish' style='width:50px'></td>" +
		"<td class=Padate><input type='date' class='Padate' style='width:50px'></td>"+ 
		"<td class=Pagrad><input type='text' class='Pagrad' style='width:50px'></td>"+ 
		"<td class=Paremarks><input type='text' class='Paremarks' style='width:50px'></td>" +
		"<td><a class='delete'>删除</a><a class='saveNewMsg' >保存</a></td>"
		"</tr>";
		$("table").append(tr);//向table中追加tr
	});

//获取新建行当前每个表格单元的信息
$(document).on("click",".saveNewMsg",function(){
	var Pasearchnum = $(this).closest("tr").find("input[class='Pasearchnum']").val();
	var Paname = $(this).closest("tr").find("input[class='Paname']").val();
	var Pawriter = $(this).closest("tr").find("input[class='Pawriter']").val();
	var Papublish = $(this).closest("tr").find("input[class='Papublish']").val();
	var Padate = $(this).closest("tr").find("input[class='Padate']").val();
	var Pagrad = $(this).closest("tr").find("input[class='Pagrad']").val();
	var Paremarks = $(this).closest("tr").find("input[class='Paremarks']").val();
	$.ajax({
        url:"../servlet/PaperServlet?value=3",
        type:"post",
        datatype:"json",
        data:{
            "Pasearchnum" : Pasearchnum,
            "Paname" : Paname,
            "Pawriter" :Pawriter,
            "Papublish" : Papublish,
            "Padate" : Padate,
            "Pagrad" : Pagrad,
            "Paremarks" :Paremarks,
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
