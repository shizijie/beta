create table beta_role_menu(
	role_menu_id	varchar(32)	primary key  comment '物理id',
	role_id 	varchar(32) not null comment '角色id',
	menu_id 	varchar(32) not null comment '菜单id',
	is_valid	varchar(1)	not null comment '是否有效,1-有效,0-失效',
	create_time	timestamp not null default now() comment '创建时间',
	create_by	varchar(50)	not null comment '创建人',
	update_time timestamp not null default now() comment '更新时间',
	update_by	varchar(50)	not null comment '更新人'	
)