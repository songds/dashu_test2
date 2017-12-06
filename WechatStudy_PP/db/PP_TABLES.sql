drop table user_info;
create table user_info(
        id int COMMENT '编号',
        name varchar(100) COMMENT '姓名',
        nickname varchar(100) COMMENT '昵称',
        sex varchar(100) COMMENT '性别',
        birth_date timestamp COMMENT '出生日期',
        education varchar(100) COMMENT '学历',
        user_name varchar(100) COMMENT '用户名',
        password varchar(150) COMMENT '密码',
        user_status varchar(150) COMMENT '用户状态',
        address varchar(1000) COMMENT '地址',
        mobile varchar(100) COMMENT '手机号码',
        image_url varchar(100) COMMENT '图片路径',
        registration_date timestamp COMMENT '注册日期',
        balance_account Double COMMENT '账户余额',
        updated_date timestamp COMMENT '修改日期',
        created_by varchar(100) COMMENT '创建人',
        updated_by varchar(100) COMMENT '修改人'
)comment='用户信息表' ;


drop table weixin_Bound_info;
create table weixin_Bound_info(
        id int COMMENT '编号',
        weixin_id varchar(100) COMMENT '微信好',
        open_Id varchar(100) COMMENT '微信OPENID',
        user_name varchar(100) COMMENT '用户名',
        weixin_name varchar(100) COMMENT '微信名称',
        weixin_image_url varchar(100) COMMENT '微信头像路径',
        union_id varchar(100) COMMENT '微信UNIONID',
        created_date timestamp COMMENT '创建日期',
        updated_date timestamp COMMENT '修改日期',
        created_by varchar(100) COMMENT '创建人',
        updated_by varchar(100) COMMENT '修改人'
)COMMENT='微信绑定表';

drop table QQ_Bound_info;
create table QQ_Bound_info(
        id int COMMENT '编号',
        qq_id varchar(100) COMMENT 'QQ号',
        open_Id varchar(100) COMMENT 'QQOPENID',
        user_name varchar(100) COMMENT '用户名',
        qq_name varchar(100) COMMENT 'QQ名称',
        qq_image_url varchar(100) COMMENT 'QQ头像路径',
        union_id varchar(100) COMMENT 'QQUNIONID',
        created_date timestamp COMMENT '创建日期',
        updated_date timestamp COMMENT '修改日期',
        created_by varchar(100) COMMENT '创建人',
        updated_by varchar(100) COMMENT '修改人'
)COMMENT='QQ绑定表';

drop table login_log_info;
create table login_log_info(
        id int COMMENT '编号',
        user_name varchar(100) COMMENT '用户名',
        to_ken varchar(100) COMMENT 'toKen',
        device_number varchar(100) COMMENT '设备号',
        IP_addr varchar(100) COMMENT 'IP地址',
        login_status varchar(100) COMMENT '登陆状态',
        login_date timestamp COMMENT '登陆时间',
        expiration_time timestamp COMMENT '过期时间'
)COMMENT='登陆记录信息表';


drop table member_info;
create table member_info(
        id int COMMENT '编号',
        member_name varchar(100) COMMENT '会员名称',
        created_date timestamp COMMENT '创建日期',
        updated_date timestamp COMMENT '修改日期',
        created_by varchar(100) COMMENT '创建人',
        updated_by varchar(100) COMMENT '修改人'
)COMMENT='会员信息表';

drop table trading_record_info;
create table trading_record_info(
        id int COMMENT '编号',
        user_name varchar(100) COMMENT '用户名',
        trading_id varchar(150) COMMENT '交易编号',
        trading_type varchar(150) COMMENT '交易类型',
        trading_status varchar(150) COMMENT '交易状态',
        trading_time timestamp COMMENT '交易时间',
        created_date timestamp COMMENT '创建日期',
        updated_date timestamp COMMENT '修改日期',
        created_by varchar(100) COMMENT '创建人',
        updated_by varchar(100) COMMENT '修改人'
)COMMENT='交易记录信息表';


