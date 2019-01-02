package com.zhaoyu.campus.autocode.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.zhaoyu.campus.autocode.entity.TableEntity;

@Mapper
@Component
public interface TableDao {
	
	public List<TableEntity> getByTableSchema(@Param("table_schema") String tableSchema);
	
	public List<TableEntity> getByTableSchemaAndTableNames(@Param("table_schema") String tableSchema,@Param("tableNames") List<String> tableNames);
	
	public List<TableEntity> getAllTableSchema();
	
	public List<TableEntity> getTableNameByTableSchema(String tableSchema);
}
