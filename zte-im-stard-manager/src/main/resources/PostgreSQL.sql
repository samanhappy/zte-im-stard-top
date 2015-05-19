-- Table: td_desk_software_info

-- DROP TABLE td_desk_software_info;

CREATE TABLE td_desk_software_info
(
  id integer NOT NULL DEFAULT 0,
  software_version integer,
  update_url text NOT NULL,
  forcetwo integer NOT NULL DEFAULT 0,
  update_content text NOT NULL,
  update_time date NOT NULL DEFAULT '1753-01-01'::date,
  client_type character varying(50) NOT NULL,
  user_id integer NOT NULL DEFAULT 0,
  is_active integer NOT NULL DEFAULT 0,
  version_name character varying(20) NOT NULL,
  is_enable integer NOT NULL DEFAULT 0,
  software1 character varying(20),
  software2 character varying(20),
  software3 character varying(20),
  apk_size integer,
  CONSTRAINT pk_software_info PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE td_desk_software_info
  OWNER TO dbuser;


-- Table: uc_group

-- DROP TABLE uc_group;

CREATE TABLE uc_group
(
  id character varying(40) NOT NULL,
  tenant_id character varying(40) NOT NULL,
  type_id character varying(40) NOT NULL DEFAULT 'dept'::character varying, -- 群组类型ID
  pid character varying(40) DEFAULT '’‘'::character varying, -- 空串，则为顶级群组，指向ID
  name character varying(255) NOT NULL,
  pinyin character varying(255) NOT NULL, -- 首字母 全拼
  full_id character varying(1000), -- 上级.full_id+id组成
  full_name character varying(1000), -- 使用‘\’分隔的整个群组搜索目录
  mail character varying(255),
  sequ numeric(10,0) NOT NULL, -- 倒序排列
  creator character varying(40) NOT NULL,
  create_time date,
  modifier character varying(40),
  modified_time date,
  CONSTRAINT pk_uc_group PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE uc_group
  OWNER TO dbuser;
COMMENT ON TABLE uc_group
  IS '群组除了通过上级关系进行递归搜索外，为了增加搜索效率和减低搜素实现难度，增加full_id。

进行查询某一节点所有下级节点时，只需 full_id like ''#full_id#%'' 即可查询。

增：full_id = parent.full_id + id
删：根据级联删除选项进行删除，如果级联删除成员，群组以及下级群组和成员、群组成员关系一起删除；如果不级联删除，需要删除群组以及下级群组和群组成员关系；
改：如parent做了修改，就需要修改同步更新其以及下级的full_id。update full_id = replace(full_id, parent.full_id, new_parent.full_id)

另外GROUP_ID相同表示同一群组，只是其memberOf不同的上级群组。

';
COMMENT ON COLUMN uc_group.type_id IS '群组类型ID';
COMMENT ON COLUMN uc_group.pid IS '空串，则为顶级群组，指向ID';
COMMENT ON COLUMN uc_group.pinyin IS '首字母 全拼';
COMMENT ON COLUMN uc_group.full_id IS '上级.full_id+id组成';
COMMENT ON COLUMN uc_group.full_name IS E'使用‘\\’分隔的整个群组搜索目录';
COMMENT ON COLUMN uc_group.sequ IS '倒序排列';


-- Table: uc_group_class

-- DROP TABLE uc_group_class;

CREATE TABLE uc_group_class
(
  id character varying(40) NOT NULL,
  class_name character varying(255) NOT NULL, -- 分类名称
  CONSTRAINT pk_uc_group_class PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE uc_group_class
  OWNER TO dbuser;
COMMENT ON TABLE uc_group_class
  IS '默认数据：

ID ：dept  Name：部门
';
COMMENT ON COLUMN uc_group_class.class_name IS '分类名称';



-- Table: uc_group_member

-- DROP TABLE uc_group_member;

CREATE TABLE uc_group_member
(
  id character varying(40) NOT NULL,
  tenant_id character varying(40) NOT NULL,
  group_id character varying(40) NOT NULL,
  member_id character varying(40),
  CONSTRAINT pk_uc_group_member PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE uc_group_member
  OWNER TO dbuser;




-- Table: uc_member

-- DROP TABLE uc_member;

CREATE TABLE uc_member
(
  id character varying(40) NOT NULL,
  tenant_id character varying(40) NOT NULL,
  cn character varying(255), -- 不显示维护，由姓和名组合而成，同组下姓名唯一
  pinyin character varying(255), -- displayName的拼音 首字母 全拼
  t9_pinyin character varying(255), -- displayName的拼音 首字母 全拼的T9键盘映射
  nickname character varying(255),
  sex character(1) DEFAULT '1'::bpchar, -- 1 男 0 女
  birthday date,
  photo character varying(128), -- 照片对应的附件id
  description character varying(1000),
  idcard character varying(64),
  mobile character varying(64),
  telephonenumber character varying(64),
  hometelephone character varying(64),
  othertelephone character varying(255), -- 多个号码用分号分隔
  fax character varying(64),
  shortcode character varying(64),
  ipphone character varying(64),
  mail character varying(255),
  othermail character varying(255),
  qq character varying(64),
  msn character varying(255),
  wwwhomepage character varying(255),
  co character varying(64), -- full name of country
  st character varying(64),
  l character varying(64),
  streetaddress character varying(255),
  postaladdress character varying(255),
  postalcode character varying(64),
  superior_id character varying(64), -- 上级ID
  info character varying(1000),
  sequ numeric(10,0) NOT NULL, -- 倒序排列
  custom_field1 character varying(512),
  custom_field2 character varying(512),
  custom_field3 character varying(512),
  custom_field4 character varying(512),
  custom_field5 character varying(512),
  custom_field6 character varying(512),
  custom_field7 character varying(512),
  custom_field8 character varying(512),
  custom_field9 character varying(512),
  custom_field10 character varying(512),
  photo_sign character varying(512), -- MD5摘要
  state character varying(5) NOT NULL,
  uid character varying(40),
  password character varying(255), -- SHA1 hash encode
  permit_ip character varying(1000),
  usable character varying(5) NOT NULL, -- 是否可用
  expired_time date,
  last_login_time date,
  passwd_update_time date,
  user_type character varying(64), -- admin:管理员 user:普通用户
  editable character varying(5) NOT NULL,
  deletable character varying(5) NOT NULL,
  creator character varying(40) NOT NULL,
  create_time date,
  modifier character varying(40),
  modified_time date,
  CONSTRAINT pk_uc_member PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE uc_member
  OWNER TO dbuser;
COMMENT ON TABLE uc_member
  IS '
参见：http://www.zytrax.com/books/ldap/ape/#person

成员删除时需要同步删除：群组成员关系、自定义群组关系以及添加成员删除记录';
COMMENT ON COLUMN uc_member.cn IS '不显示维护，由姓和名组合而成，同组下姓名唯一';
COMMENT ON COLUMN uc_member.pinyin IS 'displayName的拼音 首字母 全拼';
COMMENT ON COLUMN uc_member.t9_pinyin IS 'displayName的拼音 首字母 全拼的T9键盘映射';
COMMENT ON COLUMN uc_member.sex IS '1 男 0 女';
COMMENT ON COLUMN uc_member.photo IS '照片对应的附件id';
COMMENT ON COLUMN uc_member.othertelephone IS '多个号码用分号分隔';
COMMENT ON COLUMN uc_member.co IS 'full name of country';
COMMENT ON COLUMN uc_member.superior_id IS '上级ID';
COMMENT ON COLUMN uc_member.sequ IS '倒序排列';
COMMENT ON COLUMN uc_member.photo_sign IS 'MD5摘要';
COMMENT ON COLUMN uc_member.password IS 'SHA1 hash encode';
COMMENT ON COLUMN uc_member.usable IS '是否可用';
COMMENT ON COLUMN uc_member.user_type IS 'admin:管理员 user:普通用户';




-- Table: uc_tenant

-- DROP TABLE uc_tenant;

CREATE TABLE uc_tenant
(
  id character varying(40) NOT NULL,
  platform_id character varying(40) NOT NULL,
  ecid character varying(64),
  name character varying(255) NOT NULL,
  catalog_id character varying(40), -- 数据来自于AD_LOV_CODE中分类为BUSINESS_CATALOG的数据
  max_users numeric(10,0) NOT NULL,
  address character varying(512),
  linkman character varying(255),
  mobile character varying(64),
  mail character varying(255),
  tel character varying(64),
  fax character varying(64),
  org_code character varying(64), -- 组织机构代码
  license_file character varying(40), -- 企业营业执照附件
  user_count numeric(10,0) NOT NULL,
  audited character varying(5) NOT NULL DEFAULT 'false'::character varying, -- true 已审 false 未审
  initialized character varying(5) NOT NULL DEFAULT 'false'::character varying, -- true 已初始化 false 未初始化
  usable character varying(5) NOT NULL DEFAULT 'false'::character varying, -- true 启用 false 停用
  pause_time date,
  is_fixed character varying(5) NOT NULL DEFAULT 'false'::character varying, -- true 内置 false-非内置...
  creator character varying(40) NOT NULL,
  create_time date,
  modifier character varying(40),
  modified_time date,
  CONSTRAINT pk_uc_tenant PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE uc_tenant
  OWNER TO dbuser;
COMMENT ON TABLE uc_tenant
  IS '企业新增时，在群组中默认添加类型为dept，id为tenantId的根部门。';
COMMENT ON COLUMN uc_tenant.catalog_id IS '数据来自于AD_LOV_CODE中分类为BUSINESS_CATALOG的数据';
COMMENT ON COLUMN uc_tenant.org_code IS '组织机构代码';
COMMENT ON COLUMN uc_tenant.license_file IS '企业营业执照附件';
COMMENT ON COLUMN uc_tenant.audited IS 'true 已审 false 未审';
COMMENT ON COLUMN uc_tenant.initialized IS 'true 已初始化 false 未初始化';
COMMENT ON COLUMN uc_tenant.usable IS 'true 启用 false 停用';
COMMENT ON COLUMN uc_tenant.is_fixed IS 'true 内置 false-非内置
内置企业不出现在企业管理及接口中';

-- Table: user_permission

-- DROP TABLE user_permission;

CREATE TABLE user_permission
(
  id character varying(40) NOT NULL, -- 主键
  member_id character varying(40), -- 用户id
  tenant_id character varying(40), -- 公司id
  perm_type integer, -- 权限类型，1职位可看，2部门可看，3人员可看
  perm_id character varying(40), -- 允许权限id
  perm_name character varying(40), -- 允许权限名称
  CONSTRAINT pk_id PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE user_permission
  OWNER TO dbuser;
GRANT ALL ON TABLE user_permission TO public;
GRANT ALL ON TABLE user_permission TO postgres;
COMMENT ON COLUMN user_permission.id IS '主键';
COMMENT ON COLUMN user_permission.member_id IS '用户id';
COMMENT ON COLUMN user_permission.tenant_id IS '公司id';
COMMENT ON COLUMN user_permission.perm_type IS '权限类型，1职位可看，2部门可看，3人员可看';
COMMENT ON COLUMN user_permission.perm_id IS '允许权限id';
COMMENT ON COLUMN user_permission.perm_name IS '允许权限名称';

-- Table: zte_im_apk

-- DROP TABLE zte_im_apk;

CREATE TABLE zte_im_apk
(
  "ID" serial NOT NULL,
  "CATE_ID" integer,
  "NAME" character varying(30),
  "IMG" character varying(1024),
  "APK_URL" character varying(1024),
  "IS_ENABLE" integer,
  "UPDATE_TIME" timestamp without time zone,
  "USER_ID" integer,
  "BANNER" character varying(1024),
  "IS_CHOICE" integer,
  "IS_RECOMMEND" integer,
  CONSTRAINT zte_im_apk_pkey PRIMARY KEY ("ID")
)
WITH (
  OIDS=FALSE
);
ALTER TABLE zte_im_apk
  OWNER TO dbuser;
-- Table: zte_im_category

-- DROP TABLE zte_im_category;

CREATE TABLE zte_im_category
(
  "ID" serial NOT NULL,
  "NAME" character varying(20),
  "IS_ORDER" integer,
  "IMG" character varying(1024),
  "IS_ENABLE" integer,
  "UPDATE_TIME" timestamp without time zone,
  "USER_ID" integer,
  CONSTRAINT zte_im_category_pkey PRIMARY KEY ("ID")
)
WITH (
  OIDS=FALSE
);
ALTER TABLE zte_im_category
  OWNER TO dbuser;


-- Table: zte_im_software

-- DROP TABLE zte_im_software;

CREATE TABLE zte_im_software
(
  "ID" serial NOT NULL,
  "VERSION" integer,
  "VERSION_NAME" character varying(64),
  "UPDATE_URL" character varying(256),
  "CLIENT_TYPE" character varying(32),
  "UPDATE_TIME" date,
  "IS_ENABLE" integer,
  CONSTRAINT zte_im_software_pkey PRIMARY KEY ("ID")
)
WITH (
  OIDS=FALSE
);
ALTER TABLE zte_im_software
  OWNER TO dbuser;


-- Table: zte_im_view

-- DROP TABLE zte_im_view;

CREATE TABLE zte_im_view
(
  id integer NOT NULL,
  mobile character varying(20),
  "VIEWS" character varying(1024),
  "USER_ID" integer,
  "UPDATE_TIME" timestamp without time zone,
  "VERSION" integer,
  CONSTRAINT zte_im_view_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE zte_im_view
  OWNER TO dbuser;


  
 
-- 插入职位角色数据
INSERT INTO uc_group(
            id, tenant_id, type_id, pid, name, pinyin, full_id, full_name, 
            mail, sequ, creator, create_time, modifier, modified_time)
    VALUES ('1', 'c1', 'position', '', '员工', '', Null, Null, 
            Null, 1, 1, Null, Null, Null);

INSERT INTO uc_group(
            id, tenant_id, type_id, pid, name, pinyin, full_id, full_name, 
            mail, sequ, creator, create_time, modifier, modified_time)
    VALUES ('2', 'c1', 'position', '', '主管', '', Null, Null, 
            Null, 1, 1, Null, Null, Null);

INSERT INTO uc_group(
            id, tenant_id, type_id, pid, name, pinyin, full_id, full_name, 
            mail, sequ, creator, create_time, modifier, modified_time)
    VALUES ('3', 'c1', 'position', '', 'CEO', '', Null, Null, 
            Null, 1, 1, Null, Null, Null);


