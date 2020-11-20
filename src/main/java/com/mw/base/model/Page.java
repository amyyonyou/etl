package com.mw.base.model;

public class Page {
	private Integer start;
	private Integer offset;
	private Integer end;

	public Page(Integer pageNb, Integer pageSize) {
		this.start = pageSize * (pageNb - 1);
		this.offset = pageSize;
		this.end = this.start + this.offset;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getEnd() {
		return end;
	}

	public void setEnd(Integer end) {
		this.end = end;
	}

}
