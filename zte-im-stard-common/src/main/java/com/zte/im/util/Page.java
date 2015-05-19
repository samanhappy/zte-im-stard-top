package com.zte.im.util;

import java.io.Serializable;

public class Page implements Serializable {

	private int pagesize = 10;// 单页记录

	private int currentpage = 1;// 当前页面

	private int countdate;// 总记录数

	private int countPage;

	private int length;// 给redis使用的取指定长度的数据

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public Page() {
	}

	public Page(int pageSize, int currentPage) {
		if (pageSize > 0) {
			this.pagesize = pageSize;
		}
		if (currentPage > 0) {
			this.currentpage = currentPage;
		}
	}

	public void setCurrentpage(int currentpage) {

		if (currentpage > getCountPage()) {
			this.currentpage = getCountPage();
		} else {
			if (currentpage < 1) {
				this.currentpage = 1;
			} else {
				this.currentpage = currentpage;
			}
		}
	}

	public void setCountdate(int countdate) {
		this.countdate = countdate;
	}

	// 由记录数设定有关的页面数。
	public int getCountPage() {
		int i = this.countdate / this.pagesize;
		if ((this.countdate % this.pagesize) != 0) {
			i += 1;
		}
		return i;
	}

	public int getCurrent() {
		return (this.currentpage - 1) * pagesize + 1;
	}

	public int getNext() {
		return this.currentpage * pagesize;
	}

	public int getCurrentpage() {
		return currentpage;
	}

	public int getLastPage() {
		return this.getCurrentpage() - 1;
	}

	public int getNextPage() {
		return this.getCurrentpage() + 1;
	}

	public int getStart() {
		return (this.currentpage - 1) * pagesize;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public int getCountdate() {
		return countdate;
	}

}
