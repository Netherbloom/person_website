<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>人员管理</title>
	<jsp:include page="../../include/admin_css&js.jsp" />
</head>
<body>
	<table id="dg" title="人员管理" style="width:100%;"  data-options="
				rownumbers:true,
				singleSelect:true,
				autoRowHeight:false,
				pagination:true,
				pageSize:10">
		<thead>
			<tr>
				<th field="inv" width="80">Inv No</th>
				<th field="date" width="100">Date</th>
				<th field="name" width="80">Name</th>
				<th field="amount" width="80" align="right">Amount</th>
				<th field="price" width="80" align="right">Price</th>
				<th field="cost" width="100" align="right">Cost</th>
				<th field="note" width="110">Note</th>
			</tr>
		</thead>
	</table>
	<script>
	
		function getData(){
			var rows = [];
			for(var i=1; i<=800; i++){
				var amount = Math.floor(Math.random()*1000);
				var price = Math.floor(Math.random()*1000);
				rows.push({
					inv: 'Inv No '+i,
					date: $.fn.datebox.defaults.formatter(new Date()),
					name: 'Name '+i,
					amount: amount,
					price: price,
					cost: amount*price,
					note: 'Note '+i
				});
			}
			return rows;
		}
		
		function pagerFilter(data){
			if (typeof data.length == 'number' && typeof data.splice == 'function'){	// is array
				data = {
					total: data.length,
					rows: data
				}
			}
			var dg = $(this);
			var opts = dg.datagrid('options');
			var pager = dg.datagrid('getPager');
			pager.pagination({
				onSelectPage:function(pageNum, pageSize){
					opts.pageNumber = pageNum;
					opts.pageSize = pageSize;
					pager.pagination('refresh',{
						pageNumber:pageNum,
						pageSize:pageSize
					});
					dg.datagrid('loadData',data);
				}
			});
			if (!data.originalRows){
				data.originalRows = (data.rows);
			}
			var start = (opts.pageNumber-1)*parseInt(opts.pageSize);
			var end = start + parseInt(opts.pageSize);
			data.rows = (data.originalRows.slice(start, end));
			return data;
		}
		
		$(function(){
			$('#dg').datagrid({loadFilter:pagerFilter}).datagrid('loadData', getData());
		});
	</script>
</body>
</html>