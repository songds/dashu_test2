package com.dashuf.kafka.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.dashuf.core.message.dto.Message;
import com.dashuf.core.message.kafkaimpl.KafkaMessageHandler;
import com.dashuf.support.event.Event;
import com.dashuf.support.event.EventAPI;

import lombok.extern.slf4j.Slf4j;

@RestController
@EventAPI
@Slf4j
public class KafKaTestApi {

	@Autowired
	private KafkaMessageHandler mh = null;
	   
	@KafkaListener(topics = {"KafKaTestApiTestBegin"})
	public void handleMessage(Message msg) {
	    mh.handleMessage(msg);
	}
	
	@Event(listenTopic = "KafKaTestApiTestBegin", returnTopic = "KafKaTestApiTestEnd")
	//@RequestMapping("/api/dataDictionaryDetailBegin.do")
	public JSONObject dataDictionaryDetailBegin(String soleCode) {
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("soleCode", soleCode);
		log.info("数据字典-详情查询参数："+jsonObject.toString());
		return jsonObject;
	}
	
}
