package TestCase.FkhTestCase.SHIPPER;

import Method.HttpRequest;
import Method.StringTools;
import Method.jdbc;
import org.apache.log4j.Logger;
import org.testng.Assert;

public class ShipperOne {
    static String ProjectId;
    static String userId;
    private static final Logger log =Logger.getLogger(ShipperOne.class);
    public void FKHOwnerProcessOne() throws Exception {

        /*
         *APP:千迅智运
         *项目：自己作为承运方
         *计划：吨+扣磅差比
         *派车
         *上传、完善、审核发货凭证+上传、完善、审核收货凭证
         * */

        log.info("\n/**\n*APP:千迅智运\n*项目：自己作为承运方\n*计划：吨+扣磅差比\n*派车\n*上传、完善、审核发货凭证+上传、完善、审核收货凭证\n**/");
        //数据库、请求、工具
        jdbc db=new jdbc();
        StringTools stringTools=new StringTools();
        HttpRequest httpRequest=new HttpRequest();

        log.info("-----------------登录-----------------");
        String shipper="https://dev.fkhwl.com/fkhtest/api/logins/shipper";
        log.info(shipper);
        String LoginJson="{\"userMobileNo\":\"18181910613\",\"userPassword\":\"95075dfedfe8f80ac6e45115918fd7f3\",\"clientVersion\":\"1.3.0.2.0625\",\"apiVersion\":\"v2.0\",\"clientName\":\"返空汇-信息部\",\"locality\":\"四川|成都|武侯区|天府大道中段辅路\",\"networkType\":\"WIFI\",\"platformType\":\"android,24,7.0,HUAWEI,HUAWEI NXT-AL10\",\"longitude\":104.07471,\"latitude\":30.543224,\"simOperatorCode\":\"\"}";
        log.info("参数："+LoginJson);
        String shipperBack=httpRequest.postDataWithJson(shipper,LoginJson);
//        log.info(shipperBack);
        String code=stringTools.code(shipperBack);
        Assert.assertEquals(code,"200","登录失败！");
        String httpBackShipper=stringTools.back(shipperBack);
        userId=httpRequest.Jsonvalue(httpBackShipper,"userId");
        log.info("登录成功，获得userId:"+userId);

        log.info("-----------------创建项目-----------------");
        String name=stringTools.name();
        String projectAdd="https://dev.fkhwl.com/fkhtest/api/obd/project/add/"+userId;
        log.info(projectAdd);
        String ProjectName="APP承"+name;
        String projectAddJson="{\"projectName\":\""+ProjectName+"\",\"sendUserId\":\"7517\",\"transportUserId\":\""+userId+"\",\"consigneeUserId\":\"7802\"}";
        log.info("参数："+projectAddJson);
        String projectAddBack=httpRequest.postDataWithJson(projectAdd,projectAddJson);
        log.info(projectAddBack);
        Assert.assertEquals(stringTools.code(projectAddBack),"200","创建项目失败！");
        String SqlProjectId="select id from project where projectName='"+ProjectName+"'";
        ProjectId=db.getSearchResult(SqlProjectId);//数据库获得项目id
        String HttpBackProjectAdd=stringTools.back(projectAddBack);
        Assert.assertEquals(httpRequest.Jsonvalue((httpRequest.Jsonvalue(HttpBackProjectAdd,"data")),"projectId"),ProjectId,"创建项目失败！");
        log.info("项目创建成功："+ProjectName+"，ID:"+ProjectId);

        log.info("-----------------创建计划-----------------");
        String addProgram="https://dev.fkhwl.com/fkhtest/api/obd/project/addProgram/"+userId;
        log.info(addProgram);
        String nameTwo=stringTools.name();
        String PlanName="吨磅差比"+nameTwo;
        String addProgramJson="{\"projectId\":"+ProjectId+",\"planName\":\""+PlanName+"\",\"loadAddress\":1299,\"arrivalAddress\":1293,\"departureCity\":\"重庆\",\"arrivalCity\":\"四川-成都\",\"units\":\"吨\",\"cargoPrice\":10.0,\"packagedForm\":1101,\"modelType\":\"31616\",\"programNo\":100.0,\"arrivalAddressWarn\":1,\"loadAddressWarn\":1,\"carLength\":\"不限\",\"carType\":\"不限\",\"unitPrice\":0.0,\"cargoDesc\":\"\",\"poundKey\":1,\"poundValue\":\"3\",\"programStartDate\":0,\"programEndDate\":0,\"mileage\":\"298公里\"}";
        log.info("参数："+addProgramJson);
        String addProgramBack=httpRequest.postDataWithJson(addProgram,addProgramJson);
        log.info(addProgramBack);
        String HttpAddProgramBack=stringTools.back(addProgramBack);
        Assert.assertEquals(stringTools.code(addProgramBack),"200","创建计划失败！");
        Assert.assertEquals(httpRequest.Jsonvalue(HttpAddProgramBack,"rescode"),"1200","创建计划失败！");
        Assert.assertEquals(httpRequest.Jsonvalue(HttpAddProgramBack,"message"),"操作成功","创建计划失败！");
        String SqlPlanId="select id from program where programName='"+PlanName+"'";
        String PlanId=db.getSearchResult(SqlPlanId);
        log.info("创建计划成功："+PlanName+",Id:"+PlanId);

        log.info("-----------------派车-----------------");
        String sendOrAssign="https://dev.fkhwl.com/fkhtest/api/cargos/sendOrAssign/"+userId;
        log.info(sendOrAssign);
        String sendOrAssignJson="{\"planId\":"+PlanId+",\"cargoNum\":\"10.0吨\",\"vehicleIds\":\"8072\",\"sijiIds\":\"8073\",\"logisticId\":0}";
        log.info("参数："+sendOrAssignJson);
        String sendOrAssignBack=httpRequest.postDataWithJson(sendOrAssign,sendOrAssignJson);
        String HttpBackSendOrAssign=stringTools.back(sendOrAssignBack);
        log.info(HttpBackSendOrAssign);
        Assert.assertEquals(stringTools.code(sendOrAssignBack),"200","派车失败！");
        Assert.assertEquals(httpRequest.Jsonvalue(HttpBackSendOrAssign,"rescode"),"1200","派车失败！");
        Assert.assertEquals(httpRequest.Jsonvalue(HttpBackSendOrAssign,"message"),"操作成功","派车失败！");
        String SqlWaybillId="select waybillId from waybillcar where driverId=8073 and waybillCarStatus=1";
        String SqlWaybillNo="select waybillNo from waybillcar where driverId=8073 and waybillCarStatus=1";
        String SqlwaybillCarId="select id from waybillcar where driverId=8073 and waybillCarStatus=1";
        String WaybillId=db.getSearchResult(SqlWaybillId);//数据库获取运单id
        String WaybillNo=db.getSearchResult(SqlWaybillNo);//数据库获取运单号
        String waybillCarId=db.getSearchResult(SqlwaybillCarId);//数据库获取运单车辆表id
        log.info("派车成功，运单号："+WaybillNo+",Id:"+WaybillId+",waybillCarId:"+waybillCarId);

        log.info("--------------------上传装货凭证--------------------");
        String senderuploadinvoice="https://dev.fkhwl.com/fkhtest/api/waybills/sender/uploadinvoice/"+userId+"/"+WaybillId;
        log.info(senderuploadinvoice);
        String senderuploadinvoiceJson="{\"invoice\":\"https://dev.fkhwl.com/uploads/thumbnail/thumbnail_1530778284694_1_.jpg\"}";
        log.info("参数："+senderuploadinvoiceJson);
        String senderuploadinvoiceBack=httpRequest.postDataWithJson(senderuploadinvoice,senderuploadinvoiceJson);
        log.info(senderuploadinvoiceBack);
        String HttpBacksenderuploadinvoice=stringTools.back(senderuploadinvoiceBack);
        Assert.assertEquals(stringTools.code(senderuploadinvoiceBack),"200","上传装货凭证失败！");
        Assert.assertEquals(httpRequest.Jsonvalue(HttpBacksenderuploadinvoice,"rescode"),"1200","上传装货凭证失败！");
        Assert.assertEquals(httpRequest.Jsonvalue(HttpBacksenderuploadinvoice,"message"),"操作成功","上传装货凭证失败！");
        log.info("上传收货凭证成功！");

        log.info("--------------------上传收货凭证--------------------");
        String consigneeuploadinvoice="https://dev.fkhwl.com/fkhtest/api/waybills/consignee/uploadinvoice/"+userId+"/"+WaybillId;
        log.info(consigneeuploadinvoice);
        String consigneeuploadinvoiceJson="{\"invoice\":\"https://dev.fkhwl.com/uploads/thumbnail/thumbnail_1530778284694_1_.jpg\"}";
        log.info("参数："+consigneeuploadinvoiceJson);
        String consigneeuploadinvoiceBack=httpRequest.postDataWithJson(consigneeuploadinvoice,consigneeuploadinvoiceJson);
        log.info(consigneeuploadinvoiceBack);
        String HttpBackconsigneeuploadinvoice=stringTools.back(consigneeuploadinvoiceBack);
        Assert.assertEquals(stringTools.code(consigneeuploadinvoiceBack),"200","上传收货凭证失败！");
        Assert.assertEquals(httpRequest.Jsonvalue(HttpBackconsigneeuploadinvoice,"rescode"),"1200","上传收货凭证失败！");
        Assert.assertEquals(httpRequest.Jsonvalue(HttpBackconsigneeuploadinvoice,"message"),"操作成功","上传收货凭证失败！");
        log.info("上传收货凭证成功！");

        log.info("--------------------完善发货凭证--------------------");
        String senderwriteDetail="https://dev.fkhwl.com/fkhtest/api/waybills/sender/writeDetail/"+userId+"/"+waybillCarId;
        log.info(senderwriteDetail);
        String senderwriteDetailJson="{\"receiveGrossWeight\":0.0,\"receiveNetWeight\":0.0,\"receiveTareWeight\":0.0,\"sendGrossWeight\":100.0,\"sendNetWeight\":90.0,\"sendTareWeight\":10.0,\"allowDifference\":1,\"allowDifferenceVal\":3.0,\"allowDifferenceAmount\":0.0,\"totalPrice\":0.0,\"unitPrice\":10.0,\"valuePrice\":10.0,\"invoice\":\"https://dev.fkhwl.com/uploads/thumbnail/thumbnail_1530778284694_1_.jpg,https://dev.fkhwl.com/uploads/thumbnail/thumbnail_1530780078723_1_.jpg\",\"unit\":1}";
        log.info("参数"+senderwriteDetailJson);
        String senderwriteDetailBack=httpRequest.postDataWithJson(senderwriteDetail,senderwriteDetailJson);
        log.info(senderwriteDetailBack);
        String HttpBacksenderwriteDetail=stringTools.back(senderwriteDetailBack);
        Assert.assertEquals(stringTools.code(senderwriteDetailBack),"200","上传收货凭证失败！");
        Assert.assertEquals(httpRequest.Jsonvalue(HttpBacksenderwriteDetail,"rescode"),"1200","完善发货凭证失败！");
        Assert.assertEquals(httpRequest.Jsonvalue(HttpBacksenderwriteDetail,"message"),"操作成功","完善发货凭证失败！");
        log.info("完善发货凭证成功！");

        log.info("--------------------完善收货凭证--------------------");
        String consigneewriteDetail="https://dev.fkhwl.com/fkhtest/api/waybills/consignee/writeDetail/"+userId+"/"+waybillCarId;
        log.info(consigneewriteDetail);
        String consigneewriteDetailJson="{\"receiveGrossWeight\":100.0,\"receiveNetWeight\":90.0,\"receiveTareWeight\":10.0,\"sendGrossWeight\":100.0,\"sendNetWeight\":90.0,\"sendTareWeight\":10.0,\"allowDifference\":1,\"allowDifferenceVal\":3.0,\"allowDifferenceAmount\":0.0,\"totalPrice\":0.0,\"unitPrice\":10.0,\"valuePrice\":10.0,\"invoice\":\"https://dev.fkhwl.com/uploads/thumbnail/thumbnail_1530778284694_1_.jpg,https://dev.fkhwl.com/uploads/thumbnail/thumbnail_1530780078723_1_.jpg\",\"unit\":1}";
        log.info("参数"+consigneewriteDetailJson);
        String consigneewriteDetailBack=httpRequest.postDataWithJson(consigneewriteDetail,consigneewriteDetailJson);
        log.info(consigneewriteDetailBack);
        String HttpBackconsigneewriteDetail=stringTools.back(consigneewriteDetailBack);
        Assert.assertEquals(stringTools.code(consigneewriteDetailBack),"200","上传收货凭证失败！");
        Assert.assertEquals(httpRequest.Jsonvalue(HttpBackconsigneewriteDetail,"rescode"),"1200","完善收货凭证失败！");
        Assert.assertEquals(httpRequest.Jsonvalue(HttpBackconsigneewriteDetail,"message"),"操作成功","完善收货凭证失败！");
        log.info("完善收货凭证成功！");

        log.info("--------------------复核发货凭（通过）--------------------");
        String FHcheck="https://dev.fkhwl.com/fkhtest/api/shipper/bills/check/"+userId;
        log.info(FHcheck);
        String FHcheckJson="{\"waybillCarId\":"+waybillCarId+",\"agree\":1,\"billType\":1,\"cashPayAmount\":\"900.0\"}";
        log.info("参数："+FHcheckJson);
        String FHcheckBack=httpRequest.postDataWithJson(FHcheck,FHcheckJson);
        log.info(FHcheckBack);
        String HttpBackFHcheck=stringTools.back(FHcheckBack);
        Assert.assertEquals(stringTools.code(FHcheckBack),"200","复核发货凭证失败！");
        Assert.assertEquals(httpRequest.Jsonvalue(HttpBackFHcheck,"rescode"),"1200","复核发货凭证失败！");
        Assert.assertEquals(httpRequest.Jsonvalue(HttpBackFHcheck,"message"),"操作成功","复核发货凭证失败！");
        log.info("复核发货凭成功！（通过）");

        log.info("--------------------复核收货凭证（通过）--------------------");
        String SHcheck="https://dev.fkhwl.com/fkhtest/api/shipper/bills/check/"+userId;
        log.info(SHcheck);
        String SHcheckJson="{\"waybillCarId\":"+waybillCarId+",\"agree\":1,\"billType\":2,\"cashPayAmount\":\"900.0\"}";
        log.info("参数："+SHcheckJson);
        String SHcheckBack=httpRequest.postDataWithJson(SHcheck,SHcheckJson);
        log.info(SHcheckBack);
        String HttpBackSHcheck=stringTools.back(SHcheckBack);
        Assert.assertEquals(stringTools.code(SHcheckBack),"200","复核收货凭失败！");
        Assert.assertEquals(httpRequest.Jsonvalue(HttpBackSHcheck,"rescode"),"1200","复核收货凭失败！");
        Assert.assertEquals(httpRequest.Jsonvalue(HttpBackSHcheck,"message"),"操作成功","复核收货凭失败！");
        log.info("复核收货凭成功！（通过）");

    }

}
