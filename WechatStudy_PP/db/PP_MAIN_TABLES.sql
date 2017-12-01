drop table subject_info;
create table subject_info(
        subject_id int primary key AUTO_INCREMENT COMMENT '��Ŀ���',
        subject_name varchar(100) COMMENT '��Ŀ����',
        subject_type varchar(100) COMMENT '��Ŀ����',
        is_leaf_subject varchar(100) COMMENT '�Ƿ�Ҷ�ӿ�Ŀ',
        parent_subject_id int COMMENT '����Ŀ���',
        subject_name_letter varchar(100) COMMENT '��Ŀ���ƶ�Ӧ��ĸ',
        created_date timestamp  DEFAULT CURRENT_TIMESTAMP COMMENT '��������',
        updated_date timestamp  DEFAULT CURRENT_TIMESTAMP COMMENT '�޸�����',
        created_by varchar(100) DEFAULT CURRENT_TIMESTAMP COMMENT '������',
        updated_by varchar(100) DEFAULT CURRENT_TIMESTAMP COMMENT '�޸���'
)COMMENT='��Ŀ��Ϣ��';

drop table subject_curriculum_rel;
create table subject_curriculum_rel(
        id int primary key AUTO_INCREMENT COMMENT '���',
        subject_id int COMMENT '��Ŀ���',
        curriculum_id int COMMENT '�γ̱��',
        created_date timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '��������',
        updated_date timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '�޸�����',
        created_by varchar(100) COMMENT '������',
        updated_by varchar(100) COMMENT '�޸���'
)COMMENT='��Ŀ�γ̹�ϵ��';

CREATE TABLE subject_section_rel (
        id INT NOT NULL AUTO_INCREMENT COMMENT '���',
        section_id INT COMMENT '�½ڱ��',
        subject_id INT COMMENT '��Ŀ���',
        created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '��������',
        updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '�޸�����',
        created_by VARCHAR(100) COMMENT '������',
        updated_by VARCHAR(100) COMMENT '�޸���',
        PRIMARY KEY (id)
) COMMENT='��Ŀ�½ڹ�ϵ��';

drop table curriculum_info;
create table curriculum_info(
        curriculum_id int primary key AUTO_INCREMENT COMMENT '�γ̱��',
        curriculum_name varchar(100) COMMENT '�γ�����',
        created_date timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '��������',
        updated_date timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '�޸�����',
        created_by varchar(100) COMMENT '������',
        updated_by varchar(100) COMMENT '�޸���'
)COMMENT='�γ���Ϣ��';

drop table curriculum_section_rel;
create table curriculum_section_rel(
        id int primary key AUTO_INCREMENT COMMENT '���',
        section_id int COMMENT '�½ڱ��',
        curriculum_id int COMMENT '�γ̱��',
        created_date timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '��������',
        updated_date timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '�޸�����',
        created_by varchar(100) COMMENT '������',
        updated_by varchar(100) COMMENT '�޸���'
)COMMENT='�γ��½ڹ�ϵ��';

drop table section_info;
create table section_info(
        section_id int primary key AUTO_INCREMENT COMMENT '�½ڱ��',
        section_name varchar(100) COMMENT '�½�����',
        parent_section_id int COMMENT '���½ڱ��',
        is_leaf_section varchar(100) COMMENT '�Ƿ�Ҷ���½�',
        created_date timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '��������',
        updated_date timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '�޸�����',
        created_by varchar(100) COMMENT '������',
        updated_by varchar(100) COMMENT '�޸���'
)COMMENT='�½���Ϣ��';

drop table section_topic_rel;
create table section_topic_rel(
        id int primary key AUTO_INCREMENT COMMENT '���',
        section_id int COMMENT '�½ڱ��',
        topic_id int COMMENT '��Ŀ���',
        created_date timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '��������',
        updated_date timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '�޸�����',
        created_by varchar(100) COMMENT '������',
        updated_by varchar(100) COMMENT '�޸���'
)COMMENT='�½���Ŀ��ϵ��';

drop table topic_info;
create table  topic_info(
        topic_id int primary key AUTO_INCREMENT COMMENT '��Ŀ���',
        topic_name varchar(100) COMMENT '��Ŀ����',
        topic_content varchar(1000) COMMENT '��Ŀ����',
        topice_type varchar(100) COMMENT '��Ŀ����',
        created_date timestamp  DEFAULT CURRENT_TIMESTAMP COMMENT '��������',
        updated_date timestamp  DEFAULT CURRENT_TIMESTAMP COMMENT '�޸�����',
        created_by varchar(100) COMMENT '������',
        updated_by varchar(100) COMMENT '�޸���'
)COMMENT='�½���Ϣ��';

drop table topice_select_info;
create table topice_select_info(
        id int primary key AUTO_INCREMENT COMMENT '���',
        topic_id int COMMENT '��Ŀ���',
        select_id varchar(100) COMMENT '��Ŀѡ����',
        select_content varchar(1000) COMMENT '��Ŀ����',
        is_correct_select varchar(100) COMMENT '�Ƿ���ȷѡ��',
        created_date timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '��������',
        updated_date timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '�޸�����',
        created_by varchar(100) COMMENT '������',
        updated_by varchar(100) COMMENT '�޸���'
)COMMENT='��Ŀ��Ӧѡ��';






--������
alter table subject_curriculum_rel add constraint sub_curr_rel_subFK foreign key(subject_id)  references subject_info(subject_id) ;
alter table subject_curriculum_rel add constraint sub_curr_rel_currFK foreign key(curriculum_id)  references curriculum_info(curriculum_id) ;


alter table subject_section_rel add constraint sub_sec_rel_subFK foreign key(subject_id)  references subject_info(subject_id) ;
alter table subject_section_rel add constraint sub_sec_rel_secFK foreign key(section_id)  references section_info(section_id) ;


alter table curriculum_section_rel add constraint curr_sec_rel_currFK foreign key(curriculum_id)  references curriculum_info(curriculum_id) ;
alter table curriculum_section_rel add constraint curr_sec_rel_secFK foreign key(section_id)  references section_info(section_id) ;


alter table section_topic_rel add constraint sec_top_rel_topFK foreign key(topic_id)  references topic_info(topic_id) ;
alter table section_topic_rel add constraint sec_top_rel_secFK foreign key(section_id)  references section_info(section_id) ;


alter table topice_select_info add constraint top_sel_rel_topFK foreign key(topic_id)  references topic_info(topic_id) ;