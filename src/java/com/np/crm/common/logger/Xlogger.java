/**
* @author yuhui.tang 
* @date 2015年11月17日 下午4:15:11 
 */
package com.np.crm.common.logger;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

/**
 * 日志输出类
 * @author yuhui.tang
 * 2015年11月17日 下午4:15:11
 */
public class Xlogger extends LogDefinition {
	/**
	 * 写入日志在指定的日志文件或info.log
	 * @param msg
	 * @return void
	 * @author yuhui.tang
	 * 2015年11月17日 下午5:11:42
	 */
	public static void log(XMsgBase msg) {
		Throwable t = msg.getException();
		//指定logger输出
		Logger logger = null;
		String logName = msg.getLogName();
		if(StringUtils.isNotBlank(logName)){
			logger = getLogger(logName);
		}
		if(logger == null){
			logger = InfoLog;
		}
		String message = msg.toMessage(logger.isDebugEnabled());
		Level level = msg.getLogLevel();
		if (StringUtils.isNotBlank(message)) {
			
			if(level == null)
				level = Level.INFO;
			logger.log(level, message, t);
		}
	}
	/**
	 * 记录错误日志在error.log
	 * @param msg
	 * @return void
	 * @author zhiya.chai
	 * 2015年11月17日 下午5:49:56
	 */
	public static void error(XMsgBase msg) {
		String message = msg.toMessage(ErrorLog.isDebugEnabled());
		Throwable t = msg.getException();
		ErrorLog.error(message, t);
	}
}
