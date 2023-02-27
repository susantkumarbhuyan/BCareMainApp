package com.bcareapp.commonclasses;

import java.io.Serializable;
import java.util.List;

public class BaseResponseListData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String statusMessage;
	private int statusCode;

	private List<?> data;

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public BaseResponseListData() {
		super();
	}

	public BaseResponseListData(String statusMessage, int statusCode) {
		super();
		this.statusMessage = statusMessage;
		this.statusCode = statusCode;
	}

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}

}

