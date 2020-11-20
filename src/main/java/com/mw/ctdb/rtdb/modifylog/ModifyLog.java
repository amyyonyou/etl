package com.mw.ctdb.rtdb.modifylog;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.mw.plugins.excel.ExcelField;

@Alias("ModifyLog")
public class ModifyLog {
	private Long nb;
	private Long batchNb;
	@ExcelField(title = "Table name", align = 1, sort = 10)
	private String tableName;
	@ExcelField(title = "Field name", align = 1, sort = 20)
	private String fieldName;
	@ExcelField(title = "Sensor date", align = 1, sort = 30)
	private Date sensorDd;
	@ExcelField(title = "Original value", align = 1, sort = 40)
	private Double oriFieldValue;
	@ExcelField(title = "Latest value", align = 1, sort = 50)
	private Double newFieldValue;
	@ExcelField(title = "Adjustment reason", align = 1, sort = 60)
	private String modifyReason;
	@ExcelField(title = "Adjustment method", align = 1, sort = 70)
	private String modifyMethod;
	@ExcelField(title = "Adjustment user", align = 1, sort = 80)
	private String modifyBy;
	@ExcelField(title = "Adjustment date", align = 1, sort = 90)
	private Date modifyDt;
	private Integer statusCd;
	private Date syncCtdbDt;
	private Date syncScadaDt;

	public ModifyLog() {

	}

	public ModifyLog(String tableName, String fieldName, Date sensorDd) {
		this.tableName = tableName;
		this.fieldName = fieldName;
		this.sensorDd = sensorDd;
	}

	public Long getNb() {
		return nb;
	}

	public void setNb(Long nb) {
		this.nb = nb;
	}

	public Long getBatchNb() {
		return batchNb;
	}

	public void setBatchNb(Long batchNb) {
		this.batchNb = batchNb;
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

	public Date getModifyDt() {
		return modifyDt;
	}

	public void setModifyDt(Date modifyDt) {
		this.modifyDt = modifyDt;
	}

	public Integer getStatusCd() {
		return statusCd;
	}

	public void setStatusCd(Integer statusCd) {
		this.statusCd = statusCd;
	}

	public Date getSyncCtdbDt() {
		return syncCtdbDt;
	}

	public void setSyncCtdbDt(Date syncCtdbDt) {
		this.syncCtdbDt = syncCtdbDt;
	}

	public Date getSyncScadaDt() {
		return syncScadaDt;
	}

	public void setSyncScadaDt(Date syncScadaDt) {
		this.syncScadaDt = syncScadaDt;
	}

}
