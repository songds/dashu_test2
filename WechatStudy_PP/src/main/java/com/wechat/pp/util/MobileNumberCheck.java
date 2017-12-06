package com.wechat.pp.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.alibaba.fastjson.JSONObject;

/**
 * 手机号码校验
 * @author ex-songdeshun
 *
 */
public class MobileNumberCheck {

	public static JSONObject check(String mobileNumber){
		JSONObject result=new JSONObject();
		String reg="^1[3|4|5|7|8][0-9]\\d{8}$";
		// 编译正则表达式
	    Pattern pattern = Pattern.compile(reg);
	    // 忽略大小写的写法
	    // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(mobileNumber);
	    // 字符串是否与正则表达式相匹配
	    boolean rs = matcher.matches();
		if(rs){
			result.put("code", "SUC000");
			result.put("message", "手机号码校验成功!");
			return result;
		}else{
			result.put("code", "F00006");
			result.put("message", "手机号码校验不合法,请您重新输入!");
			return result;
		}
		
	} 
}
