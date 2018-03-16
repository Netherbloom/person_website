<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<jsp:include page="../../css&js.jsp" />
	<link rel="stylesheet" href="${path}/statics/ztree/css/demo.css" type="text/css">
	<link rel="stylesheet" href="${path}/statics/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${path}/statics/ztree/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="${path}/statics/ztree/js/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="${path}/statics/ztree/js/jquery.ztree.exedit.js"></script>
<link rel="stylesheet" href="${path}/statics/ztree/css/metroStyle/metroStyle.css" type="text/css">
<link rel="stylesheet" href="${path}/statics/admin/css/pintuer.css">
<script src="${path}/statics/admin/js/pintuer.js"></script>

</head>
<SCRIPT type="text/javascript">
		
		var setting = {
				   view: {
				          addHoverDom: addHoverDom,
				          selectedMulti: false
				      },
				      data: {
				          simpleData: {
				              enable: true
				          }
				      },
				      callback: {
				      
				          // beforeDrag:beforeDrag,//用户禁止拖动节点
				           onClick:clickNode//点击节点触发的事件
				          // onNodeCreated: zTreeOnNodeCreated
				       //   onAsyncSuccess: zTreeOnAsyncSuccess,   //默认展开所有节点
				      }
		};
		  var zNodes =[];

		$(document).ready(function(){
			initDate();
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		});
		
		
		 function addHoverDom(treeId, treeNode) {
			 if(treeNode.level<3){
				    var sObj = $("#" + treeNode.tId + "_span");
				      if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
				      var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
				          + "' title='添加' ></span>";
				      sObj.after(addStr);
				      var btn = $("#addBtn_"+treeNode.tId);
				      if (btn) btn.bind("click", function(){
				    	  modif(treeNode.id,0);
				          return false;
				      });
			 }
		  
		  };
		  
		  
		  function clickNode(event, treeId, treeNode){
			  modif(treeNode.id,1);
		  }
		
		  function initDate(){
				$.ajax({
					   type: "POST",
					   url: "${path}/admin/getmenulist",
					   cache: false,
					   async: false,//不锁住浏览器
					   data: {},
					   success: function(data){
						   zNodes=eval(data); 
				           $.fn.zTree.init($("#treeDemo"), setting, zNodes);  
					   },
					   error:function(XMLHttpRequest, textStatus){
					   }
				});
		  }
		  
		  function modif(id,type){
				$.ajax({
					   type: "POST",
					   url: "${path}/admin/menuedit",
					   cache: false,
					   async: false,//不锁住浏览器
					   data: {"id":id,"type":type},
					   success: function(data){
						   console.log(data);
						    var html="";
						    if(type==1){//修改
						    	html+='<input type="hidden" value="'+data.id+'" name="id" id="id"/>';
						    }
						    html+='<input type="hidden" value="'+data.level+'" name="level" />';
					    	html+='<input type="hidden" value="'+data.parentId+'" name="parentId" />';
						    html+='<div class="form-group"><div class="label"> <label>名称:</label> </div>'
						    	+'<div class="field">'
						    	+'<input type="text" class="input w50" value="'+data.name+'" name="name" data-validate="required:请输入名称" />'
						    	+'<div class="tips"></div> </div> </div>';
						    html+='<div class="form-group"><div class="label"> <label>code:</label> </div>'
						    	+'<div class="field">'
						    	+'<input type="text" class="input w50" value="'+data.code+'" name="code" data-validate="required:请输入Code" />'
						    	+'<div class="tips"></div> </div> </div>'; 
				    	  html+='<div class="form-group"><div class="label"> <label>URL：</label> </div>'
						    	+'<div class="field">'
						    	+'<input type="text" class="input w50" value="'+data.url+'" name="url"  />'
						    	+' </div> </div>'; 
				    	  html+='<div class="form-group"><div class="label"> <label>图标:</label> </div>'
						    	+'<div class="field">'
						    	+'<input type="file" class="input w50" value="'+data.ico+'" name="ico"  />'
						    	+'</div> </div>'; 
				    	  html+='<div class="form-group"><div class="label"> <label>排序:</label> </div>'
						    	+'<div class="field">'
						    	+'<input type="text" class="input w50" value="'+data.sort+'" name="sort"  />'
						    	+'</div> </div>'; 
				    	  html+='<div class="form-group"><div class="label"> <label></label> </div>'
						    	+'<div class="field">'
						    	+'&nbsp;&nbsp;&nbsp;&nbsp;<button class="button bg-main icon-check-square-o" type="submit" onclick="savemenu();"> 提交</button>';
						  if(type==1){
							  html+='<button  style="height:33px;width:80px; margin-left:30px;" type="submit" onclick="delmenu();"> 删除</button>';  
						  }  
						  html+=' </div> </div>'; 
					          
					     $("#modifyform").html(html);
					     
					   },
					   error:function(XMLHttpRequest, textStatus){
					   }
				});
		  }
		  
			function savemenu(){
				$("#modifyform").submit(function(){
					$.ajax({
						type:"post",
						url:"${path}/admin/ajax_addmenu",
						async: false,//不锁住浏览器
						data:$("#modifyform").serialize(),
					    success:function(data){
					    	if(data.result){
					    		initDate();
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
			
			
			function delmenu(){
			var id=$("#id").val();
			if(id==null){
				return ;
			}
					$.ajax({
						type:"post",
						url:"${path}/admin/ajax_delmenu",
						async: false,//不锁住浏览器
						data:{"id":id},
					    success:function(data){
					    	if(data.result){
					    		initDate();
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
				
			}
</SCRIPT>
<body>
<h1>菜单管理</h1>
<div class="content_wrap">
	<div class="zTreeDemoBackground left">
		<ul id="treeDemo" class="ztree"></ul>
	</div>
	<div class="right">

	<div class="admin-panel margin-top">
	  <div class="body-content">
	    <form method="post" class="form-x" action="" id="modifyform">    

	    </form>
	  </div>
	</div>
	</div>
</div>
</body>
</html>