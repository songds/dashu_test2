package com.wechat.pp.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="ENUM_INFO")
@Data
public class EnumInfoPo {

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
	
	@Column (name="CREATED_DATE")
	private Date createdDate;
	
	@Column (name="UPDATED_DATE")
	private Date updatedDate;
	
	@Column (name="CREATED_BY")
	private String createdBy;
	
	@Column (name="UPDATED_BY")
	private String updatedBy;
	
}
