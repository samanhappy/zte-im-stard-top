package com.zte.im.mybatis.bean.page;

public class PageModel {

	protected int currentPage;// 当前页

	protected int pageSize;// 每页显示条数

	protected int totalPage;// 总页数

	protected int totalRecord;// 总记录数

	public void setCurrentPage(int page) {

		currentPage = page;

		if (currentPage < 1) {
			currentPage = 1;
		}
		if (currentPage > totalPage) {
			currentPage = totalPage;
		}
	}

	protected void setTotalPage() {
		if (pageSize != 0 && totalRecord % pageSize == 0) {
			totalPage = totalRecord / pageSize;
		} else {
			totalPage = totalRecord / pageSize + 1;
		}
	}

	public int getOffset() {
		return (currentPage - 1) * pageSize;
	}

	public int getFirst() {
		return 1;
	}

	public int getPrevious() {
		return currentPage - 1;
	}

	public int getNext() {
		return currentPage + 1;
	}

	public int getLast() {
		return totalPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

}
