package com.gt.driver;

import com.gt.config.ConfigFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public final class DriverFactory {

    private DriverFactory(){}

    public static WebDriver getDriver(String browserName, String runmode) throws MalformedURLException {
    WebDriver driver = null;
        if (runmode.equalsIgnoreCase("local"))
        {
           driver = getLocalWebDriver(browserName);
        } else if (runmode.equalsIgnoreCase("remote"))
        {
           driver = getRemoteWebDriver(browserName);
        }
        return driver;
    }

    public static WebDriver getLocalWebDriver(String browserName) throws MalformedURLException {
        WebDriver driver = null ;
        if (browserName.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            // options.setBrowserVersion("115"); //-- Working in Mac with 115
            options.addArguments("--remote-allow-origins=*");
            driver = new ChromeDriver(options);
        } else if(browserName.equalsIgnoreCase("firefox")){
            driver = new FirefoxDriver();
        }
        return  driver;
    }

    public static WebDriver getRemoteWebDriver(String browserName) throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        if (browserName.equalsIgnoreCase("chrome")) {
            capabilities.setBrowserName("chrome");
        } else if(browserName.equalsIgnoreCase("firefox")){
            capabilities.setBrowserName("firefox");
        }
        return new RemoteWebDriver(new URL(ConfigFactory.getConfig().remoteurl()),capabilities);
    }






}
