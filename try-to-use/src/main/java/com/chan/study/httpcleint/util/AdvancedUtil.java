/*
package com.chan.study.httpcleint.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.net.www.http.HttpClient;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.MessageFormat;
import java.util.List;


*/
/**
 * 高级接口工具类
 *
 * @author Administrator
 *//*

public class AdvancedUtil {
    private static Logger log = LoggerFactory.getLogger(AdvancedUtil.class);
    public static String WX_ACCESS_TOKEN_KEY = "agent:wx_access_token";
    public static String WX_ACCESS_TOKEN_KEY_LOCK = "agent:wx_access_token:lock";

    */
/**
     * 获取网页授权凭证
     *
     * @param appId     公众账号的唯一标识
     * @param appSecret 公众账号的密钥
     * @param code
     * @return WeixinAouth2Token
     *//*

    public static WeixinOauth2Token getOauth2AccessToken(String appId, String appSecret, String code) {
        WeixinOauth2Token wat = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        requestUrl = requestUrl.replace("APPID", appId);
        requestUrl = requestUrl.replace("SECRET", appSecret);
        requestUrl = requestUrl.replace("CODE", code);
        // 获取网页授权凭证
        String json = httpsRequest(requestUrl, "GET", null);
        if (StringUtils.isNotBlank(json)) {
            try {
                wat = getAuthorToken(json);
            } catch (Exception e) {
                wat = null;

                int errorCode = ((JSONObject) JSONObject.toJSON(json)).getIntValue("errcode");
                ;
                String errorMsg = ((JSONObject) JSONObject.toJSON(json)).getString("errmsg");
                log.error("获取网页授权凭证失败 errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }
        return wat;
    }

    */
/**
     * 刷新网页授权凭证
     *
     * @param appId        公众账号的唯一标识
     * @param refreshToken
     * @return WeixinAouth2Token
     *//*

    public static WeixinOauth2Token refreshOauth2AccessToken(String appId, String refreshToken) {
        WeixinOauth2Token wat = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
        requestUrl = requestUrl.replace("APPID", appId);
        requestUrl = requestUrl.replace("REFRESH_TOKEN", refreshToken);
        // 刷新网页授权凭证
        String json = httpsRequest(requestUrl, "GET", null);
        if (StringUtils.isNotBlank(json)) {
            try {
                wat = getAuthorToken(json);
            } catch (Exception e) {
                wat = null;

                int errorCode = ((JSONObject) JSONObject.toJSON(json)).getIntValue("errcode");
                ;
                String errorMsg = ((JSONObject)JSONObject.toJSON(json)).getString("errmsg");
                log.error("刷新网页授权凭证失败 errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }
        return wat;
    }

    private static WeixinOauth2Token getAuthorToken(String json) throws Exception {
        WeixinOauth2Token wat = new WeixinOauth2Token();
        wat.setAccessToken(((JSONObject)JSONObject.toJSON(json)).getString("access_token"));
        wat.setExpiresIn(((JSONObject)JSONObject.toJSON(json)).getIntValue("expires_in"));
        wat.setRefreshToken(((JSONObject)JSONObject.toJSON(json)).getString("refresh_token"));
        wat.setOpenId(((JSONObject)JSONObject.toJSON(json)).getString("openid"));
        wat.setScope(((JSONObject)JSONObject.toJSON(json)).getString("scope"));
        return wat;
    }

    */
