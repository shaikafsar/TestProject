package com.gt.utils;

import com.gt.reports.ExtentLogger;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.testng.Assert;
import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class ReportUtils {

    public static String path = "./test-results/";
    public static String oldReportName;
    public static String newReportName;

    public static void preExecutionSetUp(){
        getReportFileName("PreExecution");
        //preExecutionReportSetUp();
    }

    public static void postExecutionSetUp(){
        getReportFileName("PostExecution");
    }

    // Updates TestCase - count , passed , Failed details in Excel Sheet
    public static void preExecutionReportSetUp(){
        String str = null ;
        if(System.getenv("build") == null ){
            str = "parent" ;
        }else{
            if (System.getenv("build").equalsIgnoreCase("parent")){
                str = "parent";
            }else{
                str = "child" ;
            }
        }

        if (str.equalsIgnoreCase("parent")){
            // Clear Excel data from Row no 2
        }else if(str.equalsIgnoreCase("child")){
            // Enter data from Row 3 - Child Build Data
        }
    }
    public static void updatePreExecutionDetails(){

    }
    public static void getReportFileName(String executionType) {
        try {

            if (executionType.equalsIgnoreCase("PreExecution")) {
                oldReportName = getLatestFileName().toString();
            }else if(executionType.equalsIgnoreCase("PostExecution")) {
                for (int i = 0; i < 20; i++) {
                    Thread.sleep(TimeUnit.SECONDS.toSeconds(1));
                    newReportName = getLatestFileName().toString();
                    if (!newReportName.equals(oldReportName)) {
                        System.out.println("Latest Report: "+newReportName);
                        break;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static File getLatestFileName(){
        File theNewestFile = null;
        File dir = new File(path);
        FileFilter fileFilter = new WildcardFileFilter("*." + "html");
        File[] files = dir.listFiles(fileFilter);
        if (files.length > 0) {
            Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
            theNewestFile = files[0];
        }
        return theNewestFile;
    }
    public static void addStepValidation(String actual,String expected){
        ExtentLogger.info("Step Validation - "+"Actual: "+actual+" , "+" Expected: "+expected);
        Assert.assertEquals(actual,expected);
    }
}
