--多行数据合并在一起
--oracle  wm_concat(column)
--mysql   group_concat(column)
--postgresql  string_agg(column,',' order by column)



--oracle drop table if exists
declare
  v_count number;
begin
select count(1) into v_count from user_tables t where t.table_name='TABLE' --表名必须大写
if v_count>0 then
  execute immediate 'drop table TABLE' --表名必须大写
end if;
end;
/


--oracle创建索引并行
create index indexName on tableName (columnName) parallel 8;

--pg创建索引并行
create index concurrently indexName on tableName (columnName);

