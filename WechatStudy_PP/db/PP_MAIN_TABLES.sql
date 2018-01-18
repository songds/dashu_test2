drop table subject_curriculum_rel;
drop table subject_section_rel;
drop table curriculum_section_rel;
drop table section_topic_rel;
drop table free_topic_info;
drop table topic_error_info;
drop table topic_enshrine_info;
drop table topic_status_info;
drop table topic_select_info;
drop table topic_info;
drop table subject_info;
drop table curriculum_info;
drop table section_info;
create table subject_info(
        subject_id int primary key AUTO_INCREMENT COMMENT '科目编号',
        subject_name varchar(100) COMMENT '科目名称',
        subject_type varchar(100) COMMENT '科目类型',
        is_leaf_subject varchar(100) COMMENT '是否叶子科目',
        parent_subject_id int COMMENT '父科目编号',
        subject_name_letter varchar(100) COMMENT '科目名称对应字母',
        created_by VARCHAR(150) NOT NULL DEFAULT 'SYSTEM' COMMENT '创建人',
	    created_date DATETIME  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
	    updated_by VARCHAR(150) NOT NULL DEFAULT 'SYSTEM' COMMENT '更新人',
	    updated_date DATETIME  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日期'
)COMMENT='科目信息表';


create table curriculum_info(
        curriculum_id int primary key AUTO_INCREMENT COMMENT '课程编号',
        curriculum_name varchar(100) COMMENT '课程名称',
        created_by VARCHAR(150) NOT NULL DEFAULT 'SYSTEM' COMMENT '创建人',
	    created_date DATETIME  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
	    updated_by VARCHAR(150) NOT NULL DEFAULT 'SYSTEM' COMMENT '更新人',
	    updated_date DATETIME  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日期'
)COMMENT='课程信息表';



create table section_info(
        section_id int primary key AUTO_INCREMENT COMMENT '章节编号',
        section_name varchar(100) COMMENT '章节名称',
        parent_section_id int COMMENT '父章节编号',
        is_leaf_section varchar(100) COMMENT '是否叶子章节',
        topic_count int DEFAULT 0 COMMENT '题目数量',
        created_by VARCHAR(150) NOT NULL DEFAULT 'SYSTEM' COMMENT '创建人',
	    created_date DATETIME  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
	    updated_by VARCHAR(150) NOT NULL DEFAULT 'SYSTEM' COMMENT '更新人',
	    updated_date DATETIME  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日期'
)COMMENT='章节信息表';



create table  topic_info(
        topic_id int primary key AUTO_INCREMENT COMMENT '题目编号',
        section_id INT NOT NULL  COMMENT '章节编号',
        topic_name varchar(100) COMMENT '题目名称',
        topic_content varchar(1000) COMMENT '题目内容',
        topice_type varchar(100) COMMENT '题目类型',
        anlitxt text COMMENT '题目案例文本',
        AnliList text COMMENT '题目案例列表',
        answer_exp text COMMENT '题目答案解析',
        is_correct_select varchar(100) COMMENT '是否正确选项',
        created_by VARCHAR(150) NOT NULL DEFAULT 'SYSTEM' COMMENT '创建人',
	    created_date DATETIME  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
	    updated_by VARCHAR(150) NOT NULL DEFAULT 'SYSTEM' COMMENT '更新人',
	    updated_date DATETIME  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日期'
)COMMENT='题目信息表';


create table topic_select_info(
        id int primary key AUTO_INCREMENT COMMENT '编号',
        topic_id int COMMENT '题目编号',
        select_id varchar(100) COMMENT '题目选项编号',
        select_content varchar(1000) COMMENT '题目内容',
        is_correct_select varchar(100) COMMENT '是否正确选项',
        created_by VARCHAR(150) NOT NULL DEFAULT 'SYSTEM' COMMENT '创建人',
	    created_date DATETIME  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
	    updated_by VARCHAR(150) NOT NULL DEFAULT 'SYSTEM' COMMENT '更新人',
	    updated_date DATETIME  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日期'
)COMMENT='题目对应选项';



