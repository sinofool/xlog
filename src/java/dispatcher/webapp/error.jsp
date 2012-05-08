<%@page language="java" contentType="text/html; charset=utf-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="css/commons.css" type="text/css" />
<title>xlog dispatcher后台管理</title>
</head>
<body style="text-align: center">
<%
	String errTip=request.getParameter("errTip");
%>
<br><br>
<p align="center"><font color="red" size="4">对不起，<%=errTip %></font></p>
</body>
</html>