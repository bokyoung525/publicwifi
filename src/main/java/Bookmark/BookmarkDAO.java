package Bookmark;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BookmarkDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public BookmarkDAO() {
		try {
			Class.forName("org.sqlite.JDBC"); // SQLite JDBC 체크
         			
         	String dbFile = "C:\\Users\\leebokyoung\\eclipse-workspace\\zerobase-mission1\\seoul_wifi.db";
         	conn = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
        } catch(Exception e) {
            e.printStackTrace();
        }
	}
	
	public int getNext() {
        String SQL = "SELECT ID FROM BOOKMARK ORDER BY ID DESC"; // bbsID를 새로 생성(+1)하기 위해, 가장 마지막 bbsID를 가져옴
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL); // 연결되어 있는 객체를 이용해서 실행준비단계로 만들어 줌
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) + 1; // 나온값에 1을 해줘서 새로운 bbsID값을 반환
            }
            return 1; // 기존데이터가 없어 첫번째 게시물인경우
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // 데이터 오류발생 시
    }
	
	public String getDate() {
        String SQL = "SELECT datetime('now', 'localtime')"; // 현재시간을 가져오는 SQL구문
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL); // 연결되어 있는 객체를 이용해서 실행준비단계로 만들어 줌
            rs = pstmt.executeQuery(); // 실행된 결과를 가져오도록 해줌
            if (rs.next()) {
                return rs.getString(1); // 나온값을 반환해 주도록 해줌
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ""; // 데이터 베이스 오류 알림
    }
	
	public int addBookmark(int ID, String X_SWIFI_MGR_NO) {
        String SQL = "INSERT INTO BOOKMARK VALUES (?, ?, ?)"; 
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, ID);
            pstmt.setString(2, X_SWIFI_MGR_NO);
            pstmt.setString(3, getDate());
            
            pstmt.executeUpdate();
            
            pstmt.close();
            conn.close();
            return 1;
            
        } catch(Exception e) {
            e.printStackTrace();
        } 
        return -1;
    }
	
	public ArrayList<Bookmark> getBookmarkList() {
        String SQL = "SELECT * FROM BOOKMARK ORDER BY ID ASC";

        ArrayList<Bookmark> list = new ArrayList<Bookmark>();

        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL); // 연결되어 있는 객체를 이용해서 실행준비단계로 만들어 줌
            
            rs = pstmt.executeQuery();

            while (rs.next()) {
            	Bookmark bookmark = new Bookmark();
            	
            	bookmark.setID(rs.getInt(1));
            	bookmark.setX_SWIFI_MGR_NO(rs.getString(2));
            	bookmark.setREGI_DATE(rs.getString(3));
				
				list.add(bookmark);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list; // 리스트 출력
    }
	
	public Bookmark getBookmark(int ID, String X_SWIFI_MGR_NO) {
		String SQL = "SELECT * FROM BOOKMARK WHERE ID = ? AND X_SWIFI_MGR_NO = ?";
		
		Bookmark bookmark = new Bookmark();
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, ID);
			pstmt.setString(2, X_SWIFI_MGR_NO);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				bookmark.setID(rs.getInt(1));
            	bookmark.setX_SWIFI_MGR_NO(rs.getString(2));
            	bookmark.setREGI_DATE(rs.getString(3));
            }
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bookmark;
	}
	
	public int deleteBookmark(int ID, String X_SWIFI_MGR_NO) {
		String SQL = "DELETE FROM BOOKMARK WHERE ID = ? AND X_SWIFI_MGR_NO = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, ID);
			pstmt.setString(2, X_SWIFI_MGR_NO);
			
			pstmt.executeUpdate();
			
			pstmt.close();
			conn.close();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
