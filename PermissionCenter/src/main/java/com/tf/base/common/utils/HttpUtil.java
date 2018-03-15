package com.tf.base.common.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * HTTP请求工具类.
 * 
 */
public class HttpUtil {

	//private static Logger log = Logger.getLogger(HttpUtil.class);

	/**
	 * 传输超时时间，单位毫秒.
	 */
	public static int socketTimeout = 30000;

	/**
	 * Get方式取得URL的内容.
	 * 
	 * @param url
	 *            访问的网址
	 * @return
	 */
	public static String getUrlContent(String url) {

		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = null;
		try {

			// 执行请求
			response = httpclient.execute(httpGet);

			// 转换结果
			HttpEntity entity1 = response.getEntity();
			String html = EntityUtils.toString(entity1);

			// 消费掉内容
			EntityUtils.consume(entity1);
			return html;
		} catch (IOException e) {
		//	log.error("IO异常.", e);
			return "";
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
				}
			}
			try {
				httpclient.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * Post方式取得URL的内容，默认为"application/x-www-form-urlencoded"格式，charset为UTF-8.
	 * 
	 * @param url
	 *            访问的网址
	 * @param content
	 *            提交的数据
	 * @return
	 * @throws IOException
	 */
	public static String postToUrl(String url, String content)
			throws IOException {
		return postToUrl(url, content, "application/x-www-form-urlencoded",
				"UTF-8");
	}

	/**
	 * JSON字符串形式请求
	 * 
	 * @param url
	 * @param content
	 * @return
	 * @throws IOException
	 */
	public static String postForJson(String url, String content)
			throws IOException {
		return postToUrl(url, content, "application/json", "UTF-8");
	}

	/**
	 * Post方式取得URL的内容.
	 * 
	 * @param url
	 *            访问的网址
	 * @param content
	 *            提交的数据
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static String postToUrl(String url, String content,
			String contentType, String charset) throws IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);

		// 设置内容
		ContentType type = ContentType.create(contentType,
				Charset.forName(charset));
		//StringEntity reqEntity = new StringEntity(content, type);
		//httpPost.setEntity(reqEntity);
		httpPost.addHeader("User-Agent",
				"Mozilla/4.0 (compatible; MSIE .0; Windows NT 6.1; Trident/4.0; SLCC2;)");
		httpPost.addHeader("Accept-Encoding", "*");

		// 超时设置
		// ConnectionRequestTimeout，从连接池获取连接的超时时间
		// ConnectTimeout，连接服务器的超时时间
		// SocketTimeout，传输数据的超时时间，是两个数据包之间的间隔时间，并非整体传输时间。如果一直有数据传输，不会触发此异常。
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(3000).setConnectTimeout(3000)
				.setSocketTimeout(socketTimeout).build();
		httpPost.setConfig(requestConfig);

		CloseableHttpResponse response = null;
		try {
			// 执行请求
			response = httpclient.execute(httpPost);

			// 转换结果
			HttpEntity entity1 = response.getEntity();
			String html = EntityUtils.toString(entity1, charset);

			// 消费掉内容
			EntityUtils.consume(entity1);
			return html;
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
				}
			}
			try {
				httpclient.close();
			} catch (IOException e) {
			}
		}
	}

	
	public static String getToUrl(String url){
		String result = "" ;
		CloseableHttpClient httpclient = HttpClients.createDefault();

		StringBuilder entityStringBuilder = new StringBuilder();

		JSONArray resultJson = null;


		try {
			// 创建httpget.
			HttpGet httpget = new HttpGet(url);

			BufferedReader bufferedReader = null;

			// 执行get请求.
			CloseableHttpResponse response = httpclient.execute(httpget);

			// 获取响应实体
			HttpEntity entity = response.getEntity();

			// 打印响应状态
			if (entity != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"), 8 * 1024);
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					entityStringBuilder.append(line);
				}

				result = entityStringBuilder.toString();

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
	
	public static void main(String[] args) {
		//http://196.1681.122:8076/notificationServer
		try {
			 // 发送邮件测试
			String url = "http://115.182.234.156:9001/notificationServer/do";
		    
			String html = postForJson(url,"");
			System.out.println(html);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}
