package com.wechat.pp.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wechat.pp.dao.PhotoSheetInfoDao;

@Service
public class PhotoSheetInfoService {

	@Resource
	private PhotoSheetInfoDao photoSheetInfoDao;
}
