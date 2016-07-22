package com.np.crm.common.response;

import java.io.Serializable;

/**
 * 接口返回数据对象
 * @author zhiya.chai
 * 2016年7月20日 下午3:00:37
 */
public class Response implements Serializable{

	private static final long serialVersionUID = -400690014341997449L;
	/**
	 * 返回码
	 */
	private int retcode;
	/**
	 * 返回信息
	 */
	private String msg;
	/**
	 * 返回数据，用户查询结果或者页面需要结果的输出
	 */
	private Object data;
	
	public int getRetcode() {
		return retcode;
	}
	public Response setRetcode(int retcode) {
		this.retcode = retcode;
		return this;
	}
	public String getMsg() {
		return msg;
	}
	public Response setMsg(String msg) {
		this.msg = msg;
		return this;
	}
	public Object getData() {
		return data;
	}
	public Response setData(Object data) {
		this.data = data;
		return this;
	}
	public static Response build(){
		return new Response();
	}
}
