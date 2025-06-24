create table evententry
(
    id           int primary key auto_increment,
    type         varchar(255),
    content_type varchar(255),
    payload      clob,
    timestamp    timestamp
);