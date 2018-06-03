create table beta_menu(
	menu_id		varchar(32)	primary key  comment '菜单id',
	menu_name	varchar(50) not null comment '菜单名称',
	menu_url 	varchar(100) comment '菜单url',
	menu_level	varchar(1)	not null comment '菜单等级',
	menu_pid	varchar(32)	not null comment '菜单父id',
	is_valid	varchar(1)	not null comment '是否有效,1-有效,0-失效',
	create_time	timestamp not null default now() comment '创建时间',
	create_by	varchar(50)	not null comment '创建人',
	update_time timestamp not null default now() comment '更新时间',
	update_by	varchar(50)	not null comment '更新人'	
)