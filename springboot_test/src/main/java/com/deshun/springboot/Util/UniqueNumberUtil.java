package com.deshun.springboot.Util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.Enumeration;


import lombok.extern.slf4j.Slf4j;

/**
 * 唯一编号生成器
 * @author linhaiquan
 *
 */
@Slf4j
public class UniqueNumberUtil {
	
	private static final Object lock = new Object();
	private static final String ipString;
	
	private static long sequence = 1L;
	private static long sequenceBits = 13L; //序列号4位长度
	private static long sequenceMask = -1L ^ (-1L << sequenceBits); //8191
	private static long lastTimestamp = -1L;
	static{
		String ip= UniqueNumberUtil.getLocalHost();
		String[] ipStrings= ip.split("\\.");
		ipString=String.format("%03d",Integer.valueOf(ipStrings[2]) )+String.format(String.format("%03d",Integer.valueOf(ipStrings[3])));
	}
	static volatile int  num=100;
	
	/** 
	 * @Title: createUniqueNumString 
	 * @Description: 生成一个 23位纯数字的唯一编号
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String createUniqueNumString(){
		synchronized (lock) {
			long timestamp = System.currentTimeMillis(); //获取当前毫秒数
			if (timestamp < lastTimestamp) {
		        throw new RuntimeException(String.format(
		                "Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
		    }
			//如果上次生成时间和当前时间相同,在同一毫秒内
		    if (lastTimestamp == timestamp) {
		        //sequence自增，因为sequence只有12bit，所以和sequenceMask相与一下，去掉高位
		        sequence = (sequence + 1) & sequenceMask;
		        //判断是否溢出,也就是每毫秒内超过8191，当为8191时，与sequenceMask相与，sequence就等于0
		        if (sequence == 0L) {
		        	sequence = 1L;
		            //自旋等待到下一毫秒
		        	timestamp = System.currentTimeMillis();
		        	while (timestamp <= lastTimestamp) {
		        		timestamp = System.currentTimeMillis();
		    	    }
		        	lastTimestamp = System.currentTimeMillis();
		        	timestamp = lastTimestamp;
		        }
		        log.debug("生成唯一编号走的是同一时间的分支");
		    } else {
		        sequence = 1L; //如果和上次生成时间不同,重置sequence，就是下一毫秒开始，sequence计数重新从1开始累加
		        lastTimestamp = System.currentTimeMillis();
		        timestamp=lastTimestamp;
		    }
		    String suffix = String.format("%04d", sequence);
		    //String datePrefix = DateFormatUtils.format(timestamp, "yyyyMMddHHMMssSSS");
		    String datePrefix = String.format("%013d", timestamp);
		    StringBuffer sb=new StringBuffer(datePrefix);
		    sb.append(ipString);
		    sb.append(suffix);
		    return sb.toString();
		}
	}
	
	
	/** 
	 * @Title: createUniqueNumString 
	 * @Description: 根据截取生成一个 位纯数字的唯一编号 默认生成23位
	 * @return 参数说明
	 * @return String    返回类型
	 * @param cutStart 截取开始位数
	 */
	public static String createUniqueNumString(int cutStart){
		synchronized (lock) {
			long timestamp = System.currentTimeMillis(); //获取当前毫秒数
			if (timestamp < lastTimestamp) {
		        throw new RuntimeException(String.format(
		                "Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
		    }
			//如果上次生成时间和当前时间相同,在同一毫秒内
		    if (lastTimestamp == timestamp) {
		        //sequence自增，因为sequence只有12bit，所以和sequenceMask相与一下，去掉高位
		        sequence = (sequence + 1) & sequenceMask;
		        //判断是否溢出,也就是每毫秒内超过8191，当为8191时，与sequenceMask相与，sequence就等于0
		        if (sequence == 0L) {
		        	sequence = 1L;
		            //自旋等待到下一毫秒
		        	timestamp = System.currentTimeMillis();
		        	while (timestamp <= lastTimestamp) {
		        		timestamp = System.currentTimeMillis();
		    	    }
		        	lastTimestamp = System.currentTimeMillis();
		        	timestamp = lastTimestamp;
		        }
		        log.debug("生成唯一编号走的是同一时间的分支");
		    } else {
		        sequence = 1L; //如果和上次生成时间不同,重置sequence，就是下一毫秒开始，sequence计数重新从1开始累加
		        lastTimestamp = System.currentTimeMillis();
		        timestamp=lastTimestamp;
		    }
		    String suffix = String.format("%04d", sequence);
		   // System.out.println("suffix=="+suffix);
		    //String datePrefix = DateFormatUtils.format(timestamp, "yyyyMMddHHMMssSSS");
		    //String datePrefix = String.format("%013d", timestamp);
		    String timestr=String.valueOf(timestamp);
		    String datePrefix = (timestr.length()<cutStart||cutStart<0)?timestr.substring(0):timestr.substring(cutStart);
		    //System.out.println(datePrefix.length());
		    StringBuffer sb=new StringBuffer(datePrefix);
		    sb.append(ipString);
		    sb.append(suffix);
		    return sb.toString();
		}
	}
	
	
	/**
	 * 并发都不高的生成唯一值的方法  20位
	 * @return
	 */
	public static String getNumString(){
		UniqueNumberUtil.num+=1;
		long now = System.currentTimeMillis();  
		//获取4位年份数字  
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy");  
		//获取时间戳  
		String time=dateFormat.format(now);
		String info=now+"";
		//获取三位随机数  
		//int ran=(int) ((Math.random()*9+1)*100); 
		//要是一段时间内的数据连过大会有重复的情况，所以做以下修改
		int ran=0;
		if(UniqueNumberUtil.num>999){
			UniqueNumberUtil.num=100;    	
		}
		ran=UniqueNumberUtil.num;		
		//return time+info.substring(2, info.length())+ran;
		return time+info+ran;
	}	
	
	
	
	
	
	
	
	/** 
	 * @Title: getLocalHost 
	 * @Description: 获取Ip地址 
	 * @return 参数说明
	 * @return String    返回类型
	 */
	private static String getLocalHost() {
		String sIP = null;
		InetAddress ip = null;
		try {
			// 如果是Windows操作系统
			if (isWindowsOS()) {
				ip = InetAddress.getLocalHost();
			} else {
				// 如果是Linux操作系统
				boolean bFindIP = false;
				Enumeration<NetworkInterface> netInterfaces = (Enumeration<NetworkInterface>) NetworkInterface
						.getNetworkInterfaces();
				while (netInterfaces.hasMoreElements()) {
					if (bFindIP) {
						break;
					}
					NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
					// ----------特定情况，可以考虑用ni.getName判断
					// 遍历所有ip
					Enumeration<InetAddress> ips = ni.getInetAddresses();
					while (ips.hasMoreElements()) {
						ip = (InetAddress) ips.nextElement();
						if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() // 127.开头的都是lookback地址
								&& ip.getHostAddress().indexOf(":") == -1) {
							bFindIP = true;
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		if (null != ip) {
			sIP = ip.getHostAddress();
		}
		return sIP;
	}
	
	/** 
	 * @Title: isWindowsOS 
	 * @Description: 判断是否是windows操作系统 
	 * @return 参数说明
	 * @return boolean    返回类型
	 */
	private static boolean isWindowsOS() {
		boolean isWindowsOS = false;
		String osName = System.getProperty("os.name");
		if (osName.toLowerCase().indexOf("windows") > -1) {
			isWindowsOS = true;
		}
		return isWindowsOS;
	}
}
