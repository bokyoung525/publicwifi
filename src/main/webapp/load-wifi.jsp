<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%@ page import = "Wifi.WifiLoad" %>

<%@ page import = "java.io.PrintWriter" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>와이파이 정보 구하기</title>
</head>
<style>
	h1 { text-align: center; }
</style>
<body>
	<%
		WifiLoad wl = new WifiLoad();
	
		int total = wl.AddWifi();
	%>
	
	<div style="text-align: center;">
		<h1><%=total %>개의 WIFI 정보를 정상적으로 저장하였습니다.</h1>
    	<a href="index.jsp">홈으로 가기</a>
	</div>
</body>
</html>