package com.mw.ctdb.rtdb.modifylog;

import javax.annotation.Resource;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class ModifyLogService {
	@Resource
	private ModifyLogDao modifyLogDao;
	
	/**
	 * 首先增加一條記錄
	 * 如果此記錄存在則進入DuplicateKeyException
	 * 更新cd_status為1的記錄，如果更新記錄數不為1，則說明已存在記錄的cd_status為2，即有
	 * 
	 * @param modifyLog
	 */
	public void save(ModifyLog modifyLog){
		try{
			modifyLogDao.addTS(modifyLog);
		}catch(DuplicateKeyException e){
			//modifyLogDao.updTS(modifyLog);
		}
	}
}
