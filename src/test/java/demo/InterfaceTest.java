package demo;

import Method.HttpRequest;
import Method.OneExcelDemo;
import Method.Params;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.List;

public class InterfaceTest {
//    private static final Logger log =Logger.getLogger(InterfaceTest.class);
//    int i=10000;
//
//    public String GetCookie() throws IOException {
////        String LoginUrl="https://dev.fkhwl.com/webgis_test/login.html?loginAccount=18181915310&loginPasswd=95075dfedfe8f80ac6e45115918fd7f3&randomCode=111&loginType=2";
//        String LoginUrl="http://192.168.2.3/webgis/login.html?loginAccount=18181915310&loginPasswd=95075dfedfe8f80ac6e45115918fd7f3&randomCode=111&loginType=2";
//        HttpRequest httpRequest=new HttpRequest();
//        String cookie=httpRequest.getTmpcookies(LoginUrl);
//        return cookie;
//    }
    public void go() throws Exception {
        String LoginUrl="http://192.168.2.3/webgis/login.html?loginAccount=18181915310&loginPasswd=95075dfedfe8f80ac6e45115918fd7f3&randomCode=111&loginType=2";
        String Url="http://192.168.2.3/webgis/vehicleConvene/saveSpecialCar.json?editCar=false&licensePlateNoArea=%E5%B7%9D&licensePlateNoLetter=D&carLicensePlateNo=10000&axleNum=%E4%B8%8D%E9%99%90&carType=%E4%B8%8D%E9%99%90&carLength=%E4%B8%8D%E9%99%90&cargoTonnage=22%E5%90%A8&carBrand=2321321312&siji.driverName=%E6%B5%8B%E8%AF%95%E5%8F%B8%E6%9C%BA&siji.mobileNo=13659595959&siji.driverCarNo=513701199104162310&siji.driverCarApproveDate=2018-06-11&siji.driverCarDate=2018-06-12&licensePlateNo=%E5%B7%9DD-10000";
        String ss="http://192.168.2.3/webgis/projects/listProjects.json?isClosed=2";
        String oo="https://test.chinaylzl.com/j_spring_security_check?mobile=13999999995&password=111111&passwordState=false";
        String wewe="https://test.chinaylzl.com/assistBusiness/listBusinessCloseService?pageIndex=1&businessName=&ready=1&sort=&orderby=";
        String online="http://192.168.2.3/webgis/projects/listProjects.json?isClosed=2&_=1528778136126?isClosed=2";
        String dsad="http://192.168.2.3/webgis/home/home.html";
        String TestLogin="https://dev.fkhwl.com/webgis_test/login.html?loginAccount=18181915310&loginPasswd=95075dfedfe8f80ac6e45115918fd7f3&randomCode=111&loginType=2";
        String TestIndex="https://dev.fkhwl.com/webgis_test/projects/listProjects.json?isClosed=2";
        HttpRequest httpRequest=new HttpRequest();
        String cookie=httpRequest.getTmpcookies(TestLogin);
        System.out.println(cookie);
//        httpRequest.postMethod(Url,cookie);
        System.out.println(httpRequest.getMethod(TestIndex,cookie));

    }
//@Test
//    public void ss() throws IOException {
////        for(int a=1;a<2;a++){
//    InterfaceTest interfaceTest=new InterfaceTest();
//    interfaceTest.go();
////        }
//    System.out.println(i);
}




//    @Test
//   public void IntetfaceaTestOne() throws IOException {
////        String path = "E:/接口参数.xlsx";
//        String path = "/usr/source/接口参数.xlsx";
//        OneExcelDemo oneExcelDemo=new OneExcelDemo();
//        List<Params> params=oneExcelDemo.read(path);
////        第一行数据
//        String Url=params.get(0).getUrl();
//        String Parameter=params.get(0).getParameter();
//        String result=params.get(0).getResult();
//        String HttpUrl=Url+"?"+Parameter;
//        System.out.println(Url);
//        System.out.println(Parameter);
////        System.out.println(result);
//        String Cookie=GetCookie();
//        HttpRequest httpRequest=new HttpRequest();
//        String response=httpRequest.getMethod(HttpUrl,Cookie);
//        log.info(httpRequest.getMethod(HttpUrl,Cookie));
//        System.out.println(response);
//    }
//    @Test
//    public void zero(){
//        System.out.println("0000000000000");
//    }
//    @Test
//    public void one(){
//        System.out.println("111111111111111");
//        String a="我";
//        String b="你";
//        Assert.assertEquals(a,b);
//
//    }
//    @Test
//    public void two(){
//        System.out.println("22222222222222");
//    }
//    @Test
//    public void three(){
//        System.out.println("3333333333333333");
//    }
//    @Test
//    public void four(){
//        System.out.println("4444444444444444444");
//    }
//    @Test
//    public void five(){
//        System.out.println("5555555555555555555555");
//        int a=1;
//        int b=a/0;
//    }
//    @Test
//    public void six(){
//        System.out.println("666666666666666666");
//    }
//    @Test
//    public void seven(){
//        System.out.println("7777777777777777777");
//    }
//    @Test
//    public void eight(){
//        System.out.println("888888888888888888888");
//    }


