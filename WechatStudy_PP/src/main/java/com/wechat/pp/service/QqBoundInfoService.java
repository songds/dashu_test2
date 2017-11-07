package com.wechat.pp.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wechat.pp.dao.QqBoundInfoDao;

@Service
public class QqBoundInfoService {

	@Resource
	private QqBoundInfoDao qqBoundInfoDao;
}
