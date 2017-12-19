package com.wechat.pp.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="WEIXIN_PAY_INFO")
@EqualsAndHashCode(callSuper=false)
@Data
public class WeixinPayInfoPo extends BasePo{

	/**
	 * 编号
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)  
    @Column (name="ID",length=11) 
	private int id;
	
	/**
	 * 用户名
	 */
	@Column (name="USER_NAME") 
	private String userName;
	
	
	/**
	 * 应用APPID
	 */
	@Column (name="APPID") 
	private String appid;
	
	/**
	 * 商户号
	 */
	@Column (name="MCH_ID") 
	private String mchId;
	
	/**
	 * 预支付交易会话标识
	 */
	@Column (name="PREPAY_ID") 
	private String prepayId;
	/**
	 * 扩展字段
	 */
	@Column (name="PACKAGE") 
	private String packages;
	
	/**
	 * 随机字符串
	 */
	@Column (name="NONCE_STR") 
	private String nonceStr;
	
	/**
	 * 签名
	 */
	@Column (name="SIGN") 
	private String sign;
	
	/**
	 * 商户订单号
	 */
	@Column (name="OUT_TRADE_NO") 
	private String outTradeNo;
	/**
	 * 时间戳
	 */
	@Column(name="TIMESTAMPS")
	private String timestamps;
	/**
	 * 预支付交易会话过期时间
	 */
	@Column (name="EXPIRATION_TIME") 
	private Date expirationTime;
	
	/**
	 * 交易类型
	 */
	@Column (name="TRADE_TYPE") 
	private String tradeType;
	
	/**
	 * 交易类型
	 */
	@Column (name="PAY_TYPE") 
	private String payType;
	
	@Column(name="TOTAL_FEE")
	private int totalFee;
	
	@Column(name="TRADE_STATUS")
	private String tradeStatus;
	
	@Column(name="BODY")
	private String body;
	
	@Column(name="ATTACH")
	private String attach;
}
