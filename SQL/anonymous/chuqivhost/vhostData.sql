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

