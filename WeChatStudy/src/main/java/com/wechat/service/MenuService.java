package com.wechat.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.wechat.menu.Button;
import com.wechat.menu.CommonButton;
import com.wechat.menu.ComplexButton;
import com.wechat.menu.Menu;
import com.wechat.message.resp.Article;
import com.wechat.message.resp.NewsMessage;
import com.wechat.util.MessageUtil;
import com.wechat.util.WeixinUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MenuService {
	
	private static final String GET_MENU_URL="https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	
	@Value("${application.system.manager.url}")
	private String system_manager_url;
	
	public JSONObject getMenu(String accessToken){
		String requestUrl=GET_MENU_URL.replace("ACCESS_TOKEN", accessToken);
		log.info(" requestUrl : {}",requestUrl);
		JSONObject result=WeixinUtil.httpRequest(requestUrl, "GET", null);
		return result;
	}
	
	public int createMenu(String accessToken){
		Menu menu=new Menu();
		Button [] buttons=new Button[3];
		
		
		CommonButton prdocutButton=new CommonButton();
		prdocutButton.setName("商品中心");
		prdocutButton.setKey("PRODUCT_CENTER");
		prdocutButton.setType("view");
		prdocutButton.setUrl("https://aplha.cn/#/shopCar/:001");
		Button [] userSubButtons=new Button[3];
		
		CommonButton posterButton=new CommonButton();
		posterButton.setName("海报中心");
		posterButton.setKey("POSTER_CENTER");
		posterButton.setType("view");
		posterButton.setUrl("https://aplha.cn/#/posterList");
		CommonButton messageButton=new CommonButton();
		messageButton.setName("消息通知");
		messageButton.setKey("MESSAGE_CENTER");
		messageButton.setType("view");
		messageButton.setUrl("https://aplha.cn/#/msg");
		
		CommonButton useButton=new CommonButton();
		useButton.setName("使用中心");
		useButton.setKey("USE_CENTER");
		useButton.setType("click");
		userSubButtons[0]=posterButton;
		userSubButtons[1]=messageButton;
		userSubButtons[2]=useButton;
		
		/*CommonButton tweetButton=new CommonButton();
		tweetButton.setName("吐个槽");
		tweetButton.setKey("TWEET_CENTER");
		tweetButton.setType("view");
		tweetButton.setUrl("https://support.qq.com/products/55044");
		
		userSubButtons[3]=tweetButton;*/
		
		ComplexButton userCenter=new ComplexButton();
		userCenter.setName("用户中心");
		userCenter.setSub_button(userSubButtons);
		
		
		
		
		Button [] authSubButtons=new Button[5];
		
		CommonButton createAuthButton=new CommonButton();
		createAuthButton.setName("新增授权");
		createAuthButton.setKey("CREATE_AUTH_CENTER");
		createAuthButton.setType("view");
		createAuthButton.setUrl("https://aplha.cn/#/recommend");
		
		CommonButton groupButton=new CommonButton();
		groupButton.setName("团队中心");
		groupButton.setKey("GROUP_CENTER");
		groupButton.setType("view");
		groupButton.setUrl("https://aplha.cn/#/team");
		
		CommonButton orderButton=new CommonButton();
		orderButton.setName("订单中心");
		orderButton.setKey("ORDER_CENTER");
		orderButton.setType("view");
		orderButton.setUrl("https://aplha.cn/#/orderList");
		
		CommonButton workbenchButton=new CommonButton();
		workbenchButton.setName("工作台");
		workbenchButton.setKey("WORKBENCH_CENTER");
		workbenchButton.setType("view");
		workbenchButton.setUrl(system_manager_url);
		
		CommonButton personalButton=new CommonButton();
		personalButton.setName("我的");
		personalButton.setKey("PERSONAL_CENTER");
		personalButton.setType("view");
		personalButton.setUrl("https://aplha.cn/#/personalCenter");
		
		
		authSubButtons[0]=createAuthButton;
		authSubButtons[1]=groupButton;
		authSubButtons[2]=orderButton;
		authSubButtons[3]=workbenchButton;
		authSubButtons[4]=personalButton;
		
		ComplexButton authCenter=new ComplexButton();
		authCenter.setName("授权中心");
		authCenter.setSub_button(authSubButtons);
		
		buttons[0]=prdocutButton;
		buttons[1]=userCenter;
		buttons[2]=authCenter;
		menu.setButton(buttons);
		
		int i=WeixinUtil.createMenu(menu, accessToken);
		return i;
	}
	
	
	public String menuCilck(Map<String, String> requestMap){
		String eventKey=requestMap.get("EventKey");
		String openId=requestMap.get("FromUserName");
        // 开发者微信号
        String toUserName = requestMap.get("ToUserName");
		if("USE_CENTER".equals(eventKey)){
			String title="会员管理系统说明";
			String description="会员管理系统说明";
			String picUrl="https://aplha.cn/images/message/wmessage_20190303103412.png";
			String url="https://aplha.cn/#/activity";
			List<Article> articles=new ArrayList<Article>();
			Article article=new Article();
			article.setDescription(description);
			article.setPicUrl(picUrl);
			article.setTitle(title);
			article.setUrl(url);
			articles.add(article);
			
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
		}
		return  null;
	}
}
