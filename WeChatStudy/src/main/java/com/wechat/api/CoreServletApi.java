package com.wechat.api;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wechat.service.CoreService;
import com.wechat.util.SignUtil;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class CoreServletApi {
	
	@Autowired
	private CoreService coreService;

	/**
     * 确认请求来自微信服务器
     */
	@RequestMapping(value="/api/testWechat.do",method=RequestMethod.GET)
    public void testWechat(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log.info(" Came in GET ");
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");
        log.info(" paramater signature:{}  timestamp:{} nonce:{} echostr:{}",signature,timestamp,nonce,echostr);
        PrintWriter out = response.getWriter();
        
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            out.print(echostr);
        }
        
        out.close();
        out = null;
    }
	
	/**
     * 确认请求来自微信服务器
	 * @throws IOException 
     */
	@RequestMapping(value="/api/testWechat.do",method=RequestMethod.POST)
    public void testWechat1(HttpServletRequest request, HttpServletResponse response) throws IOException{
		 log.info(" Came in POST ");
		// 消息的接收、处理、响应
	        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
	        request.setCharacterEncoding("UTF-8");
	        response.setCharacterEncoding("UTF-8");

	        // 调用核心业务类接收消息、处理消息
	        String respXml = coreService.processRequest(request);

	        // 响应消息
	        PrintWriter out = response.getWriter();
	        out.print(respXml);
	        out.close();
		 
	}
    
    
}
