<%@ taglib uri="/jstl1.1/core.tld" prefix="c"%>
<%@page contentType="text/html;charset=UTF-8"%>

<div class="header bg-main">
  <div class="logo margin-big-left fadein-top">
    <h1> <img src="${path }/statics/admin/images/y.jpg" class="radius-circle rotate-hover" height="50" alt="" />后台管理中心</h1>
  </div>
  <div class="head-l" id="topmenu">
    </div>
</div>
<!-- 左侧菜单 -->
<div class="leftnav" id="leftnav">
</div>

<script type="text/javascript">
var firstleft="";
$(function(){

  gettopmenu("",1);
  getleftmenu(firstleft,2);
});

//顶部菜单
function gettopmenu(id,level){
	$.ajax({
		   type: "POST",
		   url: "${path}/admin/getmenu",
		   cache: false,
		   async: false,//不锁住浏览器
		   data: {
			   "parentid":id,
				"level":level   
		   },
		   success: function(data){
			   var html="";
			   if(data!=null){
				   $.each(data.data,function(i,result){
					   if(i==0){
						   firstleft=result.id;
					   }
					   html+=' &nbsp;&nbsp;<a class="button button-little bg-green" href="javascript:void(0);" onclick="getleftmenu(\''+result.id+'\',2)"><span class="icon-home"></span> '+result.name+'</a>';
				   });
			   }
			   html+='&nbsp;&nbsp;<a class="button button-little bg-red" href="javascript:void(0);" onclick="singout();" ><span class="icon-power-off"></span> 退出登录</a>';
		  	$("#topmenu").html(html);
		   },
		   error:function(XMLHttpRequest, textStatus){
		   }
	});
}
				   
   function getleftmenu(id,level){
		$.ajax({
			   type: "POST",
			   url: "${path}/admin/getmenu",
			   cache: false,
			   async: false,//不锁住浏览器
			   data: {
				   "parentid":id,
					"level":level   
			   },
			   success: function(data){
				   var html='<div class="leftnav-title"><strong><span class="icon-list"></span>菜单列表</strong></div>';
				   if(data.data!=null){
					     $.each(data.data,function(i,res){
						  html+='<h2><span class="icon-pencil-square-o"></span>'+res.name+'</h2>'
							  +'<ul>';
							  if(res.list!=null){
								  $.each(res.list,function(index,result1){
										html+='<li><a href="${path}/'+result1.url+'" target="right"><span class="icon-caret-right"></span>'+result1.name+'</a></li>';
									 });
							  }
							  html+= '</ul>';     
					   });  
				   }
			  	$("#leftnav").html(html); 
			   },
			   error:function(XMLHttpRequest, textStatus){
			   }
		});
		  $(".leftnav h2").click(function(){
			  $(this).next().slideToggle(200);	
			  $(this).toggleClass("on"); 
		  });
		  $(".leftnav ul li a").click(function(){
			    $("#a_leader_txt").text($(this).text());
		  		$(".leftnav ul li a").removeClass("on");
				$(this).addClass("on");
		  });
		  
}
	//登出
	  function singout(){
	  	$.ajax({
	  		   type: "POST",
	  		   url: "${path}/center/logout",
	  		   cache: false,
	  		   async: false,//不锁住浏览器
	  		   data: {},
	  		   success: function(data){
	  			   if(data.result){
	  				 window.location.href="${path}/center/login";
	  			   }
	  		   },
	  		   error:function(XMLHttpRequest, textStatus){
	  		   }
	  	});
	  }
</script>