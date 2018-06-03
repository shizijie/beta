create table beta_role(
	role_id		varchar(32)	primary key  comment '角色id',
	role_name	varchar(50) not null comment '角色名称',
	role_desc 	varchar(300) comment '角色描述',
	is_valid	varchar(1)	not null comment '是否有效,1-有效,0-失效',
	create_time	timestamp not null default now() comment '创建时间',
	create_by	varchar(50)	not null comment '创建人',
	update_time timestamp not null default now() comment '更新时间',
	update_by	varchar(50)	not null comment '更新人'	
)