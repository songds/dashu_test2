package com.wechat.service;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.wechat.message.resp.TextMessage;
import com.wechat.util.MessageUtil;

import lombok.extern.slf4j.Slf4j;

/**
* 类名: CoreService </br>
* 描述: 核心服务类 </br>
* 开发人员： souvc </br>
* 创建时间：  2015-9-30 </br>
* 发布版本：V1.0  </br>
 */
@Slf4j
@Service
public class CoreService {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private MessageSerevice messageSerevice;
	
	@Value("${application.system.manager.url}")
	private String system_manager_url;
	
	
	
    /**
     * 处理微信发来的请求
     * @param request
     * @return xml
     */
    public  String processRequest(HttpServletRequest request) {
        // xml格式的消息数据
        String respXml = null;
        // 默认返回的文本消息内容
        String respContent = "未知的消息类型！";
        try {
            // 调用parseXml方法解析请求消息
            Map<String, String> requestMap = MessageUtil.parseXml(request);
            log.info(" requestMap : {} ",JSONObject.toJSONString(requestMap));
            // 发送方帐号
            String fromUserName = requestMap.get("FromUserName");
            // 开发者微信号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");

            // 回复文本消息
            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

            // 文本消息
            String url="<a href=\""+system_manager_url+"/images/template/company_qrcode.jpg\">客服</a>";
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
            	respContent = "您在说什么,我都听不懂,要不要联系 "+url+" 聊聊";
            }
            // 图片消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
                respContent = "您在说什么,我都听不懂,要不要联系 "+url+" 聊聊";
            }
            // 语音消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
            	respContent = "您在说什么,我都听不懂,要不要联系 "+url+" 聊聊";
            }
            // 视频消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) {
            	respContent = "您在说什么,我都听不懂,要不要联系 "+url+" 聊聊";
            }
            // 视频消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_SHORTVIDEO)) {
            	respContent = "您在说什么,我都听不懂,要不要联系 "+url+" 聊聊";
            }
            // 地理位置消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
            	respContent = "您在说什么,我都听不懂,要不要联系 "+url+" 聊聊";
            }
            // 链接消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
            	respContent = "您在说什么,我都听不懂,要不要联系 "+url+" 聊聊";
            }
            // 事件推送
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                // 事件类型
                String eventType = requestMap.get("Event");
                // 关注
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                    
                    String result=userService.wxAttention(requestMap);
                    if(StringUtils.isBlank(result)){
                    	respContent = "感谢您的关注，这里有您喜欢的东西";
                    }else{
                    	return result;
                    }
                }
                // 取消关注
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
                    // TODO 取消订阅后用户不会再收到公众账号发送的消息，因此不需要回复
                	userService.wxCancelAttention(requestMap);
                }
                // 扫描带参数二维码
                else if (eventType.equalsIgnoreCase(MessageUtil.EVENT_TYPE_SCAN)) {
                    // TODO 处理扫描带参数二维码事件
                	String result=userService.scanQrcode(requestMap);
                	if(!StringUtils.isBlank(result)){
                		return result;
                	}
                }
                // 上报地理位置
                else if (eventType.equals(MessageUtil.EVENT_TYPE_LOCATION)) {
                    // TODO 处理上报地理位置事件
                }
                // 自定义菜单
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                    // TODO 处理菜单点击事件
                	String result=menuService.menuCilck(requestMap);
                	if(StringUtils.isBlank(result)){
                		respContent="您点击操作已收到，请您等待回复!";
                	}else{
                		return result;
                	}
                }else if(eventType.equalsIgnoreCase(MessageUtil.EVENT_TYPE_TEMPLATESENDJOBFINISH)){
                	messageSerevice.templateMessageCall(requestMap);
                }
            }
            // 设置文本消息的内容
            textMessage.setContent(respContent);
            // 将文本消息对象转换成xml
            respXml = MessageUtil.messageToXml(textMessage);
            log.info(" respXml --> {} ",respXml);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respXml;
    }
}