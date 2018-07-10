package Method;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringTools {

    public String code(String Parameters){
        String [] a=Parameters.split("\\$\\$");
        return a[0];
    }
    public String back(String Parameters){
        String [] a=Parameters.split("\\$\\$");
        return a[1];
    }
    public String name(){
        SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");
        //    System.out.println(sdf.format(new Date()));
        String name=sdf.format(new Date());
        return name;
    }
}
