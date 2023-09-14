package com.gt.driver;

import org.openqa.selenium.WebDriver;

public final class DriverManager {

    private DriverManager(){}

    private static final ThreadLocal<WebDriver> threadLocal = new ThreadLocal<>(); // This creates a new driver object for every testcase

    public static WebDriver getDriver() {
        return threadLocal.get();
    }

    public static void setDriver(WebDriver driver) {
        threadLocal.set(driver);
    }
}
