package com.suks.sittiporn.lslamic.util;

/**
 * Created by Weerawat on 18/6/2559.
 */
public class CheckNull {
    private static final CheckNull INSTANCE  = new CheckNull();

    public CheckNull() {
        super();
    }

    public static CheckNull getInstance() {
        return INSTANCE;
    }

    public String chkNullString(String str) {
        String strTemp = "";
        try {
            if (str != null) {
                strTemp = str.trim();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return strTemp;
    }

    public int chkNullStringToInt(String str) {
        String strTemp = "";
        String rep="0";
        try {
            if (str != null) {
                strTemp=this.chkBlankString(str.trim(),rep);
            } else {
                strTemp = rep;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return Integer.parseInt(strTemp);
    }

    public double chkNullStringToDouble(String str) {
        String strTemp = "";
        String rep="0.00";
        try {
            if (str != null) {
                strTemp=this.chkBlankString(str.trim(),rep);
            } else {
                strTemp = rep;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return Double.parseDouble(strTemp);
    }
    public float chkNullStringToFloat(String str) {
        String strTemp = "";
        String rep="0.00";
        try {
            if (str != null) {
                strTemp=this.chkBlankString(str.trim(),rep);
            } else {
                strTemp = rep;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return Float.parseFloat(strTemp);
    }

    public String chkNullString(String str, String rep) {
        String strTemp = "";
        try {
            if (str != null) {
                strTemp=this.chkBlankString(str.trim(),rep);
            } else {
                strTemp = rep;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return strTemp;
    }

    private String chkBlankString(String strnull, String rep){
        String strbnk="";
        try{
            if(strnull.length()>0){
                strbnk=strnull;
            }else{
                strbnk=rep;
            }
        }catch(Throwable e){
            e.printStackTrace();
        }
        return strbnk;
    }

    public String chkNullString(Double d, String s) {
        if (d==null) {
            return s;
        } else {
            return d.toString();
        }
    }


    public static void main(String agrs[]){
        try{
            //CheckNull s=new CheckNull();
            Double.valueOf(new CheckNull().chkNullString("MDISEASEGRP__AMOUNTCOST","0.00"));
            System.out.println(" string : int "+new CheckNull().chkNullString(""));
        }catch(Exception ex){}
    }
}
