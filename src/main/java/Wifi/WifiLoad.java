package Wifi;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class WifiLoad {
    static final String KEY = "44434a53636c656538304643574352";
    static int TOTALCNT;

    public static int TotalCnt() throws ParseException {
        URL url = null;
        HttpURLConnection con= null;
        JSONObject result = null;
        StringBuilder sb = new StringBuilder();
        int start = 1;
        int end = 1;
        String baseUrl = "http://openapi.seoul.go.kr:8088/" + KEY + "/" +
                "json/TbPublicWifiInfo/"+ start + "/"+end+"/";

        try {
            url = new URL(baseUrl);
            con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");

            con.setRequestProperty("Content-type", "application/json");

            con.setDoOutput(true);

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            while(br.ready()) {
                sb.append(br.readLine());
            }
            con.disconnect();
        }catch(Exception e) {
            e.printStackTrace();
        }

        result = (JSONObject) new JSONParser().parse(sb.toString());

        JSONObject data = (JSONObject) result.get("TbPublicWifiInfo");
        int totalGet = Integer.parseInt( data.get("list_total_count").toString());

        return totalGet;
    }

    public static int AddWifi() throws ParseException{
        int start = 0;
        int end = 0;
        int total = 0;

        //TOTALCNT = TotalCnt();
        TOTALCNT = 50;
        int pageEnd = TOTALCNT / 1000;
        int pageEndRemain = TOTALCNT % 1000;
        
        WifiDAO wifiDAO = new WifiDAO();
        
        wifiDAO.deleteAllWifi();		//현재 데이터베이스에 있는 API 전부 삭제

        for (int k = 0; k <= pageEnd; k++) {
            start = (int) Math.pow(10, 3) * k + 1;

            if(k == pageEnd){
                end = start + pageEndRemain - 1;
            }
            else{
                end = (int) Math.pow(10, 3) * (k+1) ;
            }

            String baseUrl = "http://openapi.seoul.go.kr:8088/" + KEY + "/" +
                    "json/TbPublicWifiInfo/";

            baseUrl = baseUrl + start + "/" + end + "/";

            URL url = null;
            HttpURLConnection con= null;
            JSONObject result = null;
            StringBuilder sb = new StringBuilder();


            try {
                url = new URL(baseUrl);
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Content-type", "application/json");
                con.setDoOutput(true);


                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                while(br.ready()) {
                    sb.append(br.readLine());
                }
                con.disconnect();
            }catch(Exception e) {
                e.printStackTrace();
            }
            result = (JSONObject) new JSONParser().parse(sb.toString());

            JSONObject data = (JSONObject) result.get("TbPublicWifiInfo");
            JSONArray array = (JSONArray) data.get("row");

            JSONObject tmp;

            for(int i = 0; i<array.size(); i++) {
                tmp = (JSONObject) array.get(i);
                
                Wifi wifi = new Wifi();
                
                wifi.setX_SWIFI_MGR_NO((String) tmp.get("X_SWIFI_MGR_NO"));
				wifi.setX_SWIFI_WRDOFC((String) tmp.get("X_SWIFI_WRDOFC"));
				wifi.setX_SWIFI_MAIN_NM((String) tmp.get("X_SWIFI_MAIN_NM"));
				wifi.setX_SWIFI_ADRES1((String) tmp.get("X_SWIFI_ADRES1"));
				wifi.setX_SWIFI_ADRES2((String) tmp.get("X_SWIFI_ADRES2"));
				wifi.setX_SWIFI_INSTL_FLOOR((String) tmp.get("X_SWIFI_INSTL_FLOOR"));
				wifi.setX_SWIFI_INSTL_TY((String) tmp.get("X_SWIFI_INSTL_TY"));
				wifi.setX_SWIFI_INSTL_MBY((String) tmp.get("X_SWIFI_INSTL_MBY"));
				wifi.setX_SWIFI_SVC_SE((String) tmp.get("X_SWIFI_SVC_SE"));
				wifi.setX_SWIFI_CMCWR((String) tmp.get("X_SWIFI_CMCWR"));
				wifi.setX_SWIFI_CNSTC_YEAR((String) tmp.get("X_SWIFI_CNSTC_YEAR"));
				wifi.setX_SWIFI_INOUT_DOOR((String) tmp.get("X_SWIFI_INOUT_DOOR"));
				wifi.setX_SWIFI_REMARS3((String) tmp.get("X_SWIFI_REMARS3"));
				wifi.setLAT(Double.parseDouble((String) tmp.get("LNT")));
				wifi.setLNT(Double.parseDouble((String) tmp.get("LAT")));
				wifi.setWORK_DTTM((String) tmp.get("WORK_DTTM"));
                
                wifiDAO.registerWifi(wifi);
                //System.out.println((String) tmp.get("X_SWIFI_MGR_NO")); // 가져오고자 하는 인자를 작성하면 됨.
                total++;
            }
            System.out.println(total);	// 1000개 단위로 잘 실행되고 있는지 콘솔에 진행숫자 표시
        }
        return total;
    }
}