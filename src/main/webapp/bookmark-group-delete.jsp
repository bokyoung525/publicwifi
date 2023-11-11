<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import = "BookmarkGroup.BookmarkGroupDAO" %>
<%@ page import = "BookmarkGroup.BookmarkGroup" %>
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
	<h1>북마크 그룹 삭제</h1>
	<a href="index.jsp">홈</a> |
	<a href="history.jsp">위치 히스토리 목록</a> |
	<a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a> |
	<a href="bookmark-list.jsp">북마크 보기</a> |
	<a href="bookmark-group.jsp">북마크 그룹 관리</a>
	<br><br>북마크 그룹을 삭제하시겠습니까?
	<br><br>
	<%
		int ID = Integer.parseInt(request.getParameter("ID"));
		BookmarkGroupDAO bookmarkgroupDAO = new BookmarkGroupDAO();
		
		BookmarkGroup bookmarkgroup = bookmarkgroupDAO.getBookmarkGroup(ID);
		
		String name = bookmarkgroup.getBOOKMARK_NAME();
		int orderindex = bookmarkgroup.getORDERINDEX();
	%>
	<form name="bookmarkgroup" action="bookmark-group-delete-submit.jsp" method ="post">
		<table>
			<tr>
				<th>ID</th>
				<td><input type="text" name="ID" value="<%=ID%>" readonly="true"></td>
			</tr>
			<tr>
				<th>북마크 이름</th>
				<td><input type="text" name="name" value="<%=name%>" readonly="true"></td>
			</tr>
			<tr>
				<th>순서</th>
				<td><input type="text" name="orderindex" value="<%=orderindex%>" readonly="true"></td>
			</tr>
			<tr>
				<td colspan="2"; style=text-align:center;><a href="bookmark-group.jsp">돌아가기</a> | <button type="submit">삭제</button></td>
			</tr>
		</table>
	</form>
</body>
</html>