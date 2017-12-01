drop table user_info;
create table user_info(
        id int COMMENT '���',
        name varchar(100) COMMENT '����',
        nickname varchar(100) COMMENT '�ǳ�',
        sex varchar(100) COMMENT '�Ա�',
        birth_date timestamp COMMENT '��������',
        education varchar(100) COMMENT 'ѧ��',
        user_name varchar(100) COMMENT '�û���',
        password varchar(150) COMMENT '����',
        user_status varchar(150) COMMENT '�û�״̬',
        address varchar(1000) COMMENT '��ַ',
        mobile varchar(100) COMMENT '�ֻ�����',
        image_url varchar(100) COMMENT 'ͼƬ·��',
        registration_date timestamp COMMENT 'ע������',
        balance_account Double COMMENT '�˻����',
        updated_date timestamp COMMENT '�޸�����',
        created_by varchar(100) COMMENT '������',
        updated_by varchar(100) COMMENT '�޸���'
)comment='�û���Ϣ��' ;


drop table weixin_Bound_info;
create table weixin_Bound_info(
        id int COMMENT '���',
        weixin_id varchar(100) COMMENT '΢�ź�',
        open_Id varchar(100) COMMENT '΢��OPENID',
        user_name varchar(100) COMMENT '�û���',
        weixin_name varchar(100) COMMENT '΢������',
        weixin_image_url varchar(100) COMMENT '΢��ͷ��·��',
        union_id varchar(100) COMMENT '΢��UNIONID',
        created_date timestamp COMMENT '��������',
        updated_date timestamp COMMENT '�޸�����',
        created_by varchar(100) COMMENT '������',
        updated_by varchar(100) COMMENT '�޸���'
)COMMENT='΢�Ű󶨱�';

drop table QQ_Bound_info;
create table QQ_Bound_info(
        id int COMMENT '���',
        qq_id varchar(100) COMMENT 'QQ��',
        open_Id varchar(100) COMMENT 'QQOPENID',
        user_name varchar(100) COMMENT '�û���',
        qq_name varchar(100) COMMENT 'QQ����',
        qq_image_url varchar(100) COMMENT 'QQͷ��·��',
        union_id varchar(100) COMMENT 'QQUNIONID',
        created_date timestamp COMMENT '��������',
        updated_date timestamp COMMENT '�޸�����',
        created_by varchar(100) COMMENT '������',
        updated_by varchar(100) COMMENT '�޸���'
)COMMENT='QQ�󶨱�';

drop table login_log_info;
create table login_log_info(
        id int COMMENT '���',
        user_name varchar(100) COMMENT '�û���',
        to_ken varchar(100) COMMENT 'toKen',
        device_number varchar(100) COMMENT '�豸��',
        IP_addr varchar(100) COMMENT 'IP��ַ',
        login_status varchar(100) COMMENT '��½״̬',
        login_date timestamp COMMENT '��½ʱ��',
        expiration_time timestamp COMMENT '����ʱ��'
)COMMENT='��½��¼��Ϣ��';


drop table member_info;
create table member_info(
        id int COMMENT '���',
        member_name varchar(100) COMMENT '��Ա����',
        created_date timestamp COMMENT '��������',
        updated_date timestamp COMMENT '�޸�����',
        created_by varchar(100) COMMENT '������',
        updated_by varchar(100) COMMENT '�޸���'
)COMMENT='��Ա��Ϣ��';

drop table trading_record_info;
create table trading_record_info(
        id int COMMENT '���',
        user_name varchar(100) COMMENT '�û���',
        trading_id varchar(150) COMMENT '���ױ��',
        trading_type varchar(150) COMMENT '��������',
        trading_status varchar(150) COMMENT '����״̬',
        trading_time timestamp COMMENT '����ʱ��',
        created_date timestamp COMMENT '��������',
        updated_date timestamp COMMENT '�޸�����',
        created_by varchar(100) COMMENT '������',
        updated_by varchar(100) COMMENT '�޸���'
)COMMENT='���׼�¼��Ϣ��';


drop table photo_sheet_info;
create table photo_sheet_info(
        id int COMMENT '���',
        user_name varchar(100) COMMENT '�û���',
        Photo_number int COMMENT '���մ���',
        photo_status varchar(100) COMMENT '����״̬',
        created_date timestamp COMMENT '��������',
        updated_date timestamp COMMENT '�޸�����',
        created_by varchar(100) COMMENT '������',
        updated_by varchar(100) COMMENT '�޸���'
)COMMENT='������Ϣ��¼��';

drop table enum_info;
create table enum_info(
        id int COMMENT '���',
        code_type varchar(100) COMMENT '��������',
        code_desc varchar(100) COMMENT '��������',
        code_id varchar(100) COMMENT '������',
        code_name varchar(100) COMMENT '��������',
        created_date timestamp COMMENT '��������',
        updated_date timestamp COMMENT '�޸�����',
        created_by varchar(100) COMMENT '������',
        updated_by varchar(100) COMMENT '�޸���'
)COMMENT = '������Ϣ��';

drop table device_info;
create table device_info(
        user_name varchar(100),
        to_ken varchar(100) COMMENT 'toKen',
        device_number varchar(100) COMMENT '�豸��',
        IP_addr varchar(100) COMMENT 'IP��ַ',
         created_date timestamp COMMENT '��������',
        updated_date timestamp COMMENT '�޸�����',
        created_by varchar(100) COMMENT '������',
        updated_by varchar(100) COMMENT '�޸���'
)COMMENT='�豸��Ϣ��';

drop table sys_config_tables;
CREATE TABLE sys_config_tables(
        id int COMMENT '���',
        table_name varchar(150) COMMENT '����',
        column_name varchar(150) COMMENT '���ֶ�',
        column_desc varchar(150) COMMENT '���ֶ�����',
        is_null varchar(10) COMMENT '�Ƿ�Ϊ��',
        column_length  int default -1 COMMENT '�ֶγ���',
        is_primary_key varchar(10) COMMENT '�Ƿ�����',
        created_date timestamp COMMENT '��������',
        updated_date timestamp COMMENT '�޸�����',
        created_by varchar(100) COMMENT '������',
        updated_by varchar(100) COMMENT '�޸���'
)COMMENT='��ṹ��Ϣ��';


drop table user_member_relation;
create table user_member_relation(
        id int COMMENT '���',
        user_name varchar(100) COMMENT '�û���',
        member_id int COMMENT '��Ա���',
        member_type varchar(100) COMMENT '��Ա����',
        created_date timestamp COMMENT '��������',
        updated_date timestamp COMMENT '�޸�����',
        created_by varchar(100) COMMENT '������',
        updated_by varchar(100) COMMENT '�޸���'
)COMMENT='�û���Ա��ϵ��';


drop table member_price_info;
create table member_price_info(
	id int COMMENT '���',
	member_id int COMMENT '��Ա���',
	card_type varchar(100) COMMENT '������',
	card_type_desc varchar(100) COMMENT '����������',
	card_price double COMMENT '���۸�',
	created_date timestamp COMMENT '��������',
  updated_date timestamp COMMENT '�޸�����',
  created_by varchar(100) COMMENT '������',
  updated_by varchar(100) COMMENT '�޸���'
)COMMENT='��Ա�۸���Ϣ��';




