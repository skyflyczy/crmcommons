package com.np.crm.common.http;

import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.nio.charset.CodingErrorAction;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSON;
import com.np.crm.common.logger.XMsgBaseSimlpe;
import com.np.crm.common.logger.XMsgError;
import com.np.crm.common.logger.Xlogger;

/**
 * http client invoker工具类
 * @author zhiya.chai
 * 2014-04-25 下午7:01:12
 */
public class HttpClientInvoker {
	
	private static PoolingHttpClientConnectionManager connManager = null;

	private static CloseableHttpClient httpclient = null;

	static {

		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
				.<ConnectionSocketFactory> create()
				.register("http", PlainConnectionSocketFactory.INSTANCE)
				.register("https", SSLConnectionSocketFactory.getSocketFactory())
				.build();

		connManager = new PoolingHttpClientConnectionManager(
				socketFactoryRegistry);

		httpclient = HttpClients.custom().setConnectionManager(connManager)
				.build();

		// Create socket configuration
		SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true)
				.build();

		connManager.setDefaultSocketConfig(socketConfig);
		// Create message constraints

		MessageConstraints messageConstraints = MessageConstraints.custom()
		.setMaxHeaderCount(200)
		.setMaxLineLength(2000)
		.build();

		// Create connection configuration
		ConnectionConfig connectionConfig = ConnectionConfig.custom()
		.setMalformedInputAction(CodingErrorAction.IGNORE)
		.setUnmappableInputAction(CodingErrorAction.IGNORE)
		.setCharset(Consts.UTF_8)
		.setMessageConstraints(messageConstraints).build();
		connManager.setDefaultConnectionConfig(connectionConfig);
		connManager.setMaxTotal(200);
		connManager.setDefaultMaxPerRoute(20);

	}
	
	/**
	 * 发起post请求
	 * @param parameter
	 * @param timeout
	 * @param content
	 * @return
	 * @throws Exception 
	 */
	public static ResponseResult doPost(RequestParameter parameter, int timeout,String content) throws Exception {
		HttpPost post = null;
		CloseableHttpResponse response = null;
		try {
			post = new HttpPost(parameter.getUrl());
			if(!StringUtils.isEmpty(parameter.getContentType())){
				post.setHeader("Content-type", parameter.getContentType());
			}
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout)
					.setConnectTimeout(timeout).setConnectionRequestTimeout(timeout)
					.setExpectContinueEnabled(false).build();
			post.setConfig(requestConfig);
			if(!StringUtils.isEmpty(parameter.getEncoding())){
				post.setEntity(new StringEntity(content, parameter.getEncoding()));
			}
			List<Header> headerlist=parameter.getHeaderlist();
			for(Header header:headerlist){
				post.addHeader(header);				
			}
			Xlogger.log(XMsgBaseSimlpe.build(HttpClientInvoker.class.getName(), "doPost", null).setRequestUrl(parameter.getUrl()).setRequestParams(content));
			response = httpclient.execute(post);
			ResponseResult responseResult  = new ResponseResult(
					HttpClientUtil.getEntityString(response.getEntity(), parameter), response.getStatusLine());
			return responseResult;
		} catch (UnsupportedEncodingException e) {
			Xlogger.error(XMsgError.buildSimple(HttpClientInvoker.class.getName(), "doPost", e).setRequestURL(parameter.getUrl()));
			throw new Exception(e);
		} catch (Exception e) {
			Xlogger.error(XMsgError.buildSimple(HttpClientInvoker.class.getName(), "doPost", e).setRequestURL(parameter.getUrl()));
			throw new Exception(e);
		} finally {
			if (response != null) {
				response.close();
			}
			if(post!=null){
				post.releaseConnection();
			}
		}
	}
	

	/**
	 * 页面发起http请求
	 * 
	 * @param url
	 * @param map
	 * @param charset
	 * @param timeout
	 * @return
	 * @throws Exception 
	 */
	public static ResponseResult doPost(RequestParameter parameter,Map<String, String> map, int timeout) throws Exception {
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		return doPost(parameter, list, timeout);
	}

	/**
	 * 页面发起http请求 不包含附件
	 * 
	 * @param list
	 * @param charset
	 * @param timeout
	 * @return
	 * @throws Exception 
	 */
	public static ResponseResult doPost(RequestParameter parameter, List<NameValuePair> list, int timeout) throws Exception {
		HttpPost post = new HttpPost(parameter.getUrl());
		CloseableHttpResponse response = null;
		try {
			if (parameter.getContentType() != null)
				post.setHeader("Content-type", parameter.getContentType());
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(timeout).setConnectTimeout(timeout)
					.setConnectionRequestTimeout(timeout)
					.setExpectContinueEnabled(false).build();
			post.setConfig(requestConfig);
			if (timeout <= 0) {
				timeout = 10;
			}
			post.setEntity(new UrlEncodedFormEntity(list, parameter.getEncoding())); // 将参数传入post方法中
			List<Header> headerlist=parameter.getHeaderlist();
			for(Header header:headerlist){
				post.addHeader(header);				
			}
			Xlogger.log(XMsgBaseSimlpe.build(HttpClientInvoker.class.getName(), "doPost", null).setRequestUrl(parameter.getUrl()).setRequestParams(JSON.toJSONString(list)));
			response = httpclient.execute(post);
			ResponseResult responseResult  = new ResponseResult(
					HttpClientUtil.getEntityString(response.getEntity(), parameter), response.getStatusLine());
			return responseResult;
		} catch (UnsupportedEncodingException e) {
			Xlogger.error(XMsgError.buildSimple(HttpClientInvoker.class.getName(), "doPost", e).setRequestURL(parameter.getUrl()));
			throw new Exception(e);
		} catch (Exception e) {
			Xlogger.error(XMsgError.buildSimple(HttpClientInvoker.class.getName(), "doPost", e).setRequestURL(parameter.getUrl()));
			throw new Exception(e);
		} finally {
			if (response != null) {
				response.close();
			}
			if (post != null) {
				post.releaseConnection();
			}
		}
	}	
	/**
	 * get 方法
	 * @param url
	 * @param decoding
	 * @return
	 * @throws Exception
	 */
	public static ResponseResult doGet(String url,String decoding) throws Exception{
		RequestParameter parameter = new RequestParameter();
		parameter.setUrl(url);
		parameter.setDecoding(decoding);
		return doGet(parameter,10*1000,10*1000);
	}
	/**
	 * get方法
	 * @param parameter
	 * @param connectTimeout
	 * @param soTimeout
	 * @return
	 * @throws Exception
	 */
	public static ResponseResult doGet(RequestParameter parameter, int connectTimeout,int soTimeout) throws Exception {

		RequestConfig requestConfig = RequestConfig.custom()
		.setSocketTimeout(connectTimeout)
		.setConnectTimeout(connectTimeout)
		.setConnectionRequestTimeout(connectTimeout).build();
		
		StringBuilder sb = new StringBuilder();
		sb.append(parameter.getUrl());
		
		HttpGet get = new HttpGet(sb.toString());
		get.setConfig(requestConfig);
		
		CloseableHttpResponse response = null ;
		try {
			List<Header> headerlist=parameter.getHeaderlist();
			for(Header header:headerlist){
				get.addHeader(header);				
			}			
			Xlogger.log(XMsgBaseSimlpe.build(HttpClientInvoker.class.getName(), "doGet", null).setRequestUrl(parameter.getUrl()));
			response = httpclient.execute(get);
			ResponseResult responseResult  = new ResponseResult(
					HttpClientUtil.getEntityString(response.getEntity(), parameter), response.getStatusLine());
			return responseResult;
		} catch (SocketTimeoutException e) {
			Xlogger.error(XMsgError.buildSimple(HttpClientInvoker.class.getName(), "doGet", e).setRequestURL(parameter.getUrl()));
			throw new Exception(e);
		} catch (Exception e) {
			Xlogger.error(XMsgError.buildSimple(HttpClientInvoker.class.getName(), "doGet", e).setRequestURL(parameter.getUrl()));
			throw new Exception(e);
		} finally {
			if (response != null) {
				response.close();
			}
			if(get!=null){
				get.releaseConnection();
			}
		}
	}
}
