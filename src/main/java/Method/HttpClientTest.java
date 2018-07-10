package Method;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class HttpClientTest {

    public void cookie() throws Exception {
        //创建httpClient
       HttpClientBuilder httpClientBuilder=HttpClientBuilder.create();
        CloseableHttpClient httpClient=httpClientBuilder.build();
        String url="https://dev.fkhwl.com/webgis_test/login.html";
        HttpPost httpPost = new HttpPost(url);
        //提交表单数据
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("loginAccount", "18181910613"));
        nvps.add(new BasicNameValuePair("loginPasswd", "95075dfedfe8f80ac6e45115918fd7f3"));
        nvps.add(new BasicNameValuePair("randomCode", "1111"));
        nvps.add(new BasicNameValuePair("loginType", "2"));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps,"UTF-8"));
        //返回响应
        HttpResponse result=httpClient.execute(httpPost);
        System.out.println(result);
    }

    public static void main(String[] args) throws Exception {
        HttpClientTest httpClientTest=new HttpClientTest();
        httpClientTest.cookie();
    }
}
