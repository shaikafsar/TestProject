package com.gt.constants;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class FrameworkConstants {

    private FrameworkConstants(){}

    public static final String REPORT_PATH = System.getProperty("user.dir")+"//test-results//index_"+getCurrentDate()+".html";

    public static String getReportPath(){
    return REPORT_PATH ;
    }

    public static String getCurrentDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now).trim() ;
    }

}
