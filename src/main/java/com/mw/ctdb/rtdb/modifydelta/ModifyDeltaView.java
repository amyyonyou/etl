package com.mw.ctdb.rtdb.modifydelta;

import java.util.Date;

import org.apache.ibatis.type.Alias;

@Alias("ModifyDeltaView")
public class ModifyDeltaView {
	private String tableName;
	private String fieldName;
	private Date sensorDd;
	private Double oriFieldValue;
	private Double curFieldValue;
	private Double newFieldValue;
	private String modifyReason;
	private String modifyMethod;

	private String sensorDdStr;

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

	public Date getSensorDd() {
		return sensorDd;
	}

	public void setSensorDd(Date sensorDd) {
		this.sensorDd = sensorDd;
	}

	public Double getOriFieldValue() {
		return oriFieldValue;
	}

	public void setOriFieldValue(Double oriFieldValue) {
		this.oriFieldValue = oriFieldValue;
	}

	public Double getCurFieldValue() {
		return curFieldValue;
	}

	public void setCurFieldValue(Double curFieldValue) {
		this.curFieldValue = curFieldValue;
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

	public String getSensorDdStr() {
		return sensorDdStr;
	}

	public void setSensorDdStr(String sensorDdStr) {
		this.sensorDdStr = sensorDdStr;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ModifyDeltaView [tableName=");
		builder.append(tableName);
		builder.append(", fieldName=");
		builder.append(fieldName);
		builder.append(", sensorDd=");
		builder.append(sensorDd);
		builder.append(", oriFieldValue=");
		builder.append(oriFieldValue);
		builder.append(", curFieldValue=");
		builder.append(curFieldValue);
		builder.append(", newFieldValue=");
		builder.append(newFieldValue);
		builder.append(", modifyReason=");
		builder.append(modifyReason);
		builder.append(", modifyMethod=");
		builder.append(modifyMethod);
		builder.append(", sensorDdStr=");
		builder.append(sensorDdStr);
		builder.append("]");
		return builder.toString();
	}

}
