<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	 <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>新闻管理</title>
<%-- 	<jsp:include page="../../include/admin_css&js.jsp" /> --%>
<link rel="stylesheet" type="text/css" href="${path}/statics/plugins/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${path}/statics/plugins/easyui/themes/icon.css">
<script type="text/javascript" src="${path}/statics/plugins/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${path}/statics/plugins/easyui/jquery.easyui.min.js"></script>
</head>
<body>
	<table id="dg" title="文章管理" class="easyui-datagrid" fitColumns="true" pagination="true"
    url="${path}/admin/news/getNewsPage" toolbar="#tb" rownumbers="true">
    <thead>
        <tr>
            <th field="id" width="20" align="center" hidden="true"></th>    
            <th field="title" width="200" align="center">标题</th>
            <th field="ftype" width="100" align="center">类型</th> 
            <th field="newsdate" width="100" align="center">发布日期</th> 
            <th  data-options="field:'_operate',width:80,align:'center',formatter:formatOper">操作</th> 
        </tr>
    </thead>
    	<div id="tb" style="padding:5px;height:auto">
		
		<div>
			标题: <input  name="title" id="title" style="width:180px">
			<a href="javascript:void(0);" class="easyui-linkbutton" onclick="doSearch()" iconCls="icon-search">Search</a>
		</div>
	</div>
</table>
<script>
//操作列
function formatOper(val,row,index){  
	var str='<a href="javascript:void(0);" onclick="edit(\''+row.id+'\')" style="margin-left: 5px;margin-right: 5px;" class="icon-edit"  title="修改">&nbsp;&nbsp;&nbsp;&nbsp;</a>';
	str+='<a href="javascript:void(0);" onclick="delnews(\''+row.id+'\')" style="margin-left: 5px;margin-right: 5px;" class="icon-clear" title="删除">&nbsp;&nbsp;&nbsp;&nbsp;</a>';
    return str;  
} 

function edit(id){  
	  showMyWindow("弹出查询页面",  
              '${path}/admin/news/edit?id='+id,  
              800, 540);  
}

function delnews(id){  
	$.messager.confirm('提示', '确定删除此条消息?', function(r){
		if (r){
			$.ajax({
				   type: "POST",
				   url: "${path}/admin/news/deleteNews",
				   cache: false,
				   async: false,//不锁住浏览器
				   data: {"id":id},
				   success: function(data){
					   if(data.code=="200"){
						   doSearch();  
					   }else{
						   alert(data.msg);
					   }
					   
				   },
				   error:function(XMLHttpRequest, textStatus){
				   }
			});
		}
	});
	
}

function doSearch(){
	$('#dg').datagrid('load',{
		title: $('#title').val()
		
	});
}
</script>
</body>
</html>