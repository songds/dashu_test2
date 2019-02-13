package com.wechat.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wechat.service.MenuService;

@RestController
public class MenuApi {

	@Autowired
	private MenuService menuService;
	
	@RequestMapping(value="/api/getMenu.do",method=RequestMethod.GET)
	public JSONObject getMenu(@RequestParam("accessToken")String accessToken){
		return menuService.getMenu(accessToken);
	}
	@RequestMapping(value="/api/createMenu.do",method=RequestMethod.GET)
	public int createMenu(@RequestParam("accessToken")String accessToken){
		return menuService.createMenu(accessToken);
	}
	
}
