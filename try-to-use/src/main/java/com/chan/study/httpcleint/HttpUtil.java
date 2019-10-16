//package com.chan.study.httpcleint;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpEntityEnclosingRequest;
//import org.apache.http.HttpHost;
//import org.apache.http.HttpRequest;
//import org.apache.http.NameValuePair;
//import org.apache.http.NoHttpResponseException;
//import org.apache.http.client.HttpRequestRetryHandler;
//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.client.methods.HttpRequestBase;
//import org.apache.http.client.protocol.HttpClientContext;
//import org.apache.http.config.Registry;
//import org.apache.http.config.RegistryBuilder;
//import org.apache.http.conn.ConnectTimeoutException;
//import org.apache.http.conn.routing.HttpRoute;
//import org.apache.http.conn.socket.ConnectionSocketFactory;
//import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
//import org.apache.http.conn.socket.PlainConnectionSocketFactory;
//import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.protocol.HttpContext;
//import org.apache.http.util.EntityUtils;
//import sun.net.www.http.HttpClient;
//
//import javax.net.ssl.HttpsURLConnection;
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.SSLException;
//import javax.net.ssl.SSLHandshakeException;
//import javax.net.ssl.SSLSocketFactory;
//import javax.net.ssl.TrustManager;
//import javax.net.ssl.X509TrustManager;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.InterruptedIOException;
//import java.io.OutputStream;
//import java.io.UnsupportedEncodingException;
//import java.net.ConnectException;
//import java.net.URL;
//import java.net.UnknownHostException;
//import java.security.cert.CertificateException;
//import java.security.cert.X509Certificate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//public class HttpUtil {
//
//    static final int TIME_OUT = 10 * 1000;
//
//    private static CloseableHttpClient httpClient = null;
//
//    private final static Object SYNC_LOCK = new Object();
//    private static PoolingHttpClientConnectionManager  connManager = null;
//
//    private static void config(HttpRequestBase httpRequestBase) {
//        // 设置Header等
//        // httpRequestBase.setHeader("User-Agent", "Mozilla/5.0");
//        // httpRequestBase
//        // .setHeader("Accept",
//        // "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//        // httpRequestBase.setHeader("Accept-Language",
//        // "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");// "en-US,en;q=0.5");
//        // httpRequestBase.setHeader("Accept-Charset",
//        // "ISO-8859-1,utf-8,gbk,gb2312;q=0.7,*;q=0.7");
//
//        // 配置请求的超时设置
//        RequestConfig requestConfig = RequestConfig.custom()
//                .setConnectionRequestTimeout(TIME_OUT)
//                .setConnectTimeout(TIME_OUT).setSocketTimeout(TIME_OUT).build();
//        httpRequestBase.setConfig(requestConfig);
//    }
//
//    /**
//     * 获取HttpClient对象
//     *
//     * @return
//     * @author SHANHY
//     * @create 2015年12月18日
//     */
//    public static CloseableHttpClient getHttpClient(String url) {
//        String hostname = url.split("/")[2];
//        int port = 80;
//        if (hostname.contains(":")) {
//            String[] arr = hostname.split(":");
//            hostname = arr[0];
//            port = Integer.parseInt(arr[1]);
//        }
//        if (httpClient == null) {
//            synchronized (SYNC_LOCK) {
//                if (httpClient == null) {
//                    httpClient = createHttpClient(200, 40, 100, hostname, port);
//                }
//            }
//        }
//        return httpClient;
//    }
//
//    /**
//     * 创建HttpClient对象
//     *
//     * @return
//     * @author SHANHY
//     * @create 2015年12月18日
//     */
//    public static CloseableHttpClient createHttpClient(int maxTotal,
//                                                       int maxPerRoute, int maxRoute, String hostname, int port) {
//        ConnectionSocketFactory plainsf = PlainConnectionSocketFactory
//                .getSocketFactory();
//        LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory
//                .getSocketFactory();
//        Registry<ConnectionSocketFactory> registry = RegistryBuilder
//                .<ConnectionSocketFactory>create().register("http", plainsf)
//                .register("https", sslsf).build();
//        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(
//                registry);
//        // 将最大连接数增加
//        cm.setMaxTotal(maxTotal);
//        // 将每个路由基础的连接增加
//        cm.setDefaultMaxPerRoute(maxPerRoute);
//        HttpHost httpHost = new HttpHost(hostname, port);
//        // 将目标主机的最大连接数增加
//        cm.setMaxPerRoute(new HttpRoute(httpHost), maxRoute);
//
//        // 请求重试处理
//        HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
//            @Override
//            public boolean retryRequest(IOException exception,
//                                        int executionCount, HttpContext context) {
//                if (executionCount >= 5) {// 如果已经重试了5次，就放弃
//                    return false;
//                }
//                if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
//                    return true;
//                }
//                if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
//                    return false;
//                }
//                if (exception instanceof InterruptedIOException) {// 超时
//                    return false;
//                }
//                if (exception instanceof UnknownHostException) {// 目标服务器不可达
//                    return false;
//                }
//                if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
//                    return false;
//                }
//                if (exception instanceof SSLException) {// SSL握手异常
//                    return false;
//                }
//
//                HttpClientContext clientContext = HttpClientContext
//                        .adapt(context);
//                HttpRequest request = clientContext.getRequest();
//                // 如果请求是幂等的，就再次尝试
//                if (!(request instanceof HttpEntityEnclosingRequest)) {
//                    return true;
//                }
//                return false;
//            }
//        };
//
//        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm)
//                .setRetryHandler(httpRequestRetryHandler).build();
//
//        return httpClient;
//    }
//
//    private static void setPostParams(HttpPost httpost,
//                                      Map<String, Object> params) {
//        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//        Set<String> keySet = params.keySet();
//        for (String key : keySet) {
//            nvps.add(new BasicNameValuePair(key, params.get(key).toString()));
//        }
//        try {
//            httpost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * GET请求URL获取内容
//     *
//     * @param url
//     * @return
//     * @throws IOException
//     * @author SHANHY
//     * @create 2015年12月18日
//     */
//    public static String post(String url, Map<String, Object> params) throws IOException {
//        HttpPost httppost = new HttpPost(url);
//        config(httppost);
//        setPostParams(httppost, params);
//        CloseableHttpResponse response = null;
//        try {
//            response = getHttpClient(url).execute(httppost,
//                    HttpClientContext.create());
//            HttpEntity entity = response.getEntity();
//            String result = EntityUtils.toString(entity, "utf-8");
//            EntityUtils.consume(entity);
//            return result;
//        } catch (Exception e) {
////          e.printStackTrace();
//            throw e;
//        } finally {
//            try {
//                if (response != null) {
//                    response.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * GET请求URL获取内容
//     *
//     * @param url
//     * @return
//     * @author SHANHY
//     * @create 2015年12月18日
//     */
//    public static String get(String url) {
//        HttpGet httpget = new HttpGet(url);
//        config(httpget);
//        CloseableHttpResponse response = null;
//        try {
//            response = getHttpClient(url).execute(httpget,
//                    HttpClientContext.create());
//            HttpEntity entity = response.getEntity();
//            String result = EntityUtils.toString(entity, "utf-8");
//            EntityUtils.consume(entity);   //关闭HttpEntity是的流，如果手动关闭了InputStream instream = entity.getContent();这个流，也可以不调用这个方法
//            return result;
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (response != null) {
//                    response.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//    public <T> T get(String path,Class<T> clazz){
//        CloseableHttpClient httpClient= HttpClientBuilder.create().setConnectionManager(connManager).build();
//
//    }
//
//    public static <T> T httpsRequest(String requestUrl,String requestMethod,String outputStr){
//        HttpClient httpClient = HttpClients.custom().setConnectionManager(connManager)
//                //SSlContext
//    }
//    /**
//     * 发起https请求并获取结果
//     *
//     * @param requestUrl    请求地址
//     * @param requestMethod 请求方式（GET、POST）
//     * @param outputStr     提交的数据
//     * @return JSONObject(通过JSONObject.get ( key)的方式获取json对象的属性值)
//     */
//    public static String httpsRequest4214(String requestUrl, String requestMethod, String outputStr) {
//        StringBuffer buffer = new StringBuffer();
//        HttpsURLConnection httpUrlConn = null;
//        InputStream inputStream = null;
//        InputStreamReader inputStreamReader = null;
//        BufferedReader bufferedReader = null;
//        OutputStream outputStream = null;
//        try {
//            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
//            TrustManager[] tm = {new X509TrustManager() {
//                // 检查客户端证书
//                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//                }
//
//                // 检查服务器端证书
//                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//                }
//
//                // 返回受信任的x509数组
//                public X509Certificate[] getAcceptedIssuers() {
//                    return null;
//                }
//            }};
//            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
//            sslContext.init(null, tm, new java.security.SecureRandom());
//            // 从上述SSLContext对象中得到SSLSocketFactory对象
//            SSLSocketFactory ssf = sslContext.getSocketFactory();
//
//            URL url = new URL(requestUrl);
//            httpUrlConn = (HttpsURLConnection) url.openConnection();
//            httpUrlConn.setSSLSocketFactory(ssf);
//
//            httpUrlConn.setDoOutput(true);
//            httpUrlConn.setDoInput(true);
//            httpUrlConn.setUseCaches(false);
//            // 设置请求方式（GET/POST）
//            httpUrlConn.setRequestMethod(requestMethod);
//
//            if ("GET".equalsIgnoreCase(requestMethod)) httpUrlConn.connect();
//
//            // 当有数据需要提交时
//            if (null != outputStr) {
//                outputStream = httpUrlConn.getOutputStream();
//                // 注意编码格式，防止中文乱码
//                outputStream.write(outputStr.getBytes("UTF-8"));
//            }
//
//            // 将返回的输入流转换成字符串
//            inputStream = httpUrlConn.getInputStream();
//            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
//            bufferedReader = new BufferedReader(inputStreamReader);
//
//            String str = null;
//            while ((str = bufferedReader.readLine()) != null) {
//                buffer.append(str);
//            }
//        } catch (ConnectException ce) {
//            log.error("Weixin server connection timed out.");
//        } catch (Exception e) {
//            log.error("https request error:{}", e);
//        } finally {
//            // 释放资源
//            try {
//                if (bufferedReader != null) {
//                    bufferedReader.close();
//                }
//                if (outputStream != null) {
//                    outputStream.close();
//                }
//                if (inputStreamReader != null) {
//                    inputStreamReader.close();
//                }
//                if (inputStream != null) {
//                    inputStream.close();
//                }
//                if (httpUrlConn != null) {
//                    httpUrlConn.disconnect();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return buffer.toString();
//    }
//
///*    public static void main(String[] args) {
//        // URL列表数组
//        String[] urisToGet = {
//                "http://blog.csdn.net/catoop/article/details/38849497",
//                "http://blog.csdn.net/catoop/article/details/38849497",
//                "http://blog.csdn.net/catoop/article/details/38849497",
//                "http://blog.csdn.net/catoop/article/details/38849497",
//
//                "http://blog.csdn.net/catoop/article/details/38849497",
//                "http://blog.csdn.net/catoop/article/details/38849497",
//                "http://blog.csdn.net/catoop/article/details/38849497",
//                "http://blog.csdn.net/catoop/article/details/38849497",
//
//                "http://blog.csdn.net/catoop/article/details/38849497",
//                "http://blog.csdn.net/catoop/article/details/38849497",
//                "http://blog.csdn.net/catoop/article/details/38849497",
//                "http://blog.csdn.net/catoop/article/details/38849497",
//
//                "http://blog.csdn.net/catoop/article/details/38849497",
//                "http://blog.csdn.net/catoop/article/details/38849497",
//                "http://blog.csdn.net/catoop/article/details/38849497",
//                "http://blog.csdn.net/catoop/article/details/38849497",
//
//                "http://blog.csdn.net/catoop/article/details/38849497",
//                "http://blog.csdn.net/catoop/article/details/38849497",
//                "http://blog.csdn.net/catoop/article/details/38849497",
//                "http://blog.csdn.net/catoop/article/details/38849497",
//
//                "http://blog.csdn.net/catoop/article/details/38849497",
//                "http://blog.csdn.net/catoop/article/details/38849497",
//                "http://blog.csdn.net/catoop/article/details/38849497",
//                "http://blog.csdn.net/catoop/article/details/38849497"};
//
//        long start = System.currentTimeMillis();
//        try {
//            int pagecount = urisToGet.length;
//            ExecutorService executors = Executors.newFixedThreadPool(pagecount);
//            CountDownLatch countDownLatch = new CountDownLatch(pagecount);
//            for (int i = 0; i < pagecount; i++) {
//                HttpGet httpget = new HttpGet(urisToGet[i]);
//                config(httpget);
//                // 启动线程抓取
//                executors
//                        .execute(new GetRunnable(urisToGet[i], countDownLatch));
//            }
//            countDownLatch.await();
//            executors.shutdown();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            System.out.println("线程" + Thread.currentThread().getName() + ","
//                    + System.currentTimeMillis() + ", 所有线程已完成，开始进入下一步！");
//        }
//
//        long end = System.currentTimeMillis();
//        System.out.println("consume -> " + (end - start));
//    }
//
//    static class GetRunnable implements Runnable {
//        private CountDownLatch countDownLatch;
//        private String url;
//
//        public GetRunnable(String url, CountDownLatch countDownLatch) {
//            this.url = url;
//            this.countDownLatch = countDownLatch;
//        }
//
//        @Override
//        public void run() {
//            try {
//                System.out.println(HttpUtil.get(url));
//            } finally {
//                countDownLatch.countDown();
//            }
//        }
//    }*/
//}
//   /* Post使用方法
//
//    // 其中 params 为 Map<String, Object> params
//    String ret = HttpClientUtil.post(url, params);
//    jsonRet = new JSONObject(ret);
//    一开始我是使用传统的 HttpURLConnection 来做网络请求的，查了很多资料，有不少说 HttpURLConnection 效率高的。可是经过我修改实现方法后，HttpClient 连接池版本的网络请求相对比较稳定。这也说明，我们并不请尽信他人解说，凡事还是要寻找适合自己的方法，真正的解决自己的问题，才是王道。
//
//            ===========================================
//
//    在使用 HttpURLConnection 的时候，大并发对外做网络请求的时候，前期请求耗时还好，后面耗时越来越高。下面是我之前的实现代码：
//
//    @Deprecated
//    protected JSONObject callRestfulOld(String url, Map<String, Object> params)
//    {
//        String temp;
//        String ret="";
//        JSONObject jsonRet=null;
//        String sign = generateSign("POST", url, params);// 对参数进行加密签名
//        if(sign.isEmpty()) return new JSONObject("{\"ret_code\":-1,\"err_msg\":\"generateSign error\"}");
//        params.put("sign", sign);
//        try{
//            URL u = new URL(url);
//            HttpURLConnection conn = (HttpURLConnection)u.openConnection();
//            conn.setRequestMethod("POST");
//            conn.setConnectTimeout(10000);
//            conn.setDoOutput(true);
//            conn.setDoInput(true);
//            conn.setUseCaches(false);
//            StringBuffer param = new StringBuffer();
//            for (String key: params.keySet())
//            {
//                param.append(key).append("=").append(URLEncoder.encode(params.get(key).toString(), "UTF-8")).append("&");
//            }
//            conn.getOutputStream().write(param.toString().getBytes("UTF-8"));
//
//            //System.out.println(param);
//            conn.getOutputStream().flush();
//            conn.getOutputStream().close();
//            InputStreamReader isr = new InputStreamReader(conn.getInputStream());
//            BufferedReader br = new BufferedReader(isr);
//            while((temp = br.readLine()) != null){
//                ret += temp;
//            }
//            br.close();
//            isr.close();
//            conn.disconnect();
//            //System.out.println(ret);
//            jsonRet = new JSONObject(ret);
//
//        } catch(java.net.SocketTimeoutException e) {
//            //e.printStackTrace();
//            jsonRet = new JSONObject("{\"ret_code\":-1,\"err_msg\":\"call restful timeout\"}");
//        } catch(Exception e) {
//            //e.printStackTrace();
//            jsonRet = new JSONObject("{\"ret_code\":-1,\"err_msg\":\"call restful error\"}");
//        }
//        return jsonRet;
//    }*/