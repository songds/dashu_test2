package com.wechat.pp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wechat.pp.po.MemberInfoPo;

public interface MemberInfoDao extends JpaRepository<MemberInfoPo, Integer>{

}
