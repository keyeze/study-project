package com.chan.study.httpcleint.resttemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class HttpRequestHelper {
	private static Logger log = LoggerFactory.getLogger(HttpRequestHelper.class);
	private RestTemplate template;
	/**
	 * 
	 */
	public HttpRequestHelper(RestTemplate restTemplate) {
		this.template = restTemplate;
	}
	/**
	 * <pre>
	 * for example:
	 * HttpHeaders headers = new HttpHeaders();
	 * headers.set("channelId", "1");
	 * User user = post(url,headers,httpBody,User.class)
	 * </pre>
	 * 
	 * @param url
	 * @param httpHeaders
	 * @param responseType
	 * @return
	 */
	public <T> T get(String url, HttpHeaders httpHeaders, Class<T> responseType) {
		T t = null;
		if (StringUtils.isEmpty(url)) {
			return t;
		}

		httpHeaders = addTraceId(httpHeaders);

		HttpEntity<Object> requestEntity = new HttpEntity<Object>(httpHeaders);
		try {
			t = template.exchange(url, HttpMethod.GET, requestEntity, responseType).getBody();
			log.debug("com.dafy.composite.commons.springboot.resttemplate.HttpRequestHelper-get|url={},params={},result={}", url, requestEntity,
					t == null ? t : t.toString());
		} catch (RestClientException e) {
			log.error("com.dafy.composite.commons.springboot.resttemplate.HttpRequestHelper-get RestClientException,url={},params={}", url, requestEntity, e);
		}
		return t;
	}

	/**
	 * <pre>
	 * for example:
	 * HttpHeaders headers = new HttpHeaders();
	 * headers.set("channelId", "1");
	 * MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
	 * headers.setContentType(type);
	 * headers.add("Accept", MediaType.APPLICATION_JSON.toString());
	 * Map<String,String> httpBody = new HashMap<>();
	 * httpBody.put("a", "1234");
	 * User user = post(url,headers,httpBody,User.class)
	 * </pre>
	 * 
	 * @param url
	 * @param httpHeaders
	 * @param httpBody
	 * @param responseType
	 * @return
	 */
	public <T> T post(String url, HttpHeaders httpHeaders, Object httpBody, Class<T> responseType) {
		T t = null;
		if (StringUtils.isEmpty(url)) {
			return t;
		}

        httpHeaders = addTraceId(httpHeaders);

		HttpEntity<Object> requestEntity = new HttpEntity<Object>(httpBody, httpHeaders);
		try {
			t = template.exchange(url, HttpMethod.POST, requestEntity, responseType).getBody();
			log.info("com.dafy.composite.commons.springboot.resttemplate.HttpRequestHelper-post|url={},params={},result={}", url, requestEntity,
					t == null ? t : t.toString());

		} catch (RestClientException e) {
			log.error("com.dafy.composite.commons.springboot.resttemplate.HttpRequestHelper-post RestClientException,url={},params={}", url, requestEntity, e);
		}
		return t;
	}

	/**
	 * 表单形式提交
	 * 
	 * @param url
	 * @param httpHeaders
	 *            请求头
	 * @param requestData
	 *            请求数据
	 * @param typeReference
	 *            返回类型
	 * @return
	 */
	public <T> T post(String url, HttpHeaders httpHeaders, MultiValueMap<String, String> requestData,
                      Class<T> responseType) {
		T t = null;
		if (StringUtils.isEmpty(url)) {
			return t;
		}

        httpHeaders = addTraceId(httpHeaders);

		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(
				requestData, httpHeaders);
		try {
			ResponseEntity<T> resultEntity = template.postForEntity(url, requestEntity, responseType);
			if (resultEntity != null) {
				t = resultEntity.getBody();
				log.info("com.dafy.composite.commons.springboot.resttemplate.HttpRequestHelper-post|url={}", url);
			} else {
				log.error("com.dafy.composite.commons.springboot.resttemplate.HttpRequestHelper-post No Data Response,url={}", url);
			}
		} catch (Exception e) {
			log.error("com.dafy.composite.commons.springboot.resttemplate.HttpRequestHelper-post RestClientException,url={},msg={}", url, e);
		}
		return t;
	}

	private HttpHeaders addTraceId(HttpHeaders header) {
		String traceId = MDC.get("traceId");
		if (traceId != null) {
			if (header == null) {
				header = new HttpHeaders();
			}

			if (!header.containsKey("x-baoko-trace-id")) {
				header.add("x-baoko-trace-id", traceId);
			}
		}
		return header;
	}
}