create table topic_status_info(
	id int primary key AUTO_INCREMENT COMMENT '编号',
	user_name varchar(100) NOT NULL COMMENT '用户名',
	topic_id int NOT NULL COMMENT '题目编号',
	topic_status int NOT NULL COMMENT '题目状态',
    created_by VARCHAR(150) NOT NULL DEFAULT 'SYSTEM' COMMENT '创建人',
    created_date DATETIME  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
    updated_by VARCHAR(150) NOT NULL DEFAULT 'SYSTEM' COMMENT '更新人',
    updated_date DATETIME  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日期'
)COMMENT='用户题目状态信息表';


create table topic_enshrine_info(
	id int primary key AUTO_INCREMENT COMMENT '编号',
	user_name varchar(100) NOT NULL COMMENT '用户名',
	topic_id int  COMMENT '题目编号',
    section_id INT NOT NULL  COMMENT '章节编号',
    topic_name varchar(100) COMMENT '题目名称',
    topic_content varchar(1000) COMMENT '题目内容',
    topic_type varchar(100) COMMENT '题目类型',
    anlitxt text COMMENT '题目案例文本',
    AnliList text COMMENT '题目案例列表',
    answer_exp text COMMENT '题目答案解析',
    is_correct_select varchar(100) COMMENT '是否正确选项',
    created_by VARCHAR(150) NOT NULL DEFAULT 'SYSTEM' COMMENT '创建人',
    created_date DATETIME  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
    updated_by VARCHAR(150) NOT NULL DEFAULT 'SYSTEM' COMMENT '更新人',
    updated_date DATETIME  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日期'
)COMMENT='用户题目收藏信息表';


create table topic_error_info(
	id int primary key AUTO_INCREMENT COMMENT '编号',
	user_name varchar(100) NOT NULL COMMENT '用户名',
	topic_id int COMMENT '题目编号',
    section_id INT NOT NULL  COMMENT '章节编号',
    topic_name varchar(100) COMMENT '题目名称',
    topic_content varchar(1000) COMMENT '题目内容',
    topic_type varchar(100) COMMENT '题目类型',
    anlitxt text COMMENT '题目案例文本',
    AnliList text COMMENT '题目案例列表',
    answer_exp text COMMENT '题目答案解析',
    is_correct_select varchar(100) COMMENT '是否正确选项',
    created_by VARCHAR(150) NOT NULL DEFAULT 'SYSTEM' COMMENT '创建人',
    created_date DATETIME  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
    updated_by VARCHAR(150) NOT NULL DEFAULT 'SYSTEM' COMMENT '更新人',
    updated_date DATETIME  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日期'
)COMMENT='用户题目错题记录表';


create table  free_topic_info(
	id int primary key AUTO_INCREMENT COMMENT '编号',
	topic_id int COMMENT '题目编号',
	subject_id INT NOT NULL  COMMENT '科目编号',
	topic_name varchar(100) COMMENT '题目名称',
	topic_content varchar(1000) COMMENT '题目内容',
	topice_type varchar(100) COMMENT '题目类型',
	anlitxt text COMMENT '题目案例文本',
	AnliList text COMMENT '题目案例列表',
	answer_exp text COMMENT '题目答案解析',
	is_correct_select varchar(100) COMMENT '是否正确选项',
	created_by VARCHAR(150) NOT NULL DEFAULT 'SYSTEM' COMMENT '创建人',
	created_date DATETIME  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
	updated_by VARCHAR(150) NOT NULL DEFAULT 'SYSTEM' COMMENT '更新人',
	updated_date DATETIME  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日期'
)COMMENT='免费题目信息表';





