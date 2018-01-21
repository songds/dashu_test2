package com.wechat.pp.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

/**
 * http请求工具类
 * @author Administrator
 *
 */
@Slf4j
public class HttpClientUtil {

	public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr){
		JSONObject result=new JSONObject();
		try {  
            
            URL url = new URL(requestUrl);  
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();  
            conn.setConnectTimeout(30 * 1000);
            conn.setDoOutput(true);  
            conn.setDoInput(true);  
            conn.setUseCaches(false);  
            // 设置请求方式（GET/POST）  
            conn.setRequestMethod(requestMethod);  
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");  
            // 当outputStr不为null时向输出流写数据  
            if (null != outputStr) {  
                OutputStream outputStream = conn.getOutputStream();  
                // 注意编码格式  
                outputStream.write(outputStr.getBytes("UTF-8"));  
                outputStream.close();  
            }  
            // 从输入流读取返回内容  
            InputStream inputStream = conn.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
            String str = null;  
            StringBuffer buffer = new StringBuffer();  
            while ((str = bufferedReader.readLine()) != null) {  
                buffer.append(str);  
            }  
            // 释放资源  
            bufferedReader.close();  
            inputStreamReader.close();  
            inputStream.close();  
            inputStream = null;  
            conn.disconnect();  
            result.put("code", "SUC000");
            result.put("message", "处理成功");
            result.put("data", buffer.toString());
            return result;  
        } catch (ConnectException ce) {  
            log.error(ce.getMessage());
            result.put("code", "F00001");
            result.put("message", "微信下单异常,连接超时,请您重新下单!");
            return result;  
        } catch (Exception e) {  
        	log.error(e.getMessage());
        	result.put("code", "F00001");
            result.put("message", "微信下单异常,请您联系客服!");
            return result;  
        }  
	}
	
	
	public static JSONObject httpsWeixinRequest(String requestUrl, String requestMethod, String outputStr){
		JSONObject result=new JSONObject();
		try {  
            
            URL url = new URL(requestUrl);  
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();  
            conn.setConnectTimeout(30 * 1000);
            conn.setDoOutput(true);  
            conn.setDoInput(true);  
            conn.setUseCaches(false);  
            // 设置请求方式（GET/POST）  
            conn.setRequestMethod(requestMethod);  
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");  
            // 当outputStr不为null时向输出流写数据  
            if (null != outputStr) {  
                OutputStream outputStream = conn.getOutputStream();  
                // 注意编码格式  
                outputStream.write(outputStr.getBytes("UTF-8"));  
                outputStream.close();  
            }  
            // 从输入流读取返回内容  
            InputStream inputStream = conn.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8"); 
            Map<String, Object> map=WeiXinXmlUtil.parseXML(inputStreamReader);
           /* BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
            String str = null;  
            StringBuffer buffer = new StringBuffer();  
            while ((str = bufferedReader.readLine()) != null) {  
                buffer.append(str);  
            }  
            // 释放资源  
            bufferedReader.close();  */
            inputStreamReader.close();  
            inputStream.close();  
            inputStream = null;  
            conn.disconnect();  
            result.put("code", "SUC000");
            result.put("message", "处理成功");
            result.put("data", map);
            return result;  
        } catch (ConnectException ce) {  
            log.error(ce.getMessage());
            result.put("code", "F00001");
            result.put("message", "微信下单异常,连接超时,请您重新下单!");
            return result;  
        } catch (Exception e) {  
        	log.error(e.getMessage());
        	result.put("code", "F00001");
            result.put("message", "微信下单异常,请您联系客服!");
            return result;  
        }  
	}
	
	
}
