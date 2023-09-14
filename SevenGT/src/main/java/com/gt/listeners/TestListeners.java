package com.gt.listeners;
import com.gt.reports.ExtentLogger;
import com.gt.reports.ExtentReport;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Arrays;

public class TestListeners implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        ExtentReport.createTests(result.getMethod().getDescription());
    }
    @Override
    public void onTestSuccess(ITestResult result) {
        // ExtentLogger.pass(result.getName()+" is passed "); - Everytime prints as pass in Report
    }
    @Override
    public void onTestFailure(ITestResult result) {
        ExtentLogger.fail(result.getName()+" is failed ");
        ExtentLogger.fail(result.getThrowable().getMessage());
        ExtentLogger.fail(Arrays.toString(result.getThrowable().getStackTrace()));
    }
    @Override
    public void onStart(ITestContext context) {
        ExtentReport.initReport();
    }
    @Override
    public void onFinish(ITestContext context) {
        ExtentReport.flushReports();
    }





}
