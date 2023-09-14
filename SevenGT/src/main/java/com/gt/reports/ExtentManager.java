package com.gt.reports;

import com.aventstack.extentreports.ExtentTest;

public final class ExtentManager {

    private ExtentManager() {
    }

    private static final ThreadLocal<ExtentTest> threadLocal = new ThreadLocal<>(); // This creates a new driver object for every testcase

    static ExtentTest getExtentTest() {
        return threadLocal.get();
    }

    static void setExtentTest(ExtentTest test) {
        threadLocal.set(test);
    }
}
