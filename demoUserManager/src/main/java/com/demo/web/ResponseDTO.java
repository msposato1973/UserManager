package com.demo.web;

public class ResponseDTO<T> {
	private boolean exist;
	private Integer count;
	public boolean getExist() {
		return exist;
	}
	public void setExist(Boolean exist) {
		this.exist = exist;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	public ResponseDTO() {
		super();
	}
	public ResponseDTO(boolean exist, Integer count) {
		super();
		this.exist = exist;
		this.count = count;
	}
	

}
