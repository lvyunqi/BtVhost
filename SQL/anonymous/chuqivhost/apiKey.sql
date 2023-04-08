create table apiKey
(
    appId      int auto_increment
        primary key,
    appKey     varchar(255) not null,
    secretKey  varchar(255) not null,
    createDate datetime     not null
);

