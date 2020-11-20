package com.mw.ctdb.rtdb.modifylog;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.mw.base.model.Page;
import com.mw.plugins.mybatis.MybatisMapper;

@MybatisMapper
public interface ModifyLogDao {
	
	Long getNextBatchSeq();
	
	/**
	 * temporary storage(means status code is 1)
	 * @param modifyLog
	 * @return
	 */
	int addTS(ModifyLog modifyLog);

	/**
	 * update to temporary storage(means status code is 1)
	 * note: don't set ori_field_value in this function
	 * @param modifyLog
	 * @return
	 */
	//int updTS(ModifyLog modifyLog);
	
	int updStatusByBatchNb(@Param("batchNb")Long batchNb, @Param("statusCd")Integer statusCd, @Param("syncCtdbDt")Date syncCtdbDt);
	
	int updSyncScadaDt(@Param("nb")Long nb, @Param("syncScadaDt")Date syncScadaDt);
	
	@Select("select to_char(min(sensordd), 'yyyy-mm-dd hh24:mi:ss') as minSensorDt, to_char(max(sensordd), 'yyyy-mm-dd hh24:mi:ss') as maxSensorDt from scada_data.modify_log where batch_nb = #{batchNb}")
	Map<String, String> getMinMaxSensorDtByBatchNb(Long batchNb);
	
	List<ModifyLog> getByBatchNbAndStatusCd(@Param("batchNb")Long batchNb, @Param("statusCd")Integer statusCd);
	
	List<ModifyLog> getBy(@Param("ffMap") Map<String, Object> ffMap, @Param("page") Page page);
	int getCountBy(@Param("ffMap") Map<String, Object> ffMap);
	
	/**
	 * 按照batch_nb排序，以按用戶的修改順序更新SCADA
	 * @param statusCd
	 * @return
	 */
	List<ModifyLogAndSensor> getModifyLogAndSensor(@Param("statusCd")Integer statusCd);
}
