<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/TeacherWeb/UI/CSS/password.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="/TeacherWeb/UI/JS/jquery-3.3.1.min.js"></script>
<title>Insert title here</title>
</head>
<body>
	<header class="head">
            <div onclick="goBack()"><img src="arrow.jpg"/></div>
            <div>修改密码</div>
        </header>
        <form action="#" onsubmit="return validate_form(this);" method="post">
            <div class="mainBody">
                <div class="inputs">
                    <div>
                        <label for="prevpd">原密码</label>
                        <input id="prevpd" name="prevpd" type="password" oninput="OnInput(event)"></input>
                        <span class="input-addon"><img src="/TeacherWeb/UI/images/eye.jpg"/></span>
                    </div>
                    <div>
                        <label for="newpd">新密码</label>
                        <input placeholder="请输入新密码" id="newpd" name="newpd" type="password" oninput="OnInput(event)"></input>
                        <span class="input-addon"><img src="/TeacherWeb/UI/images/eye.jpg"/></span>
                    </div>
                    <div>
                        <label for="newpdagain">确认新密码</label>
                        <input placeholder="请再次输入新密码" id="newpdagain" name="newpdagain" type="password" oninput="OnInput(event)"></input>
                        <span class="input-addon"><img src="/TeacherWeb/UI/images/eye.jpg"/></span>
                    </div>
                    <div>
                        密码标准：需由不少于6位的大写字母、小写字母、数字组成。
                    </div>
                </div>
                <div class="confirm waiting">
                    <button type="" disabled>确定</button>
                </div>
            </div>
        </form>

        <div class="popdiv">***</div>

        <script type="text/javascript">
            (function(){
                // input输入框后面的眼睛图片：点击切换密码是否可见。
                Array.prototype.forEach.call(document.querySelectorAll('.input-addon img'), function(node){
                    node.onclick = function(){
                        if(node.src.indexOf("eye.jpg")>0){
                            // 密码可见
                            node.parentNode.previousSibling.previousSibling.type = "text";
                            node.src = "/TeacherWeb/UI/images/eyedark.jpg";
                        }else{
                            // 密码不可见
                            node.parentNode.previousSibling.previousSibling.type = "password";
                            node.src = "/TeacherWeb/UI/images/eye.jpg";
                        }

                    }
                });
            })();

            function goBack(){
                console.log("back");
                window.history.back();
            }

            function OnInput(e){
                // 检测确认按钮是否可用
                var isDone = Array.prototype.every.call(document.getElementsByTagName('input'), function(one){
                    return one.value.length > 5;
                });
                var content = document.querySelector('.confirm');
                if(isDone){
                    content.className = "confirm";
                    content.childNodes[1].type = "submit";
                    content.childNodes[1].disabled = false;
                }else{
                    content.className = "confirm waiting";
                    content.childNodes[1].type = "";
                    content.childNodes[1].disabled = true;
                }
            }

            function popup(text){
                // 弹出框
                document.getElementsByClassName('popdiv')[0].innerHTML = text;
                document.getElementsByClassName('popdiv')[0].style.display = "block";

                setTimeout(function hidePopup(){
                    document.getElementsByClassName('popdiv')[0].style.display = "none";
                },2000);
            }

            function validate_form(thisform){
            	var prevpd = $('#prevpd').val();
            	var newpd = $('#newpd').val();
                // 校验密码
                var options = {
                    url: "../../servlet/TeacherServlet", 
                    type: "POST",
                    data: { prevpd: "prevpd", newpd: "newpd"},     // 如有用户信息，在此对象中添加 userId 属性
                    dataType: "json",
                    success: function (response, xml) {
                        response = eval("(" + response + ")");
                        if(response.success){
                            popup("修改成功");
                        }else{
                            popup("原密码错误");
                        }
                    },
                    fail: function (status) {
                        popup("错误，修改不成功")
                    }
                };
                with (thisform){
                    if(newpd.value != newpdagain.value){
                        popup("两次输入不一致");
                        return false;
                    }else if(newpd.value.length < 6 || (/[^a-zA-Z0-9]/g).test(newpd.value)){
                        popup("密码不符合要求");
                        return false;
                    }else{
                        options.data.oldPassword = prevpd.value;
                        options.data.newPassword = newpd.value;
                        ajax(options);
                        return false;
                    }
                }
            }

            function ajax(options) {
                // 原生Ajax
                options = options || {};
                options.type = (options.type || "GET").toUpperCase();
                options.dataType = options.dataType || "json";
                var params = formatParams(options.data);

                //创建(非IE6) - 第一步
                if (window.XMLHttpRequest) {
                    var xhr = new XMLHttpRequest();
                } else { //IE6及其以下版本浏览器
                    var xhr = new ActiveXObject('Microsoft.XMLHTTP');
                }

                if (xhr == null){
                    alert('您的浏览器不支持AJAX！');
                    return;
                }

                //连接和发送 - 第二步
                if (options.type == "GET") {
                    xhr.open("GET", options.url + "?" + params, true);
                    xhr.send(null);
                } else if (options.type == "POST") {
                    xhr.open("POST", options.url, true);
                    //设置表单提交时的内容类型
                    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                    xhr.send(params);
                }

                //接收 - 第三步
                xhr.onreadystatechange = function () {
                    if (xhr.readyState == 4) {
                        var status = xhr.status;
                        if (status >= 200 && status < 300) {
                            options.success && options.success(xhr.responseText, xhr.responseXML);
                        } else {
                            options.fail && options.fail(status);
                        }
                    }
                }
            }

            function formatParams(data) {
                // Ajax 格式化参数
                var arr = [];
                for (var name in data) {
                    arr.push(encodeURIComponent(name) + "=" + encodeURIComponent(data[name]));
                }
                arr.push(("v=" + Math.random()).replace(".",""));
                return arr.join("&");
            }
        </script>
</body>
</html>