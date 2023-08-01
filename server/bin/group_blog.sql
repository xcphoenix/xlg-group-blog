create table blog
(
    blog_id       bigint unsigned auto_increment comment '博客id'
        primary key,
    source_id     varchar(50)          not null comment '博客来源Id',
    uid           bigint unsigned      not null,
    title         varchar(200)         not null comment '标题',
    author        varchar(10)          null comment '作者',
    is_original   tinyint(1) default 1 not null comment '是否原创',
    pub_time      datetime             not null comment '发布时间',
    summary       varchar(300)         null comment '概要',
    content       mediumtext           null comment '文章内容',
    original_link varchar(255)         null comment '原文链接',
    tags          varchar(100)         null comment '标签',
    constraint UNI_SOURCE_ID
        unique (source_id)
)
    engine = MyISAM
    collate = utf8mb4_unicode_520_ci;

create fulltext index title
    on blog (title, content);

create index uid
    on blog (uid);


create table blog_type
(
    type_id        int unsigned auto_increment comment '类型id'
        primary key,
    type_name      varchar(10)  not null comment '类型名',
    overview_rule  varchar(256) null comment '用户中心表达式',
    blog_page_rule varchar(256) null comment '博客页url表达式',
    feed_rule      varchar(256) null comment 'rss订阅',
    need_arg       varchar(256) null comment '博客类型需要的参数',
    bean_name      varchar(20)  not null comment '执行抓取任务bean的名字'
)
    collate = utf8mb4_unicode_520_ci;

create table category
(
    category_id int unsigned auto_increment comment '分类id'
        primary key,
    name        varchar(10) not null comment '分类名',
    constraint name
        unique (name)
)
    collate = utf8mb4_unicode_520_ci;

create table tag
(
    tag_id   int unsigned not null comment '标签id',
    tag_name varchar(10)  not null comment '标签名'
)
    collate = utf8mb4_unicode_520_ci;

create table user
(
    uid           bigint unsigned auto_increment comment '用户id'
        primary key,
    username      varchar(10)  not null comment '用户名',
    password      varchar(35)  not null comment '用户密码',
    qq            varchar(15)  null comment 'QQ 号',
    signature     varchar(25)  null comment '个性签名',
    blog_type     int unsigned not null comment '博客类型',
    blog_arg      varchar(256) not null,
    last_pub_time datetime     not null comment '最新发布博客的时间',
    category_id   int unsigned not null comment '分组id',
    authority     int          not null comment '权限',
    avatar_url    varchar(256) null,
    constraint FK_USER_CATEGORY
        foreign key (category_id) references category (category_id),
    constraint FK_USER_TYPE
        foreign key (blog_type) references blog_type (type_id)
)
    collate = utf8mb4_unicode_520_ci;

create index blog_type
    on user (blog_type);

create index category_id
    on user (category_id);