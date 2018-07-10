package Fkh;

import TestCase.FkhTestCase.TMS.TmsFour;
import TestCase.FkhTestCase.TMS.TmsOne;
import TestCase.FkhTestCase.TMS.TmsThree;
import TestCase.FkhTestCase.TMS.TmsTwo;
import org.testng.annotations.Test;

public class FkhTmsProcess {
    TmsOne tmsOne=new TmsOne();
    TmsTwo tmsTwo=new TmsTwo();
    TmsThree tmsThree=new TmsThree();
    TmsFour tmsFour=new TmsFour();
    @Test(priority = 1)
    public void CaseOne() throws Exception {
        /*
         *WEB:TMS
         *项目：自己作为承运方
         *计划：吨+扣磅差比
         *派车
         *上传、完善、审核发货凭证+上传、完善、审核收货凭证
         * */
        tmsOne.FKHTmsProcessOne();
    }
    @Test(priority = 2)
    public void CaseTwo() throws Exception {
        /*
         *WEB:TMS
         *项目：自己作为承运方
         *计划：件+不扣磅差+以发货为准
         *派车
         *上传、完善、审核发货凭证+确认卸货
         * */
        tmsTwo.FKHTmsProcessTwo();
    }
    @Test(priority = 3)
    public void CaseThree() throws Exception {
        /*
         *WEB:TMS
         *项目：自己作为承运方
         *计划：吨+不扣磅差+以收货为准
         *派车
         *上传、完善、审核收货凭证+确认装货
         * */
        tmsThree.FKHTmsProcessThree();
    }
    @Test(priority = 4)
    public void CaseFour() throws Exception {
        /*
         *WEB:TMS
         *项目：自己作为承运方
         *计划：件+不过磅
         *派车
         *上传、完善、审核发货凭证+确认卸货
         * */
        tmsFour.FKHTmsProcessFour();
    }

}

