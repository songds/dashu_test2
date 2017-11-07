package com.wechat.pp.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wechat.pp.dao.WeixinBoundInfoDao;

@Service
public class WeixinBoundInfoService {

	@Resource
	private WeixinBoundInfoDao weixinBoundInfoDao;
}
