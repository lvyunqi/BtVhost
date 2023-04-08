create table apiKey
(
    appId      int auto_increment
        primary key,
    appKey     varchar(255) not null,
    secretKey  varchar(255) not null,
    createDate datetime     not null
);

create table apiToken
(
    id      int auto_increment
        primary key,
    appid   int          not null,
    token   varchar(255) not null,
    endTime bigint       not null
);

create table regionClass
(
    id          int(50) auto_increment
        primary key,
    firstClass  varchar(255)             not null,
    parentClass varchar(255) default '0' null,
    nodeList    json                     null,
    scheduling  int(1)                   null comment '1=顺序，2=自动'
)
    comment '地区分类';

create table serverNode
(
    id         int auto_increment
        primary key,
    host       varchar(255)  not null comment '节点地址',
    port       int           not null comment '端口',
    skey       varchar(255)  not null comment '服务器接口密钥',
    probeToken varchar(255)  not null comment '探针token',
    os         int default 0 not null comment '服务器操作系统 默认0=Linux 1=Windows',
    soft       int default 0 not null comment '环境架构 默认0=LNMP 1=LAMP 2=IIS 3=LNP 4=LAP',
    serverSSL  int default 0 not null comment '服务器是否开启SSL 默认0=关闭 1=开启',
    groupClass int           null comment '区域所属',
    satus      int default 0 not null comment '状态 默认0=开启 1=关闭',
    createDate datetime      null comment '创建时间'
);

create table sysConfig
(
    id                        int                not null
        primary key,
    probeCronRedisToMysqlTime int default 120000 not null comment 'redis监控数据持久化监控间隔'
);

create table user
(
    id              int auto_increment
        primary key,
    username        varchar(255) null comment '用户名',
    password        varchar(255) null comment '密码',
    email           varchar(50)  null comment '邮箱',
    mobil           varchar(20)  null comment '手机号',
    permissionGroup int          null comment '权限组'
)
    comment '超管表';

create table vhostData
(
    id          bigint auto_increment
        primary key,
    vhostName   varchar(255) not null comment '虚拟主机名',
    requestCode int          null comment '请求状态码',
    requestSend mediumtext   null comment '请求流量',
    requestIP   varchar(255) null comment '请求IP',
    requestDate datetime     null,
    request     text         null comment '请求',
    httpReferer text         null comment '请求来源',
    serverID    int          not null comment '服务器id'
);


