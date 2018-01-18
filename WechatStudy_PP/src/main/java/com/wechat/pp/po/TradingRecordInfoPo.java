package com.wechat.pp.po;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="TRADING_RECORD_INFO")
@Data
@EqualsAndHashCode(callSuper=false)
public class TradingRecordInfoPo extends BasePo{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)  
    @Column (name="ID",length=11) 
	private int id;
	
	@Column (name="USER_NAME")
	private String userName;
	
	@Column (name="TRADING_ID")
	private String tradingId;
	
	@Column (name="TRADING_TYPE")
	private String tradingType;
	
	@Column (name="TRADING_STATUS")
	private String tradingStatus;
	
	@Column (name="TRADING_TIME")
	private String tradingTime;
	
	@Column(name="TRADING_AMT")
	private int tradingAmt;
	
	@Column(name="TRADING_DESC")
	private String tradingDesc;
	
	
}
