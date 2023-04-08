create table sysConfig
(
    id                        int                not null
        primary key,
    probeCronRedisToMysqlTime int default 120000 not null comment 'redis监控数据持久化监控间隔'
);

