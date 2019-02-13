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
//新建表格行
$(document).on("click","#addMsg",function(){
		var tr = "<tr><td><input type='checkbox' name = 'select'></td>"+
		"<td class=Psn><input type='text' class='Psn' style='width:50px'></td>" +
		"<td class=Pname><input type='text' class='Pname' style='width:50px'></td>" +
		"<td class=Pleader><input type='text' class='Pleader' style='width:50px'></td>" +
		"<td class=Pgrad><input type='text' class='Pgrad' style='width:80px'></td>"+ 
		"<td class=Pkind><input type='text' class='Pkind' style='width:50px'></td>"+ 
		"<td class=Pcondition><input type='text' class='Pcondition' style='width:80px'></td>"+ 
		"<td class=Pmember><input type='text' class='Pmember' style='width:80px'></td>" +
		"<td class=Pmoney><input type='text' class='Pmoney' style='width:50px'></td>" +		
		"<td class=Pstatime><input type='date' class='Pstatime' style='width:50px'></td>"+
		"<td class=Pendtime><input type='date' class='Pendtime' style='width:50px'></td>"+ 
		"<td class=Premarks><input type='text' class='Premarks' style='width:50px'></td>" +
		"<td class=Paccessory><input type='file' class='Paccessory' style='width:50px'></td>"+
		"<td><a class='delete'>删除</a><a class='saveNewMsg' >保存</a></td>"
		"</tr>";
		$("table").append(tr);//向table中追加tr
	});

//获取新建行当前每个表格单元的信息
$(document).on("click",".saveNewMsg",function(){
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
            "Paccessory" : Paccessory,
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



	




	