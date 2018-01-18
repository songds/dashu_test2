package com.wechat.pp.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

@MappedSuperclass
@Component
public class BasePo {

	@Column (name="CREATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createdDate;
	
	@Column (name="UPDATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@UpdateTimestamp
	private Date updatedDate;
	
	@Column (name="CREATED_BY")
	private String createdBy;
	
	@Column (name="UPDATED_BY")
	private String updatedBy;
	
	@Transient
	@Value("${spring.application.name}")
	private String systemName;

	public BasePo(){
		if(StringUtils.isEmpty(systemName)){
			setCreatedBy("Q_SYSTEM");
			setUpdatedBy("Q_SYSTEM");
		}else{
			setCreatedBy(systemName);
			setUpdatedBy(systemName);
		}
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		if(updatedDate!=null){
			this.updatedDate = updatedDate;
		}else{
			this.updatedDate = new Date(System.currentTimeMillis());
		}
		
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		if(createdDate!=null){
			this.createdDate = createdDate;
		}else{
			this.createdDate = new Date(System.currentTimeMillis());
		}
	}
	
	
	
	
}
