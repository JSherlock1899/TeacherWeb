/**
 * 实现下拉框的值改变，查询的结果也改变
 */

//当用户选择查询内容时，iframe跳转到对应页面
//也是为了设定一个参数来区分四个页面
function Projectchange(){
	document.getElementById("select_frame").src='../servlet/PageServlet?option=Project';
    }
	


function Paperchange(){
	document.getElementById("select_frame").src='../servlet/PageServlet?option=Paper';
	
}

function Honorchange(){
	document.getElementById("select_frame").src='../servlet/PageServlet?option=Honor';

}

function Patentchange(){	
	document.getElementById("select_frame").src='../servlet/PageServlet?option=Patent';
}


function ajaxSelect(option) {
	var collegevalue = $('#college option:selected').val();// 选中的学院值
	var sdeptValue = $('#sdept option:selected').val();// 选中的学院值
	var starttime = $('#starttime').val();
	var endtime = $('#endtime').val();
	var selectByNameVal = $("#selectByNameVal").val();
	var pageSizeSelect = $("#pageSizeSelect").val();
	var currentPage = $("#currentPage").val();
	document.getElementById("select_frame").src="../servlet/PageServlet?sdeptValue=" +sdeptValue 
	+ "&collegevalue=" +collegevalue + "&starttime=" + starttime + "&endtime=" + endtime + "&selectByNameVal=" + selectByNameVal
	+ "&pageSizeSelect=" + pageSizeSelect + "&currentPage=" + currentPage + "&option=" + option;
}

//获取iframe中的src
function getSrc(){
	var newcss = document.getElementById('select_frame').src;
    // var srcx = "xxx.ccom/id=2&aid=3";
    var reg = /.*aid\=([^\&]+).*/g;
    var result = newcss.replace(reg,"$1");
}


//查询框的值改变时动态改变查询内容
function CollegeSelectchange(){
	var newcss = document.getElementById('select_frame').src;
    // var srcx = "xxx.ccom/id=2&aid=3";
    var reg = /.*aid\=([^\&]+).*/g;
    var result = newcss.replace(reg,"$1");
    //当已经进行过一次查询后将正确的url分割出来
    var firsturl = result.split('option='); //通过字符串分割得到option
    var option = firsturl[1];
    console.log(firsturl)
    ajaxSelect(option);
    
}

	