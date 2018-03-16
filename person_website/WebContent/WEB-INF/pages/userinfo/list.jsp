<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="${path }/statics/js/jquery/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${path }/statics/layer/layer.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function del(id){
		layer.confirm('您确定删除此用户？', {
			  btn: ['确定','取消'] //按钮
			}, function(){
				$.post("${path}/userinfo/ajax_edit?status=0&id=" +id,function(data){
					if(true == data){
						layer.msg('删除成功', {icon: 1});
						window.location.reload();
					}else{
						layer.msg('删除失败', {time: 1500});
					}
				});
			 
			});
		
	}
	//添加用户
	function toedit(id){
		  layer.open({
			  type: 2,
			  title:"新增",
			  area: ['400px', '460px'],
			  shadeClose: true, //点击遮罩关闭
			  content:['${path }/userinfo/edit?id='+id,'no']
			  });
	}
</script>
</head>
<body>
	<h6><a href="javascript:void(0);" onclick="toedit();">添加用户</a></h6>
	<table border="1">
		<tbody>
			<tr>
				<th>姓名</th>
				<th>密码</th>
				<th>操作</th>
			</tr>
			<c:if test="${!empty userList }">
				<c:forEach items="${userList }" var="user">
					<tr>
						<td>${user.username }</td>
						<td>${user.password }</td>
						<td>
							<a href="javascript:void(0);" onclick="toedit('${user.id}')">编辑</a>
							<a href="${path }/user/getAllBlog?userId=${user.id }">我的blog</a>
							<a href="javascript:del('${user.id }')">删除</a>
						</td>
					</tr>				
				</c:forEach>
			</c:if>
		</tbody>
	</table>
</body>
</html>