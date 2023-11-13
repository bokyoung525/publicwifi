package Location;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class LocationDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public LocationDAO() {
		try {
			Class.forName("org.sqlite.JDBC"); // SQLite JDBC 체크
         			
         	String dbFile = "C:\\Users\\leebokyoung\\eclipse-workspace\\zerobase-mission1\\seoul_wifi.db";
         	conn = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
        } catch(Exception e) {
            e.printStackTrace();
        }
	}
	
	public int getNext() {
        String SQL = "SELECT ID FROM LOCATION ORDER BY ID DESC"; // 가장 마지막 ID
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL); // 연결되어 있는 객체를 이용해서 실행준비단계로 만들어 줌
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) + 1; // 나온값에 +1
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
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            rs = pstmt.executeQuery(); // 실행된 결과를 가져오도록 해줌
            if (rs.next()) {
                return rs.getString(1); // 나온값을 반환해 주도록 해줌
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ""; // 데이터 베이스 오류 알림
    }
	
	public int registerLocation(Double LAT, Double LNT) {
        String SQL = "INSERT INTO LOCATION VALUES (?, ?, ?, ?)"; 
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, getNext());
            pstmt.setDouble(2, LAT);
            pstmt.setDouble(3, LNT);
            pstmt.setString(4, getDate());
            
            pstmt.executeUpdate();
            
            pstmt.close();
            conn.close();
            return 1;
            
        } catch(Exception e) {
            e.printStackTrace();
        } 
        return -1;
    }
	
	public ArrayList<Location> getLocationList() {
        String SQL = "SELECT * FROM LOCATION ORDER BY ID DESC";

        ArrayList<Location> list = new ArrayList<Location>();

        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL); // 연결되어 있는 객체를 이용해서 실행준비단계로 만들어 줌
            
            rs = pstmt.executeQuery();

            while (rs.next()) {
            	Location location = new Location();
            	
            	location.setID(rs.getInt(1));
            	location.setLAT(rs.getDouble(2));
            	location.setLNT(rs.getDouble(3));
            	location.setDATE(rs.getString(4));
				
				list.add(location);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list; // 리스트 출력
    }
	
	public Location getLocation(int ID) {
		String SQL = "SELECT LAT, LNT FROM LOCATION WHERE ID = ?";
		
		Location location = new Location();
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, ID);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				location.setLAT(rs.getDouble(1));
				location.setLNT(rs.getDouble(2));
            }
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return location;
	}
	
	public int deleteLocation(int ID) {
		String SQL = "DELETE FROM LOCATION WHERE ID = ?";
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
}
