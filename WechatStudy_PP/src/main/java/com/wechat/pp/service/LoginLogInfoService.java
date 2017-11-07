package com.wechat.pp.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wechat.pp.dao.LoginLogInfoDao;

@Service
public class LoginLogInfoService {

	@Resource
	private LoginLogInfoDao loginLogInfoDao;
}
