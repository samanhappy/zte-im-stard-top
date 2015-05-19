package com.zte.im.bean;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.zte.im.util.Constant;

public class ResponseObject implements Serializable {
	private static final long serialVersionUID = -6866949976137881683L;
	@Expose
	private String code;
	@Expose
	private String message;
	@Expose
	private String status;
	@Expose
	private Object obj;
	@Expose
	private List<?> item;

	public ResponseObject() {
		super();
		this.code = Constant.SUCCESS_CODE;
		this.message = Constant.SUCCESS_CODE;
	}

	public ResponseObject(String message) {
		super();
		this.code = Constant.ERROR_CODE;
		this.message = message;

	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public List<?> getItem() {
		return item;
	}

	public void setItem(List<?> item) {
		this.item = item;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
