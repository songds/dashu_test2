package com.wechat.pp.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wechat.pp.dao.MemberInfoDao;

@Service
public class MemberInfoService {

	@Resource
	private MemberInfoDao memberInfoDao;
}
