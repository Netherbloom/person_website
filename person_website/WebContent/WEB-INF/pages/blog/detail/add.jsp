<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${path}/statics/js/jquery/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${path }/statics/layer/layer.js"></script>
<script type="text/javascript" src="${path}/statics/js/map.js"></script>
<script type="text/javascript" src="${path }/statics/plugins/ueditor/ueditor.config.js"></script>
<script type="text/javascript"  src="${path }/statics/plugins/ueditor/ueditor.all.js"> </script>
<script type="text/javascript" src="${path }/statics/plugins/ueditor/lang/zh-cn/zh-cn.js"></script>
<script src="${path }/statics/js/jquery/jquery.form.js"></script>
<script type="text/javascript" src="${path }/statics/layer/layer.js"></script>

<title>Insert title here</title>
<script type="text/javascript">
var _editor ;
_editor = UE.getEditor('editor', {
	'serverUrl' : '${path}/ueditor/ueditorUpload',
	'toolbars' : [[ 'attachment']]
});
_editor.ready(function() {
	//隐藏编辑器，因为不会用到这个编辑器实例，所以要隐藏
	_editor.addListener('beforeInsertHtml',function(t, arg) {
		var map = new Map();
		for (var i = 0; i < $(arg).length; i++) {
			if ($(arg)[i].childNodes[1] != undefined) {
				map.put($($(arg)[i]).text(),$(arg)[i].childNodes[1].getAttribute('href'));
			}
		}
		$.ajax({
			url : "${path}/blog/detail/addimg",
			type : "post",
			async : false,
			dataType : "json",
			data : {
				elements:JSON.stringify(map).toString()
			},
			error : function() {
			},
			success : function(result) {
				alert(11);
			}
		});

		});
});   
</script>
</head>
<body>
<div>
<input type="hidden" name="blogId" id="blogId" value="${blogId }"/>
    <h1></h1>
    <script id="editor" type="text" style="width:1024px;height:500px;"></script>
</div>

</body>
</html>