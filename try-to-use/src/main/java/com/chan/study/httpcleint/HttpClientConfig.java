package com.chan.study.httpcleint;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
public class HttpClientConfig {

    @Resource
    private HttpClientProperties httpClientProperties;

    @Bean
    public PoolingHttpClientConnectionManager httpClientConnectionManager(){
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", SSLConnectionSocketFactory.getSystemSocketFactory())
                .build();
        PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
        manager.setDefaultSocketConfig(socketConfig);
        //设置整个连接池的最大连接数
        manager.setMaxTotal(300);
        // 设置每个路由的默认组大连接
        // 设置过小无法支持大并发(ConnectionPoolTimeoutException)
        manager.setDefaultMaxPerRoute(200);
        // 设置从连接池获连接时,连接不活跃多长时间需要进行一次验证
        manager.setValidateAfterInactivity(5000);
        return manager;
    }

    @Bean
    @ConditionalOnMissingBean
    public RequestConfig requestConfig(){
        log.info("装载 http-client 对象...");
        return RequestConfig.custom()
                //请求等待时间
                .setConnectTimeout(2000)
                //等待响应数据时间
                .setSocketTimeout(5000)
                //从连接池获得连接的等待时间
                .setConnectionRequestTimeout(2000)
                .build();
    }

    /**
     * 1. HttpClient的线程安全的创建一个就可以提供给多个进程使用了,无需频繁创建HttpClient
     * 2. 只有在连接池较大时,考虑配置多个HttpClient
     * 3. 使用时应该尽快消费响应体并释放资源,不要保持太久
     *
     * @param httpClientConnectionManager
     * @param requestConfig
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public HttpClient  httpClient(PoolingHttpClientConnectionManager httpClientConnectionManager,RequestConfig requestConfig){
        log.info("装载 http-client 对象...");
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(httpClientConnectionManager)
                //设置连接池为非共享模式
                .setConnectionManagerShared(false)
                //定时回收空闲连接
                .evictIdleConnections(60, TimeUnit.SECONDS)
                //定时关闭无效链接
                .evictExpiredConnections()
                //连接存活时间设置,如果不设置,则根据长连接信息决定
                .setConnectionTimeToLive(60, TimeUnit.SECONDS)
                .setDefaultRequestConfig(requestConfig)
                //连接重用策略,即是否使用keep-alive
                .setConnectionReuseStrategy(DefaultConnectionReuseStrategy.INSTANCE)
                //长连接配置,获得长连接的时间配置
                .setKeepAliveStrategy(DefaultConnectionKeepAliveStrategy.INSTANCE)
                //不采取重试策略
                .setRetryHandler(new DefaultHttpRequestRetryHandler(0,false))
                .build();
        //JVM 停止或重启,关闭连接池
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            try {
                httpClient.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }));
        return httpClient;
    }

    @Bean
    @ConditionalOnMissingClass
    public HttpComponentsClientHttpRequestFactory requestFactory(HttpClient httpClient) {
        log.info("装载 request-factory 对象...");
        // 注入 httpClient 连接配置
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        clientHttpRequestFactory.setConnectTimeout(httpClientProperties.getConnectTimeout());
        clientHttpRequestFactory.setReadTimeout(httpClientProperties.getReadTimeout());
        clientHttpRequestFactory.setConnectionRequestTimeout(httpClientProperties.getConnectionRequestTimeout());
        return clientHttpRequestFactory;
    }

    @Bean
    @LoadBalanced
    @ConditionalOnClass(ClientHttpRequestFactory.class)
    public RestTemplate restTemplate(ClientHttpRequestFactory requestFactory){
        log.info("装载 rest-template 对象...");
        return new RestTemplate(requestFactory);
    }
    //todo CtChan 执行监控线程用于管理线程安全
}
