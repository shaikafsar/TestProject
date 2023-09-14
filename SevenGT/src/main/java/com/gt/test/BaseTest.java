package com.gt.test;

import com.gt.driver.Driver;
import com.gt.utils.ReportUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.net.MalformedURLException;


public class BaseTest {



    @BeforeMethod
    public void setUp() throws MalformedURLException {
        Driver.initiateDriver();
        System.out.println("End Before Method");
    }

    @AfterMethod
    public void tearDown() {
        Driver.quitDriver();
    }

    @BeforeSuite
    public void getPreExecutionDetails() {
        ReportUtils.preExecutionSetUp();
    }

    @AfterSuite
    public void getPostExecutionDetails(){
        ReportUtils.postExecutionSetUp();
    }


}
