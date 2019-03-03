package com.wechat.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.wechat.util.HttpClientUtil;
import com.wechat.util.HttpClientUtils;
import com.wechat.util.WeixinUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MessageSerevice {

	private static final String SEND_MESSAGE_URL="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
	
	@Value("${application.system.manager.url}")
	private String system_manager_url;
	
	@Autowired
	private UserService userService;
	
	public JSONObject sendMessage(String json){
		String accessToken=userService.getAccessToken();
		String requestUrl=SEND_MESSAGE_URL.replace("ACCESS_TOKEN", accessToken);
		JSONObject result=WeixinUtil.httpRequest(requestUrl, "POST", json);
		log.info(" result : {} ",result);
		return result;
	}
	/**
	 * 模板消息回调
	 * @param json
	 */
	public void templateMessageCall(Map<String, String> requestMap){
		log.info(" templateMessageCall --> requestMap : {} ",requestMap);
		String status=requestMap.get("Status");
		String createTime=requestMap.get("CreateTime");
		String msgId=requestMap.get("MsgID");
		JSONObject json=new JSONObject();
		json.put("Status", status);
		json.put("msgId", msgId);
		json.put("createTime", createTime);
		String requestUrl=system_manager_url+"/api/message/wxMessageCall.do";
		if(system_manager_url.startsWith("https")){
			JSONObject result=HttpClientUtils.httpRequest(requestUrl, "POST", JSONObject.toJSONString(json),"application/json; charset=utf-8");
			log.info(" templateMessageCall--> result : {} ",result);
		}else{
			String resultStr=HttpClientUtil.httpPostJson(requestUrl, JSONObject.toJSONString(json));
			log.info(" templateMessageCall--> result : {} ",resultStr);
		}
	}

}
