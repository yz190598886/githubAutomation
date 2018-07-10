package Method;
import java.util.Random;
public class IdCard {
    public static class Id {
        public static String Calculate() {
            // 市/区/县的名称与之对应的编号，目前仅支持江苏省
//            String areaCodes[] = { "320102", "320103", "320104", "320105", "320106", "320107", "320111", "320113", "320114",
//                    "320115", "320116", "320124", "320125", "320202", "320203", "320204", "320205", "320206", "320211",
//                    "320281", "320282", "320302", "320303", "320304", "320305", "320311", "320321", "320322", "320323",
//                    "320324", "320381", "320382", "320402", "320404", "320405", "320411", "320412", "320481", "320482",
//                    "320502", "320503", "320504", "320505", "320506", "320507", "320581", "320582", "320583", "320584",
//                    "320585", "320602", "320611", "320612", "320621", "320623", "320681", "320682", "320684", "320703",
//                    "320705", "320706", "320721", "320722", "320723", "320724", "320802", "320803", "320804", "320811",
//                    "320826", "320829", "320830", "320831", "320902", "320903", "320921", "320922", "320923", "320924",
//                    "320925", "320981", "320982", "321002", "321003", "321011", "321023", "321081", "321084", "321088",
//                    "321088", "321102", "321111", "321112", "321181", "321182", "321183", "321202", "321203", "321281",
//                    "321282", "321283", "321284", "321302", "321311", "321322", "321323", "321324" };
            String areaCodes[]={"999001","999002"};
            Random random = new Random();
            String areaCode = areaCodes[random.nextInt(areaCodes.length)];
            int year = 1920 + random.nextInt(100);
            int month = random.nextInt(11);
            if (month == 0)
                month = 12;
            int day = 0;
            while (true) {
                day = random.nextInt(31);
                if (!((day == 0 || (month == 4 || month == 6 || month == 9 || month == 11) && day > 30)
                        || (month == 2 && (((year) % 4 > 0 && day > 28) || day > 29)))) {
                    break;
                }
            }
            String birthday = String.valueOf(year * 10000 + month * 100 + day);
            String randomCode = String.valueOf(1000 + random.nextInt(999)).substring(1);
            String verify = getVerify(areaCode + birthday + randomCode);
            String ret = areaCode + birthday + randomCode + verify;
            System.out.println(ret);
            return ret;
        }

        public static void main(String[] args) {
            for(int i=0;i<10;i++
                    ) {
                Calculate();}
        }
        public static String getVerify(String cardId) {
            String[] ValCodeArr = { "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2" };
            String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2" };
            int TotalmulAiWi = 0;
            for (int i = 0; i < 17; i++) {
                TotalmulAiWi = TotalmulAiWi + Integer.parseInt(String.valueOf(cardId.charAt(i))) * Integer.parseInt(Wi[i]);
            }
            int modValue = TotalmulAiWi % 11;
            String strVerifyCode = ValCodeArr[modValue];

            return strVerifyCode;
        }
    }
}
