package org.coding.http;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.*;
import java.util.logging.Logger;

/**
 * @ClassName: HttpClientUtil
 * @Description: http请求工具
 * @author zhanghb
 * @date 2022-09-21
 */
public class HttpClientUtil
{
    private static final Logger logger     = Logger.getLogger(HttpClientUtil.class.getName());
    private CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    /**
     * 接口超时时间
     */
    private static final int TIMEOUT = 3000;

    public static HttpClientUtil getInstance(List<BasicHeader> basicHeaderList)
    {
        HttpClientUtil HttpClientUtil = new HttpClientUtil();

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(TIMEOUT)
                .setConnectionRequestTimeout(TIMEOUT)
                .setSocketTimeout(TIMEOUT).build();
        httpClientBuilder.setDefaultRequestConfig(config);
        /**
         * header
         */
        if (null != basicHeaderList)
        {
            httpClientBuilder.setDefaultHeaders(basicHeaderList);
        }
        HttpClientUtil.httpClient = httpClientBuilder.build();
        return HttpClientUtil;
    }

    public static HttpClientUtil getInstance()
    {
       return getInstance(null);
    }

    /**
     * 发送http请求(发送中台请求)
     *
     * @param requestUrl
     *            请求的路径，域名的前缀信息请在项目配置文件的“XXX.host”中配置
     * @param requestBody
     *            请求的内容
     * @return
     * @throws Exception
     */
    public String doPost(String requestUrl, String requestBody) throws Exception
    {

        long startTime = System.currentTimeMillis();
        String url = requestUrl;
        HttpPost httpPost = new HttpPost(url);
        StringEntity entity = new StringEntity(requestBody, "UTF-8");
        httpPost.setEntity(entity);
        CloseableHttpResponse response = null;
        try {
            logger.info("requestUrl:"+requestUrl);
            logger.info("requestBody:"+requestBody);
            //5. 由客户端执行(发送)Post 请求
            response = httpClient.execute(httpPost);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            String ret = EntityUtils.toString(responseEntity);
            logger.info("resp:"+ret);
            return ret;
        }catch (SocketTimeoutException e2)
        {
            //接口请求超时
            logger.info("HttpClientUtil SocketTimeoutException error : "+e2.getMessage());
            throw new Exception("接口请求超时");
        }catch (ConnectException e1)
        {
            //服务器连接超时
            logger.info("HttpClientUtil ConnectException error ："+e1.getMessage());
            throw new Exception("连接服务器超时");
        } catch (Exception e) {
            logger.info("HttpClientUtil request error : "+e.getMessage());
            throw new Exception("接口请求异常");
        } finally {
            long costTime = System.currentTimeMillis() - startTime;
            logger.info("HttpClientUtil requestTime ："+ costTime);
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                logger.info("HttpClient 请求错误！"+ e);
                throw new Exception("HttpClient 请求错误！", e);
            }
        }
    }

}
