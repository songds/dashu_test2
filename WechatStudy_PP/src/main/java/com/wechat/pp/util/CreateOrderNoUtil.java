package com.wechat.pp.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateOrderNoUtil {

	private static final String DATARANGE="0123456789";
	
	private static final int LENGTH=6; 
	
	public static String createOrderNo(){
		Date date=new Date(System.currentTimeMillis());
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String time=sdf.format(date);
		String randomStr=RandomUtil.createRandom(DATARANGE, LENGTH);
		String nostr="Q"+time+randomStr;
		return nostr;
	}
	public static void main(String[] args) {
		System.out.println(System.currentTimeMillis()/1000);
	}
	
}
