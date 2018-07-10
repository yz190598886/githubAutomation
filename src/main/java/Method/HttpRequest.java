package Method;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;


public class HttpRequest {

    public String getTmpcookies(String loginurl) throws Exception {
        String tmpcookies = "";
        HttpClient httpClient = new HttpClient();
        //获得POST请求方法
        PostMethod postMethod = new PostMethod(loginurl);
        //       postMethod.setRequestBody(data);
//        try {
            //设置 HttpClient 接收 Cookie,用与浏览器一样的策略
            httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
            int loginState = httpClient.executeMethod(postMethod);
//            String LoginBack = postMethod.getResponseBodyAsString();
//            System.out.println(loginState+LoginBack);
            //获得登陆后的 Cookie
            Cookie[] cookies = httpClient.getState().getCookies();

            for (Cookie c : cookies) {
                tmpcookies += c.toString()+";";
//                        System.out.println(tmpcookies);
            }
//        } catch (HttpException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return loginState+"$$"+tmpcookies;
    }
    public static String postDataWithJson(String url,String json) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        try {
            httpPost.setHeader("Content-type", "application/json;charset=UTF-8");
            httpPost.setHeader("Accept","application/json;charset=UTF-8");
            httpPost.setHeader("isPostMan","true");
            httpPost.setEntity(new StringEntity(json, "UTF-8"));
            CloseableHttpResponse response = httpclient.execute(httpPost);
            int code=response.getStatusLine().getStatusCode();
            String srtResult = EntityUtils.toString(response.getEntity(),"UTF-8");//获得返回的结果
            return code+"$$"+srtResult;
        } catch (HttpHostConnectException e){
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
//    public String postMethodParaJson(String LoginUrl,String Json) throws IOException {
//            HttpClient httpClient = new HttpClient();
//            PostMethod postMethod = new PostMethod(LoginUrl);
//            postMethod.setRequestHeader("Content-type","application/json;charset=UTF-8");
//            postMethod.setRequestHeader("Accept","application/json;charset=UTF-8");
//            postMethod.setRequestEntity(new StringRequestEntity(Json));
//            int code = httpClient.executeMethod(postMethod);
//            String LoginTxt = postMethod.getResponseBodyAsString();
//            postMethod.releaseConnection();
//            return code+"$$"+LoginTxt;
//        }

    public String postMethod(String httpurl,String cookie) throws IOException {
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod1 = new PostMethod(httpurl);
        postMethod1.setRequestHeader("Cookie",cookie);
        int code=httpClient.executeMethod(postMethod1);
        String posttxt = postMethod1.getResponseBodyAsString();
        postMethod1.releaseConnection();
        return code+"$$"+posttxt;

    }

    public String getMethod(String httpurl,String cookie) throws IOException {
        HttpClient httpClient1 = new HttpClient();
        GetMethod getMethod1 = new GetMethod(httpurl);
        getMethod1.setRequestHeader("cookie", cookie);
        int code = httpClient1.executeMethod(getMethod1);
        String gettxt = getMethod1.getResponseBodyAsString();
        getMethod1.releaseConnection();
        return code+"$$"+gettxt;

    }

    public String Jsonvalue(String jsondata,String key) throws IOException {
                JSONObject json= JSON.parseObject(jsondata);
                String first=json.get(key).toString();
                if(first==null){
                    return first;
                }else {
                    return first;
                }

}
}