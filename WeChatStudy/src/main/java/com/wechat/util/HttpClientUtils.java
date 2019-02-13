package com.wechat.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpClientUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);
	      

	@SuppressWarnings("finally")
	public JSONObject doPost(String url, String parms){
		if ("".equals(url)){
			return null;
		}
		if (url.contains("DSPC") || url.contains("a")){
			HttpClient client = new DefaultHttpClient();
			JSONObject response = null;
			System.out.println("AAAA");
			try {
				HttpPost post = new HttpPost(url);
//				post.setHeader("Content-Type", "application/json;charset=UTF-8");
				StringEntity s = new StringEntity(parms,"utf-8");    
                s.setContentType("application/json");  
                post.setEntity(s);
				HttpResponse res = client.execute(post);
				HttpEntity entity = (HttpEntity) res.getEntity();
				String content = EntityUtils.toString(entity, "UTF-8");
				logger.info(content);
				response = JSONObject.parseObject(content);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			} finally{
				logger.info("请求参数："+parms.toString());
				logger.info("响应参数："+response.toString());
				return response;
			}
		}else{
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);
			StringBuffer json = new StringBuffer();
			JSONObject response = null;
			String line = null;
			try {
				post.setEntity(new StringEntity(parms, "UTF-8"));
				HttpResponse res = client.execute(post);
				HttpEntity entity = res.getEntity();
				InputStream instream = entity.getContent();   
				BufferedReader reader = new BufferedReader(new InputStreamReader(instream, "UTF-8"));   
				while((line = reader.readLine()) != null) {
	                json.append(line);
	            }
				String content = java.net.URLDecoder.decode(json.toString(), "UTF-8");
				response = JSONObject.parseObject(content);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			} finally{
				logger.info("请求参数："+parms.toString());
				logger.info("响应参数："+response.toString());
				return response;
			}
		}
	}
	
	
	/**
	 * 是否黑名单客户校验
	 * @param customerName
	 * @param certID
	 * @return
	 * @throws IOException
	 */
	public String cancellSign(JSONObject jsonObject) throws IOException{
		 HttpClientUtils httpClient= new HttpClientUtils();
		 JSONObject json = httpClient.doPost("http://localhost:8080/dswx/user/sendOtpCode",jsonObject.toString());
		 return null;
	}
	
	public static JSONObject doPost(String url, File file,String parms) throws IOException, Exception{
		URL httpUrl=new URL(url);
		HttpURLConnection conn=(HttpURLConnection) httpUrl.openConnection();
		// 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在
	    // http正文内，因此需要设为true, 默认情况下是false;
		conn.setDoOutput(true);
	    // 设置是否从httpUrlConnection读入，默认情况下是true;
		conn.setDoInput(true);
	    // 设定请求的方法为"POST"，默认是GET
		conn.setRequestMethod("POST");
	    // Post 请求不能使用缓存
		conn.setUseCaches(false);
	    // 设定传送的内容类型是可序列化的java对象
	    // (如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
//	    con.setRequestProperty("Content-type", "application/x-java-serialized-object");
	    OutputStream out = conn.getOutputStream();
	    //读取本地图片文件流
	    FileInputStream localFins = new FileInputStream(file);
	    byte[] data = new byte[2048];
	    int len = 0;
	    int sum = 0;
	    while ((len = localFins.read(data)) != -1) {
	      //将读取到的本地文件流读取到HttpsURLConnection,进行上传
	      out.write(data, 0, len);
	      sum = len + sum;
	    }
	    logger.info(" Upload file size :" + sum);
	    out.flush();
	    localFins.close();
	    out.close();
	    int code = conn.getResponseCode(); //获取post请求返回状态
	    logger.info(" http request status message {}",code);
	    if (code == 200) {
	      InputStream inputStream = conn.getInputStream();
	      ByteArrayOutputStream bos = new ByteArrayOutputStream();
	      while ((len = inputStream.read(data)) != -1) {
	        bos.write(data, 0, len);
	      }
	      inputStream.close();
	      String content = bos.toString();
	      bos.close();
	      logger.info(" http response result message {}" ,content);
	      //将返回的json格式的字符串转化为json对象
	      JSONObject json = JSONObject.parseObject(content);
	      logger.info(" http response JSON result message {}" ,json);
	      //断开HttpsURLConnection连接
	      conn.disconnect();
	      return json;
	    }else{
	    	throw new Exception("文件上传请求异常");
	    }
	    
	}
	
	public static String doPostSend(String url, String parms) throws ClientProtocolException, IOException{
		HttpPost client=new HttpPost(url);
		MultipartEntityBuilder meBuilder=MultipartEntityBuilder.create();
		meBuilder.addPart("params", new StringBody(parms,ContentType.TEXT_PLAIN));
		HttpEntity reqEntity = meBuilder.build();  
		client.setEntity(reqEntity);
		return sendHttpPost(client);
	}
	
	public static String doPostSendFile(String url, File file,String parms) throws ClientProtocolException, IOException{
		HttpPost client=new HttpPost(url);
		MultipartEntityBuilder meBuilder=MultipartEntityBuilder.create();
		meBuilder.addPart("params", new StringBody(parms,ContentType.TEXT_PLAIN));
		FileBody fileBody = new FileBody(file);  
		meBuilder.addPart("file", fileBody);  
		HttpEntity reqEntity = meBuilder.build();  
		client.setEntity(reqEntity);
		return sendHttpPost(client);
	}

	public static String doPostBatchSendFile(String url, File[] files,String parms) throws ClientProtocolException, IOException{
		HttpPost client=new HttpPost(url);
		MultipartEntityBuilder meBuilder=MultipartEntityBuilder.create();
		meBuilder.addPart("params", new StringBody(parms,ContentType.TEXT_PLAIN));
		for (File f : files) {
			 FileBody fileBody = new FileBody(f);  
             meBuilder.addPart("files", fileBody);  
		}
		HttpEntity reqEntity = meBuilder.build();  
		client.setEntity(reqEntity);
		return sendHttpPost(client);
	}
	
	/** 
     * 发送Post请求 
     * @param httpPost 
     * @return 
	 * @throws IOException 
	 * @throws ClientProtocolException 
     */  
    private static String sendHttpPost(HttpPost httpPost) throws ClientProtocolException, IOException {  
        CloseableHttpClient httpClient = null;  
        CloseableHttpResponse response = null;  
        HttpEntity entity = null;  
        String responseContent = null;  
    	RequestConfig requestConfig = RequestConfig.custom()  
	            .setSocketTimeout(15000)  
	            .setConnectTimeout(15000)  
	            .setConnectionRequestTimeout(15000)  
	            .build();  
        // 创建默认的httpClient实例.  
        httpClient = HttpClients.createDefault();  
        httpPost.setConfig(requestConfig);  
        // 执行请求  
        response = httpClient.execute(httpPost);  
        entity = response.getEntity();  
        responseContent = EntityUtils.toString(entity, "UTF-8");  
            // 关闭连接,释放资源  
        if (response != null) {  
            response.close();  
        }  
        if (httpClient != null) {  
            httpClient.close();  
        }  
        return responseContent;  
    }  
	
    
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
            result.put("code", "SUC000");
            result.put("message", "微信下单异常,连接超时,请您重新下单!");
            return result;  
        } catch (Exception e) {  
        	log.error(e.getMessage());
        	result.put("code", "F00001");
            result.put("message", "微信下单异常,请您联系客服!");
            return result;  
        }  
	}
    
    
    
    /**
     * 描述:  发起https请求并获取结果
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr
    		,String contentType) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            httpUrlConn.setRequestProperty("content-type",contentType);  
            
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);
            if ("GET".equalsIgnoreCase(requestMethod))
                httpUrlConn.connect();

            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            jsonObject = JSONObject.parseObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("Weixin server connection timed out.");
        } catch (Exception e) {
            log.error("https request error:{}", e);
        }
        return jsonObject;
    }
	
	
}
