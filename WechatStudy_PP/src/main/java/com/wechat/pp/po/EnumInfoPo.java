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
@Table(name="ENUM_INFO")
@Data
@EqualsAndHashCode(callSuper=false)
public class EnumInfoPo extends BasePo{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)  
    @Column (name="ID",length=11) 
	private int id;
	
	@Column (name="CODE_TYPE") 
	private String codeType;
	
	@Column (name="CODE_DESC") 
	private String codeDesc;
	
	@Column (name="CODE_ID")
	private String codeId;

	@Column (name="CODE_NAME")
	private String codeName;
	
	
}
