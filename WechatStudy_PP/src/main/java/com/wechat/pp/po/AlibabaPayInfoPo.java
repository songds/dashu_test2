package com.wechat.pp.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 支付宝支付类
 * @author songds
 *
 */
@Entity
@Table(name="ALIBABA_PAY_INFO")
@EqualsAndHashCode(callSuper=false)
@Data
public class AlibabaPayInfoPo extends BasePo{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)  
    @Column (name="ID") 
	private int id;
	
	@Column (name="TOTAL_AMOUNT")
	private float totalAmount;
	
	@Column (name="SUBJECT")
	private String subject;
	
	@Column (name="BODY")
	private String body;
	
	@Column (name="TRADE_STATUS")
	private String tradeStatus;
	
	@Column (name="TIMEOUT_EXPRESS")
	private String timeoutExpress;
	
	@Column (name="OUT_TRADE_NO")
	private String outTradeNo;
	
	@Column (name="attach")
	private String attach;
	
}
