<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-cn">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta name="renderer" content="webkit">
    <title>登录</title>  
    <link rel="stylesheet" href="${path }/statics/admin/css/pintuer.css">
    <link rel="stylesheet" href="${path }/statics/admin/css/admin.css">
    <script  type="text/javascript" src="${path }/statics/admin/js/jquery.js"></script>
    <script  type="text/javascript" src="${path }/statics/admin/js/pintuer.js"></script>  
	<script type="text/javascript" src="${path }/statics/js/jquery/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${path }/statics/js/jquery/jquery.form.js"></script>
	<script type="text/javascript" src="${path }/statics/layer/layer.js"></script>
</head>
<body>
<div class="bg"></div>
<div class="container">
    <div class="line bouncein">
        <div class="xs6 xm4 xs3-move xm4-move">
            <div style="height:150px;"></div>
            <div class="media media-y margin-big-bottom">           
            </div>         
            <form  action="#" method="post" id="theForm">
            <div class="panel loginbox">
                <div class="text-center margin-big padding-big-top"><h1>注册</h1></div>
                <div class="panel-body" style="padding:30px; padding-bottom:10px; padding-top:10px;">
                    <div class="form-group">
                        <div class="field field-icon-right">
                            <input type="text" class="input input-big" name="username" placeholder="登录账号" data-validate="required:请填写账号" />
                            <span class="icon icon-user margin-small"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="field field-icon-right">
                            <input type="password" class="input input-big" name="password" placeholder="登录密码" data-validate="required:请填写密码" />
                            <span class="icon icon-key margin-small"></span>
                        </div>
                    </div>
              <!--     <div class="form-group">
                        <div class="field">
                            <input type="text" class="input input-big" name="code" placeholder="填写右侧的验证码" data-validate="required:请填写右侧的验证码" />
                           <img src="images/passcode.jpg" alt="" width="100" height="32" class="passcode" style="height:43px;cursor:pointer;" onclick="this.src=this.src+'?'">                                                     
                        </div>
                    </div>  -->
                  
                    
                </div>
                <div style="padding:30px;"><input type="submit" class="button button-block bg-main text-big input-big" value="登录"></div>
            </div>
            </form>          
        </div>
    </div>
</div>
<script>
$(function(){
	
	$("#theForm").submit(function(){
		//登录验证
		$.ajax({
			   type: "POST",
			   url: "${path}/center/ajax_login",
			   cache: false,
			   async: true,//不锁住浏览器
			   data: $("#theForm").serialize(),
			   success: function(data){
			   		if(data.type==1){
			   			window.location.href="${path}/admin/index";
			   		}else{
			   			window.location.href="${path}/blog/list";
			   		}
			   },
			   error:function(XMLHttpRequest, textStatus){
			   }
		});
		return false;
	});
});

</script>
</body>

</html>