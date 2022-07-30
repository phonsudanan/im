package information;

import java.text.DecimalFormat;

public class NumberFormat {

    public static String format(Double n){
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        String number = formatter.format(n);
        return number;
    }
    public static  String format(int n){
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        String number = formatter.format(n);
        return number;
    }

}
