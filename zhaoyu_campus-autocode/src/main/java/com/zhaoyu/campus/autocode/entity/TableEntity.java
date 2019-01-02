package com.zhaoyu.campus.autocode.entity;

public class TableEntity {
	
	private String tableSchema;
	
	private String tableName;
	
	private String columnName;
	
	private String columnNameTransformed;
	
	private String dataType;
	
	private String columnKey;
	
	private String columnComment;

	public String getTableSchema() {
		return tableSchema;
	}

	public void setTableSchema(String tableSchema) {
		this.tableSchema = tableSchema;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnNameTransformed() {
		return columnNameTransformed;
	}

	public void setColumnNameTransformed(String columnNameTransformed) {
		this.columnNameTransformed = columnNameTransformed;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getColumnKey() {
		return columnKey;
	}

	public void setColumnKey(String columnKey) {
		this.columnKey = columnKey;
	}

	public String getColumnComment() {
		return columnComment;
	}

	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}

	

}
