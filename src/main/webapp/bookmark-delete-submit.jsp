<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import = "Bookmark.BookmarkDAO" %>
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
		String X_SWIFI_MGR_NO = request.getParameter("X_SWIFI_MGR_NO");
		X_SWIFI_MGR_NO = new String(X_SWIFI_MGR_NO.getBytes("8859_1"), "UTF-8");	//한글 오류 방지
	
		BookmarkDAO bookmarkDAO = new BookmarkDAO();
		
		int result = bookmarkDAO.deleteBookmark(ID, X_SWIFI_MGR_NO);
		if (result == -1) {    // 데이터베이스 오류가 발생했을 경우
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('북마크 삭제에 실패하였습니다.')");
			script.println("history.back()");
			script.println("</script>");
		} else {    // 정상처리 되었을 경우 게시판메인화면(bbs.jsp)로 이동
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('북마크를 삭제하였습니다.')");
			script.println("location.href='bookmark-list.jsp'");
			script.println("</script>");
		}
	%>
</body>
</html>