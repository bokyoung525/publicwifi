<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import = "Bookmark.BookmarkDAO" %>
<%@ page import = "Bookmark.Bookmark" %>
<%@ page import = "BookmarkGroup.BookmarkGroupDAO" %>
<%@ page import = "BookmarkGroup.BookmarkGroup" %>
<%@ page import = "Wifi.WifiDAO" %>
<%@ page import = "Wifi.Wifi" %>
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
	<h1>북마크 삭제</h1>
	<a href="index.jsp">홈</a> |
	<a href="history.jsp">위치 히스토리 목록</a> |
	<a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a> |
	<a href="bookmark-list.jsp">북마크 보기</a> |
	<a href="bookmark-group.jsp">북마크 그룹 관리</a>
	<br><br>북마크를 삭제하시겠습니까?
	<br><br>
	<%
		int ID = Integer.parseInt(request.getParameter("ID"));
		String X_SWIFI_MGR_NO = request.getParameter("X_SWIFI_MGR_NO");
		X_SWIFI_MGR_NO = new String(X_SWIFI_MGR_NO.getBytes("8859_1"), "UTF-8");	//한글 오류 방지
		
		BookmarkDAO bookmarkDAO = new BookmarkDAO();
		Bookmark bookmark = bookmarkDAO.getBookmark(ID, X_SWIFI_MGR_NO);
		
		BookmarkGroupDAO bookmarkgroupDAO = new BookmarkGroupDAO();
		BookmarkGroup bookmarkgroup = bookmarkgroupDAO.getBookmarkGroup(ID);
		
		WifiDAO wifiDAO = new WifiDAO();
		Wifi wifi = wifiDAO.getWifi(X_SWIFI_MGR_NO);
		
		String bookmarkgroupname = bookmarkgroup.getBOOKMARK_NAME();
		String wifiname = wifi.getX_SWIFI_MAIN_NM();
		String regidate = bookmark.getREGI_DATE();
	%>
	<form name="bookmark" action="bookmark-delete-submit.jsp" method ="post">
		<input type="hidden" name="ID" value=<%=ID %> />
		<input type="hidden" name="X_SWIFI_MGR_NO" value=<%=X_SWIFI_MGR_NO %> />
		<table>
			<tr>
				<th>북마크 이름</th>
				<td><input type="text" name="bookmarkgroupname" value="<%=bookmarkgroupname%>" readonly="true"></td>
			</tr>
			<tr>
				<th>와이파이명</th>
				<td><input type="text" name="wifiname" value="<%=wifiname%>" readonly="true"></td>
			</tr>
			<tr>
				<th>등록일자</th>
				<td><input type="text" name="regidate" value="<%=regidate%>" readonly="true"></td>
			</tr>
			<tr>
				<td colspan="2"; style=text-align:center;><a href="bookmark-list.jsp">돌아가기</a> | <button type="submit">삭제</button></td>
			</tr>
		</table>
	</form>
</body>
</html>