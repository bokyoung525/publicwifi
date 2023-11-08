package Wifi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class WifiDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	
	public WifiDAO() {
		try {Class.forName("org.sqlite.JDBC"); // SQLite JDBC 체크
         			
         	String dbFile = "seoul_wifi.db";
         	conn = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
        } catch(Exception e) {
            e.printStackTrace();
        }
	}
	
	public int registerWifi(Wifi wifi) {
        String SQL = "INSERT INTO WIFI VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; 

        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, wifi.getX_SWIFI_MGR_NO());
            pstmt.setString(2, wifi.getX_SWIFI_WRDOFC());
            pstmt.setString(3, wifi.getX_SWIFI_MAIN_NM());
            pstmt.setString(4, wifi.getX_SWIFI_ADRES1());
            pstmt.setString(5, wifi.getX_SWIFI_ADRES2());
            pstmt.setString(6, wifi.getX_SWIFI_INSTL_FLOOR());
            pstmt.setString(7, wifi.getX_SWIFI_INSTL_TY());
            pstmt.setString(8, wifi.getX_SWIFI_INSTL_MBY());
            pstmt.setString(9, wifi.getX_SWIFI_SVC_SE());
            pstmt.setString(10, wifi.getX_SWIFI_CMCWR());
            pstmt.setString(11, wifi.getX_SWIFI_CNSTC_YEAR());
            pstmt.setString(12, wifi.getX_SWIFI_INOUT_DOOR());
            pstmt.setString(13, wifi.getX_SWIFI_REMARS3());
            pstmt.setString(14, wifi.getLAT());
            pstmt.setString(15, wifi.getLNT());
            pstmt.setString(16, wifi.getWORK_DTTM());

            return pstmt.executeUpdate();
            
        } catch(Exception e) {
            e.printStackTrace();
        } return -1;
    }
	
	public int searchWifi(String userID, String userPassword) {
        String SQL = "Select userPassword From USER where userID = ?";
        
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, userID);

            //인젝션해킹등을 방지하기 위한 기법 ?에 ID값을 받은 후 사용.
            rs = pstmt.executeQuery();

            if(rs.next()) {

 

                if(rs.getString(1).equals(userPassword)) {

 

                    return 1; // 로그인 성공

 

                } else 

 

                    return 0; // 비밀번호 불일치

 

            }

 

            return -1; //아이디가 없음

 

        }catch(Exception e) {

 

            e.printStackTrace();    // 예외처리

 

        }

 

        return -2; // 데이터베이스 오류

 

    }
}