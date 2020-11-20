package com.mw.base.model;

import java.util.HashMap;
import java.util.Map;

public class BaseQueryForm {
	private Integer pageNb;
	private Integer pageSize;
	private Map<String, Object> ffMap = new HashMap<String, Object>();

	public Integer getPageNb() {
		return pageNb;
	}

	public void setPageNb(Integer pageNb) {
		this.pageNb = pageNb;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Map<String, Object> getFfMap() {
		return ffMap;
	}

	public void setFfMap(Map<String, Object> ffMap) {
		this.ffMap = ffMap;
	}

	public Page getPage() {
		Page page = new Page(pageNb, pageSize);
		return page;
	}
}
