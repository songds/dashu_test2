create table weixin_order_ret(
	id INT PRIMARY KEY AUTO_INCREMENT COMMENT '编号',
	user_name varchar(100) COMMENT '用户名',
	return_code VARCHAR(16) NOT NULL COMMENT '返回状态码',
	return_msg VARCHAR(128) COMMENT '返回信息',
	appid VARCHAR(32) NOT NULL COMMENT '应用APPID',
	mch_id VARCHAR(32) NOT NULL COMMENT '商户号',
	device_info VARCHAR(32) COMMENT '设备号',
	nonce_str VARCHAR(32) NOT NULL COMMENT '随机字符串',
	sign VARCHAR(32) NOT NULL COMMENT '签名',
	result_code VARCHAR(16) NOT NULL COMMENT '业务结果',
	err_code VARCHAR(32) COMMENT '错误代码',
	err_code_des VARCHAR(128) COMMENT '错误代码描述',
	trade_type VARCHAR(16) NOT NULL COMMENT '交易类型',
	prepay_id VARCHAR(64) NOT NULL COMMENT '预支付交易会话标识',
	expiration_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '预支付交易会话过期时间',
	created_by VARCHAR(150) NOT NULL DEFAULT 'SYSTEM' COMMENT '创建人',
    created_date DATETIME  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
    updated_by VARCHAR(150) NOT NULL DEFAULT 'SYSTEM' COMMENT '更新人',
    updated_date DATETIME  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日期'

)comment='微信订单返回信息表';

drop table weixin_pay_info;
create table weixin_pay_info(
	id INT PRIMARY KEY AUTO_INCREMENT COMMENT '编号',
	appid VARCHAR(32) NOT NULL COMMENT '应用APPID',
	mch_id VARCHAR(32) NOT NULL COMMENT '商户号',
	prepay_id VARCHAR(64) NOT NULL COMMENT '预支付交易会话标识',
	package VARCHAR(128) NOT NULL COMMENT '商户号',	
	nonce_str VARCHAR(32) NOT NULL COMMENT '随机字符串',
	timestamps varchar(10) NOT NULL COMMENT '时间戳',
	sign VARCHAR(32) NOT NULL COMMENT '签名',
	user_name varchar(100) NOT NULL COMMENT '用户名',
	trade_type VARCHAR(16) NOT NULL COMMENT '交易类型',
	total_fee int NOT NULL COMMENT '金额',
	body VARCHAR(128) NOT NULL COMMENT '商品描述',
	trade_status VARCHAR(16) COMMENT '交易状态',
	pay_type VARCHAR(16) NOT NULL COMMENT '支付类型',
	out_trade_no varchar(32) NOT NULL COMMENT '商户订单号',
	attach varchar(128) NOT NULL COMMENT '附件数据',
	expiration_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '预支付交易会话过期时间',
	created_by VARCHAR(150) NOT NULL DEFAULT 'SYSTEM' COMMENT '创建人',
    created_date DATETIME  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
    updated_by VARCHAR(150) NOT NULL DEFAULT 'SYSTEM' COMMENT '更新人',
    updated_date DATETIME  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日期'
)COMMENT='微信支付接口信息表';