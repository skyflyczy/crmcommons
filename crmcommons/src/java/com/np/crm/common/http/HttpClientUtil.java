package com.np.crm.common.http;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import com.np.crm.common.logger.XMsgBaseSimlpe;
import com.np.crm.common.logger.XMsgError;
import com.np.crm.common.logger.Xlogger;
import com.np.crm.common.util.ArrayUtils;




/**
 * http client 工具类
 * @author zhiya.chai
 * 2013-11-15 下午7:01:12
 */
public class HttpClientUtil {
	
	//默认编码格式
	private static final String DEFAULT_ENCODING = "UTF-8";
	//默认超时时间
	private static final int TIMEOUT = 10*1000; 
	
	public static void main(String[] args){
		RequestParameter parameter = new RequestParameter();
		parameter.setUrl("http://172.16.31.1:8001/pmto263");
		parameter.setEncoding(DEFAULT_ENCODING);
		parameter.setDecoding(DEFAULT_ENCODING);
		parameter.setContentType("application/json");
//		String content = "{\"data\":\"中文\",\"msgType\":0,\"sender\":\"hao.chen01@cfaoe.com\",\"title\":\"test\",\"userlist\":\"hao.chen@cfaoe.com,zhiya.chai@cfaoe.com\",\"username\":\"HR\"}";
		String content = "{\"data\":\"asdasd\",\"msgType\":0,\"oaurl\":\"\",\"offline\":true,\"sender\":\"lina.ye@cfaoe.com\",\"title\":\"43\",\"userlist\":\"jie.deng@cfaoe.com\",\"username\":\"123\"}";
		System.out.println(content);
		post(parameter, TIMEOUT, content);
	}
	
	/**
	 * post重载 
	 * @return String
	 * @author wenqiang
	 * 2015年5月27日 下午3:46:32
	 */
	public static String post(Map<String,Object> params,String url){
		RequestParameter parameter = new RequestParameter();
		parameter.setUrl(url);
		parameter.setEncoding(DEFAULT_ENCODING);
		parameter.setDecoding(DEFAULT_ENCODING);
		String content = JSONObject.toJSONString(params);
		return post(parameter, TIMEOUT, content);
	}
	
	/**
	 * post重载 
	 * @param content jsonString
	 * @return String
	 * @author wenqiang
	 * 2015年5月27日 下午3:46:32
	 */
	public static String post(String content,String url){
		RequestParameter parameter = new RequestParameter();
		parameter.setUrl(url);
		parameter.setEncoding(DEFAULT_ENCODING);
		parameter.setDecoding(DEFAULT_ENCODING);
		return post(parameter, TIMEOUT, content);
	}
	/**
	 * post重载 
	 * @return String
	 * @author wenqiang
	 * 2015年5月27日 下午3:46:32
	 */
	public static String post(Map<String,Object> params,String url,String contentType){
		RequestParameter parameter = new RequestParameter();
		parameter.setUrl(url);
		parameter.setEncoding(DEFAULT_ENCODING);
		parameter.setDecoding(DEFAULT_ENCODING);
		parameter.setContentType(contentType);
		String content = JSONObject.toJSONString(params);
		return post(parameter, TIMEOUT, content);
	}
	
	/**
	 * 发起post请求
	 * @param parameter
	 * @param timeout
	 * @param content
	 * @return
	 */
	public static String post(RequestParameter parameter, int timeout,String content) {
		try {
			ResponseResult result = HttpClientInvoker.doPost(parameter, timeout, content);
			//HttpEntity entity = result.getEntity();
			return result.getResponseText();
		} catch (Exception e) {
			Xlogger.error(XMsgError.buildSimple(HttpClientUtil.class.getName(), "post", e));
		}
		return null;
	}
	/**
	 * 发起post请求
	 * @param parameter
	 * @param map
	 * @param timeout
	 * @return
	 */
	public static String post(RequestParameter parameter,Map<String, String> map, int timeout){
		try {
			ResponseResult result = HttpClientInvoker.doPost(parameter, map, timeout);
			//HttpEntity entity = result.getEntity();
			return result.getResponseText();
		} catch (Exception e) {
			Xlogger.error(XMsgError.buildSimple(HttpClientUtil.class.getName(), "post", e));
		}
		return null;
	}
	/**
	 *  发起post请求
	 * @param parameter
	 * @param list
	 * @param timeout
	 * @return
	 */
	public static String post(RequestParameter parameter,List<NameValuePair> list, int timeout){
		try {
			ResponseResult result = HttpClientInvoker.doPost(parameter, list, timeout);
			//HttpEntity entity = result.getEntity();
			return result.getResponseText();
		} catch (Exception e) {
			Xlogger.error(XMsgError.buildSimple(HttpClientUtil.class.getName(), "post", e));
		}
		return null;	
	}
	
	/**
	 * 重载
	 * 使用默认编码格式
	 * @return String
	 * @author wenqiang
	 * 2015年5月27日 下午3:41:59
	 */
	public static String get(String url){
		return get(url, DEFAULT_ENCODING);
	}
	
	
	/**
	 * 重载，拼接url参数
	 * @param url
	 * @param param
	 * @return
	 * @author wenqiang
	 * 2015年6月26日 上午10:38:59
	 */
	@SuppressWarnings("all")
	public static String get(String url, Map<String, String> param) {
		List<String> params = new ArrayList<String>();
		if (param != null) {
			for (String key : param.keySet()) {
				String value = param.get(key);
				if (value != null) {
					params.add(key + "=" + URLEncoder.encode(value));
				}
			}
		}
		String urls = "";
		if (params.size() > 0) {
			urls = url + "?" + ArrayUtils.join(params.toArray(), "&");
		} else {
			urls = url;
		}
		return get(urls);
	}
	
	/**
	 * get
	 * @param url
	 * @param decoding
	 * @return
	 */
	public static String get(String url,String decoding){
		RequestParameter parameter = new RequestParameter();
		parameter.setUrl(url);
		parameter.setDecoding(decoding);
		return get(parameter,10*1000,10*1000);
	}
	
	/**
	 * 
	 * @param url
	 * @param encode
	 * @param connectTimeout
	 * @param soTimeout
	 * @return
	 */
	public static String get(RequestParameter parameter, int connectTimeout,int soTimeout) {
		try {
			ResponseResult result = HttpClientInvoker.doGet(parameter, connectTimeout, soTimeout);
			//HttpEntity entity = result.getEntity();
			return result.getResponseText();
		} catch (Exception e) {
			Xlogger.error(XMsgError.buildSimple(HttpClientUtil.class.getName(), "get", e));
		}
		return null;
	}
	/**
	 * 获得entity内容
	 * @param entity
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public static String getEntityString(HttpEntity entity,RequestParameter parameter) throws Exception{
		String str = "";
		try {
			if (entity != null) {
				if(StringUtils.isEmpty(parameter.getDecoding())){
					str = EntityUtils.toString(entity);							
				}
				else{
					str = EntityUtils.toString(entity, parameter.getDecoding());							
				}
				Xlogger.log(XMsgBaseSimlpe.build(HttpClientUtil.class.getName(), "getEntityString", null).setRequestUrl(parameter.getUrl()).setResponseStr(str));
				return str;
			}
		}catch(Exception e){
			throw e;
		} finally {
			if (entity != null) {
				entity.getContent().close();
			}
		}
		return str;
	}
}
