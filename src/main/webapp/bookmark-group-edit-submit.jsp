<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import = "BookmarkGroup.BookmarkGroupDAO" %>

<%@ page import = "java.io.PrintWriter" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>와이파이 정보 구하기</title>
</head>

<body>
	<%
		int ID = Integer.parseInt(request.getParameter("ID"));
		String BOOKMARK_NAME = request.getParameter("name");
		BOOKMARK_NAME = new String(BOOKMARK_NAME.getBytes("8859_1"), "UTF-8");	//한글 오류 방지
		int ORDERINDEX = Integer.parseInt(request.getParameter("orderindex"));
		
		BookmarkGroupDAO bookmarkgroupDAO = new BookmarkGroupDAO();
		
		int result = bookmarkgroupDAO.updateBookmarkGroup(ID, BOOKMARK_NAME, ORDERINDEX);
		if (result == -1) {    // 데이터베이스 오류
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('북마크 그룹 수정에 실패하였습니다.')");
			script.println("history.back()");
			script.println("</script>");
		} else {    // 정상처리
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('북마크 그룹을 수정하였습니다.')");
			script.println("location.href='bookmark-group.jsp'");
			script.println("</script>");
		}
	%>
</body>
</html>