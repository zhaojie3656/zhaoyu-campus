package com.zhaoyu.campus.autocode.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhaoyu.campus.autocode.dao.TableDao;
import com.zhaoyu.campus.autocode.entity.TableEntity;


@Service
public class TableService {
	
	@Autowired
	private TableDao tableDao;
	
	public List<TableEntity> getByTableSchema(String tableSchema){
		List<TableEntity> list = tableDao.getByTableSchema(tableSchema);
		return list;
	}
	
	public List<TableEntity> getByTableSchemaAndTableNames(String tableSchema,List<String> tableNames){
		List<TableEntity> res = tableDao.getByTableSchemaAndTableNames(tableSchema,tableNames);
		return res;
	}
	
	public List<TableEntity> getAllTableSchema(){
		List<TableEntity> res = tableDao.getAllTableSchema();
		return res;
	}
	
	public List<TableEntity> getTableNameByTableSchema(String tableSchema){
		List<TableEntity> res = tableDao.getTableNameByTableSchema(tableSchema);
		return res;
	}
	
}
