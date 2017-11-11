package com.wechat.pp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wechat.pp.po.PhotoSheetInfoPo;

public interface PhotoSheetInfoDao extends JpaRepository<PhotoSheetInfoPo, Integer>{
	
	public PhotoSheetInfoPo getPhotoByUserName(String userName);
	
	@Query("select count(1) from PhotoSheetInfoPo where date_format(sysdate(),'%Y-%m-%d')<=createdDate and createdDate<=sysdate() and userName=?1")
	public int countPhotoByUserName(String userName);

}
