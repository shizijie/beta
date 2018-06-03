create table beta_user(
	user_id		varchar(32)	primary key  comment '用户id',
	user_name	varchar(50) not null comment '用户名称',
	password 	varchar(32) not null comment '密码',
	is_valid	varchar(1)	not null comment '是否有效,1-有效,0-失效',
	create_time	timestamp not null default now() comment '创建时间',
	create_by	varchar(50)	not null comment '创建人',
	update_time timestamp not null default now() comment '更新时间',
	update_by	varchar(50)	not null comment '更新人'	
)