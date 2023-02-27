package com.bcareapp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.bcareapp.commonclasses.BCException;

public class HttpUtil {
	private static final Logger logger = Logger.getLogger(HttpUtil.class);

	public static String performHTTPGetRequest(String url, Map<String, String> headerMap) throws BCException {
		Logger.getLogger("org.apache.http").setLevel(Level.WARN);
		Logger.getLogger("httpclient.wire.header").setLevel(Level.WARN);
		Logger.getLogger("httpclient.wire.content").setLevel(Level.WARN);
		CloseableHttpResponse httpResponse = null;
		StringBuffer response = new StringBuffer();
		try {
			logger.debug("url: " + url);
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url);
			if (headerMap != null && headerMap.size() > 0) {
				Iterator<String> keyIter = headerMap.keySet().iterator();
				while (keyIter.hasNext()) {
					String headerKey = keyIter.next();
					String headerValue = headerMap.get(headerKey);
					httpGet.setHeader(headerKey, headerValue);
				}
			}
			httpResponse = httpClient.execute(httpGet);
			logger.debug("GET Response Status:: " + httpResponse.getStatusLine().getStatusCode());
			BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
			String inputLine;
			while ((inputLine = reader.readLine()) != null) {
				response.append(inputLine);
			}
			reader.close();
			httpClient.close();

			return response.toString();
		} catch (IOException e) {
			throw new BCException("Exception occured while GET service call", e);
		} catch (Exception e) {
			throw new BCException("Exception occured while GET service call", e);
		}
	}

	public static String performHTTPGetRequest(String url, Map<String, String> headerMap, String jsonInString)
			throws BCException {
		Logger.getLogger("org.apache.http").setLevel(Level.WARN);
		Logger.getLogger("httpclient.wire.header").setLevel(Level.WARN);
		Logger.getLogger("httpclient.wire.content").setLevel(Level.WARN);
		CloseableHttpResponse httpResponse = null;
		StringBuffer response = new StringBuffer();
		try {
			logger.debug("url: " + url);
			headerMap.put("Content-Type", "application/json");
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpGetWithEntity httpGet = new HttpGetWithEntity(url);
			if (headerMap != null && headerMap.size() > 0) {
				Iterator<String> keyIter = headerMap.keySet().iterator();
				while (keyIter.hasNext()) {
					String headerKey = keyIter.next();
					String headerValue = headerMap.get(headerKey);
					httpGet.setHeader(headerKey, headerValue);
				}
			}

			if (jsonInString != null && !jsonInString.isEmpty()) {
				logger.debug("URL: " + url);
				logger.debug("POST request body:: " + jsonInString);
				StringEntity params = new StringEntity(jsonInString, "UTF-8");
				httpGet.setEntity(params);
			}

			httpResponse = httpClient.execute(httpGet);
			logger.debug("GET Response Status:: " + httpResponse.getStatusLine().getStatusCode());
			BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
			String inputLine;
			while ((inputLine = reader.readLine()) != null) {
				response.append(inputLine);
			}
			reader.close();
			httpClient.close();

			return response.toString();
		} catch (IOException e) {
			throw new BCException("Exception occured while GET service call", e);
		} catch (Exception e) {
			throw new BCException("Exception occured while GET service call", e);
		}
	}

	public static String performPostRequest(String jsonInString, String url, Map<String, String> headerMap)
			throws BCException {
		Logger.getLogger("org.apache.http").setLevel(Level.WARN);
		Logger.getLogger("httpclient.wire.header").setLevel(Level.WARN);
		Logger.getLogger("httpclient.wire.content").setLevel(Level.WARN);
		CloseableHttpResponse httpResponse = null;
		StringBuffer response = new StringBuffer();
		try {
			CloseableHttpClient client = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader("Accept", "application/json");
			httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
			httpPost.addHeader("cache-control", "no-cache");
			if (headerMap != null && headerMap.size() > 0) {
				Iterator<String> keyIter = headerMap.keySet().iterator();
				while (keyIter.hasNext()) {
					String headerKey = keyIter.next();
					String headerValue = headerMap.get(headerKey);
					httpPost.addHeader(headerKey, headerValue);
				}
			}
			if (jsonInString != null && !jsonInString.isEmpty()) {
				logger.debug("URL: " + url);
				logger.debug("POST request body:: " + jsonInString);
				StringEntity params = new StringEntity(jsonInString, "UTF-8");
				httpPost.setEntity(params);
			}
			httpResponse = client.execute(httpPost);
			BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
			String inputLine = null;
			while ((inputLine = reader.readLine()) != null) {
				response.append(inputLine);
			}
			reader.close();
			client.close();
			logger.debug("POST Response body:: " + response.toString());
			return response.toString();
		} catch (IOException e) {
			throw new BCException("Exception occured while POST service call", e);
		} catch (Exception e) {
			throw new BCException("Exception occured while POST service call", e);
		}

	}

}
