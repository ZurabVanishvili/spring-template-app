create table SYS_LANGUAGES
(
    id      bigint      not null,
    code    varchar(12) not null unique not null,
    name    varchar(24),
    optlock int,
    primary key (id)
)
create table SYS_USERS
(
    id       bigint              not null,
    login    varchar(255) unique not null,
    password varchar(255),
    primary key (id)
)
alter table sys_languages
    drop constraint if exists UKho1qkofwywyajn89x0sgxor0k
alter table sys_languages
    add constraint UKho1qkofwywyajn89x0sgxor0k unique (code)
create sequence sys_languages_sequence start with 1 increment by 1
create sequence sys_users_sequence start with 1 increment by 1