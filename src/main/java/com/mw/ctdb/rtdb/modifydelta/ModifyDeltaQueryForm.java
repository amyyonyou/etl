package com.mw.ctdb.rtdb.modifydelta;

import java.util.Map;

import org.hibernate.validator.constraints.NotBlank;

public class ModifyDeltaQueryForm {
	@NotBlank
	private String tableName;
	@NotBlank
	private String fieldName;
	@NotBlank
	private String begDt;
	@NotBlank
	private String endDt;

	//for preview
	private Long batchNb;//preview or confirm mode
	//for save last
	private String modifyReason;
	private String modifyMethod;

	public ModifyDeltaQueryForm() {
	}
	
	public ModifyDeltaQueryForm(String tableName, String fieldName) {
		this.tableName = tableName;
		this.fieldName = fieldName;
	}

	public ModifyDeltaQueryForm(Map<String, Object> params) {
		this.tableName = (String) params.get("tableName");
		this.fieldName = (String) params.get("fieldName");
		this.begDt = (String) params.get("begDt");
		this.endDt = (String) params.get("endDt");
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

	public Long getBatchNb() {
		return batchNb;
	}

	public void setBatchNb(Long batchNb) {
		this.batchNb = batchNb;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ModifyDeltaQueryForm [tableName=");
		builder.append(tableName);
		builder.append(", fieldName=");
		builder.append(fieldName);
		builder.append(", begDt=");
		builder.append(begDt);
		builder.append(", endDt=");
		builder.append(endDt);
		builder.append(", batchNb=");
		builder.append(batchNb);
		builder.append(", modifyReason=");
		builder.append(modifyReason);
		builder.append(", modifyMethod=");
		builder.append(modifyMethod);
		builder.append("]");
		return builder.toString();
	}

}
