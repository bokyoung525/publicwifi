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
		String BOOKMARK_NAME = request.getParameter("bookmark_group_name");
		BOOKMARK_NAME = new String(BOOKMARK_NAME.getBytes("8859_1"), "UTF-8");	//한글 오류 방지
		int ORDERINDEX = Integer.parseInt(request.getParameter("orderindex"));
		
		BookmarkGroupDAO bookmarkgroupDAO = new BookmarkGroupDAO();
		
		int result = bookmarkgroupDAO.addBookmarkGroup(BOOKMARK_NAME, ORDERINDEX);
		if (result == -1) {    // 데이터베이스 오류가 발생했을 경우
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('북마크 그룹 추가에 실패하였습니다.')");
			script.println("history.back()");
			script.println("</script>");
		} else {    // 정상처리 되었을 경우 게시판메인화면(bbs.jsp)로 이동
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('북마크 그룹을 추가하였습니다.')");
			script.println("location.href='bookmark-group.jsp'");
			script.println("</script>");
		}
	%>
</body>
</html>