/**
     * 通过网页授权获取用户信息
     *
     * @param accessToken 网页授权接口调用凭证
     * @param openId      用户标识
     * @return SNSUserInfo
     *//*

    @SuppressWarnings({"unchecked"})
    public static SNSUserInfo getSNSUserInfo(String accessToken, String openId) {
        SNSUserInfo snsUserInfo = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        // 通过网页授权获取用户信息
        String json = httpsRequest(requestUrl, "GET", null);
        if (StringUtils.isNotBlank(json)) {
            try {
                snsUserInfo = new SNSUserInfo();
                // 用户的标识
                snsUserInfo.setOpenid(((JSONObject)JSONObject.toJSON(json)).getString("openid"));
                // 用户的标识
                snsUserInfo.setUnionid(((JSONObject)JSONObject.toJSON(json)).getString("unionid"));
                // 昵称
                snsUserInfo.setNickname(((JSONObject)JSONObject.toJSON(json)).getString("nickname"));
                // 性别（1是男性，2是女性，0是未知）
                snsUserInfo.setSex(((JSONObject)JSONObject.toJSON(json)).getIntValue("sex"));
                // 用户所在国家
                snsUserInfo.setCountry(((JSONObject)JSONObject.toJSON(json)).getString("country"));
                // 用户所在省份
                snsUserInfo.setProvince(((JSONObject)JSONObject.toJSON(json)).getString("province"));
                // 用户所在城市
                snsUserInfo.setCity(((JSONObject)JSONObject.toJSON(json)).getString("city"));
                // 用户头像
                snsUserInfo.setHeadimgurl(((JSONObject)JSONObject.toJSON(json)).getString("headimgurl"));
                // 用户特权信息
                snsUserInfo.setPrivilegeList(
                        JsonUtils.jsonToObject(JsonUtils.getValueByKey(json, "privilege").toString(), List.class));
            } catch (Exception e) {
                snsUserInfo = null;
                e.printStackTrace();
                int errorCode = ((JSONObject) JSONObject.toJSON(json)).getIntValue("errcode");
                ;
                String errorMsg = ((JSONObject)JSONObject.toJSON(json)).getString("errmsg");
                log.error("获取用户信息失败 errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }
        return snsUserInfo;
    }


    // 获取access_token的接口地址（GET） 限200（次/天）

    */
/**
     * 获取access_token
     *
     * @param appid     凭证
     * @param appsecret 密钥
     * @return
     *//*

    public static AccessToken getAccessToken(String appid, String appsecret) {
        AccessToken wat = null;

        String requestUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}";
        requestUrl = MessageFormat.format(requestUrl, appid, appsecret);
        log.info("获取token，requestUrl={}", requestUrl);

        try {
            String jsonString = HttpClient.get(requestUrl, null);
            wat = JsonUtils.jsonToObject(jsonString, AccessToken.class);
            log.debug("===从微信拿AccessToken：{}", wat);
            if (null == wat || 0 != wat.getErrcode()) {
                if (wat != null) {
                    log.error("网页授权凭证失败 errcode:{} errmsg:{}", wat.getErrcode(), wat.getErrmsg());
                }
                return null;
            }

            log.info("获取AccessToken：{}", wat);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return wat;

    }

    public static <T> T httpsRequest(String requestUrl,String requestMethod,String outputStr){
        HttpClient httpClient = HttpClients.custom().setConnectionManager();
    }
    */
/**
     * 发起https请求并获取结果
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr     提交的数据
     * @return JSONObject(通过JSONObject.get ( key)的方式获取json对象的属性值)
     *//*

    public static String httpsRequest4214(String requestUrl, String requestMethod, String outputStr) {
        StringBuffer buffer = new StringBuffer();
        HttpsURLConnection httpUrlConn = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        OutputStream outputStream = null;
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new X509TrustManager() {
                // 检查客户端证书
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                // 检查服务器端证书
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                // 返回受信任的x509数组
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            }};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod)) httpUrlConn.connect();

            // 当有数据需要提交时
            if (null != outputStr) {
                outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
            }

            // 将返回的输入流转换成字符串
            inputStream = httpUrlConn.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
        } catch (ConnectException ce) {
            log.error("Weixin server connection timed out.");
        } catch (Exception e) {
            log.error("https request error:{}", e);
        } finally {
            // 释放资源
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                if (httpUrlConn != null) {
                    httpUrlConn.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return buffer.toString();
    }

    */
/**
     * 获取jsapi调用票据
     *
     * @param accessToken
     * @return
     *//*

    public static String getJsApiTicket(String accessToken) {
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
        String json = httpsRequest(requestUrl, "GET", "{}");

        JsonNode jsapiTicket = ((JSONObject)JSONObject.toJSON(json)).getString("ticket");
        if (jsapiTicket != null) {
            return jsapiTicket.asText();
        }
        return null;
    }


}
*/
