<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import = "BookmarkGroup.BookmarkGroupDAO" %>
<%@ page import = "BookmarkGroup.BookmarkGroup" %>
<%@ page import = "java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>와이파이 정보 구하기</title>
</head>

<style>
table { border-collapse: collapse; width: 100%;}
th, td { border: solid 1px lightgray; padding: 10px;}
tr:nth-child(even) { background-color: #f2f2f2;}
tr:hover {background-color: gray;}
th { background-color: #04AA6D; color: white; text-align: center}
</style>

<body>
	<h1>북마크 그룹 목록</h1>
	<a href="index.jsp">홈</a> |
	<a href="history.jsp">위치 히스토리 목록</a> |
	<a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a> |
	<a href="bookmark-list.jsp">북마크 보기</a> |
	<a href="bookmark-group.jsp">북마크 그룹 관리</a>
	<br><br>
	
	<button type="button" onclick="location.href='bookmark-group-add.jsp'">북마크 그룹 이름 추가</button>
	<br><br>
	
	<table>
		<thead>
			<tr>
				<th>ID</th>
				<th>북마크 이름</th>
				<th>순서</th>
				<th>등록일자</th>
				<th>수정일자</th>
				<th>비고</th>
			</tr>
		</thead>
		<tbody>
	<%
		BookmarkGroupDAO bookmarkgroupDAO = new BookmarkGroupDAO();
		
		ArrayList<BookmarkGroup> list = bookmarkgroupDAO.getBookmarkGroupList();
		
		for (int i = 0; i < list.size(); i++) {
	%>
			<tr>
				<td><%=list.get(i).getID() %></td>
				<td><%=list.get(i).getBOOKMARK_NAME() %></td>
				<td><%=list.get(i).getORDERINDEX() %></td>
				<td><%=list.get(i).getREGI_DATE() %></td>
				<td><%=list.get(i).getUPDATE_DATE() %></td>
				<td><a href="bookmark-group-edit.jsp?ID=<%=list.get(i).getID() %>">수정</a>
					<a href="bookmark-group-delete.jsp?ID=<%=list.get(i).getID() %>">삭제</a></td>
			</tr>
	<%
		}
	%>
</body>
</html>