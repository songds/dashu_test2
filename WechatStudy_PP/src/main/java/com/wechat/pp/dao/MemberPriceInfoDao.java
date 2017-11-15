package com.wechat.pp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wechat.pp.po.MemberPriceInfoPo;

public interface MemberPriceInfoDao extends JpaRepository<MemberPriceInfoPo, Integer>{

	public List<MemberPriceInfoPo> getMemberPriceByMemberId(int memberId);
}
