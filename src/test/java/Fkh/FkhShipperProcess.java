package Fkh;

import TestCase.FkhTestCase.SHIPPER.ShipperOne;
import TestCase.FkhTestCase.SHIPPER.ShipperThree;
import TestCase.FkhTestCase.SHIPPER.ShipperTwo;
import TestCase.FkhTestCase.SHIPPER.ShipperFour;
import org.testng.annotations.Test;

public class FkhShipperProcess {
    ShipperOne shipperOne=new ShipperOne();
    ShipperTwo shipperTwo=new ShipperTwo();
    ShipperThree shipperThree=new ShipperThree();
    ShipperFour shipperFour=new ShipperFour();
    @Test(priority=1)
        public void  caseOne() throws Exception {
            /*
         *APP:千迅智运
         *项目：自己作为承运方
         *计划：吨+扣磅差比
         *派车
         *上传、完善、审核发货凭证+上传、完善、审核收货凭证
         * */
        shipperOne.FKHOwnerProcessOne();
        }
    @Test(priority=2)
        public void  caseTwo() throws Exception {
             /*
         *APP:千迅智运
         *项目：自己作为承运方
         *计划：件+不扣磅差+以发货为准
         *派车
         *上传、完善、审核发货凭证+确认卸货
         * */
        shipperTwo.FKHOwnerProcessTwo();
    }
    @Test(priority=3)
        public void  caseThree() throws Exception {
             /*
         *APP:千迅智运
         *项目：自己作为承运方
         *计划：吨+不扣磅差+以收货为准
         *派车
         *上传、完善、审核收货凭证+确认装货
         * */
         shipperThree.FKHOwnerProcessThree();
    }
    @Test(priority=4)
        public void  caseFour() throws Exception {
             /*
         *APP:千迅智运
         *项目：自己作为承运方
         *计划：件+不过磅
         *派车
         *上传、完善、审核发货凭证+上传、完善、审核收货凭证
         * */
         shipperFour.FKHOwnerProcessFour();
    }


}
