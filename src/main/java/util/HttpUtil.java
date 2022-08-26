package util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/*
* https://stock.xueqiu.com/v5/stock/finance/cn/indicator.json?symbol=SH600690&type=Q4&is_detail=true&count=5&timestamp=
* */
public class HttpUtil {
    public static String sendHttp(String url){
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            // 创建httpget.
            HttpGet httpget = new HttpGet(url);
            httpget.addHeader("cookie", "xq_a_token=28ed0fb1c0734b3e85f9e93b8478033dbc11c856; xqat=28ed0fb1c0734b3e85f9e93b8478033dbc11c856; xq_r_token=bf8193ec3b71dee51579211fc4994d03f17c64ac; xq_id_token=eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJ1aWQiOi0xLCJpc3MiOiJ1YyIsImV4cCI6MTY2MzExMzIyMSwiY3RtIjoxNjYxMzQ4MDE0MTMyLCJjaWQiOiJkOWQwbjRBWnVwIn0.ZYxTwDzt1-gqJf3WXCrwDPITohDlpTO0znPHpfMpRoY1lTRxue3x-ONQM0I3aw1yvM5ehIBmQ_L34ybwgqGRepIq4LBAhodQ2atD__Mdh5aMPqd0YfjI9a2T2uHg8YiWxAcljroPJy9la8oezr-4aE23A4g_uUaWCwzNV1X4CSQrpCPcn8kW3ThEsgb3gdpCwz_VyfeP6YjvQoX8rySEr_upTWaCCU9wno10-0yOrnD_tYQPt7kQdISdvPA0vkEaA43OUqQCDzbxrlCryJ_fVYGxdJ0bJ-VqAmuVQmivq4cQB5hTW2jkfFi26Ik5Z5xNN7mDkz1YY_x5PyBly4X1AA; u=171661348027207; Hm_lvt_1db88642e346389874251b5a1eded6e3=1661348030; device_id=6e5ad3064ec34a002726685d820a6c3c; s=bw17w1zm9h; is_overseas=0; Hm_lpvt_1db88642e346389874251b5a1eded6e3=1661349615");
            System.out.println("executing request " + httpget.getURI());
            // 执行get请求.
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                // 打印响应状态
                System.out.println(response.getStatusLine());
                if (entity != null) {
                    return EntityUtils.toString(entity);
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
