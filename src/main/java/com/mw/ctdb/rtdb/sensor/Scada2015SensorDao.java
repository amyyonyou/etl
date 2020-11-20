package com.mw.ctdb.rtdb.sensor;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mw.base.model.Page;
import com.mw.plugins.mybatis.MybatisMapper;

@MybatisMapper
public interface Scada2015SensorDao {
	int getNextSeqNo();
	
	int add(Scada2015Sensor scada2015Sensor);

	int delById(@Param("seqNo") Integer seqNo);

	int updById(Scada2015Sensor scada2015Sensor);

	Scada2015Sensor getById(@Param("seqNo") Integer seqNo);

	List<Scada2015Sensor> getBy(@Param("ffMap") Map<String, Object> ffMap, @Param("page") Page page);

	int getCountBy(@Param("ffMap") Map<String, Object> ffMap);

	Scada2015Sensor getByTableAndFieldName(@Param("tableName") String tableName, @Param("fieldName") String fieldName);

	List<String> getTableNameList(@Param("fieldTypeArray") String[] fieldTypeArray);

	List<Map<String, String>> getFieldNameList(@Param("fieldTypeArray") String[] fieldTypeArray);

	List<Map<String, String>> getFieldNameListByTableName(@Param("tableName") String tableName);
	
	String getTableNameByFieldName(@Param("fieldName") String fieldName);

}
