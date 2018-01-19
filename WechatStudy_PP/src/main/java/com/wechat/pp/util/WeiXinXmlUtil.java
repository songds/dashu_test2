package com.wechat.pp.util;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.wechat.pp.dto.WeixinOrderReqDto;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @author Administrator
 * 解析xml工具类
 */
@Slf4j
public class WeiXinXmlUtil {
	
	/**
	 * 对象转xml字符串
	 * @param weixinOrderDto
	 * @return
	 */
	public static String ObjectToXml(WeixinOrderReqDto weixinOrderReqDto){
		XmlFriendlyReplacer xmlFriendlyReplacer=new XmlFriendlyReplacer("-_", "_");
		StaxDriver staxDriver=new StaxDriver(xmlFriendlyReplacer);
		XStream xStream=new XStream(staxDriver);
		xStream.autodetectAnnotations(true);
		String xml=xStream.toXML(weixinOrderReqDto).replace("<?xml version=\"1.0\" ?>", "");
		log.info("createXml to  result message {} ",xml);
		return xml;
	}
	
	/**
	 * xml格式字符串转对象
	 * @param xml
	 * @return
	 
	public WeixinOrderRespDto XmlStrToObject(String xml){
		XStream xStream=new XStream(new StaxDriver());
		xStream.autodetectAnnotations(true);
		xStream.alias("xml", WeixinOrderRespDto.class);
		WeixinOrderRespDto weixinOrderRespDto=(WeixinOrderRespDto) xStream.fromXML("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg><appid><![CDATA[wx2421b1c4370ec43b]]></appid><mch_id><![CDATA[10000100]]></mch_id><nonce_str><![CDATA[IITRi8Iabbblz1Jc]]></nonce_str><sign><![CDATA[7921E432F65EB8ED0CE9755F0E86D72F]]></sign><result_code><![CDATA[SUCCESS]]></result_code><prepay_id><![CDATA[wx201411101639507cbf6ffd8b0779950874]]></prepay_id><trade_type><![CDATA[APP]]></trade_type></xml>");
		return weixinOrderRespDto;
	}*/
	
	/**
     * 解析微信发来的请求（XML）
     * 
     * @param request
     * @return Map<String, String>
     * @throws Exception
     */
    //@SuppressWarnings("unchecked")
	public static Map<String, String> parseXML(InputStreamReader inputStream) throws Exception {
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<String, String>();

        // 从request中取得输入流
      //  InputStream inputStream = request.getInputStream();
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();
        
        recursiveParseXML(root,map);

        inputStream.close();
        inputStream = null;

        return map;
    }
	
	private static void recursiveParseXML(Element root,Map<String, String> map){
        // 得到根元素的所有子节点
        @SuppressWarnings("unchecked")
		List<Element> elementList = root.elements();
        //判断有没有子元素列表
        if(elementList.size() == 0){
            map.put(root.getName(), root.getText());
        }else{
            //遍历
            for (Element e : elementList){
                recursiveParseXML(e,map);
            }
        }
    }
}
