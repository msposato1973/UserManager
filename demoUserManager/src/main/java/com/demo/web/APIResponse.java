package com.demo.web;

public class APIResponse<T> {

	private Integer count;
	private Integer total;
	private T list;
	 
	
	public APIResponse(Integer count, T list, Integer total) {
		super();
		this.count = count;
		this.list = list;
		this.total = total;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
	
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public T getList() {
		return list;
	}

	public void setList(T list) {
		this.list = list;
	}

	
	
	public APIResponse() {
		super();
	}
	

	
	 
}
