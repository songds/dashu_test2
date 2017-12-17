package com.wechat.pp.util;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import com.alibaba.fastjson.JSONObject;
import com.wechat.pp.dto.WeixinOrderReqDto;
import com.wechat.pp.dto.WeixinPayDto;

/**
 * 生成微信随机数
 * @author Administrator
 *
 */
public class CreateNonceStrUtil {

	private static final String DATARANGE="abcdefghijklmnopqrstuvwxyz0123456789";
	
	private static final int LENGTH=6;
	/**
	 * 加工统一下随机数内容
	 * @param weixinOrderReqDto
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static Map<String, Object> processNonceStrContent(WeixinOrderReqDto weixinOrderReqDto) throws UnsupportedEncodingException{
		Map<String, Object> parameter=new LinkedHashMap<String, Object>();
		parameter.put("appid", weixinOrderReqDto.getAppId());
		parameter.put("mch_id", weixinOrderReqDto.getMchId());
		parameter.put("device_info", weixinOrderReqDto.getDeviceInfo());
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
	 * 加工支付随机数内容
	 * @param weixinOrderReqDto
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static Map<String, Object> processNonceStrContent(WeixinPayDto weixinPayDto) throws UnsupportedEncodingException{
		Map<String, Object> parameter=new LinkedHashMap<String, Object>();
		parameter.put("appid", weixinPayDto.getAppid());
		parameter.put("partnerid", weixinPayDto.getPartnerid());
		parameter.put("prepayid", weixinPayDto.getPrepayid());
		parameter.put("package", weixinPayDto.getPackages());
		parameter.put("timestamp", weixinPayDto.getTimestamp());
		return parameter;
	}
	/**
	 * 
	 * @param parameter  封装参数
	 * @param key 微信秘钥
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String createNonceStr(Map<String, Object> parameter,String key) throws UnsupportedEncodingException {
		Set<Map.Entry<String, Object>> se= parameter.entrySet();
		Iterator<Map.Entry<String, Object>> it=se.iterator();
		StringBuffer sb=new StringBuffer();
		while (it.hasNext()) {
			 Map.Entry<String, Object> entry = it.next();  
	        String k = entry.getKey().toString();  
	        String v =  entry.getValue()!=null?entry.getValue().toString():null;  
	        //为空不参与签名、参数名区分大小写  
	        if (null != v && !"".equals(v) && !"sign".equals(k)  
	                && !"key".equals(k)) {  
	            sb.append(k + "=" + v + "&");  
	        }  
		}
		
		sb.append("key="+key);
		String nonceStr=RandomVerfCode.createVerfCode(DATARANGE, LENGTH);
		if(!StringUtils.isEmpty(nonceStr)){
			JSONObject nonceStrJson=JSONObject.parseObject(nonceStr);
			if(!StringUtils.isEmpty(nonceStrJson.getString("code"))&&nonceStrJson.getString("code").equals("SUC000")){
				String nonceStrCode=StringUtils.isEmpty(nonceStrJson.getString("verfCode"))?"qsystem":nonceStrJson.getString("verfCode");
				sb.append("&nonceStrCode="+nonceStrCode);
			}else{
				sb.append("&nonceStrCode=qsystem");
			}
		}else{
			sb.append("&nonceStrCode=qsystem");
		}
		String noncestr=DigestUtils.md5DigestAsHex(sb.toString().getBytes("UTF-8")).toUpperCase();
		return noncestr;
	};
}
