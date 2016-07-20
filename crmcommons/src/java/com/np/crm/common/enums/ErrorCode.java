package com.np.crm.common.enums;
/**
 * 返回码
 * @author zhiya.chai
 * 2016年7月20日 下午3:17:55
 */
public enum ErrorCode {
	
	SUCCESS(0, "成功"),
	//系统异常  以 10开头
	SYSTEM_ERROR(10000,"系统异常"),
	SYSTEM_PARAMETERS_EMPTY(10001,"参数为空"),
	SYSTEM_HTTPMETHOD_ERROR(10002,"不支持此访问方式");
	
	//用户异常 以20开头
	
	//业务异常 以30开头
	private int code;
	private String message;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	private ErrorCode(int code, String message) {
		this.code = code;
		this.message = message;
	}


}
