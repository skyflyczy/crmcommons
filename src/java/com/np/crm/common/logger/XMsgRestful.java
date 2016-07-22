/**
* @author yuhui.tang 
* @date 2015年11月17日 下午3:41:23 
 */
package com.np.crm.common.logger;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;

import com.np.crm.common.support.ConfigProperties;
import com.np.crm.common.util.RequestResponseUtil;

/**
 * restful类型日志
 * @author yuhui.tang
 * 2015年11月17日 下午3:41:23
 */
public class XMsgRestful extends XMsgBase{
	//响应-状态
	private String reponseStatus="-";
	//响应-返回码
	private String reponseReturnCode="-";
	//响应-返回消息
	private String reponseReturnMsg="-";
	//响应-出参
	private String reponseParams="-";
	//请求-类型，用于筛选子请求
	private String requestType ="-";
	//请求-URL
	private String requestURL="-";
	//请求-入参
	private Map<String, String[]> requestParamMap;
	//请求-来源appname
	private String requestAppName="-";
	//请求-来源地址
	private String requestRemoteAddress="-";
	
	public static XMsgRestful build(){
		XMsgRestful msg =  new XMsgRestful();
		msg.setLogName("restful");
		return msg;
	}
	public static XMsgRestful build(Map<String, String> requestTypeMap){
		XMsgRestful msg =  new XMsgRestful();
		msg.setRequestTypeMap(requestTypeMap);
		msg.setRequestAppName(ConfigProperties.getProperty("productName"));
		msg.setLogName("restful");
		return msg;
	}
	public String getReponseStatus() {
		return reponseStatus;
	}
	public XMsgRestful setReponseStatus(String reponseStatus) {
		this.reponseStatus = reponseStatus;
		return this;
	}
	public String getReponseReturnCode() {
		return reponseReturnCode;
	}
	public XMsgRestful setReponseReturnCode(String reponseReturnCode) {
		this.reponseReturnCode = reponseReturnCode;
		return this;
	}
	
	public String getReponseReturnMsg() {
		return reponseReturnMsg;
	}

	public XMsgRestful setReponseReturnMsg(String reponseReturnMsg) {
		this.reponseReturnMsg = reponseReturnMsg;
		return this;
	}

	public String getReponseParams() {
		return reponseParams;
	}
	public XMsgRestful setReponseParams(String reponseParams) {
		this.reponseParams = reponseParams;
		return this;
	}
	
	public String getRequestType() {
		return requestType;
	}
	public XMsgRestful setRequestType(String requestType) {
		this.requestType = requestType;
		return this;
	}
	public String getRequestURL() {
		return requestURL;
	}
	public XMsgRestful setRequestURL(String requestURL) {
		this.requestURL = requestURL;
		return this;
	}
	public String getRequestAppName() {
		return requestAppName;
	}
	public XMsgRestful setRequestAppName(String requestAppName) {
		this.requestAppName = requestAppName;
		return this;
	}
	public String getRequestRemoteAddress() {
		return requestRemoteAddress;
	}
	public XMsgRestful setRequestRemoteAddress(String requestRemoteAddress) {
		this.requestRemoteAddress = requestRemoteAddress;
		return this;
	}

	@Override
	public XMsgRestful setLogName(String logName) {
		return (XMsgRestful)super.setLogName(logName);
	}
	@Override
	public XMsgRestful setLogLevel(Level logLevel) {
		return (XMsgRestful)super.setLogLevel(logLevel);
	}
	@Override
	public XMsgRestful setExcuteClass(String excuteClass) {
		return (XMsgRestful)super.setExcuteClass(excuteClass);
	}
	@Override
	public XMsgRestful setExcuteMethod(String excuteMethod) {
		return (XMsgRestful)super.setExcuteMethod(excuteMethod);
	}
	@Override
	public XMsgRestful setReponseTimeMillis(double reponseTimeMillis) {
		return (XMsgRestful)super.setReponseTimeMillis(reponseTimeMillis);
	}
	@Override
	public XMsgRestful setException(Throwable exception) {
		return (XMsgRestful)super.setException(exception);
	}
	@Override
	public XMsgRestful setMoreMsg(Object[] moreMsg) {
		return (XMsgRestful)super.setMoreMsg(moreMsg);
	}
	@Override
	public XMsgRestful setStartTime(long startTime) {
		// TODO Auto-generated method stub
		return (XMsgRestful)super.setStartTime(startTime);
	}
	@Override
	public XMsgRestful setEndTime(long endTime) {
		// TODO Auto-generated method stub
		return (XMsgRestful)super.setEndTime(endTime);
	}
	@Override
	public String toMessage(boolean isDebugEnabled){
		//#日志格式
		//[class] [method] [response_timemillis] [response_status] [respone_returncode]  [respone_msg]
		//[request_url] [request_appname] [request_remoteaddress] [request_params] [response_params] [request_type] 
		//[other msg # other msg]
		String classMethodName = this.getExcuteClass()+"."+this.getExcuteMethod();
		if(this.getRequestTypeMap()!=null){
			Map<String, String> map = this.getRequestTypeMap();
			String type = map.get(classMethodName);
			if(StringUtils.isNotBlank(type)){
				this.setRequestType(type);
			}
		}
		StringBuilder sb = new StringBuilder();
		sb.append(_SepChar_Start).append(this.getExcuteClass()).append(_SepChar_End).append(_SepChar_Segment);
		sb.append(_SepChar_Start).append(this.getExcuteMethod()).append(_SepChar_End).append(_SepChar_Segment);
		sb.append(_SepChar_Start).append(this.getReponseTimeMillis()).append(_SepChar_End).append(_SepChar_Segment);
		sb.append(_SepChar_Start).append(this.getReponseStatus()).append(_SepChar_End).append(_SepChar_Segment);
		sb.append(_SepChar_Start).append(this.getReponseReturnCode()).append(_SepChar_End).append(_SepChar_Segment);
		sb.append(_SepChar_Start).append(this.getReponseReturnMsg()).append(_SepChar_End).append(_SepChar_Segment);

		sb.append(_SepChar_Start).append(this.getRequestURL()).append(_SepChar_End).append(_SepChar_Segment);
		sb.append(_SepChar_Start).append(this.getRequestAppName()).append(_SepChar_End).append(_SepChar_Segment);
		sb.append(_SepChar_Start).append(this.getRequestRemoteAddress()).append(_SepChar_End).append(_SepChar_Segment);

		sb.append(_SepChar_Start).append(RequestResponseUtil.getRequestParameters(this.getRequestParamMap(), isDebugEnabled)).append(_SepChar_End).append(_SepChar_Segment);
		sb.append(_SepChar_Start).append(this.getReponseParams()).append(_SepChar_End).append(_SepChar_Segment);
		sb.append(_SepChar_Start).append(this.getRequestType()).append(_SepChar_End).append(_SepChar_Segment);
		if(this.getMoreMsg()!=null && this.getMoreMsg().length>0){
			sb.append(_SepChar_Start);
			if(this.getMoreMsg()!=null){
				for (int i = 0; i < getMoreMsg().length; i++) {
					Object object = this.getMoreMsg()[i];
					if(i!=(getMoreMsg().length-1)){
						sb.append(object.toString()).append(_SepChar_More);
					}
					else{
						sb.append(object.toString());
					}
				}
			}
			sb.append(_SepChar_End);
		}
		return sb.toString();
	}
	public Map<String, String[]> getRequestParamMap() {
		return requestParamMap;
	}
	public XMsgRestful setRequestParamMap(Map<String, String[]> requestParamMap) {
		this.requestParamMap = requestParamMap;
		return this;
	}

}
