package com.wechat.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wechat.po.TokenPo;

public interface ToKenDao extends JpaRepository<TokenPo, Integer>{

}
