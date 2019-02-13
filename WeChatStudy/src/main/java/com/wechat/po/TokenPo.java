package com.wechat.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="T_TOKEN")
@Data
public class TokenPo {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)  
    @Column (name="ID",length=11)  
	private int id;
	
	@Column (name="ACCESS_TOKEN",length=1024)  
	private String accessToken;
	
	@Column (name="EXPIRES_IN",length=11)  
	private long expiresIn;
	
	@Column (name="CREATE_TIME")  
	private Date createTime;
}
