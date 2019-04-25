package com.chan.study.httpcleint.temp;

import org.apache.http.client.config.RequestConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyRequestConfig {
	@Value("${httpclient.config.connectTimeout}")
	private int connectTimeout = 3000;

	@Value("${httpclient.config.connectionRequestTimeout}")
	private int connectionRequestTimeout = 2000;

	@Value("${httpclient.config.readTimeout}")
	private int socketTimeout = 5000;

	@Bean
	public RequestConfig config() {
		return RequestConfig.custom().setConnectionRequestTimeout(this.connectionRequestTimeout)
				.setConnectTimeout(this.connectTimeout).setSocketTimeout(this.socketTimeout).build();
	}
}
