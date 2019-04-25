package com.chan.study.httpcleint.temp;

import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * HttpClient客户端封装
 */
@Service("httpClientManagerFactoryBean")
public class HttpClientManagerFactoryBean implements
		FactoryBean<CloseableHttpClient>, InitializingBean, DisposableBean {

	/**
	 * FactoryBean生成的目标对象
	 */
	private CloseableHttpClient client;

	@Autowired
	private HttpRequestRetryHandler httpRequestRetryHandler;

	@Autowired
	private PoolingHttpClientConnectionManager poolHttpcConnManager;

	@Autowired
	private RequestConfig config;

	// 销毁上下文时，销毁HttpClient实例
	@Override
	public void destroy() throws Exception {
		/*
		 * 如果httpclient不是单例的，connection manager也不能是单例的
		 */
		if (null != this.client) {
			this.client.close();
		}
	}

	@Override
	// 初始化实例
	public void afterPropertiesSet() throws Exception {
		/*
		 * 建议此处使用HttpClients.custom的方式来创建HttpClientBuilder，
		 * 而不要使用HttpClientBuilder.create()方法来创建HttpClientBuilder
		 * 从官方文档可以得出，HttpClientBuilder是非线程安全的，但是HttpClients确实Immutable的，
		 * immutable 对象不仅能够保证对象的状态不被改变， 而且还可以不使用锁机制就能被其他线程共享
		 */
		this.client = HttpClients.custom()
				.setConnectionManager(poolHttpcConnManager)
				.setRetryHandler(httpRequestRetryHandler)
				.setDefaultRequestConfig(config).build();
	}

	// 返回实例的类型
	@Override
	public CloseableHttpClient getObject() throws Exception {
		return this.client;
	}

	@Override
	public Class<?> getObjectType() {
		return (this.client == null ? CloseableHttpClient.class : this.client
				.getClass());
	}

	// 构建的实例为单例
	@Override
	public boolean isSingleton() {
		return true;
	}

}
