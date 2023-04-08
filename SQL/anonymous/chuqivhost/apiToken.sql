create table apiToken
(
    id      int auto_increment
        primary key,
    appid   int          not null,
    token   varchar(255) not null,
    endTime bigint       not null
);

