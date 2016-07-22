package com.np.crm.common.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author zhiya.chai
 * 2013-5-1 上午1:59:35
 */
public class LogDefinition {
    /**
     * 默认info日志
     */
    protected static final Logger InfoLog = getLogger("info");
    /**
     * 默认错误日志
     */
    protected static final Logger ErrorLog = getLogger("error");
	
	protected static Logger getLogger(String name){
		return LogManager.getLogger(name);
	}
}
