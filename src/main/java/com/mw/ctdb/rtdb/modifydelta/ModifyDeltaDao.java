package com.mw.ctdb.rtdb.modifydelta;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mw.plugins.mybatis.MybatisMapper;

@MybatisMapper
public interface ModifyDeltaDao {
	/**
	 * @param tableName
	 * @param fieldName
	 * @param fieldValue
	 * @param sensorDd
	 *            yyyymmdd hh24miss
	 * @return
	 */
	int updBy(@Param("tableName") String tableName, @Param("fieldName") String fieldName, @Param("fieldValue") Double fieldValue, @Param("sensorDd") Date sensorDd);

	int add(@Param("tableName") String tableName, @Param("fieldName") String fieldName, @Param("fieldValue") Double fieldValue, @Param("sensorDd") Date sensorDd);
	
	Double getOriFieldValue(@Param("tableName") String tableName, @Param("fieldName") String fieldName, @Param("sensorDd") Date sensorDd);
	
	List<ModifyDeltaView> getBy(ModifyDeltaQueryForm modifyDeltaQueryForm);
	
	List<ModifyDeltaView> previewBy(ModifyDeltaQueryForm modifyDeltaQueryForm);
}
