package com.mw.ctdb.rtdb.sensor;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.mw.plugins.excel.ExcelField;

@Alias("Scada2015Sensor")
public class Scada2015Sensor implements Serializable {
	private static final long serialVersionUID = 1L;

	@ExcelField(title = "seqNo", align = 2, sort = 10)
	private Integer seqNo;
	@ExcelField(title = "tableName", align = 1, sort = 20)
	private String tableName;
	@ExcelField(title = "fieldName", align = 1, sort = 30)
	private String fieldName;
	@ExcelField(title = "fieldDesc", align = 1, sort = 40)
	private String fieldDesc;
	@ExcelField(title = "samplePeriod", align = 1, sort = 50)
	private String samplePeriod;
	@ExcelField(title = "dataUnit", align = 1, sort = 60)
	private String dataUnit;
	@ExcelField(title = "fieldType", align = 1, sort = 70)
	private String fieldType;
	@ExcelField(title = "scadaTagName", align = 1, sort = 80)
	private String scadaTagName;
	@ExcelField(title = "scadaTagDesc", align = 1, sort = 90)
	private String scadaTagDesc;
	@ExcelField(title = "scadaTagAdjustName", align = 1, sort = 100)
	private String scadaTagAdjustName;
	@ExcelField(title = "oldTableName", align = 1, sort = 110)
	private String oldTableName;
	@ExcelField(title = "oldFieldName", align = 1, sort = 120)
	private String oldFieldName;
	@ExcelField(title = "minValue", align = 1, sort = 130)
	private Double minValue;
	@ExcelField(title = "maxValue", align = 1, sort = 140)
	private Double maxValue;

	private Date addDt;
	private String addBy;
	private Date updDt;
	private String updBy;

	public Date getAddDt() {
		return addDt;
	}

	public void setAddDt(Date addDt) {
		this.addDt = addDt;
	}

	public Date getUpdDt() {
		return updDt;
	}

	public void setUpdDt(Date updDt) {
		this.updDt = updDt;
	}

	public Integer getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
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

	public String getFieldDesc() {
		return fieldDesc;
	}

	public void setFieldDesc(String fieldDesc) {
		this.fieldDesc = fieldDesc;
	}

	public String getSamplePeriod() {
		return samplePeriod;
	}

	public void setSamplePeriod(String samplePeriod) {
		this.samplePeriod = samplePeriod;
	}

	public String getDataUnit() {
		return dataUnit;
	}

	public void setDataUnit(String dataUnit) {
		this.dataUnit = dataUnit;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getScadaTagName() {
		return scadaTagName;
	}

	public void setScadaTagName(String scadaTagName) {
		this.scadaTagName = scadaTagName;
	}

	public String getScadaTagDesc() {
		return scadaTagDesc;
	}

	public void setScadaTagDesc(String scadaTagDesc) {
		this.scadaTagDesc = scadaTagDesc;
	}

	public String getScadaTagAdjustName() {
		return scadaTagAdjustName;
	}

	public void setScadaTagAdjustName(String scadaTagAdjustName) {
		this.scadaTagAdjustName = scadaTagAdjustName;
	}

	public String getOldTableName() {
		return oldTableName;
	}

	public void setOldTableName(String oldTableName) {
		this.oldTableName = oldTableName;
	}

	public String getOldFieldName() {
		return oldFieldName;
	}

	public void setOldFieldName(String oldFieldName) {
		this.oldFieldName = oldFieldName;
	}

	public Double getMinValue() {
		return minValue;
	}

	public void setMinValue(Double minValue) {
		this.minValue = minValue;
	}

	public Double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Double maxValue) {
		this.maxValue = maxValue;
	}

	public String getAddBy() {
		return addBy;
	}

	public void setAddBy(String addBy) {
		this.addBy = addBy;
	}

	public String getUpdBy() {
		return updBy;
	}

	public void setUpdBy(String updBy) {
		this.updBy = updBy;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Scada2015Sensor [seqNo=");
		builder.append(seqNo);
		builder.append(", tableName=");
		builder.append(tableName);
		builder.append(", fieldName=");
		builder.append(fieldName);
		builder.append(", fieldDesc=");
		builder.append(fieldDesc);
		builder.append(", samplePeriod=");
		builder.append(samplePeriod);
		builder.append(", dataUnit=");
		builder.append(dataUnit);
		builder.append(", fieldType=");
		builder.append(fieldType);
		builder.append(", scadaTagName=");
		builder.append(scadaTagName);
		builder.append(", scadaTagDesc=");
		builder.append(scadaTagDesc);
		builder.append(", scadaTagAdjustName=");
		builder.append(scadaTagAdjustName);
		builder.append(", oldTableName=");
		builder.append(oldTableName);
		builder.append(", oldFieldName=");
		builder.append(oldFieldName);
		builder.append(", minValue=");
		builder.append(minValue);
		builder.append(", maxValue=");
		builder.append(maxValue);
		builder.append(", addDt=");
		builder.append(addDt);
		builder.append(", addBy=");
		builder.append(addBy);
		builder.append(", updDt=");
		builder.append(updDt);
		builder.append(", updBy=");
		builder.append(updBy);
		builder.append("]");
		return builder.toString();
	}

}
