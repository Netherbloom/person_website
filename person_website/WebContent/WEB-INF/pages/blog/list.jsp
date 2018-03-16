<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD html 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <title>Blog</title>
    <link rel="shortcut icon" href="favicon.ico">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<jsp:include page="../../css&js.jsp" />
<link type="text/css" rel='stylesheet' href="${path }/statics/css/bootstrap.min.css">
<link type="text/css" data-themecolor="default" rel='stylesheet' href="${path }/statics/css/main-default.css">
<link rel="stylesheet" href="${path}/statics/ztree/css/metroStyle/metroStyle.css" type="text/css">
<script type="text/javascript" src="${path}/statics/ztree/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="${path}/statics/ztree/js/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="${path}/statics/ztree/js/jquery.ztree.exedit.js"></script>
<script type="text/javascript">
  var setting = {
      view: {
          addHoverDom: addHoverDom,
          removeHoverDom: removeHoverDom,
          selectedMulti: false
      },
      check: {
          enable: true
      },
      data: {
          simpleData: {
              enable: true
          }
      },
      edit: {
    	  showRemoveBtn: setRemoveBtn,
          enable: true,
          removeTitle: '删除',
          renameTitle: '编辑'
      },
      callback: {
          beforeRemove: beforeRemove,   //点击删除时触发
        //  beforeEditName: beforeEditName,   //点击编辑时触发
           beforeRename:beforeRename,//编辑结束时触发，
          // onRemove:onRemove,//删除节点后触发，用 后台操作
          // onRename:onRename,//编辑后触发，用于操作后台
          // beforeDrag:beforeDrag,//用户禁止拖动节点
           onClick:clickNode//点击节点触发的事件
          // onNodeCreated: zTreeOnNodeCreated
       //   onAsyncSuccess: zTreeOnAsyncSuccess,   //默认展开所有节点
      }
  };

  var zNodes =[];
  function addHoverDom(treeId, treeNode) {
      var sObj = $("#" + treeNode.tId + "_span");
      if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
      var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
          + "' title='添加' ></span>";
      sObj.after(addStr);
      var btn = $("#addBtn_"+treeNode.tId);
      if (btn) btn.bind("click", function(){
    	  toedit(treeNode.id,2);
          return false;
      });
  };
  function removeHoverDom(treeId, treeNode) {
	  $("#addBtn_"+treeNode.tId).unbind().remove();
  };
  
  function beforeRemove(treeId, treeNode){
	  del(treeNode.id);
  }
  
  function beforeRename(treeId, treeNode){
	  console.log(treeNode);
  }

  function setRemoveBtn(treeId, treeNode) {  
      var showButton = false;  
      if (treeNode.level >= 0) {  
          showButton = true;  
      }else{  
          showButton = false;  
      }  
      return showButton;
  }
  
  function clickNode(event, treeId, treeNode){
	  getblogList(treeNode.id);
  }
  $(document).ready(function(){
	   initDate();
	   gethotblog();
});

  
  function initDate(){
		$.ajax({
			   type: "POST",
			   url: "${path}/blog/getMyBlog",
			   cache: false,
			   async: false,//不锁住浏览器
			   data: {},
			   success: function(data){
				   zNodes=eval(data); 
				   console.log(zNodes);
		           $.fn.zTree.init($("#treeDemo"), setting, zNodes);  
			   },
			   error:function(XMLHttpRequest, textStatus){
			   }
		});
  }
  
	//2、添加1、修改
	function toedit(id,type){
		  layer.open({
			  type: 2,
			  title:"新增",
			  area: ['640px', '600px'],
			  shadeClose: true, //点击遮罩关闭
			  content:['${path }/blog/add?id='+id+'&type='+type,'no']
			  });
	}
	

	function del(id){
		layer.confirm('您确定删除？', {
			  btn: ['确定','取消'] //按钮
			}, function(){
				$.post("${path}/blog/ajax_delete?id=" +id,function(data){
					if(data.result){
			    		layer.msg('操作成功', {
			    		    time: 1500, //2s后自动关闭
			    		    icon:1,
			    		  });
			    	}else{
			    		layer.msg('操作失败', {
			    		    time: 2000, //2s后自动关闭
			    		    icon:2,
			    		  });
			    	}
				});
			 
			});
}
	
	  function getblogList(id){
			$.ajax({
				   type: "POST",
				   url: "${path}/blog/getBlogDetails",
				   cache: false,
				   async: false,//不锁住浏览器
				   data: {"id":id},
				   success: function(data){
					   var html="";
					   html+='<div class="b-blog-listing__block">'
	                        +'<div class="b-blog-listing__block-top">'
	                        +'  <div class=" view view-sixth">';
	                        if(data.ico==null ||data.ico==""){
	                        	html+='<img  src="${path }/statics/images/blog_listing.jpg" />';
	                        }else{
	                        	html +='<img  src="${path }/statics/images/blog_listing.jpg" />';//图片路径
	                        }
	                    html+='</div>'
	                        +'</div>'
	                        +'<div class="b-infoblock-with-icon b-blog-listing__infoblock">'
	                        +'<a href="javascript:void(0);" class="b-infoblock-with-icon__icon f-infoblock-with-icon__icon fade-in-animate hidden-xs">'
	                        +'<i class="fa fa-pencil"></i>'
	                        +'</a>'
	                        +'<div class="b-infoblock-with-icon__info f-infoblock-with-icon__info">'
	                        +'<a href="javascript:void(0);" class="f-infoblock-with-icon__info_title b-infoblock-with-icon__info_title f-primary-l b-title-b-hr f-title-b-hr">'
	                        +data.title
	                        +'</a>'
	                        +'<div class="f-infoblock-with-icon__info_text b-infoblock-with-icon__info_text f-primary-b b-blog-listing__pretitle">'
	                        +'<a href="javascript:void(0);" class="f-more">'+data.createTime+'</a>'
	                        +'</div>'
	                        +'<div class="f-infoblock-with-icon__info_text b-infoblock-with-icon__info_text c-primary b-blog-listing__text">'
	                        +data.contentstr
	                        +'</div>'
	                        +'<div class="f-infoblock-with-icon__info_text b-infoblock-with-icon__info_text">'
	                        +'<a href="#" class="f-more f-primary-b">Read more</a>'
                            +'</div>'
                            +'</div>'
                            +'</div>'
                            +'</div>';
					$("#blogDetails").html(html);
				   },
				   error:function(XMLHttpRequest, textStatus){
				   }
			});
	  }
	  
	  
	//热门博客	
	  function gethotblog(){
			$.ajax({
				   type: "POST",
				   url: "${path}/blog/gethotblog",
				   cache: false,
				   async: false,//不锁住浏览器
				   data: {},
				   success: function(data){
					   var hothtml="";
					   if(data !=null){
						   $.each(data,function(i,result){
							   hothtml+='<div class="b-blog-short-post--popular col-md-12  col-xs-12 f-primary-b">'
							          +'<div class="b-blog-short-post__item_img">'
							          +'<a href="javascript:void(0);"><img data-retina src="${path }/statics/images/gallery_1.jpg" alt=""/></a>'
							          +'</div>'
							          +'<div class="b-remaining">'
							          +'<div class="b-blog-short-post__item_text f-blog-short-post__item_text">'
							          +'<a href="javascript:void(0);">'+result.title+'</a>'
							          +'</div>'
							          +'<div class="b-blog-short-post__item_date f-blog-short-post__item_date f-primary-it">'
							          +'10, January, 2014'
							          +'</div>'
							          +'</div>'
							          +'</div>';
						   });
					   }
					  
					   $("#hotblog").html(hothtml);
				   },
				   error:function(XMLHttpRequest, textStatus){
				   }
			});
	  }
 </script>
