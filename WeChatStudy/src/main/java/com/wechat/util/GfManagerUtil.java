package com.wechat.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GfManagerUtil {

	@Value("${application.system.manager.url}")
	private String manager_url;
	
	/**
	 * 关注时调用
	 * @param json
	 * @return
	 */
	public JSONObject wxAttention(String json) {
		try {
			String requestUrl=manager_url+"/api/user/wxAttention.do";
			if(manager_url.startsWith("https")){
				JSONObject result=HttpClientUtils.httpRequest(requestUrl, "POST", json,"application/json; charset=utf-8");
				log.info(" result : {} ",JSONObject.toJSONString(result));
				return result;
			}else{
				String resultStr=HttpClientUtil.httpPostJson(requestUrl, json);
				log.info(" result : {} ",resultStr);
				JSONObject result=JSONObject.parseObject(resultStr);
				return result;
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error(" exception message : {} ",e);
			JSONObject result=new JSONObject();
			result.put("code", "F00001");
			result.put("message", "处理异常");
			return result;
		}
		
		
	}
	
	/**
	 * 取消时关注
	 * @param json
	 * @return
	 */
	public JSONObject wxCancelAttention(String json){
		try {
			String requestUrl=manager_url+"/api/user/wxCancelAttention.do";
			if(manager_url.startsWith("https")){
				JSONObject result=HttpClientUtils.httpRequest(requestUrl, "POST", json,"application/json; charset=utf-8");
				log.info(" result : {} ",JSONObject.toJSONString(result));
				return result;
			}else{
				String resultStr=HttpClientUtil.httpPostJson(requestUrl, json);
				log.info(" result : {} ",resultStr);
				JSONObject result=JSONObject.parseObject(resultStr);
				return result;
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error(" exception message : {} ",e);
			JSONObject result=new JSONObject();
			result.put("code", "F00001");
			result.put("message", "处理异常");
			return result;
		}
	}
	
	public JSONObject scanQrcode(String json){
		try {
			String requestUrl=manager_url+"/api/message/getWxCodeMessage.do";
			if(manager_url.startsWith("https")){
				JSONObject result=HttpClientUtils.httpRequest(requestUrl, "POST", json,"application/json; charset=utf-8");
				log.info(" result : {} ",JSONObject.toJSONString(result));
				return result;
			}else{
				String resultStr=HttpClientUtil.httpPostJson(requestUrl, json);
				log.info(" result : {} ",resultStr);
				JSONObject result=JSONObject.parseObject(resultStr);
				return result;
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error(" exception message : {} ",e);
			JSONObject result=new JSONObject();
			result.put("code", "F00001");
			result.put("message", "处理异常");
			return result;
		}
	}
}
