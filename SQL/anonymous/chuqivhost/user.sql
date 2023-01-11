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

