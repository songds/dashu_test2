package com.wechat.pp.util;

import java.util.Random;



public class RandomUtil {

	/**
	 * @param dataRange 随机数范围
	 * @param length 验证码长度
	 * @return
	 */
	public static String createRandom(String dataRange,int length){
		if(length==0){
			length=4;
		}
		StringBuffer sb=new StringBuffer("");
		for (int i = 0; i < length; i++) {
			char random=dataRange.charAt(new Random().nextInt(dataRange.length()));
			sb.append(random+"");
		}
		return sb.toString();
	}
}
