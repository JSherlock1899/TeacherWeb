/**
 * 对教师信息进行操作
 */

$(document).on("click",".delete",function(e,url){
        var Pasearchnum = $(this).closest("tr").find(".Pasearchnum").text();
        $.ajax({
        	url:"../servlet/TeacherServlet?value=1",
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

$(document).on("click","#Updtae",function(e,url){
    var Tsn = $(this).closest("tr").find(".Tsn").text();
    var Tname = $(this).closest("tr").find(".Tname").text();
    var Tsex = $(this).closest("tr").find(".Tsex").text();
    var Ttel = $(this).closest("tr").find(".Ttel").text();
    var Tmail = $(this).closest("tr").find("Tmail").text();
    var Cname = $(this).closest("tr").find(".Cname").text();
    var Dname = $(this).closest("tr").find(".Dname").text();
    $('#Tsn').value = Tsn;
    $('#Tname').value = Tname;
    $('#Tsex').value = Tsex;
    $('#Ttel').value = Ttel;
    $('#Tmail').value = Tmail;
    $('#Cname').value = Cname;
    $('#Dname').value = Dname;
    $.ajax({
    	url:"../servlet/TeacherServlet?value=1",
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

