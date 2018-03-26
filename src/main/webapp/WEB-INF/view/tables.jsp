<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=basePath%>/bootstrap/jquery-3.2.1.js"></script>
<style type="text/css">
	.top {
			margin: 0 20px;
			text-align: center;
	}
	.left {
        margin: 15px;
        width: 40%;
        height: 500px;
        border: 1px solid red;
        display: inline-block;
     }
     .center {
        width: 10%;
        display: inline-block;
        text-align: center;
    }
    .right {
        margin: 15px;
        width: 40%;
        height: 500px;
        border: 1px solid red;
        display: inline-block;
    }
    .content,
    .bottom {
        margin: 0 20px;
        text-align: center;
    }
</style>
<title>Insert title here</title>
</head>
<body>
<div class="content">
	<div class="top">
		<h1>迁移数据库表操作界面</h1>
	</div>
	<div class="left">
		<form method="post" name="dbTablesForm">
				<table border="0" width="98%">
					<tr style="background-color:#87CEEB">
						<td colspan="3" style="text-align: center"><h4>数据库表列表</h4></td>
					</tr>
					<tr>
						<td width="40%">
							<select style="width: 100%" multiple name="fromList" size="12"
									ondblclick="moveOption(document.dbTablesForm.fromList, document.dbTablesForm.toList)">
									<c:forEach items="${list}" var="tableName">
										<option value="${tableName}">${tableName}</option>
									</c:forEach>
							</select>
						</td>
						<td width="20%" align="center">
							<input type="button" value="添加" onclick="moveOption(document.dbTablesForm.fromList, document.dbTablesForm.toList)"><br /><br /> 
							<input type="button" value="删除" onclick="moveOption(document.dbTablesForm.toList, document.dbTablesForm.fromList)">
						</td>
						<td width="40%">
							<select style="width: 100%" multiple name="toList" size="12"
									ondblclick="moveOption(document.dbTablesForm.toList, document.dbTablesForm.fromList)">
							</select>
						</td>
						<td>
							<button onclick="changepos(document.dbTablesForm.toList,-1)" type="button">上移</button> <br />
							<button onclick="changepos(document.dbTablesForm.toList,1)" type="button">下移</button>
						</td>
					</tr>
					<tr>
						<td colspan="3" align="left"><h5 style="color:red">注：选中一项或多项后点击添加或移除(按住shift或ctrl可以多选)，或在选择项上双击进行添加和移除。</h5></td>
					</tr>
				</table>
				<input type="text" id="selectedTables" name="selectedTables" size="40" style="display: none;">
				<input type="text" id="databaseFrom" name="databaseFrom" value="${dbFrom}" style="display: none;">
				<input type="text" id="databaseTo" name="databaseTo" value="${dbTo}" style="display: none;">
			</form>
	</div>
	 <div class="center"></div>
    <div class="right">
    	<table border="0" width="98%" id="moveTablesList">
			<tr style="background-color:#87CEEB">
				<td colspan="2" style="text-align: center"><h4>准备迁移表</h4></td>
			</tr>
			<tr style="background-color:#87CEEB">
				<td>数据库表</td><td>状态</td>
			</tr>
		</table>
    </div>
    <div class="bottom"><input type="button" value="  执 行  " onClick="goExecute()"></div>
</body>
</html>
<script language="JavaScript">
function goExecute() {
	var selectedTables = $("#selectedTables").val();
	var databaseFrom = $("#databaseFrom").val();
	var databaseTo = $("#databaseTo").val();
	
	$.ajax({
		type:"post",
		url:"<%=path%>/tables/move.jhtml",
		data: {fromDb:databaseFrom, toDb:databaseTo, tablesName:selectedTables},
		dataType:"json",
		success:function(result){
			if(result && result.success) {
				var tableHtml = document.getElementById("moveTablesList").innerHTML;
				var data = result.data;
				for(var i=0; i<data.length; i++) {
					tableHtml = tableHtml + "<tr><td>"+data[i]+"</td><td id='t_"+data[i]+"'>准备就绪</td></tr>"
				}
				document.getElementById("moveTablesList").innerHTML = tableHtml;
			}
		}
	})
}

function moveOption(e1, e2) {
	try {
		for (var i = 0; i < e1.options.length; i++) {
			if (e1.options[i].selected) {
				var e = e1.options[i];
				e2.options.add(new Option(e.text, e.value));
				e1.remove(i);
				ii = i - 1
			}
		}
		document.dbTablesForm.selectedTables.value = getvalue(document.dbTablesForm.toList);
	} catch (e) {
	}
}

function getvalue(geto) {
	var allvalue = "";
	for (var i = 0; i < geto.options.length; i++) {
		allvalue += geto.options[i].value + ",";
	}
	return allvalue;
}

function changepos(obj, index) {
    if (index == -1) {
        if (obj.selectedIndex > 0) {
			obj.insertBefore(obj.options[obj.selectedIndex], obj.options[obj.selectedIndex - 1]);
        }
    } else if (index == 1) {
        if (obj.selectedIndex < obj.options.length - 1) {
			obj.insertBefore(obj.options[obj.selectedIndex + 1], obj.options[obj.selectedIndex]);
        }
    }
}
</script>