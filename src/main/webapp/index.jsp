<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import = "Location.LocationDAO" %>
<%@ page import = "Wifi.WifiDAO" %>
<%@ page import = "Wifi.Wifi" %>

<%@ page import = "java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>와이파이 정보 구하기</title>
<script>
        function getLocation() {
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(showPosition, showError);
            } else {
                alert("Geolocation is not supported by this browser.");
            }
        }

        function showPosition(position) {
            var latitude = position.coords.latitude;
            var longitude = position.coords.longitude;

            // 여기에서 얻은 위치 정보를 활용하여 필요한 작업 수행
            //alert("Latitude: " + latitude + "\nLongitude: " + longitude);
            document.forms.location.latitude.value = latitude;
            document.forms.location.longitude.value = longitude;
        }

        function showError(error) {
            switch (error.code) {
                case error.PERMISSION_DENIED:
                    alert("User denied the request for Geolocation.");
                    break;
                case error.POSITION_UNAVAILABLE:
                    alert("Location information is unavailable.");
                    break;
                case error.TIMEOUT:
                    alert("The request to get user location timed out.");
                    break;
                case error.UNKNOWN_ERROR:
                    alert("An unknown error occurred.");
                    break;
            }
        }
    </script>
</head>

<style>
table { border-collapse: collapse; width: 100%; }
th, td { border: solid 1px lightgray; padding: 10px; }
tr:nth-child(even) { background-color: #f2f2f2; }
tr:hover { background-color: gray; }
th { background-color: #04AA6D; color: white; text-align: center; }
</style>

<body>
	<h1>와이파이 정보 구하기</h1>
	<a href="index.jsp">홈</a> |
	<a href="history.jsp">위치 히스토리 목록</a> |
	<a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a> |
	<a href="bookmark-list.jsp">북마크 보기</a> |
	<a href="bookmark-group.jsp">북마크 그룹 관리</a>
	<br><br>
	
	<form name="location" action="index.jsp" method="get">
	LAT : <input type="text" name="latitude" size="10">
	, LNT : <input type="text" name="longitude" size="10">
	<button type="button" onclick="getLocation()">내 위치 가져오기</button>
	<button type="submit">근처 WIFI 정보 보기</button>
	</form>
	
	<br>
	<table>
		<thead>
			<tr>
				<th>거리<br>(Km)</th>
				<th>관리번호</th>
				<th>자치구</th>
				<th>와이파이명</th>
				<th>도로명주소</th>
				<th>상세주소</th>
				<th>상세주소<br>(층)</th>
				<th>설치유형</th>
				<th>설치기관</th>
				<th>서비스구분</th>
				<th>망종류</th>
				<th>설치년도</th>
				<th>실내외구분</th>
				<th>WIFI접속환경</th>
				<th>X좌표</th>
				<th>Y좌표</th>
				<th>작업일자</th>
			</tr>
		</thead>
		<tbody>
	<%
		if (request.getParameter("latitude") == null||request.getParameter("longitude") == null) {	//입력 전
	%>		
			<tr>
				<td colspan = "17"; style = text-align:center;>위치 정보를 입력한 후에 조회해 주세요.</td>
	<%			
		} else if (request.getParameter("latitude").isEmpty()||request.getParameter("longitude").isEmpty()){	//입력 오류
	%>	
			<tr>
				<td colspan = "17"; style = text-align:center;>위치 정보를 입력한 후에 조회해 주세요.</td>
			</tr>
	<%	
		} else {	//정상처리
				Double lat = Double.parseDouble(request.getParameter("latitude"));
				Double lnt = Double.parseDouble(request.getParameter("longitude"));
				LocationDAO locationDAO = new LocationDAO();
				
				locationDAO.registerLocation(lat, lnt);
				
				WifiDAO wifiDAO = new WifiDAO();
				
				ArrayList<Wifi> list = wifiDAO.getWifiList(lat, lnt);
				
				for (int i = 0; i < list.size(); i++) {
	%>
			<tr>
				<td><%=list.get(i).getDISTANCE() %></td>
				<td><%=list.get(i).getX_SWIFI_MGR_NO() %></td>
				<td><%=list.get(i).getX_SWIFI_WRDOFC() %></td>
				<td><a href="detail.jsp?X_SWIFI_MGR_NO=<%=list.get(i).getX_SWIFI_MGR_NO() %>"><%=list.get(i).getX_SWIFI_MAIN_NM() %></a></td>
				<td><%=list.get(i).getX_SWIFI_ADRES1() %></td>
				<td><%=list.get(i).getX_SWIFI_ADRES2() %></td>
				<td><%=list.get(i).getX_SWIFI_INSTL_FLOOR() %></td>
				<td><%=list.get(i).getX_SWIFI_INSTL_TY() %></td>
				<td><%=list.get(i).getX_SWIFI_INSTL_MBY() %></td>
				<td><%=list.get(i).getX_SWIFI_SVC_SE() %></td>
				<td><%=list.get(i).getX_SWIFI_CMCWR() %></td>
				<td><%=list.get(i).getX_SWIFI_CNSTC_YEAR() %></td>
				<td><%=list.get(i).getX_SWIFI_INOUT_DOOR() %></td>
				<td><%=list.get(i).getX_SWIFI_REMARS3() %></td>
				<td><%=list.get(i).getLAT() %></td>
				<td><%=list.get(i).getLNT() %></td>
				<td><%=list.get(i).getWORK_DTTM() %></td>
			</tr>
	<%
				}
		}
	%>
		</tbody>
	</table>
</body>
</html>