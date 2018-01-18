package com.wechat.pp.service;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.wechat.pp.dao.PhotoSheetInfoDao;
import com.wechat.pp.po.PhotoSheetInfoPo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PhotoSheetInfoService {

	@Resource
	private PhotoSheetInfoDao photoSheetInfoDao;
	/**
	 * 记录用户拍照信息
	 * @param json
	 * 
	 * @return
	 */
	public JSONObject savePhotoInfo(String userName){
		JSONObject result=new JSONObject();
		try{
			PhotoSheetInfoPo photoSheetInfoPo=new PhotoSheetInfoPo();
			photoSheetInfoPo.setPhotoNumber(1);
			photoSheetInfoPo.setPhotoStatus("0");
			photoSheetInfoPo.setUserName(userName);
			photoSheetInfoDao.save(photoSheetInfoPo);
			result.put("code", "SUC000");
			result.put("message", "拍照记录成功！");
		} catch (Exception e) {
			// TODO: handle exception
			log.info(e.getMessage());
			result.put("code", "F00001");
			result.put("message", "拍照异常，请您联系客服！");
		}
		
		return result;
	}
	/**
	 * 统计用户当天拍照次数
	 * @param userName
	 * @return
	 */
	public JSONObject countUserNamePhotoNumber(String userName){
		JSONObject result=new JSONObject();
		try{
			int photoNumber=photoSheetInfoDao.countPhotoByUserName(userName);
			result.put("code", "SUC000");
			result.put("message", "拍照次数查询成功！");
			result.put("photoNumber", photoNumber);
		} catch (Exception e) {
			// TODO: handle exception
			log.info(e.getMessage());
			result.put("code", "F00001");
			result.put("message", "拍照次数查询异常，请您联系客服！");
		}
		return result;
	}
	
	
}
