/**
 * 
 */
package com.np.crm.common.http;

import org.apache.http.StatusLine;

/**
 * @author zhiya.chai
 * 执行http请求的相应结果
 */
public class ResponseResult {
	
	private String responseText;
	private StatusLine statusLine;
	
	public ResponseResult(String responseText, StatusLine statusLine) {
		super();
		this.responseText = responseText;
		this.statusLine = statusLine;
	}
	
	public String getResponseText() {
		return responseText;
	}

	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}

	public StatusLine getStatusLine() {
		return statusLine;
	}
	public void setStatusLine(StatusLine statusLine) {
		this.statusLine = statusLine;
	}
	
	
}
