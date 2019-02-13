package com.wechat.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.wechat.dao.ToKenDao;
import com.wechat.message.resp.Article;
import com.wechat.message.resp.NewsMessage;
import com.wechat.message.resp.TextMessage;
import com.wechat.po.AccessToken;
import com.wechat.po.TokenPo;
import com.wechat.util.GfManagerUtil;
import com.wechat.util.MessageUtil;
import com.wechat.util.WeixinConf;
import com.wechat.util.WeixinUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {

	@Value("${application.wechat.official.appSecret}")
	private  String appSecret;
	
	@Value("${application.wechat.official.appId}")
	private  String appId;
	
	@Value("${application.wechat.official.token}")
	private  String token;
	//获取用户列表信息
	private static String GET_USER_LIST_URL="https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN";
	//&next_openid=NEXT_OPENID
	
	//获取用户基本信息
	private static String GET_USER_INFO_URL="https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	
	//创建带参数二维码
	private static String CREATE_QRCODE_URL="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";
	
	//长连接生成短连接二维码图片地址
	private static String CREATE_SHORT_QRCODE_URL="https://api.weixin.qq.com/cgi-bin/shorturl?access_token=ACCESS_TOKEN";
	
	@Autowired
	private ToKenDao toKenDao;
	
	@Autowired
	private GfManagerUtil gfManagerUtil;
	
	public String getAccessToken(){
		long currentTime=System.currentTimeMillis();
		List<TokenPo> tokens=toKenDao.findAll();
		TokenPo tokenPo=null;
		if(tokens==null||tokens.size()<=0){
			AccessToken accessToken =WeixinUtil.getAccessToken(StringUtils.isBlank(WeixinConf.appId)?appId:WeixinConf.appId,
					StringUtils.isBlank(WeixinConf.appSecret)?appSecret:WeixinConf.appSecret);
			log.info(" accessToken : {} ",JSONObject.toJSONString(accessToken));
			tokenPo=new TokenPo();
			tokenPo.setAccessToken(accessToken.getToken());
			tokenPo.setCreateTime(new Date(currentTime));
			tokenPo.setExpiresIn(accessToken.getExpiresIn());
			toKenDao.save(tokenPo);
		}else{
			tokenPo=tokens.get(0);
			long expiresIn=tokenPo.getExpiresIn();
			Date createDate=tokenPo.getCreateTime();
			long createTime=createDate.getTime();
			long endTime = (currentTime-createTime)/1000;
			if(endTime>expiresIn){
				AccessToken accessToken =WeixinUtil.getAccessToken(StringUtils.isBlank(WeixinConf.appId)?appId:WeixinConf.appId,
						StringUtils.isBlank(WeixinConf.appSecret)?appSecret:WeixinConf.appSecret);
				log.info(" accessToken : {} ",JSONObject.toJSONString(accessToken));
				tokenPo.setAccessToken(accessToken.getToken());
				tokenPo.setExpiresIn(accessToken.getExpiresIn());
				tokenPo.setCreateTime(new Date(currentTime));
				toKenDao.save(tokenPo);
			}
		}
		return tokenPo.getAccessToken();
	}
	
	public JSONObject getUserInfoList(){
		String accessToken=getAccessToken();
		String requestUrl=GET_USER_LIST_URL.replace("ACCESS_TOKEN", accessToken);
		JSONObject result=WeixinUtil.httpRequest(requestUrl, "GET", null);
		log.info(" result : {} ",result);
		return result;
	}
	
	public JSONObject getUserInfo(String openId){
		String accessToken=getAccessToken();
		String requestUrl=GET_USER_INFO_URL.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		JSONObject result=WeixinUtil.httpRequest(requestUrl, "GET", null);
		log.info(" result : {} ",result);
		return result;
	}
	
	public JSONObject wxAttention(Map<String, String> requestMap){
		String openId=requestMap.get("FromUserName");
		String createTime=requestMap.get("CreateTime");
		JSONObject userInfo=getUserInfo(openId);
		userInfo.put("openId", openId);
		userInfo.put("createTime", createTime);
		log.info(" USER INFO : {} ",userInfo);
		JSONObject result=gfManagerUtil.wxAttention(JSONObject.toJSONString(userInfo));
		log.info(" result : {} ",JSONObject.toJSONString(result));
		return result;
	}
	
	public JSONObject wxCancelAttention(Map<String, String> requestMap){
		String openId=requestMap.get("FromUserName");
		String createTime=requestMap.get("CreateTime");
		JSONObject userInfo=new JSONObject();
		userInfo.put("openId", openId);
		userInfo.put("createTime", createTime);
		JSONObject result=gfManagerUtil.wxCancelAttention(JSONObject.toJSONString(userInfo));
		log.info(" result : {} ",JSONObject.toJSONString(result));
		return result;
	}
	
	public JSONObject createCode(String json){
		String accessToken=getAccessToken();
		String requestUrl=CREATE_QRCODE_URL.replace("ACCESS_TOKEN", accessToken);
		JSONObject parameter=JSONObject.parseObject(json);
		int expireSeconds=parameter.getInteger("expire_seconds");
		String action_name=parameter.getString("action_name");
		JSONObject codeParameter=new JSONObject();
		codeParameter.put("expire_seconds", expireSeconds);
		codeParameter.put("action_name", action_name);
		codeParameter.put("action_info", parameter);
		log.info(" codeParameter : {} ",codeParameter);
		JSONObject result=WeixinUtil.httpRequest(requestUrl, "POST", JSONObject.toJSONString(codeParameter));
		log.info(" result : {} ",result);
		String url=result.getString("url");
		String qrcodeHttpUrl=CREATE_SHORT_QRCODE_URL.replace("ACCESS_TOKEN", accessToken);
		JSONObject qrcodeParameter=new JSONObject();
		qrcodeParameter.put("action", "long2short");
		qrcodeParameter.put("long_url", url);
		JSONObject qrcodeResult=WeixinUtil.httpRequest(qrcodeHttpUrl, "POST", JSONObject.toJSONString(qrcodeParameter));
		log.info(" qrcodeResult : {} ",qrcodeResult);
		String shortUrl=qrcodeResult.getString("short_url");
		result.put("shortUrl", shortUrl);
		return result;
	}
	
	public String scanQrcode(Map<String, String> requestMap){
		log.info("----------------------scanQrcode-----------------------");
		String openId=requestMap.get("FromUserName");
        // 开发者微信号
        String toUserName = requestMap.get("ToUserName");
        
		String createTime=requestMap.get("CreateTime");
		String codeNo=requestMap.get("EventKey");
		JSONObject httpParameter=new JSONObject();
		httpParameter.put("openId", openId);
		httpParameter.put("createTime", createTime);
		httpParameter.put("codeNo",codeNo);
		JSONObject result=gfManagerUtil.scanQrcode(JSONObject.toJSONString(httpParameter));
		log.info(" result message : {} ",JSONObject.toJSONString(result));
		JSONObject data=result.getJSONObject("data");
		String messageType=data.getString("messageType");
		if("NEWS".equals(messageType)){
			String title=data.getString("title");
			String description=data.getString("description");
			String picUrl=data.getString("picUrl");
			String url=data.getString("url");
			List<Article> articles=new ArrayList<Article>();
			Article article=new Article();
			article.setDescription(description);
			article.setPicUrl(picUrl);
			article.setTitle(title);
			article.setUrl(url);
			articles.add(article);
			
			/*Article article1=new Article();
			article1.setDescription(description);
			article1.setPicUrl(picUrl);
			article1.setTitle(title);
			article1.setUrl(url);
			articles.add(article1);*/
			
			NewsMessage newsMessage=new NewsMessage();
			newsMessage.setArticleCount(articles.size());
			newsMessage.setToUserName(openId);
			newsMessage.setFromUserName(toUserName);
			newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
			newsMessage.setCreateTime(System.currentTimeMillis());
			newsMessage.setArticles(articles);
			String respXml=MessageUtil.messageToXml(newsMessage);
	    	log.info(" respXml : {} ",respXml);
	    	return respXml;
		}else if("TEXT".equals(messageType)){
			String message=data.getString("message");
			TextMessage textMessage=new TextMessage();
			textMessage.setContent(message);
			textMessage.setToUserName(openId);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(System.currentTimeMillis());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			String respXml=MessageUtil.messageToXml(textMessage);
	    	log.info(" respXml : {} ",respXml);
	    	return respXml;
		}else{
			return null;
		}
    	
	}
	
	
}