</head>
<body>
<div class="mask-l" style="background-color: #fff; width: 100%; height: 100%; position: fixed; top: 0; left:0; z-index: 9999999;"></div> <!--removed by integration-->
<header>
  <div class="container b-header__box b-relative">
	
    <div class="b-header-r b-right b-header-r--icon">
     
      <div class="b-top-nav-show-slide f-top-nav-show-slide b-right j-top-nav-show-slide"><i class="fa fa-align-justify"></i></div>
      <nav class="b-top-nav f-top-nav b-right j-top-nav">
          <ul class="b-top-nav__1level_wrap">
    <li class="b-top-nav__1level f-top-nav__1level is-active-top-nav__1level f-primary-b"><a href="#"><i class="fa fa-home b-menu-1level-ico"></i>Home <span class="b-ico-dropdown"><i class="fa fa-arrow-circle-down"></i></span></a>
        
    </li>
    
    <li class="b-top-nav__1level f-top-nav__1level f-primary-b">
        <a href="#"><i class="fa fa-picture-o b-menu-1level-ico"></i>Portfolio <span class="b-ico-dropdown"><i class="fa fa-arrow-circle-down"></i></span></a>
        
    </li>
    <li class="b-top-nav__1level f-top-nav__1level f-primary-b">
        <a href="#"><i class="fa fa-code b-menu-1level-ico"></i>Blog <span class="b-ico-dropdown"><i class="fa fa-arrow-circle-down"></i></span></a>
        
    </li>
    <li class="b-top-nav__1level f-top-nav__1level f-primary-b b-top-nav-big">
        <a href="#"><i class="fa fa-cloud-download b-menu-1level-ico"></i>Pages<span class="b-ico-dropdown"><i class="fa fa-arrow-circle-down"></i></span></a>
     
    </li>
   
    <li class="b-top-nav__1level f-top-nav__1level f-primary-b">
        <a href="#"><i class="fa fa-folder-open b-menu-1level-ico"></i>Contact us<span class="b-ico-dropdown"><i class="fa fa-arrow-circle-down"></i></span></a>
    </li>
    
