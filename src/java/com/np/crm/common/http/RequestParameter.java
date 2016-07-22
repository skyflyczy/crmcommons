package com.np.crm.common.http;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

/**
 * @author zhiya.chai
 * 2013-11-15 下午5:47:07
 */
public class RequestParameter {
	/**
	 * 请求地址
	 */
	private String url;
	/**
	 * content-type
	 */
	private String contentType;
	/**
	 * 请求编码字符
	 */
	private String encoding;
	/**
	 * 响应解码字符
	 */
	private String decoding;
	
	private List<Header> headerlist=new ArrayList<Header>();
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public String getDecoding() {
		return decoding;
	}
	public void setDecoding(String decoding) {
		this.decoding = decoding;
	}	
	public List<Header> getHeaderlist() {
		return headerlist;
	}
	public void setHeaderlist(List<Header> headerlist) {
		this.headerlist = headerlist;
	}	
	public void addHeader(Header header) {
		headerlist.add(header);
	}
	
}
