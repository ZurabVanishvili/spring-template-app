create table SYS_LANGUAGES
(
    ID      bigint      not null,
    CODE    varchar(12) not null unique not null,
    NAME    varchar(24),
    OPTLOCK int,
    primary key (ID)
)

alter table SYS_LANGUAGES
    drop constraint if exists UKho1qkofwywyajn89x0sgxor0k
alter table SYS_LANGUAGES
    add constraint UKho1qkofwywyajn89x0sgxor0k unique (CODE)


create table SYS_USERS
(
    ID       bigint not null,
    LOGIN    varchar(255),
    PASSWORD varchar(255),
    primary key (ID)
)

create table sys_permissions
(
    ID          bigint not null,
    CODE        varchar(255),
    DESCRIPTION varchar(255),
    primary key (ID)
)
create table SYS_USER_PERMISSIONS
(
    USER_ID       bigint not null,
    permission_ID bigint not null,
    primary key (USER_ID, permission_ID)
)
create unique nonclustered index UK6ecokv071am5xwjhsk9n6cmxs on sys_permissions (CODE) where CODE is not null
alter table SYS_USER_PERMISSIONS
    drop constraint if exists UKoclw1vp54792syuot1s79uela
alter table SYS_USER_PERMISSIONS
    add constraint UKoclw1vp54792syuot1s79uela unique (permission_ID)
drop index SYS_USERS.UKcxvij6fa3q63f77icrk72s8xc
create unique nonclustered index UKcxvij6fa3q63f77icrk72s8xc on SYS_USERS (LOGIN) where LOGIN is not null
alter table SYS_USER_PERMISSIONS
    add constraint FKl0e55c8xpt2a7ww0uagly8a0c foreign key (permission_ID) references sys_permissions
alter table SYS_USER_PERMISSIONS
    add constraint FKfgk3fokmoxs7odryh1dvfo9gu foreign key (USER_ID) references SYS_USERS
create sequence SYS_LANGUAGES_sequence start with 1 increment by 1
create sequence SYS_USERS_sequence start with 1 increment by 1


create table SYS_ROLE_PERMISSIONS
(
    ROLE_ID       bigint not null,
    permission_ID bigint not null,
    primary key (ROLE_ID, permission_ID)
)
create table SYS_ROLES
(
    ID   bigint not null,
    CODE varchar(255),
    primary key (ID)
)
create table SYS_USER_ROLES
(
    ROLE_ID bigint not null,
    USER_ID bigint not null,
    primary key (ROLE_ID, USER_ID)
)
alter table SYS_ROLE_PERMISSIONS
    drop constraint if exists UK1j61mbow5tcfgapyr05g3s0qr
alter table SYS_ROLE_PERMISSIONS
    add constraint UK1j61mbow5tcfgapyr05g3s0qr unique (permission_ID)
create unique nonclustered index UKpjdyd7mxv3d909iq031ko99xv on SYS_ROLES (CODE) where CODE is not null
alter table SYS_USER_PERMISSIONS
    drop constraint if exists UKoclw1vp54792syuot1s79uela
alter table SYS_USER_PERMISSIONS
    add constraint UKoclw1vp54792syuot1s79uela unique (permission_ID)
drop index SYS_USERS.UKcxvij6fa3q63f77icrk72s8xc
create unique nonclustered index UKcxvij6fa3q63f77icrk72s8xc on SYS_USERS (LOGIN) where LOGIN is not null
create sequence SYS_ROLES_sequence start with 1 increment by 1
alter table SYS_ROLE_PERMISSIONS
    add constraint FKa6lycvisbrxexjyanm5grngc0 foreign key (permission_ID) references sys_permissions
alter table SYS_ROLE_PERMISSIONS
    add constraint FKn16g6ssysj9ncfabu2dj0s5u8 foreign key (ROLE_ID) references SYS_ROLES
alter table SYS_USER_PERMISSIONS
    add constraint FKl0e55c8xpt2a7ww0uagly8a0c foreign key (permission_ID) references sys_permissions
alter table SYS_USER_PERMISSIONS
    add constraint FKfgk3fokmoxs7odryh1dvfo9gu foreign key (USER_ID) references SYS_USERS
alter table SYS_USER_ROLES
    add constraint FK9508r3u91qjr7onvpyf6203p8 foreign key (USER_ID) references SYS_USERS
alter table SYS_USER_ROLES
    add constraint FKmkhkspb26v193sxs3dhgu6s2p foreign key (ROLE_ID) references SYS_ROLES