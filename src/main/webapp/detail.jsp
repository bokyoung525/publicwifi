<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import = "Wifi.WifiDAO" %>
<%@ page import = "Wifi.Wifi" %>
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
	<h1>와이파이 상세 정보</h1>
	<a href="index.jsp">홈</a> |
	<a href="history.jsp">위치 히스토리 목록</a> |
	<a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a> |
	<a href="bookmark-list.jsp">북마크 보기</a> |
	<a href="bookmark-group.jsp">북마크 그룹 관리</a>
	<br><br>
	<%
		BookmarkGroupDAO bookmarkgroupDAO = new BookmarkGroupDAO();
		BookmarkGroup bookmarkgroup = new BookmarkGroup();
		
		String X_SWIFI_MGR_NO = request.getParameter("X_SWIFI_MGR_NO");
		
		ArrayList<BookmarkGroup> list = bookmarkgroupDAO.getBookmarkGroupList();
	%>
	
	<form name="boomarkgroup" action="bookmark-add-submit.jsp" method="get">
		<input type="hidden" name="X_SWIFI_MGR_NO" value=<%=X_SWIFI_MGR_NO %> />
		<select name="bookmarkgroup" size="1">
			<option value="">북마크 그룹 이름 선택</option>
	<% 
			for (int i = 0; i < list.size(); i++) { 
	%>
			<option value="<%=list.get(i).getID()%>"><%=list.get(i).getBOOKMARK_NAME()%></option>
	<%
			}
	%>
		</select>
		<button type="submit">북마크 추가하기</button>
	</form>
	
	<%
		
		WifiDAO wifiDAO = new WifiDAO();
		
		Wifi wifi = new Wifi();
		
		wifi = wifiDAO.getWifi(X_SWIFI_MGR_NO);
	%>
	
	<br>
	<table>
			<tr>
				<th>관리번호</th>
				<td><%=wifi.getX_SWIFI_MGR_NO() %></td>
			</tr>
			<tr>
				<th>자치구</th>
				<td><%=wifi.getX_SWIFI_WRDOFC() %></td>
			</tr>
			<tr>
				<th>와이파이명</th>
				<td><%=wifi.getX_SWIFI_MAIN_NM() %></td>
			</tr>
			<tr>
				<th>도로명주소</th>
				<td><%=wifi.getX_SWIFI_ADRES1() %></td>
			</tr>
			<tr>
				<th>상세주소</th>
				<td><%=wifi.getX_SWIFI_ADRES2() %></td>
			</tr>
			<tr>
				<th>상세주소 (층)</th>
				<td><%=wifi.getX_SWIFI_INSTL_FLOOR() %></td>
			</tr>
			<tr>
				<th>설치유형</th>
				<td><%=wifi.getX_SWIFI_INSTL_TY() %></td>
			</tr>
			<tr>
				<th>설치기관</th>
				<td><%=wifi.getX_SWIFI_INSTL_MBY() %></td>
			</tr>
			<tr>
				<th>서비스구분</th>
				<td><%=wifi.getX_SWIFI_SVC_SE() %></td>
			</tr>
			<tr>
				<th>망종류</th>
				<td><%=wifi.getX_SWIFI_CMCWR() %></td>
			</tr>
			<tr>
				<th>설치년도</th>
				<td><%=wifi.getX_SWIFI_CNSTC_YEAR() %></td>
			</tr>
			<tr>
				<th>실내외구분</th>
				<td><%=wifi.getX_SWIFI_INOUT_DOOR() %></td>
			</tr>
			<tr>
				<th>WIFI접속환경</th>
				<td><%=wifi.getX_SWIFI_REMARS3() %></td>
			</tr>
			<tr>
				<th>X좌표</th>
				<td><%=wifi.getLAT() %></td>
			</tr>
			<tr>
				<th>Y좌표</th>
				<td><%=wifi.getLNT() %></td>
			</tr>
			<tr>
				<th>작업일자</th>
				<td><%=wifi.getWORK_DTTM() %></td>
			</tr>
			<tr>
				<td colspan="2"; style=text-align:center;><a href="index.jsp">돌아가기</a></td>
			</tr>
	</table>
	
</body>
</html>