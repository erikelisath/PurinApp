package com.xek6ae.PurinApp;

/**
 * Created by xek6ae on 03.11.14.
 */
public class DateFormat {
    private int formatmode = -1;
    public static final int DEUTSCH = 0;
    public static final int ENGLISCH = 1;

    public DateFormat(int format){
        switch(format){
            case DEUTSCH: formatmode = 0;
                break;
            case ENGLISCH: formatmode = 1;
                break;
            default: formatmode = 0;
                break;
        }
    }

    public String getDateFormat(String date){
        String[] rawdate = date.split("-");
        String newdate;

        if(formatmode==DEUTSCH){
            switch (Integer.parseInt(rawdate[1])){
                case 1: rawdate[1] = "Januar";
                    break;
                case 2: rawdate[1] = "Februar";
                    break;
                case 3: rawdate[1] = "März";
                    break;
                case 4: rawdate[1] = "April";
                    break;
                case 5: rawdate[1] = "Mai";
                    break;
                case 6: rawdate[1] = "Juni";
                    break;
                case 7: rawdate[1] = "Juli";
                    break;
                case 8: rawdate[1] = "August";
                    break;
                case 9: rawdate[1] = "September";
                    break;
                case 10: rawdate[1] = "Oktober";
                    break;
                case 11: rawdate[1] = "November";
                    break;
                case 12: rawdate[1] = "Dezember";
                    break;
                default: break;
            }

            newdate = rawdate[2]+". "+rawdate[1]+" "+rawdate[0];
        }else {
            newdate = rawdate[2]+"/"+rawdate[1]+"/"+rawdate[0];
        }

        return newdate;
    }
}
