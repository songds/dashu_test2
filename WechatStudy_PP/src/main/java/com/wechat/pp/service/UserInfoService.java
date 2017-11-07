package com.wechat.pp.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wechat.pp.dao.UserInfoDao;

@Service
public class UserInfoService {

	@Resource
	private UserInfoDao userInfoDao;
	
}
