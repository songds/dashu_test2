package com.wechat.pp.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.wechat.pp.dao.DeviceInfoDao;
import com.wechat.pp.po.DeviceInfoPo;

@Service
public class DeviceInfoService {

	@Resource
	private DeviceInfoDao deviceInfoDao;
	
	/**
	 * 检查用户的token一致性
	 * 参数案例:{userName/用户名,token/客户端token值}
	 * @param json
	 * @return
	 */
	public JSONObject checkTokenUniformity(String json){
		JSONObject result=new JSONObject();
		JSONObject jsonParameter=JSONObject.parseObject(json);
		String userName=jsonParameter.getString("userName");
		String token=jsonParameter.getString("token");
		DeviceInfoPo deviceInfo=deviceInfoDao.isExistByUserNameAndToken(userName, token);
		if(deviceInfo!=null){
			result.put("code", "SUC000");
			result.put("message", "成功");
		}else{
			result.put("code", "F00001");
			result.put("message", "用户token值不一致，有异地登陆!");
		}
		return result;
	}
	
}
