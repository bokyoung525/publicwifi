package DbSqlite;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import Wifi.Wifi;
import Wifi.WifiDAO;

public class XmlParsing {
    // tag값의 정보를 가져오는 메소드
	private static String getTagValue(String tag, Element eElement) {
	    NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
	    Node nValue = (Node) nlList.item(0);
	    if(nValue == null) 
	        return null;
	    return nValue.getNodeValue();
	}

	private int listTotalCount;
	
	public int getListTotalCount() {
		return listTotalCount;
	}
	
	public void setListTotalCount(int listTotalCount) {
		this.listTotalCount = listTotalCount;
	}

	public static void main(String[] arg) {
		
		try {
			String url = "http://openapi.seoul.go.kr:8088/44434a53636c656538304643574352/xml/TbPublicWifiInfo/1/1/";
			
            // XML 문자열을 파싱하기 위한 DocumentBuilder 생성
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(url);

            // <list_total_count> 요소를 가져오기
            NodeList listTotalCountNodeList = doc.getElementsByTagName("list_total_count");
            Node listTotalCountNode = listTotalCountNodeList.item(0); // 첫 번째 <list_total_count> 요소

            if (listTotalCountNode.getNodeType() == Node.ELEMENT_NODE) {
                Element listTotalCountElement = (Element) listTotalCountNode;
                String listTotalCountValue = listTotalCountElement.getTextContent();
//                setListTotalCount(Integer.parseInt(listTotalCountValue));
            } else {	
                System.out.println("error");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		
		WifiDAO wifiDAO = new WifiDAO();
		
		try{
			for (int i = 1; i <= 23488; i += 1000){
				// parsing할 url 지정(API 키 포함해서)
				String url = "http://openapi.seoul.go.kr:8088/44434a53636c656538304643574352/xml/TbPublicWifiInfo/"
							+ i + "/" + Math.min(i + 999,  23488) + "/";
				
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		        DocumentBuilder builder = factory.newDocumentBuilder();
		        Document doc = builder.parse(url);
				
				doc.getDocumentElement().normalize();
				
				
				// 파싱할 tag
				NodeList nList = doc.getElementsByTagName("row");
				
				for(int temp = 0; temp < nList.getLength(); temp++){
					Node nNode = nList.item(temp);
					if(nNode.getNodeType() == Node.ELEMENT_NODE){
						Element eElement = (Element) nNode;
						
						Wifi wifi = new Wifi();
						
						wifi.setX_SWIFI_MGR_NO(getTagValue("X_SWIFI_MGR_NO", eElement));
						wifi.setX_SWIFI_WRDOFC(getTagValue("X_SWIFI_WRDOFC", eElement));
						wifi.setX_SWIFI_MAIN_NM(getTagValue("X_SWIFI_MAIN_NM", eElement));
						wifi.setX_SWIFI_ADRES1(getTagValue("X_SWIFI_ADRES1", eElement));
						wifi.setX_SWIFI_ADRES2(getTagValue("X_SWIFI_ADRES2", eElement));
						wifi.setX_SWIFI_INSTL_FLOOR(getTagValue("X_SWIFI_INSTL_FLOOR", eElement));
						wifi.setX_SWIFI_INSTL_TY(getTagValue("X_SWIFI_INSTL_TY", eElement));
						wifi.setX_SWIFI_INSTL_MBY(getTagValue("X_SWIFI_INSTL_MBY", eElement));
						wifi.setX_SWIFI_SVC_SE(getTagValue("X_SWIFI_SVC_SE", eElement));
						wifi.setX_SWIFI_CMCWR(getTagValue("X_SWIFI_CMCWR", eElement));
						wifi.setX_SWIFI_CNSTC_YEAR(getTagValue("X_SWIFI_CNSTC_YEAR", eElement));
						wifi.setX_SWIFI_INOUT_DOOR(getTagValue("X_SWIFI_INOUT_DOOR", eElement));
						wifi.setX_SWIFI_REMARS3(getTagValue("X_SWIFI_REMARS3", eElement));
//						wifi.setLAT(getTagValue("LAT", eElement));
//						wifi.setLNT(getTagValue("LNT", eElement));
						wifi.setWORK_DTTM(getTagValue("WORK_DTTM", eElement));
						
						wifiDAO.registerWifi(wifi);
						
					}
				}
			}
			
		} catch (Exception e){	
			e.printStackTrace();
		}	// try~catch end
	}
}	// class end

