create table alibaba_pay_info(
	id INT PRIMARY KEY AUTO_INCREMENT COMMENT '编号',
	total_amount float NOT NULL COMMENT '金额',
	subject VARCHAR(256) NOT NULL COMMENT '商品标题',
	body VARCHAR(256) NOT NULL COMMENT '商品描述',
	trade_status VARCHAR(16) COMMENT '交易状态',
	timeout_express VARCHAR(16) COMMENT '订单失效时间',
	out_trade_no varchar(32) NOT NULL COMMENT '商户订单号',
	attach varchar(128) NOT NULL COMMENT '附件数据',
	created_by VARCHAR(150) NOT NULL DEFAULT 'SYSTEM' COMMENT '创建人',
    created_date DATETIME  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
    updated_by VARCHAR(150) NOT NULL DEFAULT 'SYSTEM' COMMENT '更新人',
    updated_date DATETIME  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日期'
)COMMENT='支付订单信息表';