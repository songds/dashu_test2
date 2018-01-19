package com.wechat.pp.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.util.DigestUtils;

import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.wechat.pp.dto.WeixinOrderReqDto;
import com.wechat.pp.dto.WeixinPayDto;

import lombok.extern.slf4j.Slf4j;

/**
 * 生成微信签名工具类
 * @author ex-songdeshun
 *
 */
@Slf4j
public class CreateSignUtil {

	/**
	 * 加工统一下单签名内容
	 * @param weixinOrderReqDto
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static Map<String, Object> processSignContent(WeixinOrderReqDto weixinOrderReqDto) throws UnsupportedEncodingException{
		Map<String, Object> parameter=new LinkedHashMap<String, Object>();
		parameter.put("appid", weixinOrderReqDto.getAppId());
		parameter.put("mch_id", weixinOrderReqDto.getMchId());
		parameter.put("device_info", weixinOrderReqDto.getDeviceInfo());
		parameter.put("nonce_str", weixinOrderReqDto.getNonceStr());
		parameter.put("sign_type", weixinOrderReqDto.getSignType());
		parameter.put("body", weixinOrderReqDto.getBody());
		parameter.put("detail", weixinOrderReqDto.getDetail());
		parameter.put("attach", weixinOrderReqDto.getAttach());
		parameter.put("out_trade_no", weixinOrderReqDto.getOutTradeNo());
		parameter.put("fee_type", weixinOrderReqDto.getFeeType());
		parameter.put("total_fee", weixinOrderReqDto.getTotalFee());
		parameter.put("spbill_create_ip", weixinOrderReqDto.getSpbillCreateIp());
		parameter.put("time_start", weixinOrderReqDto.getTimeStart());
		parameter.put("time_expire", weixinOrderReqDto.getTimeExpire());
		parameter.put("goods_tag", weixinOrderReqDto.getGoodsTag());
		parameter.put("notify_url", weixinOrderReqDto.getNotifyUrl());
		parameter.put("trade_type", weixinOrderReqDto.getTradeType());
		parameter.put("limit_pay", weixinOrderReqDto.getLimitPay());
		parameter.put("scene_info", weixinOrderReqDto.getSceneInfo());
		return parameter;
	}
	
	/**
	 * 加工支付签名内容
	 * @param weixinOrderReqDto
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static Map<String, Object> processSignContent(WeixinPayDto weixinPayDto) throws UnsupportedEncodingException{
		Map<String, Object> parameter=new LinkedHashMap<String, Object>();
		parameter.put("appid", weixinPayDto.getAppid());
		parameter.put("partnerid", weixinPayDto.getPartnerid());
		parameter.put("prepayid", weixinPayDto.getPrepayid());
		parameter.put("package", weixinPayDto.getPackages());
		parameter.put("noncestr", weixinPayDto.getNoncestr());
		parameter.put("timestamp", weixinPayDto.getTimestamp());
		return parameter;
	}
	
	public static String createSign(Map<String, Object> parameter,String key) throws UnsupportedEncodingException {
		/*Set<Map.Entry<String, Object>> se= parameter.entrySet();
		Iterator<Map.Entry<String, Object>> it=se.iterator();
		StringBuffer sb=new StringBuffer();
		while (it.hasNext()) {
			 Map.Entry<String, Object> entry = it.next();  
	        String k =  entry.getKey().toString();  
	        String v =  entry.getValue()!=null?entry.getValue().toString():null;
	        //为空不参与签名、参数名区分大小写  
	        if (null != v && !"".equals(v) && !"sign".equals(k)  
	                && !"key".equals(k)) {  
	            sb.append(k + "=" + v + "&");  
	        }  
		}*/
		Collection<String> keySet=parameter.keySet();
		List<String> parameterKeys=new ArrayList<String>(keySet);
		Collections.sort(parameterKeys);
		StringBuffer sb=new StringBuffer();
		for (String k : parameterKeys) {
		        String v =  parameter.get(k)!=null? parameter.get(k).toString():null;
		        //为空不参与签名、参数名区分大小写  
		        if (null != v && !"".equals(v) && !"sign".equals(k)  
		                && !"key".equals(k)) {  
		            sb.append(k + "=" + v + "&");  
		        }  
		}
			
		sb.append("key="+key);
		log.info(" weixin sign is parameter message : {}",sb.toString());
		//System.out.println(DigestUtils.md5DigestAsHex(DigestUtils.md5Digest(sb.toString().getBytes("UTF-8"))));
		String sign=DigestUtils.md5DigestAsHex(sb.toString().getBytes("UTF-8")).toUpperCase();
		return sign;
	};
	/*public static void main(String[] args) throws UnsupportedEncodingException {
		String xml="<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg><appid><![CDATA[wx2421b1c4370ec43b]]></appid><mch_id><![CDATA[10000100]]></mch_id><nonce_str><![CDATA[IITRi8Iabbblz1Jc]]></nonce_str><sign><![CDATA[7921E432F65EB8ED0CE9755F0E86D72F]]></sign><result_code><![CDATA[SUCCESS]]></result_code><prepay_id><![CDATA[wx201411101639507cbf6ffd8b0779950874]]></prepay_id><trade_type><![CDATA[APP]]></trade_type></xml>";
		XStream xStream=new XStream(new StaxDriver());
		xStream.autodetectAnnotations(true);
		xStream.alias("xml", WeixinOrderReqDto.class);
		WeixinOrderReqDto weixinOrderReqDto=(WeixinOrderReqDto) xStream.fromXML(xml);
		System.out.println(JSONObject.toJSONString(weixinOrderReqDto));
		System.out.println(createSign(CreateSignUtil.processSignContent(weixinOrderReqDto),"sssss"));
	}*/
}
