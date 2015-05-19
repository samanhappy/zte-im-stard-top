package com.zte.databinder;

public class PageBinder {

	int pSize = 10; // 每页记录数

	int cPage = 1; // 当前页

	int offset; // 分页偏移量

	public int getpSize() {
		return pSize;
	}

	public void setpSize(int pSize) {
		this.pSize = pSize;
	}

	public int getcPage() {
		return cPage;
	}

	public void setcPage(int cPage) {
		this.cPage = cPage;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

}
