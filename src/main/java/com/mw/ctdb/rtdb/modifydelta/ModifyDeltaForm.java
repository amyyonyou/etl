package com.mw.ctdb.rtdb.modifydelta;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;
//import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.mw.plugins.excel.ExcelField;

public class ModifyDeltaForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@ExcelField(title = "Table name", align = 1, sort = 10)
	@NotBlank
	private String tableName;

	@ExcelField(title = "Field name", align = 1, sort = 20)
	@NotBlank
	private String fieldName;

	@ExcelField(title = "Sensor date", align = 1, sort = 30)
	@NotNull
	private Date sensorDd;

	@ExcelField(title = "Original value", align = 1, sort = 40)
	//@NotNull
	private Double oriFieldValue;

	@ExcelField(title = "New value", align = 1, sort = 50)
	@NotNull
	private Double newFieldValue;

	@ExcelField(title = "Adjustment reason", align = 1, sort = 60)
	@NotBlank
	private String modifyReason;
	
	@ExcelField(title = "Adjustment method", align = 1, sort = 70)
	@NotBlank
	private String modifyMethod;

	private String modifyBy;

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

	public Double getNewFieldValue() {
		return newFieldValue;
	}

	public void setNewFieldValue(Double newFieldValue) {
		this.newFieldValue = newFieldValue;
	}

	public String getModifyMethod() {
		return modifyMethod;
	}

	public void setModifyMethod(String modifyMethod) {
		this.modifyMethod = modifyMethod;
	}

	public String getModifyReason() {
		return modifyReason;
	}

	public void setModifyReason(String modifyReason) {
		this.modifyReason = modifyReason;
	}

	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	public Double getOriFieldValue() {
		return oriFieldValue;
	}

	public void setOriFieldValue(Double oriFieldValue) {
		this.oriFieldValue = oriFieldValue;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ModifyDeltaForm [tableName=");
		builder.append(tableName);
		builder.append(", fieldName=");
		builder.append(fieldName);
		builder.append(", sensorDd=");
		builder.append(sensorDd);
		builder.append(", oriFieldValue=");
		builder.append(oriFieldValue);
		builder.append(", newFieldValue=");
		builder.append(newFieldValue);
		builder.append(", modifyMethod=");
		builder.append(modifyMethod);
		builder.append(", modifyReason=");
		builder.append(modifyReason);
		builder.append(", modifyBy=");
		builder.append(modifyBy);
		builder.append("]");
		return builder.toString();
	}

}
