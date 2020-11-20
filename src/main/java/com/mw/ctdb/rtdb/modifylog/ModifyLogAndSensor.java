package com.mw.ctdb.rtdb.modifylog;

import org.apache.ibatis.type.Alias;

@Alias("ModifyLogAndSensor")
public class ModifyLogAndSensor {
	private Long nb;
	private String tableName;
	private String fieldName;
	private String sensorDd;
	private Double oriFieldValue;
	private Double newFieldValue;
	private String modifyReason;
	private String modifyMethod;
	private String modifyBy;
	private String modifyDt;

	private String scadaTagName;
	private String fieldType;

	public Long getNb() {
		return nb;
	}

	public void setNb(Long nb) {
		this.nb = nb;
	}

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

	public String getSensorDd() {
		return sensorDd;
	}

	public void setSensorDd(String sensorDd) {
		this.sensorDd = sensorDd;
	}

	public Double getOriFieldValue() {
		return oriFieldValue;
	}

	public void setOriFieldValue(Double oriFieldValue) {
		this.oriFieldValue = oriFieldValue;
	}

	public Double getNewFieldValue() {
		return newFieldValue;
	}

	public void setNewFieldValue(Double newFieldValue) {
		this.newFieldValue = newFieldValue;
	}

	public String getModifyReason() {
		return modifyReason;
	}

	public void setModifyReason(String modifyReason) {
		this.modifyReason = modifyReason;
	}

	public String getModifyMethod() {
		return modifyMethod;
	}

	public void setModifyMethod(String modifyMethod) {
		this.modifyMethod = modifyMethod;
	}

	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	public String getModifyDt() {
		return modifyDt;
	}

	public void setModifyDt(String modifyDt) {
		this.modifyDt = modifyDt;
	}

	public String getScadaTagName() {
		return scadaTagName;
	}

	public void setScadaTagName(String scadaTagName) {
		this.scadaTagName = scadaTagName;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

}
