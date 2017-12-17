package com.wechat.pp.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.Data;

@Data
@XStreamAlias("xml")
public class WeixinPayDto {

	/**
	 * 应用ID
	 */
	@XStreamAlias("appid")
	private String appid;
	
	/**
	 * 商户号
	 */
	@XStreamAlias("partnerid")
	private String partnerid;
	
	/**
	 * 预支付交易会话ID
	 */
	@XStreamAlias("prepayid")
	private String prepayid;
	
	/**
	 *扩展字段
	 */
	@XStreamAlias("package")
	private String packages="Sign=WXPay";
	
	/**
	 *随机字符串
	 */
	@XStreamAlias("noncestr")
	private String noncestr;
	
	/**
	 * 时间戳
	 */
	@XStreamAlias("timestamp")
	private String timestamp;
	
	/**
	 * 签名
	 */
	@XStreamAlias("sign")
	private String sign;
	
}
