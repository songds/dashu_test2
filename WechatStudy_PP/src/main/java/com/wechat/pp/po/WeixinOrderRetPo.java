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
@Table(name="WEIXIN_ORDER_RET")
@EqualsAndHashCode(callSuper=false)
@Data
public class WeixinOrderRetPo extends BasePo{
	
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
	 * 返回状态码
	 */
	@Column (name="RETURN_CODE") 
	private String returnCode;
	
	/**
	 * 返回信息
	 */
	@Column (name="RETURN_MSG") 
	private String returnMsg;
	
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
	 * 设备号
	 */
	@Column (name="DEVICE_INFO") 
	private String deviceInfo;
	
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
	 * 业务结果
	 */
	@Column (name="RESULT_CODE") 
	private String resultCode;
	
	/**
	 * 错误代码
	 */
	@Column (name="ERR_CODE") 
	private String errCode;
	
	/**
	 * 错误代码描述
	 */
	@Column (name="ERR_CODE_DES") 
	private String errCodeDes;
	
	/**
	 * 交易类型
	 */
	@Column (name="TRADE_TYPE") 
	private String tradeType;
	
	/**
	 * 预支付交易会话标识
	 */
	@Column (name="PREPAY_ID") 
	private String prepayId;
	
	/**
	 * 预支付交易会话过期时间
	 */
	@Column (name="EXPIRATION_TIME") 
	private Date expirationTime;
	
	
	
}
