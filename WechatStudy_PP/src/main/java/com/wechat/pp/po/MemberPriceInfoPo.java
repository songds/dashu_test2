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
@Table(name="MEMBER_PRICE_INFO")
@EqualsAndHashCode(callSuper=false)
@Data
public class MemberPriceInfoPo extends BasePo{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)  
    @Column (name="ID",length=11) 
	private int id;
	
	@Column (name="MEMBER_ID")
	private int memberId;
	
	@Column (name="CARD_TYPE")
	private String cardType;
	
	@Column (name="CARD_TYPE_DESC")
	private String cardTypeDesc;
	
	@Column (name="CARD_PRICE")
	private double cardPrice;
}
