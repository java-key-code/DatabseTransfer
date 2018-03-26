<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=basePath%>/bootstrap/jquery-3.2.1.js"></script>
<style>
	.top {
		margin: 0 20px;
	}
	
    .left {
        margin: 20px;
        width: 40%;
        border: 1px solid red;
        display: inline-block;
        text-align: center;
     }
    .right {
        margin: 20px;
        width: 40%;
        border: 1px solid red;
        display: inline-block;
        text-align: center;
    }
    .center {
        width: 10%;
        border: 1px solid red;
        display: inline-block;
        text-align: center;
    }
    .content,
    .bottom {
        margin: 0 20px;
        text-align: center;
    }
    .text-item {
    	width: 80%;
    	display: inline-block;
    }
    .text-item-outter {
    	margin: 20px 1px;
    	width: 80%;
    	text-align: left;
    }
    .text-item-inner {
    	margin: 10px 1px;
    	width: 80%;
    	display: inline-block;
    }
    
    .text-item-value {
    	margin-left: 15px;
    	margin-top: 5px;
    	color: red;
    	/* color: gray; */
    }
</style>
<title>数据库迁移</title>
</head>
<body>
	<div class="top">
		<marquee><h1>欢迎进入数据迁移界面</h1></marquee>
	</div>
	<div class="content">
        <div class="left">
        	<h3>********************被迁移数据库********************</h3>
        	<div class="text-item">
        		<div class="text-item-outter">
	        		<label>数据库:</label>
	        		<div class="text-item-inner"><select id="dbfrom" name="dbfrom" onChange="showFromDb(this.value)"><option value='==请选择=='>==请选择==</option></select></div>
        		</div>
        		<div class="text-item-outter">
        			<div class="text-item-inner">
		        		<label>数据库类型:</label>
		        		<div id="from_dbType" class="text-item-value">N/A</div>
	        		</div>
	        		<div class="text-item-inner">
		        		<label>服务器IP:</label>
		        		<div id="from_ip" class="text-item-value">N/A</div>
	        		</div>
	        		<div class="text-item-inner">
		        		<label>服务器端口:</label>
		        		<div id="from_port" class="text-item-value">N/A</div>
	        		</div>
	        		<div class="text-item-inner">
		        		<label>数据库名:</label>
		        		<div id="from_dbname" class="text-item-value">N/A</div>
	        		</div>
	        		<div class="text-item-inner">
		        		<label>用户名:</label>
		        		<div id="from_username" class="text-item-value">N/A</div>
	        		</div>
	        		<div class="text-item-inner">
		        		<label>密码:</label>
		        		<div id="from_pwd" class="text-item-value">N/A</div>
	        		</div>
        		</div>
        	</div>
        </div>
        <div class="center">==></div>
        <div class="right">
        	<h3>********************目的地数据库********************</h3>
        	<div class="text-item">
        		<div class="text-item-outter">
	        		<label>数据库:</label>
	        		<div class="text-item-inner"><select id="dbto" name="dbto" onChange="showToDb(this.value)"><option value='==请选择=='>==请选择==</option></select></div>
        		</div>
        		<div class="text-item-outter">
        			<div class="text-item-inner">
		        		<label>数据库类型:</label>
		        		<div id="to_dbType" class="text-item-value">N/A</div>
	        		</div>
	        		<div class="text-item-inner">
		        		<label>服务器IP:</label>
		        		<div id="to_ip" class="text-item-value">N/A</div>
	        		</div>
	        		<div class="text-item-inner">
		        		<label>服务器端口:</label>
		        		<div id="to_port" class="text-item-value">N/A</div>
	        		</div>
	        		<div class="text-item-inner">
		        		<label>数据库名:</label>
		        		<div id="to_dbname" class="text-item-value">N/A</div>
	        		</div>
	        		<div class="text-item-inner">
		        		<label>用户名:</label>
		        		<div id="to_username" class="text-item-value">N/A</div>
	        		</div>
	        		<div class="text-item-inner">
		        		<label>密码:</label>
		        		<div id="to_pwd" class="text-item-value">N/A</div>
	        		</div>
        		</div>
        	</div>
        </div>
    </div>
    <div class="bottom"><input type="button" value="  开 始   " onClick="goExecute()"></div>
</body>
</html>
<script type="text/javascript">
$(function(){
	$.ajax({
		type:"post",
		url:"<%=path%>/db/selectFromDb.jhtml",
		dataType:"json",
		success:function(obj){
			$("[name='dbfrom']").empty();
			$("[name='dbfrom']").append("<option value='==请选择=='>==请选择==</option>");
			for(var i in obj){
				$("[name='dbfrom']").append("<option value='"+obj[i]+"'>"+obj[i]+"</option>");
			}
		}
	})
})

$(function(){
	$.ajax({
		type:"post",
		url:"<%=path%>/db/selectToDb.jhtml",
		dataType:"json",
		success:function(obj){
			$("[name='dbto']").empty();
			$("[name='dbto']").append("<option value='==请选择=='>==请选择==</option>");
			for(var i in obj){
				$("[name='dbto']").append("<option value='"+obj[i]+"'>"+obj[i]+"</option>");
			}
		}
	})
})

function showFromDb(key) {
	$.ajax({
		type:"post",
		url:"<%=path%>/db/showDb.jhtml",
		data: {fullName:key},
		dataType:"json",
		success:function(obj){
			$("#from_dbType").text(obj.dbType);
			$("#from_ip").text(obj.ip);
			$("#from_port").text(obj.port);
			$("#from_dbname").text(obj.dbname);
			$("#from_username").text(obj.userName);
			$("#from_pwd").text(obj.password);
		}
	})
}

function showToDb(key) {
	$.ajax({
		type:"post",
		url:"<%=path%>/db/showDb.jhtml",
		data: {fullName:key},
		dataType:"json",
		success:function(obj){
			$("#to_dbType").text(obj.dbType);
			$("#to_ip").text(obj.ip);
			$("#to_port").text(obj.port);
			$("#to_dbname").text(obj.dbname);
			$("#to_username").text(obj.userName);
			$("#to_pwd").text(obj.password);
		}
	})
}

function goExecute() {
	var dbFrom = $("#dbfrom").val();
	var dbTo = $("#dbto").val();
	<%-- window.open("<%=path%>/db/showTables.jhtml?" + encodeURIComponent("dbFrom=" + dbFrom + "&dbTo=" + dbTo));  --%>
	window.open("<%=path%>/db/showTables.jhtml?dbFrom=" + dbFrom + "&dbTo=" + dbTo); 
}
</script>