</ul>

      </nav>
    </div>
  </div>
</header>

<div class="b-inner-page-header f-inner-page-header b-bg-header-inner-page">
  <div class="b-inner-page-header__content">
    <div class="container">
      <h1 class="f-primary-l c-default">Blog Listing Left Sidebar</h1>
    </div>
  </div>
</div>
<div class="l-main-container">

    <div class="b-breadcrumbs f-breadcrumbs">
        <div class="container">
            <ul>
                <li><a href="#"><i class="fa fa-home"></i>Home</a></li>
                <li><i class="fa fa-angle-right"></i><a href="#">Blog</a></li>
                <li><i class="fa fa-angle-right"></i><span>Listing Left Sidebar</span></li>
            </ul>
        </div>
    </div>

    <div class="l-inner-page-container">
        <div class="container">
            <div class="row">
                <div class="col-md-9 col-md-push-3">
                <!-- 主体 -->
                <div id="blogDetails">
                </div>
                
<%--                     <div class="b-blog-listing__block">
                        <div class="b-blog-listing__block-top">
                            <div class=" view view-sixth">
             				 <img  src="${path }/statics/images/blog_listing.jpg" />
							</div>
                        </div>
                        <div class="b-infoblock-with-icon b-blog-listing__infoblock">
                            <a href="#" class="b-infoblock-with-icon__icon f-infoblock-with-icon__icon fade-in-animate hidden-xs">
                                <i class="fa fa-pencil"></i>
                            </a>
                            <div class="b-infoblock-with-icon__info f-infoblock-with-icon__info">
                                <a href="#" class="f-infoblock-with-icon__info_title b-infoblock-with-icon__info_title f-primary-l b-title-b-hr f-title-b-hr">
                                    Mauris ac risus neque, ut pulvinar risus
                                </a>
                                <div class="f-infoblock-with-icon__info_text b-infoblock-with-icon__info_text f-primary-b b-blog-listing__pretitle">
                                    By <a href="#" class="f-more">Stephen Brock</a> In <a href="#" class="f-more">Lifestyle</a>, <a href="#" class="f-more">Photography</a> Posted May 24th, 2013
                                    <a href="#" class="f-more b-blog-listing__additional-text f-primary"><i class="fa fa-comment"></i>12 Comments</a>
                                </div>
                                <div class="f-infoblock-with-icon__info_text b-infoblock-with-icon__info_text c-primary b-blog-listing__text">
                                    Pendisse blandit ligula turpis, ac convallis risus fermentum non. Duis vestibulum quis quam vel accumsan. Nunc a vulputate lectus. Vestibulum eleifend nisl sed massa sagittis vestibulum. Vestibulum pretium blandit tellus, sodales volutpat sapien varius vel. Phasellus tristique cursus erat, a placerat tellus laoreet eget. Blandit ligula turpis, ac convallis risus fermentum non. Duis vestibulum quis.
                                </div>
                                <div class="f-infoblock-with-icon__info_text b-infoblock-with-icon__info_text">
                                    <a href="#" class="f-more f-primary-b">Read more</a>
                                </div>
                            </div>
                        </div>
                    </div> --%>

                </div>
                <div class="visible-xs-block visible-sm-block b-hr"></div>
                <!-- 左侧部分 -->
                <div class="col-md-3 col-md-pull-9">
                    <div class="row">
    <div class="col-md-12">
        <div class="b-form-row b-input-search">
            <input class="form-control" type="text" placeholder="Enter your keywords"/>
            <span class="b-btn b-btn-search f-btn-search fa fa-search fa-2x"></span>
        </div>
    </div>
</div>
<!-- 分类 -->
<div class="row b-col-default-indent">
    <div class="col-md-12">
        <div class="b-categories-filter">
            <h4 class="f-primary-b b-h4-special f-h4-special--gray f-h4-special">blog categories</h4>
		   <ul id="treeDemo" class="ztree"></ul>
        </div>
    </div>
    <!-- 热门博客 -->
    <div class="col-md-12">
        <h4 class="f-primary-b b-h4-special  f-h4-special--gray f-h4-special">popular postes</h4>

        <div class="b-blog-short-post b-blog-short-post--img-hover-bordered b-blog-short-post--w-img f-blog-short-post--w-img row" >
            <div class="b-blog-short-post b-blog-short-post--img-hover-bordered b-blog-short-post--w-img f-blog-short-post--w-img row" id="hotblog">
 
			</div> 
        </div>
    </div>
</div>
                </div>
            </div>
        </div>
    </div>



</div>

<!-- bootstrap -->
<script src="${path }/statics/js/bootstrap.min.js"></script>
<script src="${path }/statics/js/color-themes.js"></script>
<script src="${path }/statics/js/cookie.js"></script>

</body>
</html>