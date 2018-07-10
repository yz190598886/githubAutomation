package TestCase.FkhTestCase.TMS;

import Method.HttpRequest;
import Method.StringTools;
import Method.jdbc;
import org.apache.log4j.Logger;
import org.testng.Assert;

public class TmsOne {
    private static final Logger log =Logger.getLogger(TmsOne.class);
    static String ProjectId;
    static String cookie;
    public void FKHTmsProcessOne () throws Exception {

        /*
         *WEB:TMS
         *项目：自己作为承运方
         *计划：吨+扣磅差比
         *派车
         *上传、完善、审核发货凭证+上传、完善、审核收货凭证
         * */

        log.info("\n/**\n*WEB:TMS\n*项目：自己作为承运方\n*计划：吨+扣磅差比\n*派车\n*上传、完善、审核发货凭证+上传、完善、审核收货凭证\n**/");
        //数据库、请求、工具
        jdbc db=new jdbc();
        StringTools stringTools=new StringTools();
        HttpRequest httpRequest=new HttpRequest();

        //登陆:莱尼卡贸易公司613,货主账户
        log.info("--------------------登录获得cookie--------------------");
        String TmsLogin="https://dev.fkhwl.com/webgis_test/login.html?loginAccount=18181910613&loginPasswd=95075dfedfe8f80ac6e45115918fd7f3&randomCode=99&loginType=2";
        log.info(TmsLogin);
        String comeback=httpRequest.getTmpcookies(TmsLogin);//登陆
        log.info(comeback);
        String code=stringTools.code(comeback);
        Assert.assertEquals(code,"303","登陆失败");
        cookie=stringTools.back(comeback);
        log.info("登陆成功,获得cookie："+cookie);

        log.info("--------------------创建项目（自己承运方非无车）--------------------");
        String name=stringTools.name();
        //发货+承运：莱尼卡贸易公司613
        //收货：无常科技货源有限公司
        String createProject="https://dev.fkhwl.com/webgis_test/projects/createProject.json?projectName=TMS%E6%89%BF"+name+"&sendUserId=7517&consigneeUserId=7802&transportUserId=7517&addSendShipper=&addConsigneeShipper=";
        log.info(createProject);
        String ProjectBack=httpRequest.postMethod(createProject,cookie);//创建项目
        log.info(ProjectBack);
        Assert.assertEquals(stringTools.code(ProjectBack),"200","项目创建失败！");
        String HttpBackProject=stringTools.back(ProjectBack);
        Assert.assertEquals(HttpBackProject,"{\"message\":\"创建成功！\",\"data\":null,\"success\":true}","项目创建失败！");
        String ProjectName="TMS承"+name;
        log.info("项目创建成功:"+ProjectName);

        log.info("--------------------创建计划--------------------");
        String SqlProjectId="select id from project where projectName='"+ProjectName+"'";
        ProjectId=db.getSearchResult(SqlProjectId);//数据库获取项目id
        log.info("ProjectId："+ProjectId);
        String nameTwo=stringTools.name();
        //吨+扣磅差比
        String createPlan="https://dev.fkhwl.com/webgis_test/projects/createPlan.json?projectId="+ProjectId+"&planName=%E5%90%A8%E7%A3%85%E5%B7%AE%E6%AF%94"+nameTwo+"&departureCity=%E9%87%8D%E5%BA%86&loadAddress=1299&arrivalCity=%E5%9B%9B%E5%B7%9D-%E6%88%90%E9%83%BD&arrivalAddress=1293&modelType=0&packagedForm=1102&programNo=100&units=%E5%90%A8&poundKey=1&poundValue=3&cargoPrice=10&carLengthBegin=%E4%B8%8D%E9%99%90&carLengthEnd=%E4%B8%8D%E9%99%90&carLength=%E4%B8%8D%E9%99%90&carType=%E4%B8%8D%E9%99%90&mileage=166.921%E5%85%AC%E9%87%8C";
        log.info(createPlan);
        String PlanBack=httpRequest.postMethod(createPlan,cookie);//创建计划
        log.info(PlanBack);
        Assert.assertEquals(stringTools.code(PlanBack),"200","计划创建失败！");
        String HttpBackPlan=stringTools.back(PlanBack);
        Assert.assertEquals(HttpBackPlan,"{\"message\":\"创建成功！\",\"data\":null,\"success\":true}","计划创建失败！");
        String planName="吨磅差比"+nameTwo;
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

        log.info("--------------------完善发货凭证--------------------");
        String SqlWaybillCarId="select id from waybillcar where waybillNo='"+WaybillNo+"'";
        String waybillCarId=db.getSearchResult(SqlWaybillCarId);
        log.info("waybillCarId:"+waybillCarId);
        String perfectSendBill="https://dev.fkhwl.com/webgis_test/bills/perfectSendBill.json?sendGrossWeight=100&sendTareWeight=10&sendNetWeight=90.000&allowDifference=1&allowDifferenceVal=3&allowDifferenceAmount=&waybillCarId="+waybillCarId+"&valuePrice=10&unitPrice=10&invoice=https%3A%2F%2Fdev.fkhwl.com%2Fuploads%2Fthumbnail%2Fthumbnail_1530170039559_1_icon.jpg%2Chttps%3A%2F%2Fdev.fkhwl.com%2Fuploads%2Fthumbnail%2Fthumbnail_1530173925464_1_icon.jpg&unit=1";
        log.info(perfectSendBill);
        String perfectSendBillBack=httpRequest.postMethod(perfectSendBill,cookie);//完善发货
        log.info(perfectSendBillBack);
        Assert.assertEquals(stringTools.code(perfectSendBillBack),"200","完善发货凭证失败！");
        String HttpBackPerfectSendBill=stringTools.back(perfectSendBillBack);
        Assert.assertEquals(HttpBackPerfectSendBill,"{\"message\":null,\"success\":true,\"data\":null}","完善发货凭证失败！");
        log.info("完善发货凭证成功！");

        log.info("--------------------审核发货凭证--------------------");
        log.info("waybillCarId:"+waybillCarId);
        String FHCheckBil="https://dev.fkhwl.com/webgis_test/bills/checkBill.json?waybillCarId="+waybillCarId+"&agree=1&billType=1&etcCardAmount=0&cashPayAmount=&depositAmount=0&oilCardAmount=0";
        log.info(FHCheckBil);
        String FHCheckBilBack=httpRequest.postMethod(FHCheckBil,cookie);//审核发货（通过）
        log.info(FHCheckBilBack);
        Assert.assertEquals(stringTools.code(FHCheckBilBack),"200","审核发货凭证失败！");
        String HttpBackFHCheckBill=stringTools.back(FHCheckBilBack);
        Assert.assertEquals(HttpBackFHCheckBill,"{\"message\":null,\"success\":true,\"data\":null}","审核发货凭证失败！");
        log.info("审核发货凭证成功（通过）！");

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
        String perfectConsigneeBill="https://dev.fkhwl.com/webgis_test/bills/perfectConsigneeBill.json?sendGrossWeight=100&sendTareWeight=10&sendNetWeight=90.000&receiveGrossWeight=100&receiveTareWeight=10&receiveNetWeight=90.000&allowDifferenceVal=3&allowDifferenceAmount=0&valuePrice=10&unitPrice=10&totalPrice=900.00&waybillCarId="+waybillCarId+"&allowDifference=1&invoice=https%3A%2F%2Fdev.fkhwl.com%2Fuploads%2Fthumbnail%2Fthumbnail_1530170039559_1_icon.jpg%2Chttps%3A%2F%2Fdev.fkhwl.com%2Fuploads%2Fthumbnail%2Fthumbnail_1530177260997_1_icon.jpg&unit=1";
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
