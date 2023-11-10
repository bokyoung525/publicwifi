package Wifi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class WifiDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	
	public WifiDAO() {
		try {
			Class.forName("org.sqlite.JDBC"); // SQLite JDBC 체크
         			
         	String dbFile = "C:\\Users\\leebokyoung\\eclipse-workspace\\zerobase-mission1\\seoul_wifi.db";
         	conn = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
        } catch(Exception e) {
            e.printStackTrace();
        }
	}
	
	public int deleteAllWifi() {
        String SQL = "DELETE FROM WIFI;";
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);

            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // 데이터 오류발생 시
    }
	
	public String getDate() {
        String SQL = "SELECT NOW()"; // 현재시간을 가져오는 SQL구문
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
            pstmt.setDouble(14, wifi.getLAT());
            pstmt.setDouble(15, wifi.getLNT());
            pstmt.setString(16, wifi.getWORK_DTTM());
            
            return pstmt.executeUpdate();
            
        } catch(Exception e) {
            e.printStackTrace();
        } return -1;
    }
	
	public ArrayList<Wifi> getWifiList(Double latitude, Double longitude) {
        String SQL = "SELECT 6371*acos(cos(radians(WIFI.LAT))*cos(radians(?))*cos(radians(?)-radians(WIFI.LNT))+sin(radians(WIFI.LAT))*sin(radians(?))) AS distance, * "
        		+ "FROM WIFI ORDER BY distance LIMIT 20";

        ArrayList<Wifi> list = new ArrayList<Wifi>();

        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL); // 연결되어 있는 객체를 이용해서 실행준비단계로 만들어 줌
            
            pstmt.setDouble(1, latitude);
            pstmt.setDouble(2, longitude);
            pstmt.setDouble(3, latitude);
            
            rs = pstmt.executeQuery();

            while (rs.next()) {
            	Wifi wifi = new Wifi();
            	
            	wifi.setDISTANCE(Math.round(rs.getDouble(1)*10000)/10000.0);
            	wifi.setX_SWIFI_MGR_NO(rs.getString(2));
				wifi.setX_SWIFI_WRDOFC(rs.getString(3));
				wifi.setX_SWIFI_MAIN_NM(rs.getString(4));
				wifi.setX_SWIFI_ADRES1(rs.getString(5));
				wifi.setX_SWIFI_ADRES2(rs.getString(6));
				wifi.setX_SWIFI_INSTL_FLOOR(rs.getString(7));
				wifi.setX_SWIFI_INSTL_TY(rs.getString(8));
				wifi.setX_SWIFI_INSTL_MBY(rs.getString(9));
				wifi.setX_SWIFI_SVC_SE(rs.getString(10));
				wifi.setX_SWIFI_CMCWR(rs.getString(11));
				wifi.setX_SWIFI_CNSTC_YEAR(rs.getString(12));
				wifi.setX_SWIFI_INOUT_DOOR(rs.getString(13));
				wifi.setX_SWIFI_REMARS3(rs.getString(14));
				wifi.setLAT(rs.getDouble(15));
				wifi.setLNT(rs.getDouble(16));
				wifi.setWORK_DTTM(rs.getString(17));
				
				list.add(wifi);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list; // 리스트 출력
    }
}