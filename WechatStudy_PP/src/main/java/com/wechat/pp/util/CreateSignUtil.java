package com.wechat.pp.util;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.util.DigestUtils;

import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.wechat.pp.dto.WeixinOrderRespDto;

/**
 * 生成微信签名工具类
 * @author ex-songdeshun
 *
 */
public class CreateSignUtil {

	/**
	 * 
	 * @param weixinOrderRespDto
	 * @param key 微信秘钥
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static Map<String, String> processSignContent(WeixinOrderRespDto weixinOrderRespDto) throws UnsupportedEncodingException{
		Map<String, String> parameter=new LinkedHashMap<String, String>();
		parameter.put("appid", weixinOrderRespDto.getAppId());
		parameter.put("mch_id", weixinOrderRespDto.getMchId());
		parameter.put("device_info", weixinOrderRespDto.getAppId());
		parameter.put("nonce_str", weixinOrderRespDto.getAppId());
		parameter.put("sign_type", weixinOrderRespDto.getAppId());
		parameter.put("body", weixinOrderRespDto.getAppId());
		parameter.put("detail", weixinOrderRespDto.getAppId());
		parameter.put("attach", weixinOrderRespDto.getAppId());
		parameter.put("out_trade_no", weixinOrderRespDto.getAppId());
		parameter.put("fee_type", weixinOrderRespDto.getAppId());
		parameter.put("total_fee", weixinOrderRespDto.getAppId());
		parameter.put("spbill_create_ip", weixinOrderRespDto.getAppId());
		parameter.put("time_start", weixinOrderRespDto.getAppId());
		parameter.put("time_expire", weixinOrderRespDto.getAppId());
		parameter.put("goods_tag", weixinOrderRespDto.getAppId());
		parameter.put("notify_url", weixinOrderRespDto.getAppId());
		parameter.put("trade_type", weixinOrderRespDto.getAppId());
		parameter.put("limit_pay", weixinOrderRespDto.getAppId());
		parameter.put("scene_info", weixinOrderRespDto.getAppId());
		return parameter;
	}
	
	
	public static String createSign(Map<String, String> parameter,String key) throws UnsupportedEncodingException {
		Set<Map.Entry<String, String>> se= parameter.entrySet();
		Iterator<Map.Entry<String, String>> it=se.iterator();
		StringBuffer sb=new StringBuffer();
		while (it.hasNext()) {
			 Map.Entry<String, String> entry = it.next();  
	        String k = (String) entry.getKey();  
	        String v = (String) entry.getValue();  
	        //为空不参与签名、参数名区分大小写  
	        if (null != v && !"".equals(v) && !"sign".equals(k)  
	                && !"key".equals(k)) {  
	            sb.append(k + "=" + v + "&");  
	        }  
		}
		sb.append("key="+key);
		System.out.println(DigestUtils.md5DigestAsHex(sb.toString().getBytes("UTF-8")));
		String sign=MD5Encoder.encode(sb.toString().getBytes()).toUpperCase();
		return sign;
	};
	public static void main(String[] args) throws UnsupportedEncodingException {
		String xml="<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg><appid><![CDATA[wx2421b1c4370ec43b]]></appid><mch_id><![CDATA[10000100]]></mch_id><nonce_str><![CDATA[IITRi8Iabbblz1Jc]]></nonce_str><sign><![CDATA[7921E432F65EB8ED0CE9755F0E86D72F]]></sign><result_code><![CDATA[SUCCESS]]></result_code><prepay_id><![CDATA[wx201411101639507cbf6ffd8b0779950874]]></prepay_id><trade_type><![CDATA[APP]]></trade_type></xml>";
		XStream xStream=new XStream(new StaxDriver());
		xStream.autodetectAnnotations(true);
		xStream.alias("xml", WeixinOrderRespDto.class);
		WeixinOrderRespDto weixinOrderRespDto=(WeixinOrderRespDto) xStream.fromXML(xml);
		System.out.println(JSONObject.toJSONString(weixinOrderRespDto));
		System.out.println(createSign(CreateSignUtil.processSignContent(weixinOrderRespDto),"sssss"));
	}
}
