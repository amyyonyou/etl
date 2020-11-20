package com.mw.base;

import java.util.Date;

public class BaseConsts {

	public interface RST_CD {
		String SUS = "0";
		String PARAM = "1";// 入参错误
		String TIMEOUT = "2";// 超時
		String SYS = "9";// 系統錯誤
		String OPERATION_TIMEOUT = "11";// 操作超時
	}

	public interface DICT_CD {
		String OPDMS_SYS_USER = "opdms_sys_users";
	}

	public interface ROLE {
		String OPDMS_SYS_USER_ADMIN = "admin";
		String OPDMS_SYS_USER_MANAGE = "manage";
	}
	
	public interface STATE {
		String SUCCESS = "0";
		String ERROR_PARAM = "1";
		String ERROR_TIMEOUT = "2";
		String ERROR_SYSTEM = "9";
	}
	
	/**
	 * Database state
	 */
	public interface ST {
		String ACTIVE = "0";
		String INACTIVE = "1";
	}
	
	public interface ERROR_CODE {
		String SECURITY_WRONG_ENTRANCE = "SEC101";
		String SECURITY_DATA_PERMISSION = "SEC102";
		String DB_NORECORD_UPDATED = "DB101";
		String DB_ENTITY_DAO = "DB102";
		String DB_ENTITY_NOSUCHACTION = "DB103";
		String DB_ENTITY_DUPLICATION = "DB104";
		String DB_ENTITY_NOPRIMARYKEY = "DB105";
		String DB_ENTITY_NOSUCH_RECORD = "DB106";
	}
	
	public final static Date NULL_DATE = new Date(0);
	public final static java.sql.Timestamp NULL_TIMESTAMP = new java.sql.Timestamp(0);
}
