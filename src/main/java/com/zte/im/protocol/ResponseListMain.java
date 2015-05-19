package com.zte.im.protocol;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.Expose;
import com.zte.im.bean.DataVer;
import com.zte.im.bean.Firm;
import com.zte.im.util.Constant;

public class ResponseListMain {
	private Res res;

	private List<?> item;

	private Firm firm;

	private Long maxcount;

	@Expose
	private DataVer dataVer;

	public DataVer getDataVer() {
		return dataVer;
	}

	public void setDataVer(DataVer dataVer) {
		this.dataVer = dataVer;
	}

	public Long getMaxcount() {
		return maxcount;
	}

	public void setMaxcount(Long maxcount) {
		this.maxcount = maxcount;
	}

	public Firm getFirm() {
		return firm;
	}

	public void setFirm(Firm firm) {
		this.firm = firm;
	}

	public Map<String, Object> initResultMap() {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("res", res);
		return result;
	}

	public ResponseListMain() {
		res = new Res();
		res.setReCode(Constant.SUCCESS_CODE);
		res.setResMessage("Operation is successful");
	}

	public Res getRes() {
		return res;
	}

	public void setRes(Res res) {
		this.res = res;
	}

	public List<?> getItem() {
		return item;
	}

	public void setItem(List<?> item) {
		this.item = item;
	}

}
