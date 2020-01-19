delete from user;
insert into user value (1,null,1,'admin','','admin@admin.com','12345678901','',0,1,NOW(),'127.0.0.1',NOW(),'127.0.0.1',NOW(),'127.0.0.1',NOW());
delete from office;
insert into office value (1,null,null,'技术部','',0,1,NOW(),NOW());
delete from permission;
insert into  permission value (1,null,'系统管理','ROLE_SYSTEM_MANAGE',0,'',null,0,0,1,NOW(),NOW());

insert into  permission value (2,1,'用户管理','ROLE_USER_LIST',0,'/account/user/list',null,0,0,1,NOW(),NOW());
insert into  permission value (3,1,'部门管理','ROLE_OFFICE_LIST',0,'/account/office/list',null,0,0,1,NOW(),NOW());
insert into  permission value (4,1,'权限管理','ROLE_PERMISSION_LIST',0,'/account/permission/list',null,0,0,1,NOW(),NOW());
insert into  permission value (5,1,'角色管理','ROLE_LIST',0,'/account/role/list',null,0,0,1,NOW(),NOW());

insert into  permission value (6,2,'用户创建','ROLE_USER_CREATE',0,'/account/user/create',null,1,0,1,NOW(),NOW());
insert into  permission value (7,2,'用户修改','ROLE_USER_UPDATE',0,'/account/user/update',null,1,0,1,NOW(),NOW());
insert into  permission value (8,2,'修改用户状态','ROLE_USER_STATUS',0,'/account/user/update',null,1,0,1,NOW(),NOW());
insert into  permission value (9,2,'用户角色关系保存','ROLE_USER_ROLE_SAVE',0,'/account/user/role/save',null,1,0,1,NOW(),NOW());
insert into  permission value (10,2,'用户对应所有角色','ROLE_USER_ROLES',0,'/account/user/role/save',null,1,0,1,NOW(),NOW());
insert into  permission value (11,2,'用户角色关系状态修改','ROLE_USER_ROLE_STATUS',0,'/account/user/role/status',null,1,0,1,NOW(),NOW());
insert into  permission value (12,2,'获取当前用户角色ID集合','ROLE_USER_ROLE_STATUS',0,'/account/user/current/roles',null,1,0,1,NOW(),NOW());

insert into  permission value (13,3,'创建/修改部门信息','ROLE_OFFICE_SAVE',0,'/account/office/save',null,1,0,1,NOW(),NOW());
insert into  permission value (14,3,'部门状态修改','ROLE_OFFICE_STATUS',0,'/account/office/status',null,1,0,1,NOW(),NOW());
insert into  permission value (15,3,'查询全部部门','ROLE_OFFICE_ALL',0,'/account/office/all',null,1,0,1,NOW(),NOW());

insert into  permission value (16,4,'创建/修改权限信息','ROLE_PERMISSION_SAVE',0,'/account/permission/save',null,1,0,1,NOW(),NOW());
insert into  permission value (17,4,'权限状态修改','ROLE_PERMISSION_STATUS',0,'/account/permission/status',null,1,0,1,NOW(),NOW());
insert into  permission value (18,4,'查询全部权限','ROLE_PERMISSION_ALL',0,'/account/permission/all',null,1,0,1,NOW(),NOW());

insert into  permission value (19,5,'创建/修改角色信息','ROLE_SAVE',0,'/account/role/all',null,1,0,1,NOW(),NOW());
insert into  permission value (20,5,'角色状态修改','ROLE_STATUS',0,'/account/role/status',null,1,0,1,NOW(),NOW());
insert into  permission value (21,5,'角色权限关系保存','ROLE_PERMISSION_SAVE',0,'/account/role/permission/save',null,1,0,1,NOW(),NOW());
insert into  permission value (22,5,'角色权限关系列表','ROLE_PERMISSION_LIST',0,'/account/role/permission/list',null,1,0,1,NOW(),NOW());
insert into  permission value (23,5,'角色对应所有权限','ROLE_PERMISSION_LIST',0,'/account/role/permission/role',null,1,0,1,NOW(),NOW());
insert into  permission value (24,5,'角色权限关系状态修改','ROLE_PERMISSION_STATUS',0,'/account/role/permission/status',null,1,0,1,NOW(),NOW());
insert into  permission value (25,5,'查询全部角色','ROLE_ALL',0,'/account/role/all',null,1,0,1,NOW(),NOW());

delete from role;
insert into role value (1,'系统管理员',1,null,0,1,NOW(),NOW());

delete from role_permission;
insert into role_permission value (1,1,1,0,1,NOW(),NOW());
insert into role_permission value (2,1,2,0,1,NOW(),NOW());
insert into role_permission value (3,1,3,0,1,NOW(),NOW());
insert into role_permission value (4,1,4,0,1,NOW(),NOW());
insert into role_permission value (5,1,5,0,1,NOW(),NOW());
insert into role_permission value (6,1,6,0,1,NOW(),NOW());
insert into role_permission value (7,1,7,0,1,NOW(),NOW());
insert into role_permission value (8,1,8,0,1,NOW(),NOW());
insert into role_permission value (9,1,9,0,1,NOW(),NOW());
insert into role_permission value (10,1,10,0,1,NOW(),NOW());
insert into role_permission value (11,1,11,0,1,NOW(),NOW());
insert into role_permission value (12,1,12,0,1,NOW(),NOW());
insert into role_permission value (13,1,13,0,1,NOW(),NOW());
insert into role_permission value (14,1,14,0,1,NOW(),NOW());
insert into role_permission value (15,1,15,0,1,NOW(),NOW());
insert into role_permission value (16,1,16,0,1,NOW(),NOW());
insert into role_permission value (17,1,17,0,1,NOW(),NOW());
insert into role_permission value (18,1,18,0,1,NOW(),NOW());
insert into role_permission value (19,1,19,0,1,NOW(),NOW());
insert into role_permission value (20,1,20,0,1,NOW(),NOW());
insert into role_permission value (21,1,21,0,1,NOW(),NOW());
insert into role_permission value (22,1,22,0,1,NOW(),NOW());
insert into role_permission value (23,1,23,0,1,NOW(),NOW());
insert into role_permission value (24,1,24,0,1,NOW(),NOW());
insert into role_permission value (25,1,25,0,1,NOW(),NOW());

delete from user_role;
insert into user_role value (1,1,1,0,1,NOW(),NOW());