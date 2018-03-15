package com.tf.permission.client.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class HttpClientUtils {

	public static String getDataStringByGetMethod(String target) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		StringBuilder entityStringBuilder = new StringBuilder();
		try {
			// 创建httpget.
			HttpGet httpget = new HttpGet(target);
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
		return entityStringBuilder.toString();
	}

	public static <T> List<T> getDataListByGetMethod(String target, Class<T> convertClass) {
		List<T> result = null;
		String responseResult = getDataStringByGetMethod(target);
		if (!StringUtils.isEmpty(responseResult)) {
			result = JSON.parseArray(responseResult, convertClass);
		}
		return result;
	}
	
	public static JSONObject getDataToJsonObject(String target) {
		String responseResult = getDataStringByGetMethod(target);
		if (!StringUtils.isEmpty(responseResult)) {
			return JSON.parseObject(responseResult);
		}
		return null;
	}
}
