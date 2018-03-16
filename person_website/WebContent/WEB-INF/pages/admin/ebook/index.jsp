<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	 <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>书籍管理</title>
 	<jsp:include page="../../include/admin_css&js.jsp" /> 
</head>
<body>
	<table id="dg" title="书籍列表" class="easyui-datagrid" fitColumns="true" pagination="true"
    url="${path}/admin/ebook/getinitebook" toolbar="#tb" rownumbers="true">
    <thead>
        <tr>
            <th field="id" width="20" align="center" hidden="true"></th>    
            <th field="name" width="200" align="center">书名</th>
            <th field="writer" width="200" align="center">作者</th>
            <th field="type" width="100" align="center">类型</th> 
            <th field="updatetime" width="100" align="center">更新日期</th> 
            <th  data-options="field:'_operate',width:80,align:'center',formatter:formatOper">操作</th> 
        </tr>
    </thead>
    	<div id="tb" style="padding:5px;height:auto">
		
		<div>
			关键字: <input  name="keywords" id="keywords" style="width:180px">
			<a href="javascript:void(0);" class="easyui-linkbutton" onclick="doSearch()" iconCls="icon-search">Search</a>
		</div>
	</div>
</table>
<script>
//操作列
function formatOper(val,row,index){  
	var str='<a href="javascript:void(0);" onclick="chapter(\''+row.id+'\')" style="margin-left: 5px;margin-right: 5px;" class="icon-search"  title="详细">&nbsp;&nbsp;&nbsp;&nbsp;</a>';
	str+='<a href="javascript:void(0);" onclick="reload(\''+row.id+'\')" style="margin-left: 5px;margin-right: 5px;" class="icon-reload" title="同步">&nbsp;&nbsp;&nbsp;&nbsp;</a>';
	str+='<a href="javascript:void(0);" onclick="delebook(\''+row.id+'\')" style="margin-left: 5px;margin-right: 5px;" class="icon-clear" title="删除">&nbsp;&nbsp;&nbsp;&nbsp;</a>';
    return str;  
} 

function chapter(id){  
	  showMyWindow("弹出查询页面",  
              '${path}/admin/news/edit?id='+id,  
              800, 540);  
}

function delebook(id){  
	$.messager.confirm('提示', '确定删除此书籍?', function(r){
		if (r){
			$.ajax({
				   type: "POST",
				   url: "${path}/admin/ebook/deleteEbook",
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

function reload(id){  
	$.messager.confirm('提示', '确定同步此书籍?', function(r){
		if (r){
			$.ajax({
				   type: "POST",
				   url: "${path}/admin/ebook/reloadEbook",
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
		keywords:  $('#keywords').val().trim()
	});
}
</script>
</body>
</html>