package com.wechat.pp.po;

import java.util.Date;

import javax.persistence.Column;


public class BasePo {

	@Column (name="CREATED_DATE")
	private Date createdDate;
	
	@Column (name="UPDATED_DATE")
	private Date updatedDate;
	
	@Column (name="CREATED_BY")
	private String createdBy;
	
	@Column (name="UPDATED_BY")
	private String updatedBy;


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
