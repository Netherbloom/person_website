<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="../../css&js.jsp" />
<script src="${path }/statics/js/jquery/jquery.form.js"></script>
<link href="${path}/statics/css/style.css" rel="stylesheet">
<title>Insert title here</title>
<style>
.error{
	color:red;
}
</style>
<script type="text/javascript">

	function addUser(){
	var index= parent.layer.getFrameIndex(window.name);
	$("#commentForm").submit(function(){
		$.ajax({
			type:"post",
			url:"${path}/blog/ajax_add",
			async: false,//不锁住浏览器
			data:$("#commentForm").serialize(),
		    success:function(data){
		    	if(data.result){
		    		parent.layer.close(index);
		    		parent.initDate();
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
	<c:if test="${blog.id !=null }">
	<input type="hidden" name="id" value="${blog.id }">
	</c:if>
	<input type="hidden" name="parentId" value="${blog.parentId }">
     <p>
      <label for="title" class="lableleft">标题：</label>
      <input id="title" name="title"  type="text"  class="lableright" value="${blog.title }">
    </p>

    <p>
      <label for="contentstr" class="lableleft">内容：</label>
      <textarea id="contentstr" name="contentstr" class="lableright" rows="" cols="" style="width: 480px;height: 400px;resize:none">${blog.contentstr }</textarea>
    </p>
    <p style="text-align: center;">
      <input class="submit" type="submit" onclick="addUser();" value="确定" style="width: 80px;height: 30px;">
    </p>
</form>
</body>
</html>