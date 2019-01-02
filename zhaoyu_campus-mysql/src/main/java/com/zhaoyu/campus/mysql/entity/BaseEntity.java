package com.zhaoyu.campus.mysql.entity;

import java.util.LinkedHashMap;

public class BaseEntity {
	/**
	 * 排序
	 */
	private LinkedHashMap<String, String> sort;

	public LinkedHashMap<String, String> getSort() {
		return sort;
	}

	public void setSort(LinkedHashMap<String, String> sort) {
		this.sort = sort;
	}
}
