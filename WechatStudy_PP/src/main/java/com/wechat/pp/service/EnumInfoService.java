package com.wechat.pp.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wechat.pp.dao.EnumInfoDao;

@Service
public class EnumInfoService {

	@Resource
	private EnumInfoDao enumInfoDao;
}
