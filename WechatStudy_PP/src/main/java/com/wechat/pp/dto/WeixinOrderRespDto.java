package com.wechat.pp.dto;

import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import lombok.Data;

/**
 * 微信统一下单返回数据表
 * @author ex-songdeshun
 *
 */
@Data
@XStreamAlias("xml")
public class WeixinOrderRespDto {

	/**
	 * 返回信息
	 */
	@XStreamAlias("return_code")
	private String returnCode;
	
	/**
	 * 返回信息
	 */
	@XStreamAlias("return_msg")
	private String returnMsg;
	
	/**
	 * 应用APPID
	 */
	@XStreamAlias("appid")
	private String appId;
	
	/**
	 * 商户号
	 */
	@XStreamAlias("mch_id")
	private String mchId;
	
	/**
	 * 设备号
	 */
	@XStreamAlias("device_info")
	private String deviceInfo;
	
	/**
	 * 业务结果
	 */
	@XStreamAlias("nonce_str")
	private String nonceStr;
	
	/**
	* 签名
	 */
	@XStreamAlias("sign")
	private String sign;
	
	/**
	 * 业务结果
	 */
	@XStreamAlias("result_code")
	private String resultCode;
	
	/**
	 * 错误代码描述
	 */
	@XStreamAlias("err_code_des")
	private String errCodeDes;
	
	/**
	 * 预支付交易会话标识
	 */
	@XStreamAlias("trade_type")
	private String tradeType;
	
	/**
	 * 预支付交易会话标识,有效期2小时
	 */
	@XStreamAlias("prepay_id")
	private String prepayId;
	
	public static void main(String[] args) {
		String xml="<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg><appid><![CDATA[wx2421b1c4370ec43b]]></appid><mch_id><![CDATA[10000100]]></mch_id><nonce_str><![CDATA[IITRi8Iabbblz1Jc]]></nonce_str><sign><![CDATA[7921E432F65EB8ED0CE9755F0E86D72F]]></sign><result_code><![CDATA[SUCCESS]]></result_code><prepay_id><![CDATA[wx201411101639507cbf6ffd8b0779950874]]></prepay_id><trade_type><![CDATA[APP]]></trade_type></xml>";
		XStream xStream=new XStream(new StaxDriver());
		xStream.autodetectAnnotations(true);
		xStream.alias("xml", WeixinOrderRespDto.class);
		WeixinOrderRespDto weixinOrderRespDto=(WeixinOrderRespDto) xStream.fromXML(xml);
		System.out.println(JSONObject.toJSONString(weixinOrderRespDto));
	}
}
