package com.mw.base.model;

import org.springframework.validation.BindingResult;

import com.mw.base.BaseConsts;
import com.mw.base.BaseConsts.RST_CD;

public class BaseData {
	private Object dt;
	private Integer tt;
	private String errCd;
	private String errMsg;

	public BaseData() {
	}

	public BaseData(Object dt, Integer tt) {
		this.errCd = BaseConsts.RST_CD.SUS;
		this.dt = dt;
		this.tt = tt;
	}

	public BaseData(String errCd, String errMsg) {
		this.errCd = errCd;
		this.errMsg = errMsg;
	}

	public BaseData(String errCd) {
		this.errCd = errCd;
	}

	public BaseData(BindingResult bindingResult) {

	}

	public Object getDt() {
		return dt;
	}

	public void setDt(Object dt) {
		this.dt = dt;
	}

	public Integer getTt() {
		return tt;
	}

	public void setTt(Integer tt) {
		this.tt = tt;
	}

	public String getErrCd() {
		return errCd;
	}

	public void setErrCd(String errCd) {
		this.errCd = errCd;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BaseData [errCd=");
		builder.append(errCd);
		builder.append(", errMsg=");
		builder.append(errMsg);
		builder.append("]");
		return builder.toString();
	}

}
