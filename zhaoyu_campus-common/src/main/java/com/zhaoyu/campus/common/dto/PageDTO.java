package com.zhaoyu.campus.common.dto;

import java.util.List;

public class PageDTO {

    private Integer page;
    private int pageSize;
    private int totalPage;
    private int total;
    private List<?> rows;
    private int star;

    public PageDTO() {
    }

    public Integer getPage() {
        return this.page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return this.totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getStar() {
        return this.star;
    }

    public List<?> getRows() {
        return this.rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    public void setStar(int star) {
        this.star = star;
    }
}

