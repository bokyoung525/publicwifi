<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import = "Location.LocationDAO" %>
<%@ page import = "Location.Location" %>

<%@ page import = "java.util.ArrayList" %>
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
	<h1>위치 히스토리 목록</h1>
	<a href="index.jsp">홈</a> |
	<a href="history.jsp">위치 히스토리 목록</a> |
	<a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a> |
	<a href="bookmark-list.jsp">북마크 보기</a> |
	<a href="bookmark-group.jsp">북마크 그룹 관리</a>
	<br><br>
	
	<table>
		<thead>
			<tr>
				<th>ID</th>
				<th>X좌표</th>
				<th>Y좌표</th>
				<th>조회일자</th>
				<th>비고</th>
			</tr>
		</thead>
		<tbody>
	<%
		LocationDAO locationDAO = new LocationDAO();
	
		ArrayList<Location> list = locationDAO.getLocationList();
		
		for (int i = 0; i < list.size(); i++) {
	%>
			<tr>
				<td><%=list.get(i).getID() %></td>
				<td><%=list.get(i).getLAT() %></td>
				<td><%=list.get(i).getLNT() %></td>
				<td><%=list.get(i).getDATE() %></td>
				<td><a href="history-delete.jsp?ID=<%=list.get(i).getID() %>">삭제</a></td>
			</tr>
	<%
		}
	%>
		</tbody>
	</table>
</body>
</html>