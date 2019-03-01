/**
 * 通用模块
 */

//实现全选与反选
		$(function(){
		$("#checkAll").click(function() {
			if (this.checked){
		        $("input[name=select]:checkbox").each(function(){
		              $(this).attr("checked", true);
		        });
		    } else {
		        $("input[name=select]:checkbox").each(function() {
		              $(this).attr("checked", false);
		        });
		    }
		});
		});
		
//点击显示精确搜索框
		function searchShow(){
			            var divDisp = document.getElementById("search").style.display;
			            if (divDisp == "block") {
			                document.getElementById("search").style.display = "none";
			            } else {
			                document.getElementById("search").style.display = "block";
			            }
			        }
		
		
		//动态更改表格内的信息
	    $(function(){
	        $(".updata").click(function() {
	            str = $(this).text()=="编辑"?"保存":"编辑";
	            $(this).text(str);   // 按钮被点击后，在“编辑”和“确定”之间切换
	            $(this).parent().siblings("td").each(function() {  // 获取单元格
	                var obj_text = $(this).find("input:text");    // 判断单元格下是否有文本框
	                var obj_checkbox = $(this).find("input:checkbox");	// 判断单元格下是否有选择框，即是否是每行的第一个单元格
	                var obj_a = $(this).find("a");				//// 判断单元格下是否有超链接，即是否是附件单元格
	                if(!obj_text.length && !obj_checkbox.length && !obj_a.length)   // 如果没有文本框，则添加文本框使之可以编辑
	                    {
	                		$(this).html("<input type='text' style='width:70px' value='"+$(this).text()+"'>");
	                    }
	                else   // 如果已经存在文本框，则将其显示为文本框修改的值
	                    $(this).html(obj_text.val());
	            });
	        });
	    });	
	    
	    
	 
	    //处理分页页码问题
	    $(function(){
	    	var totalPage = $('#totalPage').val();
	    	var currentPage = $('#currentPage').val();
	    	if(totalPage < 3){
	    		$('#page3').hide();
	    	}
	    	if(totalPage<2){
	    		$('#page2').hide();
	    	}
	    	if(currentPage == 1){					//首页和尾页时分别隐藏对应按钮
				$('#pre').css("display","none");
			}
			
			if(currentPage == totalPage){
				$('#next').css("display","none");
			}
	    })
	    

	    
		