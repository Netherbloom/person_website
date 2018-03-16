<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="${path }/statics/js/jquery/jquery-1.9.1.min.js"></script>
<script src="${path }/statics/js/jquery/jquery.form.js"></script>
<script src="${path }/statics/js/jquery/jquery.validate.js"></script>
<script src="${path }/statics/js/jquery/messages_zh.js"></script>
<script type="text/javascript" src="${path }/statics/layer/layer.js"></script>
 <link href="${path}/statics/css/style.css" rel="stylesheet">
<title>Insert title here</title>
<style>
.error{
	color:red;
}
</style>
<script type="text/javascript">

$().ready(function() {
    $("#commentForm").validate();
});

function checkUserName(){
	var flag=true;
	var username=$.trim($("#username").val());
	if(username==null){
		flag=false;
		return;
	}
	$.ajax({
		type:"post",
		url:"${path}/userinfo/checkusername",
		async: true,//不锁住浏览器
		data:{"username":username},
		 success:function(data){
		    	if(data==true){
		    		flag=false;
		    		layer.msg('该用户名已存在', {
		    			icon:2,
		    		    time: 2000 //2s后自动关闭
		    		  });
		    	}
		    }
	});
	return flag;
}
	function addUser(){
		if(!checkUserName()){
			layer.msg('该用户名已存在', {
				icon:2,
    		    time: 2000 //2s后自动关闭
    		  });
			return ;
		}
	var index= parent.layer.getFrameIndex(window.name);
	$("#commentForm").submit(function(){
		$.ajax({
			type:"post",
			url:"${path}/userinfo/ajax_edit",
			async: false,//不锁住浏览器
			data:$("#commentForm").serialize(),
		    success:function(data){
		    	if(data==true){
		    		parent.layer.close(index);
		    	}else{
		    		layer.msg('操作失败', {
		    		    time: 2000, //2s后自动关闭
		    		    icon:2,
		    		  });
		    	}
		     	
		    },
		    error:function(data){
		    }
		});
	});
}
</script>
	
</head>
<body>
<form class="cmxform" id="commentForm" method="post"  >
	<c:if test="${item.id !=null }">
	<input type="hidden" name="id" value="${item.id }">
	</c:if>
     <p>
      <label for="nickname" class="lableleft">昵称：</label>
      <input id="nickname" name="nickname" rangelength="1,6" maxlength="6" type="text" required class="lableright" value="${item.nickname }">
    </p>
    <p>
      <label for="username" class="lableleft">用户名：</label>
      <c:choose>
		  	<c:when test="${item.username !=null && item.username!=''}">
              	<span  class="lableright"> ${item.username }</span> 
          </c:when>
          <c:otherwise>
           	<input id="username" name="username"  maxlength="16" type="text" required class="lableright" onblur="checkUserName();">
           </c:otherwise>
	  </c:choose>
    </p>
    <p>
      <label for="password" class="lableleft">密码：</label>
         <c:choose>
		  	<c:when test="${item.password !=null && item.password!=''}">
              	 <input id="password" type="password" name="password"  class="lableright"> 
          </c:when>
          <c:otherwise>
            <input id="password" type="password" name="password" required class="lableright">
           </c:otherwise>
	  </c:choose>
    </p>
     <p>
      <label for="phone" class="lableleft">Phone：</label>
      <input id="phone" type="phone" name="phone"  class="lableright" maxlength="11" value="${item.phone }">
    </p>
     <p>
      <label for="email" class="lableleft">E-Mail：</label>
      <input id="email" type="email" name="email" required class="lableright" value="${item.email }">
    </p>
    <p>
      <label for="signature" class="lableleft">签名：</label>
      <textarea id="signature" name="signature" class="lableright" style="height: 40px;">${item.signature}</textarea>
    </p>
     <p>
      <label for="status" class="lableleft">状态：</label>
      <input type="checkbox" name="status" value="1"  <c:if test="${item.status==1 }">checked="checked"</c:if> >激活
    </p>
    <p style="text-align: center;">
      <input class="submit" type="submit" onclick="addUser();" value="确定" style="width: 80px;height: 30px;">
    </p>
</form>
</body>
</html>