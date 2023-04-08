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

