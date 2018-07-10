package TestCase.FkhTestCase.TMS;

import Method.HttpRequest;
import Method.StringTools;
import Method.jdbc;
import org.apache.log4j.Logger;
import org.testng.Assert;

public class TmsTwo {
    private static final Logger log =Logger.getLogger(TmsTwo.class);
    public void FKHTmsProcessTwo () throws Exception {

        /*
         *WEB:TMS
         *项目：自己作为承运方
         *计划：件+不扣磅差+以发货为准
         *派车
         *上传、完善、审核发货凭证+确认卸货
         * */

        log.info("\n/**\n*WEB:TMS\n*项目：自己作为承运方\n*计划：件+不扣磅差+以发货为准\n*派车\n*上传、完善、审核发货凭证+确认卸货\n**/");
        //数据库、请求、工具
        jdbc db=new jdbc();
        StringTools stringTools=new StringTools();
        HttpRequest httpRequest=new HttpRequest();

//        //登陆:莱尼卡贸易公司613,货主账户
//        log.info("--------------------登录获得cookie--------------------");
//        String TmsLogin="https://dev.fkhwl.com/webgis_test/login.html?loginAccount=18181910613&loginPasswd=95075dfedfe8f80ac6e45115918fd7f3&randomCode=99&loginType=2";
//        log.info(TmsLogin);
//        String comeback=httpRequest.getTmpcookies(TmsLogin);//登陆
//        log.info(comeback);
//        String code=stringTools.code(comeback);
//        Assert.assertEquals(code,"303","登陆失败");
//        String cookie=stringTools.back(comeback);
//        log.info("登陆成功,获得cookie："+cookie);

        log.info("--------------------创建计划--------------------");
        //不扣磅差+件+以发货为准
        String cookie=TmsOne.cookie;
        String ProjectId=TmsOne.ProjectId;
        log.info("ProjectId:"+ProjectId);
        String name=stringTools.name();
        String createPlan="https://dev.fkhwl.com/webgis_test/projects/createPlan.json?projectId="+ProjectId+"&planName=%E4%BB%A5%E5%8F%91%E8%B4%A7%E4%BB%B6"+name+"&departureCity=%E9%87%8D%E5%BA%86&loadAddress=1299&arrivalCity=%E5%9B%9B%E5%B7%9D-%E6%88%90%E9%83%BD&arrivalAddress=1293&modelType=0&packagedForm=1102&programNo=100&units=%E4%BB%B6&poundKey=3&poundValue=-1&cargoPrice=100&carLengthBegin=%E4%B8%8D%E9%99%90&carLengthEnd=%E4%B8%8D%E9%99%90&carLength=%E4%B8%8D%E9%99%90&carType=%E4%B8%8D%E9%99%90&mileage=166.921%E5%85%AC%E9%87%8C";
        log.info(createPlan);
        String PlanBack=httpRequest.postMethod(createPlan,cookie);
        log.info(PlanBack);
        Assert.assertEquals(stringTools.code(PlanBack),"200","计划创建失败！");
        String HttpBackPlan=stringTools.back(PlanBack);
        Assert.assertEquals(HttpBackPlan,"{\"message\":\"创建成功！\",\"data\":null,\"success\":true}","计划创建失败！");
        String planName="以发货件"+name;
        log.info("计划创建成功："+planName);

        log.info("--------------------派车--------------------");
        String SqlPlanNameId="select id from program where programName='"+planName+"'";
        String planId=db.getSearchResult(SqlPlanNameId);//数据库获取计划id
        log.info("planId:"+planId);
        //个体
        String Car="https://dev.fkhwl.com/webgis_test/cargos/sendAndAssign.json?planId="+planId+"&cargoNum=10%E4%BB%B6&vehicleIds=8072&sijiIds=8073&isSend=2&logisticId=0";
        log.info(Car);
        String CarBack=httpRequest.postMethod(Car,cookie);//派车
        log.info(CarBack);
        Assert.assertEquals(stringTools.code(CarBack),"200","派车失败！");
        String HttpBackCar=stringTools.back(CarBack);
        Assert.assertEquals(HttpBackCar,"{\"message\":null,\"success\":true,\"data\":{\"errorPlateNos\":[],\"successPlateNos\":[\"川A-55555\"],\"errorMsg\":[]}}","派车失败！");
        String SqlWaybillNo="select waybillNo from waybillcar where driverId=8073 and waybillCarStatus=1";
        String WaybillNo=db.getSearchResult(SqlWaybillNo);//数据库获取运单编号
        log.info("派车成功，运单号："+WaybillNo);

        log.info("--------------------上传装货凭证--------------------");
        //跳过上传图片接口，使用存在的图片地址
        String SqlWaybillId="select id  from waybill  where waybillNo='"+WaybillNo+"'";
        String WaybillId=db.getSearchResult(SqlWaybillId);//数据库获取运单id
        log.info("waybillCarId:"+WaybillId+"（参数waybillCarId取的是WaybillId）");
        String SCFHInvoiceBills="https://dev.fkhwl.com/webgis_test/bills/saveSCInvoiceBills.json?waybillCarId="+WaybillId+"&invoice=https%3A%2F%2Fdev.fkhwl.com%2Fuploads%2Fthumbnail%2Fthumbnail_1530170039559_1_icon.jpg&type=1";
        log.info(SCFHInvoiceBills);
        String SCFHInvoiceBillsBack=httpRequest.postMethod(SCFHInvoiceBills,cookie);//上传装货
        log.info(SCFHInvoiceBillsBack);
        Assert.assertEquals(stringTools.code(SCFHInvoiceBillsBack),"200","上传装货凭证失败！");
        String HttpBackSCFHInvoiceBills=stringTools.back(SCFHInvoiceBillsBack);
        Assert.assertEquals(HttpBackSCFHInvoiceBills,"{\"message\":null,\"success\":true,\"data\":null}","上传装货凭证失败！");
        log.info("上传装货凭证成功！");

        log.info("--------------------确认卸货--------------------");
        String confirmWaybil="https://dev.fkhwl.com/webgis_test/transporting/confirmWaybill.json?type=2&waybillId="+WaybillId+"&_=1530520138626";
        log.info(confirmWaybil);
        String confirmWaybilBack=httpRequest.getMethod(confirmWaybil,cookie);
        log.info(confirmWaybilBack);
        Assert.assertEquals(stringTools.code(confirmWaybilBack),"200","确认卸货失败！");
        String HttpBackconfirmWaybil=stringTools.back(confirmWaybilBack);
        Assert.assertEquals(HttpBackconfirmWaybil,"{\"message\":null,\"success\":true,\"data\":null}","确认卸货失败！");
        log.info("确认卸货成功！");


        log.info("--------------------完善装货凭证--------------------");
        String SqlWaybillCarId="select id from waybillcar where waybillNo='"+WaybillNo+"'";
        String waybillCarId=db.getSearchResult(SqlWaybillCarId);
        log.info("waybillCarId:"+waybillCarId);
        String perfectSendBill="https://dev.fkhwl.com/webgis_test/bills/perfectSendBill.json?sendGrossWeight=0&sendTareWeight=0&sendNetWeight=100&allowDifference=2&allowDifferenceVal=&allowDifferenceAmount=10&waybillCarId="+waybillCarId+"&valuePrice=100&unitPrice=10&invoice=https%3A%2F%2Fdev.fkhwl.com%2Fuploads%2Fthumbnail%2Fthumbnail_1530170039559_1_icon.jpg&unit=2";
        log.info(perfectSendBill);
        String perfectSendBillBack=httpRequest.postMethod(perfectSendBill,cookie);
        log.info(perfectSendBillBack);
        Assert.assertEquals(stringTools.code(perfectSendBillBack),"200","完善装货凭证失败！");
        String HttpBackperfectSendBill=stringTools.back(perfectSendBillBack);
        Assert.assertEquals(HttpBackperfectSendBill,"{\"message\":null,\"success\":true,\"data\":null}","完善装货凭证失败！");
        log.info("完善装货凭证成功！");

        log.info("--------------------审核装货凭证--------------------");
        String checkBill="https://dev.fkhwl.com/webgis_test/bills/checkBill.json?waybillCarId="+waybillCarId+"&agree=1&billType=1&etcCardAmount=0&cashPayAmount=&depositAmount=0&oilCardAmount=0";
        log.info(checkBill);
        String checkBillBack=httpRequest.postMethod(checkBill,cookie);
        log.info(checkBillBack);
        Assert.assertEquals(stringTools.code(checkBillBack),"200","审核装货凭证失败！");
        String HttpBackcheckBill=stringTools.back(checkBillBack);
        Assert.assertEquals(HttpBackcheckBill,"{\"message\":null,\"success\":true,\"data\":null}","审核装货凭证失败！");
        log.info("审核装货凭证成功！");
    }
}
