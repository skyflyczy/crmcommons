package com.np.crm.common.exception;

import com.np.crm.common.enums.ErrorCode;

/**
 * 返回码异常
 * @author zhiya.chai
 * 2016年7月20日 下午3:15:40
 */
public class ErrorCodeException extends RuntimeException {
	
	private static final long serialVersionUID = 317400383270914028L;
	
	private ErrorCode errorCode;

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public ErrorCodeException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}
	
	public ErrorCodeException(ErrorCode errorCode, Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
	}

	public ErrorCodeException(ErrorCode errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public String toString(){
		return this.errorCode.getCode()+" : "+this.errorCode.getMessage();
	}
}
