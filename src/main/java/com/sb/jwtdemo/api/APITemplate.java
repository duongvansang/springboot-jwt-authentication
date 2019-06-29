package com.sb.jwtdemo.api;

import org.springframework.http.HttpStatus;


public class APITemplate {
	private Integer code;
	private HttpStatus status;
	private String errorCode;
	private String msg;
	private Object data;

	public APITemplate(){}
	public APITemplate(HttpStatus status, String msg){
		this.status = status;
		this.msg = msg;
	}
	
	public APITemplate(String errorCode, String msg, HttpStatus status){
		this.errorCode = errorCode;
		this.msg = msg;
		this.status = status;
	}

	/**
	 * @return the code
	 */
	public Integer getCode() {
		return status.value();
	}
	/**
	 * @return the status
	 */
	public HttpStatus getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
}
