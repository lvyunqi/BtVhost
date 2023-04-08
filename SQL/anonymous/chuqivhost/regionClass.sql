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

