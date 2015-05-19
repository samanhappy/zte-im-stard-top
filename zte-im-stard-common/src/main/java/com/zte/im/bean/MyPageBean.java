package com.zte.im.bean;

public class MyPageBean {
	// 页大小（获取条数）
	private int pageSize;

	// 列表节点标识
	private Long pointId;

	// 查询类型：01、刷新；02、翻页
	private String queryType;
	
	private int pageNum;

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize
	 *            the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the pointId
	 */
	public Long getPointId() {
		return pointId;
	}

	/**
	 * @param pointId
	 *            the pointId to set
	 */
	public void setPointId(Long pointId) {
		this.pointId = pointId;
	}

	/**
	 * @return the queryType
	 */
	public String getQueryType() {
		return queryType;
	}

	/**
	 * @param queryType
	 *            the queryType to set
	 */
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

}
