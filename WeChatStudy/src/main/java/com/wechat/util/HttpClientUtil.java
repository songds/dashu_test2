package com.wechat.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ex-liuzhen 基于Apache HttpClient请求
 */
@Slf4j
public class HttpClientUtil {
	// 申明http请求
	

	/**
	 * 发送http Post请求
	 * 
	 * @param url
	 *            请求地址
	 * @param parmar
	 *            Map 参数
	 * @return
	 * @throws BusinessException
	 */
	public static String httpPost(String url, Map<String, String> parmar) throws Exception {
		// 创建实例
		log.info("begin Sent Http Post Request,url={},paramt={}", url, parmar);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		// 把参数封装起来
		List<NameValuePair> formarms = new ArrayList<>();
		Iterator<String> it = parmar.keySet().iterator();
		String key = null;
		while (it.hasNext()) {
			key = it.next();
			try {
				formarms.add(new BasicNameValuePair(key, parmar.get(key) + ""));

			} catch (Exception e) {
			}
		}
		UrlEncodedFormEntity urlEntity;
		String msg = null;
		try {
			// 发起请求
			httpclient = HttpClients.createDefault();
			urlEntity = new UrlEncodedFormEntity(formarms, "UTF-8");
			httpPost.setEntity(urlEntity);
			CloseableHttpResponse response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				// 得到结果
				msg = EntityUtils.toString(entity, "UTF-8");
			}
		} catch (Exception e) {
			log.error("Http Post Send Error,Msg={}", e.getMessage());
			throw new Exception(e.getMessage());
		} finally {
			try {
				// 释放资源
				httpclient.close();
			} catch (IOException e) {
				log.error("Http Post Close Error,Msg={}", e.getMessage());
				throw new Exception(e.getMessage());
			}
		}
		return msg;
	}

	/**
	 * 发送http Post请求
	 * 
	 * @param url
	 *            请求地址
	 * @param parmar
	 *            Map 参数
	 * @return
	 * @throws BusinessException
	 */
	public static String httpPostJson(String url, String parameters) throws Exception {
		// 创建实例
		log.info("begin Sent Http Post Request,url={},paramt={}", url, parameters);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String body = null;
		httpclient = HttpClients.createDefault();
		HttpPost method = new HttpPost(url);
		if (method != null & parameters != null && !"".equals(parameters.trim())) {
			try {
				// 建立一个NameValuePair数组，用于存储欲传送的参数
				method.addHeader("Content-type", "application/json; charset=utf-8");
				method.setHeader("Accept", "application/json");
				method.setEntity(new StringEntity(parameters, Charset.forName("UTF-8")));

				HttpResponse response = httpclient.execute(method);

				HttpEntity entity = response.getEntity();
				if (entity != null) {
					// 得到结果
					body = EntityUtils.toString(entity, "UTF-8");
				}

			} catch (IOException e) {
				log.info("request is Error,Msg ={}",e.getMessage());
			} catch (Exception e) {
				log.info("request is Error,Msg ={}",e.getMessage());
			}
			log.info("end Sent Http Post Request,url={},result={}", url, parameters);
		}
		return body;
	}

	public static String httpPost(String url, JSONObject jsonParam) throws Exception {
		// 创建实例
		log.info("begin Sent Http Post Request,url={},paramt={}", url, jsonParam);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		String msg = null;
		try {
			// 发起请求
			httpclient = HttpClients.createDefault();
			StringEntity strEntiry = new StringEntity(jsonParam.toString(), "utf-8");// 解决中文乱码问题
			strEntiry.setContentEncoding("UTF-8");
			strEntiry.setContentType("application/json");
			httpPost.setEntity(strEntiry);
			CloseableHttpResponse response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				// 得到结果
				msg = EntityUtils.toString(entity, "UTF-8");
			}
		} catch (Exception e) {
			log.error("Http Post Send Error,Msg={}", e.getMessage());
			throw new Exception(e.getMessage());
		} finally {
			try {
				// 释放资源
				httpclient.close();
			} catch (IOException e) {
				log.error("Http Post Close Error,Msg={}", e.getMessage());
				throw new Exception(e.getMessage());
			}
		}
		return msg;
	}

	/**
	 * Http Get请求
	 * 
	 * @param url
	 *            请求路径
	 * @param paramr
	 *            map 参数
	 * @return String json 返回值
	 * @throws BusinessException
	 */
	public static String httpGet(String url) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		//ResponseInfo<String> result = new ResponseInfo<>("00", "ok", null);
		log.info("begin Sent Http Get Request,url={}", url);
		HttpGet httpGet = new HttpGet(url);
		String msg = null;
		httpclient = HttpClients.createDefault();
		try {
			CloseableHttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				msg = EntityUtils.toString(entity);
				//result.setData(msg);
			}
			log.info("end Http Get Request,result={}", entity);
		} catch (IOException e) {
			throw new Exception("初始化get方法IO异常," + e.getMessage());

		}
		return msg;
	}

	public static String post(String parameterData, String url) throws IOException {
		URL localURL = new URL(url);
		URLConnection connection = localURL.openConnection();
		HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

		httpURLConnection.setDoOutput(true);
		httpURLConnection.setRequestMethod("POST");
		httpURLConnection.setRequestProperty("application/json", "GBK");
		httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		httpURLConnection.setRequestProperty("Content-Length", String.valueOf(parameterData.length()));

		OutputStream outputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;

		outputStream = httpURLConnection.getOutputStream();
		outputStreamWriter = new OutputStreamWriter(outputStream);

		outputStreamWriter.write(parameterData.toString());
		outputStreamWriter.flush();

		inputStream = httpURLConnection.getInputStream();
		inputStreamReader = new InputStreamReader(inputStream);
		reader = new BufferedReader(inputStreamReader);

		while ((tempLine = reader.readLine()) != null) {
			resultBuffer.append(java.net.URLDecoder.decode(tempLine, "UTF-8"));
		}

		return resultBuffer.toString();
	}

	/**
	 * 格式化一些参数，将map形式的参数做成get请求参数的格式
	 * @param map
	 * @return
	 */
	public static String formatParams(Map<String, String> map){
		String temp = map.toString();
		temp = temp.substring(1, temp.length()-1);
		return temp.replace(", ", "&");
	}
	
	/**
	 * 调用接口
	 * @param uri  uri请注意补齐要调的接口，如方式短信：ST_URI+"/api/sendAuthCode.do"
	 * @param map  入参
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static JSONObject checkIV(String uri,Map<String, String> map) throws ClientProtocolException, IOException{
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(uri+"?"+formatParams(map));
		HttpResponse reponse = client.execute(get);
		return JSONObject.parseObject(EntityUtils.toString(reponse.getEntity()));
	}
	
	
	
	
}
