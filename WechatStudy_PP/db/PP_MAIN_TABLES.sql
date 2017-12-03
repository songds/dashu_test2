drop table subject_info;
create table subject_info(
        subject_id int primary key AUTO_INCREMENT COMMENT '科目编号',
        subject_name varchar(100) COMMENT '科目名称',
        subject_type varchar(100) COMMENT '科目类型',
        is_leaf_subject varchar(100) COMMENT '是否叶子科目',
        parent_subject_id int COMMENT '父科目编号',
        subject_name_letter varchar(100) COMMENT '科目名称对应字母',
        created_date timestamp  DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
        updated_date timestamp  DEFAULT CURRENT_TIMESTAMP COMMENT '修改日期',
        created_by varchar(100)  COMMENT '创建人',
        updated_by varchar(100)  COMMENT '修改人'
)COMMENT='科目信息表';

drop table subject_curriculum_rel;
create table subject_curriculum_rel(
        id int primary key AUTO_INCREMENT COMMENT '编号',
        subject_id int COMMENT '科目编号',
        curriculum_id int COMMENT '课程编号',
        created_date timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
        updated_date timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '修改日期',
        created_by varchar(100) COMMENT '创建人',
        updated_by varchar(100) COMMENT '修改人'
)COMMENT='科目课程关系表';

CREATE TABLE subject_section_rel (
        id INT NOT NULL AUTO_INCREMENT COMMENT '编号',
        section_id INT COMMENT '章节编号',
        subject_id INT COMMENT '科目编号',
        created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
        updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '修改日期',
        created_by VARCHAR(100) COMMENT '创建人',
        updated_by VARCHAR(100) COMMENT '修改人',
        PRIMARY KEY (id)
) COMMENT='科目章节关系表';

drop table curriculum_info;
create table curriculum_info(
        curriculum_id int primary key AUTO_INCREMENT COMMENT '课程编号',
        curriculum_name varchar(100) COMMENT '课程名称',
        created_date timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
        updated_date timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '修改日期',
        created_by varchar(100) COMMENT '创建人',
        updated_by varchar(100) COMMENT '修改人'
)COMMENT='课程信息表';

drop table curriculum_section_rel;
create table curriculum_section_rel(
        id int primary key AUTO_INCREMENT COMMENT '编号',
        section_id int COMMENT '章节编号',
        curriculum_id int COMMENT '课程编号',
        created_date timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
        updated_date timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '修改日期',
        created_by varchar(100) COMMENT '创建人',
        updated_by varchar(100) COMMENT '修改人'
)COMMENT='课程章节关系表';

drop table section_info;
create table section_info(
        section_id int primary key AUTO_INCREMENT COMMENT '章节编号',
        section_name varchar(100) COMMENT '章节名称',
        parent_section_id int COMMENT '父章节编号',
        is_leaf_section varchar(100) COMMENT '是否叶子章节',
        created_date timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
        updated_date timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '修改日期',
        created_by varchar(100) COMMENT '创建人',
        updated_by varchar(100) COMMENT '修改人'
)COMMENT='章节信息表';

drop table section_topic_rel;
create table section_topic_rel(
        id int primary key AUTO_INCREMENT COMMENT '编号',
        section_id int COMMENT '章节编号',
        topic_id int COMMENT '题目编号',
        created_date timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
        updated_date timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '修改日期',
        created_by varchar(100) COMMENT '创建人',
        updated_by varchar(100) COMMENT '修改人'
)COMMENT='章节题目关系表';

drop table topic_info;
create table  topic_info(
        topic_id int primary key AUTO_INCREMENT COMMENT '题目编号',
        section_id INT NOT NULL  COMMENT '章节编号',
        topic_name varchar(100) COMMENT '题目名称',
        topic_content varchar(1000) COMMENT '题目内容',
        topice_type varchar(100) COMMENT '题目类型',
        created_date timestamp  DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
        updated_date timestamp  DEFAULT CURRENT_TIMESTAMP COMMENT '修改日期',
        created_by varchar(100) COMMENT '创建人',
        updated_by varchar(100) COMMENT '修改人'
)COMMENT='章节信息表';

drop table topice_select_info;
create table topice_select_info(
        id int primary key AUTO_INCREMENT COMMENT '编号',
        topic_id int COMMENT '题目编号',
        select_id varchar(100) COMMENT '题目选项编号',
        select_content varchar(1000) COMMENT '题目内容',
        is_correct_select varchar(100) COMMENT '是否正确选项',
        created_date timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
        updated_date timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '修改日期',
        created_by varchar(100) COMMENT '创建人',
        updated_by varchar(100) COMMENT '修改人'
)COMMENT='题目对应选项';






--添加外键
alter table subject_curriculum_rel add constraint sub_curr_rel_subFK foreign key(subject_id)  references subject_info(subject_id) ;
alter table subject_curriculum_rel add constraint sub_curr_rel_currFK foreign key(curriculum_id)  references curriculum_info(curriculum_id) ;


alter table subject_section_rel add constraint sub_sec_rel_subFK foreign key(subject_id)  references subject_info(subject_id) ;
alter table subject_section_rel add constraint sub_sec_rel_secFK foreign key(section_id)  references section_info(section_id) ;


alter table curriculum_section_rel add constraint curr_sec_rel_currFK foreign key(curriculum_id)  references curriculum_info(curriculum_id) ;
alter table curriculum_section_rel add constraint curr_sec_rel_secFK foreign key(section_id)  references section_info(section_id) ;


--alter table section_topic_rel add constraint sec_top_rel_topFK foreign key(topic_id)  references topic_info(topic_id) ;
--alter table section_topic_rel add constraint sec_top_rel_secFK foreign key(section_id)  references section_info(section_id) ;


alter table topice_select_info add constraint top_sel_rel_topFK foreign key(topic_id)  references topic_info(topic_id) ;