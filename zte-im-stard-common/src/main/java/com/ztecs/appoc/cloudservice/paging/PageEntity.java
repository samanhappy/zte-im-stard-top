/**
 * @Title: PageEntity.java
 * @Package com.ztecs.appoc.cloudservice.entity
 * @Description: 分页对象
 * Copyright: Copyright (c) 2014 
 * Company:ZTE CLOUD SERVICE.CO
 * 
 * @author liujiang
 * @date 2014年8月13日 上午11:13:28
 * @version V0.1
 */
package com.ztecs.appoc.cloudservice.paging;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: PageEntity
 * @Description: 分页对象
 * @author liujiang
 * @date 2014年8月13日 上午11:13:28
 * 
 */

public class PageEntity {

    /***
     * 页记录偏移索引
     */
    private int                       pageOffset;

    /***
     * 每页展示数据条数
     */
    private int                       pageSize;
    /***
     * 数据总记录数
     */
    private int                       recordsTotal;
    /***
     * 数据集合
     */
    private Collection                data;
    /***
     * 当前页数据条数
     */
    private int                       recordsFiltered;

    private List<Map<String, String>> order;

    public PageEntity() {

    }

    public PageEntity(int pageOffset, int pageSize, int recordsTotal) {
        this.pageOffset = pageOffset;
        this.pageSize = pageSize;
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsTotal;
    }

    public int getPageOffset() {
        return pageOffset;
    }

    public void setPageOffset(int pageOffset) {
        this.pageOffset = pageOffset;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsTotal;
    }

    public int getRecordsFiltered() {
        return recordsFiltered;
    }

    public Collection getData() {
        return data;
    }

    public void setData(Collection data) {
        this.data = data;
    }

    public List<Map<String, String>> getOrder() {
        return order;
    }

    public void setOrder(List<Map<String, String>> order) {
        this.order = order;
    }
}
