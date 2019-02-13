package com.wechat.util;

import org.springframework.beans.factory.annotation.Value;

public class WeixinConf {

	@Value("${application.wechat.official.appSecret}")
	public static String appSecret;
	
	@Value("${application.wechat.official.appId}")
	public static String appId;
	
	@Value("${application.wechat.official.token}")
	public static String token;
	
	
}
