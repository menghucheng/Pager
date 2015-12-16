<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<% 
String path = request.getContextPath();
String basePath = request.getScheme() + "://" +
					request.getServerName() + ":" +
					request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>分页显示学生信息</title>
<head>

<style type="text/css">

select,select option,tr td{
	text-align: center;
}

</style>

<script type="text/javascript">
	//获取当前的页数
	var currentPage = ${result.currentPage};
	
	//总页数
	var totalPage = ${result.totalPage};
	
	//提交跳转函数
	function submitForm(actionUrl){
		var formElement = document.getElementById("stuForm");
		formElement.action = actionUrl;
		formElement.submit();
	}
	//初始化页面
	function initPage(){
		var genderRequest = "${gender}";
		var genderval = 0;//0 - 全部
		var genderElement = document.getElementById("gender");
		if(genderRequest != ""){
			genderVal = parseInt(genderRequest);
		}
		
		var options = genderElement.options;
		options[genderVal].selected=true;
		/* for (var i = 0; i < options.length; i++) {
			if(options[i].value == genderVal){
				options[i].selected=true;
				break;
			}
		} */
	}
	//首页
	function firstPage(){
		if(currentPage == 1){
			alert("已经是第一页数据");
			return false;
		}else{
			submitForm("<%=path%>/sublist/SublistServlet?pageNum=1");
			return true;
		}
	}
	//上一页
	function previousPage(){
		if(currentPage == 1){
			alert("已经是第一页数据");
			return false;
		}else{
			submitForm("<%=path%>/sublist/SublistServlet?pageNum=" + (currentPage-1));
			return true;
		}
	}
	
	//下一页
	function nextPage(){
		if(currentPage == totalPage){
			alert("已经是最后一页数据");
			return false;
		}else{
			submitForm("<%=path%>/sublist/SublistServlet?pageNum=" + (currentPage+1));
			return true;
		}
	}
	
	//尾页
	function lastPage(){
		if(currentPage == totalPage){
			alert("已经是最后一页数据");
			return false;
		}else{
			alert("=="+ totalPage);
			submitForm("<%=path%>/sublist/SublistServlet?pageNum=${result.totalPage}" + totalPage);
			return true;
		}
	}
</script>

</head>


<body onload="initPage();">
	<div style="margin-left :200px; margin-top: 100px;">
		<div>
			<font color="red">${errorMsg}</font>
		</div>
		<div>
			<form action="<%=path%>/sublist/SublistServlet" id="stuForm" method="post">
			姓名
			<input type="text" name="stuName" id="stu_name" style="width:120px" value="${stuName}"/>
			&nbsp;
			性别
			<select name="gender" id="gender" style="width: 120px">
				<option value="0">全部</option>
				<option value="1">男</option>
				<option value="2">女</option>
			</select>
			&nbsp;&nbsp;
			<input type="submit" value="查询"/>
			</form>
		</div>
		
		<br>
		
		学生信息列表: <br>
		<br>
		<!-- 后台返回结果为空 -->
		<c:if test="${fn: length(result.dataList) eq 0}">
			<span>查询的结果不存在</span>
		</c:if>
		<!-- 后台返回结果不为空 -->
		<c:if test="${fn: length(result.dataList) gt 0}">
			<table border="1px" bordercolor="blue" cellspacing="0px" style="border-collapse: collapse">
				<thead>
					<tr height="30">
						<th width="130">姓名</th>
						<th width="130">性别</th>
						<th width="130">年龄</th>
						<th width="130">家庭住址</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${result.dataList }" var="student" varStatus="status">
						<tr<c:if test="${status.index%2 == '0'}">
								style="background-color: #cccccc"
							</c:if>>
							
							<td><c:out value="${student.stuName }  ${status.count }"></c:out></td>
							<td>
								<c:if test="${student.gender eq 1}">男</c:if>
								<c:if test="${student.gender eq 2}">女</c:if>
							</td>
							<td><c:out value="${student.age }"></c:out> </td>
							<td><c:out value="${student.address }"></c:out> </td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<br>
			<div style="background-color: #cccccc;width: 450px;">
			共${result.totalRecords }条记录&nbsp;&nbsp;共${result.totalPage }页&nbsp;&nbsp;当前第${result.currentPage }页&nbsp;&nbsp;
			<a href="#" onclick="firstPage();">首页</a>&nbsp;&nbsp; 
			<a href="#" onclick="previousPage();">上一页</a>&nbsp;&nbsp;
			<a href="#" onclick="nextPage();">下一页</a>&nbsp;&nbsp; 
			<a href="#" onblur="lastPage();">尾页</a>	
			</div>
		</c:if>
	</div>
</body>


</html>