create table subject_curriculum_rel(
        id int primary key AUTO_INCREMENT COMMENT '编号',
        subject_id int COMMENT '科目编号',
        curriculum_id int COMMENT '课程编号',
    created_by VARCHAR(150) NOT NULL DEFAULT 'SYSTEM' COMMENT '创建人',
    created_date DATETIME  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
    updated_by VARCHAR(150) NOT NULL DEFAULT 'SYSTEM' COMMENT '更新人',
    updated_date DATETIME  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日期'
)COMMENT='科目课程关系表';

CREATE TABLE subject_section_rel (
        id INT NOT NULL AUTO_INCREMENT COMMENT '编号',
        section_id INT COMMENT '章节编号',
        subject_id INT COMMENT '科目编号',
	    created_by VARCHAR(150) NOT NULL DEFAULT 'SYSTEM' COMMENT '创建人',
	    created_date DATETIME  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
	    updated_by VARCHAR(150) NOT NULL DEFAULT 'SYSTEM' COMMENT '更新人',
	    updated_date DATETIME  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日期',
        PRIMARY KEY (id)
) COMMENT='科目章节关系表';

create table curriculum_section_rel(
        id int primary key AUTO_INCREMENT COMMENT '编号',
        section_id int COMMENT '章节编号',
        curriculum_id int COMMENT '课程编号',
	    created_by VARCHAR(150) NOT NULL DEFAULT 'SYSTEM' COMMENT '创建人',
	    created_date DATETIME  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
	    updated_by VARCHAR(150) NOT NULL DEFAULT 'SYSTEM' COMMENT '更新人',
	    updated_date DATETIME  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日期'
)COMMENT='课程章节关系表';

create table section_topic_rel(
        id int primary key AUTO_INCREMENT COMMENT '编号',
        section_id int COMMENT '章节编号',
        topic_id int COMMENT '题目编号',
	    created_by VARCHAR(150) NOT NULL DEFAULT 'SYSTEM' COMMENT '创建人',
	    created_date DATETIME  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
	    updated_by VARCHAR(150) NOT NULL DEFAULT 'SYSTEM' COMMENT '更新人',
	    updated_date DATETIME  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日期'
)COMMENT='章节题目关系表';

create table user_look_topic(
		id int primary key AUTO_INCREMENT COMMENT '编号',
        user_name varchar(100) NOT NULL COMMENT '用户名',
        topic_id int COMMENT '题目编号',
	    created_by VARCHAR(150) NOT NULL DEFAULT 'SYSTEM' COMMENT '创建人',
	    created_date DATETIME  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
	    updated_by VARCHAR(150) NOT NULL DEFAULT 'SYSTEM' COMMENT '更新人',
	    updated_date DATETIME  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日期'
)COMMENT='用户看题记录表';
--添加外键
alter table subject_curriculum_rel add constraint sub_curr_rel_subFK foreign key(subject_id)  references subject_info(subject_id) ;
alter table subject_curriculum_rel add constraint sub_curr_rel_currFK foreign key(curriculum_id)  references curriculum_info(curriculum_id) ;


alter table subject_section_rel add constraint sub_sec_rel_subFK foreign key(subject_id)  references subject_info(subject_id) ;
alter table subject_section_rel add constraint sub_sec_rel_secFK foreign key(section_id)  references section_info(section_id) ;


alter table curriculum_section_rel add constraint curr_sec_rel_currFK foreign key(curriculum_id)  references curriculum_info(curriculum_id) ;
alter table curriculum_section_rel add constraint curr_sec_rel_secFK foreign key(section_id)  references section_info(section_id) ;


--alter table section_topic_rel add constraint sec_top_rel_topFK foreign key(topic_id)  references topic_info(topic_id) ;
--alter table section_topic_rel add constraint sec_top_rel_secFK foreign key(section_id)  references section_info(section_id) ;


alter table topic_select_info add constraint top_sel_rel_topFK foreign key(topic_id)  references topic_info(topic_id) ;