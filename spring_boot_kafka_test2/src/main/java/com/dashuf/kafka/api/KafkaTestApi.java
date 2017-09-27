package com.dashuf.kafka.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.alibaba.fastjson.JSONObject;
import com.dashuf.core.message.dto.Message;
import com.dashuf.core.message.kafkaimpl.KafkaMessageHandler;
import com.dashuf.support.event.Event;
import com.dashuf.support.event.EventAPI;
import com.dashuf.support.http.HttpEventHolder;

import lombok.extern.slf4j.Slf4j;

@RestController
@EventAPI
@Slf4j
public class KafkaTestApi {

	@Autowired
	private HttpEventHolder httpEventHolder = null;
	
	//http://10.1.114.213:8080/api/dataDictionaryDetail.do?soleCode=55555
	@RequestMapping("/api/dataDictionaryDetail.do")
	public DeferredResult<?> dataDictionaryDetail(@RequestParam(required = false) String soleCode) {
		JSONObject jb=new JSONObject();
		jb.put("soleCode", soleCode);
		log.info("数据字典详情查询的入参为:"+jb.toString());
		return httpEventHolder.http2Event("KafKaTestApiTestBegin", soleCode, null); //转成事件发送出去，第三个参数在响应事件时可通过httpEventHolder.getContext()获取，如果不需要额外的参数，第三个参数设置成null
	}
	
	//响应PingBegin事件，并结束http请求，返回值是http请求返回的值
	//isHttpEvent必需设置成true，以表示这是一个响应http请求的事件
	@Event(listenTopic = "KafKaTestApiTestEnd", isHttpEvent = true)
	public JSONObject DataDictionaryDetailEnd(JSONObject jsonObject) {
		log.info("数据字典详情查询返回数据为", jsonObject.toString());
		return jsonObject;
	}
	
	@Autowired
	private KafkaMessageHandler mh = null;
	
	@KafkaListener(topics = {"KafKaTestApiTestEnd"})
	public void handleMessage(Message msg) {
		mh.handleMessage(msg);
	}
}
