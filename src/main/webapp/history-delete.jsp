<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <%@ page import = "Location.LocationDAO" %>
 <%@ page import = "Location.Location" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>와이파이 정보 구하기</title>
</head>

<style>
table { border-collapse: collapse; width: 100%; }
th, td { border: solid 1px lightgray; padding: 10px; }
tr:nth-child(even) { background-color: #f2f2f2; }
tr:hover { background-color: gray; }
th { background-color: #04AA6D; color: white; text-align: center; }
</style>

<body>
	<h1>위치 히스토리 삭제</h1>
	<a href="index.jsp">홈</a> |
	<a href="history.jsp">위치 히스토리 목록</a> |
	<a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a> |
	<a href="bookmark-list.jsp">북마크 보기</a> |
	<a href="bookmark-group.jsp">북마크 그룹 관리</a>
	<br><br>위치 히스토리를 삭제하시겠습니까?
	<br><br>
	<%
		int ID = Integer.parseInt(request.getParameter("ID"));
		LocationDAO locationDAO = new LocationDAO();
		
		Location location = locationDAO.getLocation(ID);
		
		Double latitude = location.getLAT();
		Double longitude = location.getLNT();
	%>
	<form name="location" action="history-delete-submit.jsp" method ="post">
		<table>
			<tr>
				<th>ID</th>
				<td><input type="text" name="ID" value="<%=ID %>" readonly="true"></td>
			</tr>
			<tr>
				<th>위도 (LAT)</th>
				<td><input type="text" name="latitude" value="<%=latitude %>" readonly="true"></td>
			</tr>
			<tr>
				<th>경도 (LNT)</th>
				<td><input type="text" name="longitude" value="<%=longitude %>" readonly="true"></td>
			</tr>
			<tr>
				<td colspan="2"; style=text-align:center;><a href="history.jsp">돌아가기</a> | <button type="submit">삭제</button></td>
			</tr>
		</table>
	</form>
</body>
</html>