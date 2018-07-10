package Method;

public class SelfIncrement {
    public static String weishu(int num){
        String num1=num+"";
        int a=num1.length();

            if(a==1){
            num1="0000"+num1;
            }
            else if (a==2){
            num1="000"+num1;
        }
            else if (a==3){
            num1="00"+num1;
        }
            else if (a==4){
            num1="0"+num1;
        }
        return num1;
    }

    public SelfIncrement(int frequency, int NumberOfStarts) {

        for(int i=0;i<frequency;i++){
            System.out.println(weishu(NumberOfStarts+i));

        }
    }

    public static void main(String[] args) {
        new SelfIncrement(10,1);
    }
}
