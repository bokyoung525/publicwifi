package BookmarkGroup;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BookmarkGroupDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public BookmarkGroupDAO() {
		try {
			Class.forName("org.sqlite.JDBC"); // SQLite JDBC 체크
         			
         	String dbFile = "C:\\Users\\leebokyoung\\eclipse-workspace\\zerobase-mission1\\seoul_wifi.db";
         	conn = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
        } catch(Exception e) {
            e.printStackTrace();
        }
	}
	
	public int getNext() {
        String SQL = "SELECT ID FROM BOOKMARK_GROUP ORDER BY ID DESC"; // bbsID를 새로 생성(+1)하기 위해, 가장 마지막 bbsID를 가져옴
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
	
	public int addBookmarkGroup(String Bookmark_NAME, int ORDERINDEX) {
        String SQL = "INSERT INTO BOOKMARK_GROUP VALUES (?, ?, ?, ?, ?)"; 
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, getNext());
            pstmt.setString(2, Bookmark_NAME);
            pstmt.setInt(3, ORDERINDEX);
            pstmt.setString(4, getDate());
            pstmt.setString(5, "");
            
            pstmt.executeUpdate();
            
            pstmt.close();
            conn.close();
            return 1;
            
        } catch(Exception e) {
            e.printStackTrace();
        } 
        return -1;
    }
	
	public ArrayList<BookmarkGroup> getBookmarkGroupList() {
        String SQL = "SELECT * FROM BOOKMARK_GROUP ORDER BY ORDERINDEX ASC";

        ArrayList<BookmarkGroup> list = new ArrayList<BookmarkGroup>();

        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL); // 연결되어 있는 객체를 이용해서 실행준비단계로 만들어 줌
            
            rs = pstmt.executeQuery();

            while (rs.next()) {
            	BookmarkGroup bookmarkgroup = new BookmarkGroup();
            	
            	bookmarkgroup.setID(rs.getInt(1));
            	bookmarkgroup.setBOOKMARK_NAME(rs.getString(2));
            	bookmarkgroup.setORDERINDEX(rs.getInt(3));
            	bookmarkgroup.setREGI_DATE(rs.getString(4));
            	bookmarkgroup.setUPDATE_DATE(rs.getString(5));
				
				list.add(bookmarkgroup);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list; // 리스트 출력
    }
	
	public BookmarkGroup getBookmarkGroup(int ID) {
		String SQL = "SELECT * FROM BOOKMARK_GROUP WHERE ID = ?";
		
		BookmarkGroup bookmarkgroup = new BookmarkGroup();
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, ID);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				bookmarkgroup.setID(rs.getInt(1));
				bookmarkgroup.setBOOKMARK_NAME(rs.getString(2));
				bookmarkgroup.setORDERINDEX(rs.getInt(3));
				bookmarkgroup.setREGI_DATE(rs.getString(4));
				bookmarkgroup.setUPDATE_DATE(rs.getString(5));
            }
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bookmarkgroup;
	}
	
	public int deleteBookmarkGroup(int ID) {
		String SQL = "DELETE FROM BOOKMARK_GROUP WHERE ID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, ID);
			
			pstmt.executeUpdate();
			
			pstmt.close();
			conn.close();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public int updateBookmarkGroup(int ID, String BOOKMARK_NAME, int ORDERINDEX) {
		String SQL = "UPDATE BOOKMARK_GROUP SET BOOKMARK_NAME = ?, ORDERINDEX = ? WHERE ID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, BOOKMARK_NAME);
			pstmt.setInt(2, ORDERINDEX);
			pstmt.setInt(3, ID);
			
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
