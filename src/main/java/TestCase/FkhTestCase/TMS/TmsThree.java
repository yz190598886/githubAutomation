package TestCase.FkhTestCase.TMS;

import Method.HttpRequest;
import Method.StringTools;
import Method.jdbc;
import org.apache.log4j.Logger;
import org.testng.Assert;

public class TmsThree {
    private static final Logger log = Logger.getLogger(TmsOne.class);
    public void FKHTmsProcessThree() throws Exception {

        /*
         *WEB:TMS
         *项目：自己作为承运方
         *计划：吨+不扣磅差+以收货为准
         *派车
         *上传、完善、审核收货凭证+确认装货
         * */

        log.info("\n/**\n*WEB:TMS\n*项目：自己作为承运方\n*计划：吨+不扣磅差+以收货为准\n*派车\n*上传、完善、审核收货凭证+确认装货\n**/");
        //数据库、请求、工具
        jdbc db = new jdbc();
        StringTools stringTools = new StringTools();
        HttpRequest httpRequest = new HttpRequest();

        log.info("--------------------创建计划--------------------");
        //不扣磅差+吨+以收货为准
        String cookie=TmsOne.cookie;
        String ProjectId=TmsOne.ProjectId;
        log.info("ProjectId:"+ProjectId);
        String name=stringTools.name();
        String createPlan="https://dev.fkhwl.com/webgis_test/projects/createPlan.json?projectId="+ProjectId+"&planName=%E4%BB%A5%E6%94%B6%E8%B4%A7%E5%90%A8"+name+"&departureCity=%E9%87%8D%E5%BA%86&loadAddress=1299&arrivalCity=%E5%9B%9B%E5%B7%9D-%E6%88%90%E9%83%BD&arrivalAddress=1293&modelType=0&packagedForm=1101&programNo=100&units=%E5%90%A8&poundKey=3&poundValue=-2&cargoPrice=10&carLengthBegin=%E4%B8%8D%E9%99%90&carLengthEnd=%E4%B8%8D%E9%99%90&carLength=%E4%B8%8D%E9%99%90&carType=%E4%B8%8D%E9%99%90&mileage=166.921%E5%85%AC%E9%87%8C";
        log.info(createPlan);
        String createPlanBack=httpRequest.postMethod(createPlan,cookie);
        log.info(createPlanBack);
        Assert.assertEquals(stringTools.code(createPlanBack),"200","计划创建失败！");
        String HttpBackcreatePlan=stringTools.back(createPlanBack);
        Assert.assertEquals(HttpBackcreatePlan,"{\"message\":\"创建成功！\",\"data\":null,\"success\":true}","计划创建失败！");
        String planName="以收货吨"+name;
        log.info("计划创建成功："+planName);

        log.info("--------------------派车--------------------");
        String SqlPlanNameId="select id from program where programName='"+planName+"'";
        String planId=db.getSearchResult(SqlPlanNameId);//数据库获取计划id
        log.info("planId:"+planId);
        //个体
        String Car="https://dev.fkhwl.com/webgis_test/cargos/sendAndAssign.json?planId="+planId+"&cargoNum=10%E5%90%A8&vehicleIds=8072&sijiIds=8073&isSend=2&logisticId=0";
        log.info(Car);
        String CarBack=httpRequest.postMethod(Car,cookie);//派车
        log.info(CarBack);
        Assert.assertEquals(stringTools.code(CarBack),"200","派车失败！");
        String HttpBackCar=stringTools.back(CarBack);
        Assert.assertEquals(HttpBackCar,"{\"message\":null,\"success\":true,\"data\":{\"errorPlateNos\":[],\"successPlateNos\":[\"川A-55555\"],\"errorMsg\":[]}}","派车失败！");
        String SqlWaybillNo="select waybillNo from waybillcar where driverId=8073 and waybillCarStatus=1";
        String WaybillNo=db.getSearchResult(SqlWaybillNo);//数据库获取运单编号
        log.info("派车成功，运单号："+WaybillNo);

        log.info("--------------------确认装货--------------------");
        String SqlWaybillId="select id  from waybill  where waybillNo='"+WaybillNo+"'";
        String WaybillId=db.getSearchResult(SqlWaybillId);//数据库获取运单id
        log.info("waybillCarId:"+WaybillId);
        String confirmWaybil="https://dev.fkhwl.com/webgis_test/transporting/confirmWaybill.json?type=1&waybillId="+WaybillId+"&_=1530520138626";
        log.info(confirmWaybil);
        String confirmWaybilBack=httpRequest.getMethod(confirmWaybil,cookie);
        log.info(confirmWaybilBack);
        Assert.assertEquals(stringTools.code(confirmWaybilBack),"200","确认装货失败！");
        String HttpBackconfirmWaybil=stringTools.back(confirmWaybilBack);
        Assert.assertEquals(HttpBackconfirmWaybil,"{\"message\":null,\"success\":true,\"data\":null}","确认装货失败！");
        log.info("确认装货成功！");

        log.info("--------------------上传收货凭证--------------------");
        String SCSHInvoiceBills="https://dev.fkhwl.com/webgis_test/bills/saveSCInvoiceBills.json?waybillCarId="+WaybillId+"&invoice=https%3A%2F%2Fdev.fkhwl.com%2Fuploads%2Fthumbnail%2Fthumbnail_1530170039559_1_icon.jpg&type=2";
        log.info(SCSHInvoiceBills);
        String SCSHInvoiceBillsBack=httpRequest.postMethod(SCSHInvoiceBills,cookie);//上传收货
        log.info(SCSHInvoiceBillsBack);
        Assert.assertEquals(stringTools.code(SCSHInvoiceBillsBack),"200","上传收货凭证失败！");
        String HttpBackSCSHInvoiceBills=stringTools.back(SCSHInvoiceBillsBack);
        Assert.assertEquals(HttpBackSCSHInvoiceBills,"{\"message\":null,\"success\":true,\"data\":null}","上传收货凭证失败！");
        log.info("上传收货凭证成功！");

        log.info("--------------------完善收货凭证--------------------");
        String SqlWaybillCarId="select id from waybillcar where waybillNo='"+WaybillNo+"'";
        String waybillCarId=db.getSearchResult(SqlWaybillCarId);
        log.info("waybillCarId:"+waybillCarId);
        String perfectConsigneeBill="https://dev.fkhwl.com/webgis_test/bills/perfectConsigneeBill.json?sendGrossWeight=100&sendTareWeight=10&sendNetWeight=90.000&receiveGrossWeight=100&receiveTareWeight=10&receiveNetWeight=90.000&allowDifferenceVal=&allowDifferenceAmount=0&valuePrice=10&unitPrice=10&totalPrice=900.00&waybillCarId="+waybillCarId+"&allowDifference=2&invoice=https%3A%2F%2Fdev.fkhwl.com%2Fuploads%2Fthumbnail%2Fthumbnail_1530759589680_1_icon.jpg&unit=1";
        log.info(perfectConsigneeBill);
        String perfectConsigneeBillBack=httpRequest.postMethod(perfectConsigneeBill,cookie);//完善收货
        log.info(perfectConsigneeBillBack);
        Assert.assertEquals(stringTools.code(perfectConsigneeBillBack),"200","完善收货凭证失败！");
        String HttpBackPerfectConsigneeBill=stringTools.back(perfectConsigneeBillBack);
        Assert.assertEquals(HttpBackPerfectConsigneeBill,"{\"message\":null,\"success\":true,\"data\":null}","完善收货凭证失败！");
        log.info("完善收货凭证成功！");

        log.info("--------------------审核收货凭证--------------------");
        String SHCheckBill="https://dev.fkhwl.com/webgis_test/bills/checkBill.json?waybillCarId="+waybillCarId+"&agree=1&billType=2&etcCardAmount=0&cashPayAmount=900.00&depositAmount=0&oilCardAmount=0";
        log.info(SHCheckBill);
        String SHCheckBillBack=httpRequest.postMethod(SHCheckBill,cookie);//审核收货（通过）
        log.info(SHCheckBillBack);
        Assert.assertEquals(stringTools.code(SHCheckBillBack),"200","审核收货凭证失败！");
        String HttpBackSHCheckBill=stringTools.back(SHCheckBillBack);
        Assert.assertEquals(HttpBackSHCheckBill,"{\"message\":null,\"success\":true,\"data\":null}","审核收货凭证失败！");
        log.info("审核收货凭证成功（通过）！");
    }
}