drop table photo_sheet_info;
create table photo_sheet_info(
        id int COMMENT '编号',
        user_name varchar(100) COMMENT '用户名',
        Photo_number int COMMENT '拍照次数',
        photo_status varchar(100) COMMENT '拍照状态',
        created_date timestamp COMMENT '创建日期',
        updated_date timestamp COMMENT '修改日期',
        created_by varchar(100) COMMENT '创建人',
        updated_by varchar(100) COMMENT '修改人'
)COMMENT='拍照信息记录表';

drop table enum_info;
create table enum_info(
        id int COMMENT '编号',
        code_type varchar(100) COMMENT '代码类型',
        code_desc varchar(100) COMMENT '代码描述',
        code_id varchar(100) COMMENT '代码编号',
        code_name varchar(100) COMMENT '代码名称',
        created_date timestamp COMMENT '创建日期',
        updated_date timestamp COMMENT '修改日期',
        created_by varchar(100) COMMENT '创建人',
        updated_by varchar(100) COMMENT '修改人'
)COMMENT = '代码信息表';

drop table device_info;
create table device_info(
        user_name varchar(100),
        to_ken varchar(100) COMMENT 'toKen',
        device_number varchar(100) COMMENT '设备号',
        IP_addr varchar(100) COMMENT 'IP地址',
         created_date timestamp COMMENT '创建日期',
        updated_date timestamp COMMENT '修改日期',
        created_by varchar(100) COMMENT '创建人',
        updated_by varchar(100) COMMENT '修改人'
)COMMENT='设备信息表';

drop table sys_config_tables;
CREATE TABLE sys_config_tables(
        id int COMMENT '编号',
        table_name varchar(150) COMMENT '表名',
        column_name varchar(150) COMMENT '表字段',
        column_desc varchar(150) COMMENT '表字段描述',
        is_null varchar(10) COMMENT '是否为空',
        column_length  int default -1 COMMENT '字段长度',
        is_primary_key varchar(10) COMMENT '是否主键',
        created_date timestamp COMMENT '创建日期',
        updated_date timestamp COMMENT '修改日期',
        created_by varchar(100) COMMENT '创建人',
        updated_by varchar(100) COMMENT '修改人'
)COMMENT='表结构信息表';


drop table user_member_relation;
create table user_member_relation(
        id int COMMENT '编号',
        user_name varchar(100) COMMENT '用户名',
        member_id int COMMENT '会员编号',
        member_type varchar(100) COMMENT '会员类型',
        created_date timestamp COMMENT '创建日期',
        updated_date timestamp COMMENT '修改日期',
        created_by varchar(100) COMMENT '创建人',
        updated_by varchar(100) COMMENT '修改人'
)COMMENT='用户会员关系表';


drop table member_price_info;
create table member_price_info(
	id int COMMENT '编号',
	member_id int COMMENT '会员编号',
	card_type varchar(100) COMMENT '卡类型',
	card_type_desc varchar(100) COMMENT '卡类型描述',
	card_price double COMMENT '卡价格',
	created_date timestamp COMMENT '创建日期',
  updated_date timestamp COMMENT '修改日期',
  created_by varchar(100) COMMENT '创建人',
  updated_by varchar(100) COMMENT '修改人'
)COMMENT='会员价格信息表';




CREATE TABLE SMS_INFO(
        id int primary key AUTO_INCREMENT,
        dspm_apply_no varchar(36) NOT NULL COMMENT '申请编号',
        Mobile_NO varchar(36) NOT NULL COMMENT '手机号码',
        type varchar(2) NOT NULL COMMENT '操作类型',
        status varchar(10) NOT NULL COMMENT '验证状态',
        verification_code varchar(10) NOT NULL COMMENT '验证码',
        expiration_time DATETIME NOT NULL COMMENT '过期时间',
        created_by VARCHAR(150) NOT NULL COMMENT '创建人',
        created_date DATETIME  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
        updated_by VARCHAR(150) NOT NULL COMMENT '更新人',
        updated_date DATETIME  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日期'
        
)COMMENT='短信发送记录表';
