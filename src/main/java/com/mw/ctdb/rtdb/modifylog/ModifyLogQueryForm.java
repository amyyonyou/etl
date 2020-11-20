package com.mw.ctdb.rtdb.modifylog;

public class ModifyLogQueryForm {
	private String tableName;
	private String fieldName;
	private String begDt;
	private String endDt;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getBegDt() {
		return begDt;
	}

	public void setBegDt(String begDt) {
		this.begDt = begDt;
	}

	public String getEndDt() {
		return endDt;
	}

	public void setEndDt(String endDt) {
		this.endDt = endDt;
	}

}
