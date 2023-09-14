package com.gt.utils;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestNGUtils {

    public static String ExecutionType = null ;

    public static void createSuite() {
        TestNG myTestNG = new TestNG();
        XmlSuite mySuite = new XmlSuite();
        mySuite.setName("Suite");
        mySuite.setParallel(XmlSuite.ParallelMode.METHODS); // equivalent of <suite> tag
        XmlTest myTest = new XmlTest(mySuite);
        myTest.setName("RegressionSuite"); // equivalent of <test> tag
        // Add Listeners
        List<String> listOfListeners = new ArrayList<>();
        listOfListeners.add("com.gt.listeners.TestListeners");
        listOfListeners.add("com.gt.listeners.MethodInterceptor");
        mySuite.setListeners(listOfListeners);
        // Add Classes
        List<XmlClass> myclasses = new ArrayList<XmlClass>(); // equivalent of <classes> tag
        getConfigurations(); // Set Intellij Configurations.
        if (ExecutionType.equalsIgnoreCase("Web")) {
            //myclasses.add(new XmlClass("com.gt.test.HomePageTest"));
            //myclasses.add(new XmlClass("com.gt.test.PaymentTest"));
            myclasses.add(new XmlClass("com.gt.test.SevenBoss"));
        }else if(ExecutionType.equalsIgnoreCase("Api")) {
            myclasses.add(new XmlClass("com.gt.testApi.ApiTest"));
        }
        myTest.setXmlClasses(myclasses);
        // Add tests
        List<XmlTest> myTests = new ArrayList<XmlTest>();
        myTests.add(myTest);
        mySuite.setTests(myTests);
        List<XmlSuite> mySuites = new ArrayList<XmlSuite>();
        mySuites.add(mySuite);
        //
        myTestNG.setXmlSuites(mySuites);
        mySuite.setFileName("testng-ExecutionSuite.xml");
        mySuite.setThreadCount(2);
        myTestNG.run();
        for (XmlSuite suite : mySuites) {
            createXmlFile(suite);
        }
    }

    public static void createXmlFile(XmlSuite mSuite) {
        FileWriter writer;
        try {
            writer = new FileWriter(new File("testng-ExecutionSuite.xml"));
            writer.write(mSuite.toXml());
            writer.flush();
            writer.close();
            System.out.println(new File("testng-ExecutionSuite.xml").getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

// get Intellij Configurations
    public static void getConfigurations() {

        try{
            ExecutionType = System.getenv("ExecutionType");
            System.out.println("Execution Type: "+ExecutionType);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

}
