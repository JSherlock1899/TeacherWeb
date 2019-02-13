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
//新建表格行
$(document).on("click","#addMsg",function(){
	alert('312421412412412412412412')
		var tr = "<tr><td><input type='checkbox' name = 'select'></td>"+
		"<td class=Patname><input type='text' class='Patname' style='width:50px'></td>" +
		"<td class=Pleader><input type='text' class='Pleader' style='width:50px'></td>" +
		"<td class=Patsn><input type='text' class='Patsn' style='width:50px'></td>" +
		"<td class=Patapdate><input type='date' class='Patapdate' style='width:80px'></td>" +
		"<td class=Patemdate><input type='date' class='Patemdate' style='width:80px'></td>"+ 
		"<td class=Patgrad><input type='text' class='Patgrad' style='width:50px'></td>"+ 
		"<td class=Patremarks><input type='text' class='Patremarks' style='width:50px'></td>" +
		"<td class=Paccessory><input type='file' class='Paccessory' style='width:50px'></td>"+
		"<td><a class='delete'>删除</a><a class='saveNewMsg' >保存</a></td>"
		"</tr>";
		$("table").append(tr);//向table中追加tr
	});

//获取新建行当前每个表格单元的信息
$(document).on("click",".saveNewMsg",function(){
	var Patname = $(this).closest("tr").find("input[class='Patname']").val();
	var Pleader = $(this).closest("tr").find("input[class='Pleader']").val();
	var Patsn = $(this).closest("tr").find("input[class='Patsn']").val();
	var Patapdate = $(this).closest("tr").find("input[class='Patapdate']").val();
	var Patemdate = $(this).closest("tr").find("input[class='Patemdate']").val();
	var Patgrad = $(this).closest("tr").find("input[class='Patgrad']").val();
	var Patremarks = $(this).closest("tr").find("input[class='Patremarks']").val();
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

//$(document).on("click","#homePage",function(e,url){
//	var collegevalue = $('#college option:selected').val();// 选中的学院值
//	var sdeptValue = $('#sdept option:selected').val();// 选中的学院值
//	var starttime = $('#starttime').val();
//	var endtime = $('#endtime').val();
//	var selectByNameVal = $("#selectByNameVal").val();
//	var pageSize = $("#pageSize").val();
//	var currentPage = $("#currentPage").text();
//	alert(pageSize)
//	alert(currentPage)
//    $.ajax({
//    	url:"../../servlet/PageServlet?value=4&count=1",	
//        type:"post",
//        datatype:"json",
//        data:{
//            "collegevalue" : collegevalue,
//            "sdeptValue" : sdeptValue,
//            "starttime" : starttime,
//            "endtime" : endtime,
//            "selectByNameVal" : selectByNameVal,
//            "pageSizeSelect" : pageSizeSelect,
//            "currentPage" : currentPage,
//        },
//        error:function(msg){  
//            alert('请求出现错误...');  
//        }
//    });
//});


//$(document).on("click","#submitChecked",function(){
//	obj = document.getElementsByName("submitChecked");
//    check_val = [];
//    for(k in obj){
//        if(obj[k].checked)
//            check_val.push(obj[k].value);
//    }
//    alert(check_val);
//	$.ajax({
//		url:"../../servlet/SelectExport?value=4",
//		type:"post",
//		datatype:"json",
//		data:{
//			"check_val":check_val,
//		},
//		success:function(result){
//			alert("导出成功！");
//		},
//		error:function(result){
//			alert("请求出现错误");
//		}
//		})
//	
//})



//给选中的checkbox赋对应的Patsn值
//var checkedValue =  [];  //用来储存选择的专利的授权号
//$(".checkedGroup").on("click",function(){
//	if (this.checked){
//		var Patsn = $(this).closest("tr").find(".Patsn").text();
//		checkedValue.push($(this).closest("tr").find(".Patsn").text());
//		//url = "/TeacherWeb/SelectExport?checkedValue =" + checkedValue;
//	}
//});
//
//

	




	