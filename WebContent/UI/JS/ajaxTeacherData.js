/**
 * 对教师信息进行操作
 */

$(document).on("click",".delete",function(e,url){
        var Tsn = $(this).closest("tr").find(".Tsn").text();
        $.ajax({
        	url:"../servlet/TeacherServlet?value=1",
            type:"post",
            datatype:"json",
            data:{
                "Tsn" : Tsn
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

$(document).on("click",".newTeacher",function(e,url){
	$("#Tsn").attr("value",$(this).closest("tr").find(".Tsn").text());
	$("#Tname").attr("value",$(this).closest("tr").find(".Tname").text());
	$("#Tsex").attr("value",$(this).closest("tr").find(".Tsex").text());
	$("#Ttel").attr("value",$(this).closest("tr").find(".Ttel").text());
	$("#TID").attr("value",$(this).closest("tr").find(".TID").text());
	$("#Tmail").attr("value",$(this).closest("tr").find(".Tmail").text());
	$("#Cname").attr("value",$(this).closest("tr").find(".Cname").text());
	$("#Sdept").attr("value",$(this).closest("tr").find(".Sdept").text());

});

//$(document).on("click",".newTeacher",function(e,url){
//	$("#Tsn").attr("value",$(this).closest("tr").find(".Tsn").text());
//	$("#Tname").attr("value",$(this).closest("tr").find(".Tname").text());
//	$("#Tsex").attr("value",$(this).closest("tr").find(".Tsex").text());
//	$("#Ttel").attr("value",$(this).closest("tr").find(".Ttel").text());
//	$("#Tmail").attr("value",$(this).closest("tr").find(".Tmail").text());
//	$("#Cname").attr("value",$(this).closest("tr").find(".Cname").text());
//	$("#Sdept").attr("value",$(this).closest("tr").find(".Sdept").text());
//	var Tsn = $('#Tsn').val();
//	var Tname = $('#Tname').val();
//	var Tsex = $('#Tsex').val();
//	var Ttel = $('#Ttel').val();
//	var Tmail = $('#Tmail').val();
//	var TID = $('#TID').val();
//	var Cname = $('#Cname option:selected').val();
//	var Sdept = $('#sdept option:selected').val();
//	alert(TID)
//	alert(Tsn)
//	alert(Cname)
//	$.ajax({
//		url:"../servlet/TeacherServlet?value=2",
//		type:"post",
//		datatype:"json",
//		data:{
//			"Tsn" : Tsn,
//			"Tname" : Tname,
//			"Tsex" :Tsex,
//			"Ttel" : Ttel,
//			"Tmail" : Tmail,
//			"TID" : TID,
//			"Cname" : Cname,
//			"Sdept" :Sdept,
//		},
//		success : function(msg){
//			
//			alert("删除成功");
//			$(e.target).closest("tr").fadeOut();
//			window.location.reload();
//		},
//		error:function(msg){  
//			alert('请求出现错误...');  
//		}
//	});
//});


$(document).on("click",".save",function(e,url){
	var Tsn = $('#Tsn').val();
	var Tname = $('#Tname').val();
	var Tsex = $('#Tsex').val();
	var Ttel = $('#Ttel').val();
	var Tmail = $('#Tmail').val();
	var TID = $('#TID').val();
	var Cname = $('#Cname option:selected').val();
	var Sdept = $('#sdept option:selected').val();
	$.ajax({
		url:"../servlet/TeacherServlet?value=2",
		type:"post",
		datatype:"json",
		data:{
			"Tsn" : Tsn,
            "Tname" : Tname,
            "Tsex" :Tsex,
            "Ttel" : Ttel,
            "Tmail" : Tmail,
            "TID" : TID,
            "Cname" : Cname,
            "Sdept" :Sdept,
		},
		success : function(msg){
			
			alert("修改成功");
			window.location.reload();
		},
		error:function(msg){  
			alert('请求出现错误...');  
		}
	});
});

