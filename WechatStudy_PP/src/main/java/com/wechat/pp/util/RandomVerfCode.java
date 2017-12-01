package com.wechat.pp.util;

import java.util.Random;

import com.alibaba.fastjson.JSONObject;

/**
 * 随机生成验证
 * @author ex-songdeshun
 *
 */
public class RandomVerfCode {

	/**
	 * @param dataRange 随机数范围
	 * @param length 验证码长度
	 * @return
	 */
	public static String createVerfCode(String dataRange,int length){
		JSONObject info=new JSONObject();
		if(length==0){
			length=4;
		}
		StringBuffer sb=new StringBuffer("");
		for (int i = 0; i < length; i++) {
			char random=dataRange.charAt(new Random().nextInt(dataRange.length()));
			if(sb.indexOf(random+"")>=0){
				i--;
			}else{
				sb.append(random+"");
			}
		}
		info.put("code", "SUC000");
		info.put("message", "验证码生成成功");
		info.put("verfCode", sb.toString());
		return JSONObject.toJSONString(info);
	}
}
