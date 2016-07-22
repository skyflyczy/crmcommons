package com.np.crm.common.logger;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;

import com.np.crm.common.support.ConfigProperties;
import com.np.crm.common.util.RequestResponseUtil;


/**
 * 消息基类
 * @author zhiya.chai
 * 2015年11月18日 上午10:14:28
 */
public class XMsgBaseSimlpe extends XMsgBase {
	private String appName;
	
	private String requestUrl;
	
	private String requestParams;
	
	private String responseStr;
	
	public static XMsgBaseSimlpe build(String excuteClass, String excuteMethod, String message){
		return build(null, excuteClass, excuteMethod, message);
	}
	
	public static XMsgBaseSimlpe build(String logName,String excuteClass, String excuteMethod, String message){
		XMsgBaseSimlpe msg =  new XMsgBaseSimlpe();
		if(StringUtils.isNotBlank(logName))
			msg.setLogName(logName);
		msg.setLogLevel(Level.INFO);
		msg.setAppName(ConfigProperties.getProperty("productName"));
		msg.setExcuteClass(excuteClass);
		msg.setExcuteMethod(excuteMethod);
		if(StringUtils.isNotBlank(message))
			msg.setMoreMsg(new Object[]{message});
		return msg;
	} 
	
	@Override
	public String toMessage(boolean isDebugEnabled){
		//#日志格式
		//[appName] [class] [method]
		//[startTime] [endTime] [reponseTimeMillis]
		//[other msg # other msg]
		StringBuilder sb = new StringBuilder();
		sb.append(_SepChar_Start).append(this.getAppName()).append(_SepChar_End).append(_SepChar_Segment);
		sb.append(_SepChar_Start).append(this.getExcuteClass()).append(_SepChar_End).append(_SepChar_Segment);
		sb.append(_SepChar_Start).append(this.getExcuteMethod()).append(_SepChar_End).append(_SepChar_Segment);
		sb.append(_SepChar_Start).append(this.getStartTime()).append(_SepChar_End).append(_SepChar_Segment);
		sb.append(_SepChar_Start).append(this.getEndTime()).append(_SepChar_End).append(_SepChar_Segment);
		sb.append(_SepChar_Start).append(this.getReponseTimeMillis()).append(_SepChar_End).append(_SepChar_Segment);
		sb.append(_SepChar_Start).append(this.getRequestUrl()).append(_SepChar_End).append(_SepChar_Segment);
		sb.append(_SepChar_Start).append(RequestResponseUtil.getRequestParameters(this.getRequestParams(), isDebugEnabled)).append(_SepChar_End).append(_SepChar_Segment);
		sb.append(_SepChar_Start).append(RequestResponseUtil.getCustomResponseData(this.getResponseStr(), isDebugEnabled)).append(_SepChar_End).append(_SepChar_Segment);
		if(this.getMoreMsg() != null && this.getMoreMsg().length > 0){
			sb.append(_SepChar_Start);
			for (int i = 0; i < getMoreMsg().length; i++) {
				sb.append(getMoreMsg()[i].toString());
				if(i != getMoreMsg().length-1){
					sb.append(_SepChar_More);
				}
			}
			sb.append(_SepChar_End);
		}
		return sb.toString();
	}

	public String getAppName() {
		return appName;
	}

	public XMsgBaseSimlpe setAppName(String appName) {
		this.appName = appName;
		return this;
	}

	public String getRequestParams() {
		return requestParams;
	}

	public XMsgBaseSimlpe setRequestParams(String requestParams) {
		this.requestParams = requestParams;
		return this;
	}

	public String getResponseStr() {
		return responseStr;
	}

	public XMsgBaseSimlpe setResponseStr(String responseStr) {
		this.responseStr = responseStr;
		return this;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public XMsgBaseSimlpe setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
		return this;
	}
}
