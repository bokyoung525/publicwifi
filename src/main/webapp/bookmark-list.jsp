<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import = "Bookmark.BookmarkDAO" %>
<%@ page import = "Bookmark.Bookmark" %>
<%@ page import = "BookmarkGroup.BookmarkGroupDAO" %>
<%@ page import = "BookmarkGroup.BookmarkGroup" %>
<%@ page import = "Wifi.WifiDAO" %>
<%@ page import = "Wifi.Wifi" %>
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
	<h1>북마크 목록</h1>
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
				<th>북마크 이름</th>
				<th>와이파이명</th>
				<th>등록일자</th>
				<th>비고</th>
			</tr>
		</thead>
	<%
		BookmarkDAO bookmarkDAO = new BookmarkDAO();
		
		ArrayList<Bookmark> list = bookmarkDAO.getBookmarkList();
		
		for (int i = 0; i < list.size(); i++) {
			BookmarkGroupDAO bookmarkgroupDAO = new BookmarkGroupDAO();
			BookmarkGroup bookmarkgroup = bookmarkgroupDAO.getBookmarkGroup(list.get(i).getID());
	%>
			<tr>
				<td><%=bookmarkgroup.getID() %></td>
				<td><%=bookmarkgroup.getBOOKMARK_NAME() %></td>
	<%
			WifiDAO wifiDAO = new WifiDAO();
			Wifi wifi = wifiDAO.getWifi(list.get(i).getX_SWIFI_MGR_NO());
	%>
				<td><a href="detail.jsp?X_SWIFI_MGR_NO=<%=wifi.getX_SWIFI_MGR_NO() %>"><%=wifi.getX_SWIFI_MAIN_NM()%></a></td>
				<td><%=list.get(i).getREGI_DATE() %></td>
				<td><a href="bookmark-delete.jsp?ID=<%=bookmarkgroup.getID()%>&X_SWIFI_MGR_NO=<%=wifi.getX_SWIFI_MGR_NO()%>">삭제</a></td>
			</tr>
	<%
		}
	%>
</body>
</html>