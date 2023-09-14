package com.gt.utils;

import com.gt.config.ConfigFactory;
import com.gt.driver.DriverManager;
import com.gt.enums.WaitType;
import com.gt.reports.ExtentLogger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;


public class SeleniumUtils {



    public static void sendKeys(By by, String value,String elementName) {
        waitUntilElementPresent(by).sendKeys(value);
        ExtentLogger.pass(value + " is entered Successfully in "+elementName );
    }

    public static void keysEnter(By by, String elementName) {
        waitUntilElementPresent(by).sendKeys(Keys.ENTER);
        ExtentLogger.pass(" Keys Enter Success" );
    }
    public static String getText(By by){
       String str =  waitUntilElementPresent(by).getText();
       return str ;
    }

    public static void click(By by, String elementName) {
        waitUntilElementPresent(by).click();
        ExtentLogger.pass(elementName +" is clicked Successfully" );
    }

    public static boolean isDisplayed(By by, String elementName) {
        boolean status = false ;
        WebElement element = waitUntilElementPresent(by);
        if (element.isDisplayed()){
            status = true;
        }
        ExtentLogger.pass(elementName +" is Displayed Successfully" );
        return status;
    }

    // Overloading method - If we need use some other Wait for clickable elements
    public static void click(By by, WaitType waitType) {
        WebElement element = null;
        if (waitType == WaitType.PRESENCE) {
            element = waitUntilElementPresent(by);
        } else if(waitType == WaitType.CLICKABLE){
            element = waitUntilElementTobeClickable(by);
        }else if (waitType==WaitType.VISIBLE){
            element = waitUntilElementTobeVisible(by);
        }
        element.click();
    }

    private static WebElement waitUntilElementPresent(By by) {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(ConfigFactory.getConfig().timeout()));
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private static WebElement waitUntilElementTobeClickable(By by) {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(ConfigFactory.getConfig().timeout()));
        return wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    private static WebElement waitUntilElementTobeVisible(By by) {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(ConfigFactory.getConfig().timeout()));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }



    public static void waitForPageLoad(long secs){
        try {
            Thread.sleep(secs);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // Tried all the Below

    public static void fluentWait(WebDriver driver,WebElement element){ // To be Tested
        System.out.println("Page Load Started "+getCurrentDate());
        FluentWait wait = new FluentWait(driver);
        wait.withTimeout(Duration.ofSeconds(5000));
        wait.pollingEvery(Duration.ofSeconds(1));
        wait.ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.visibilityOf(element));
        System.out.println("Page Load Ends "+getCurrentDate());
    }

    public static void waitUntilPageLoad(WebDriver driver, Function<WebDriver, Boolean> waitCondition, Duration timeoutInSeconds){
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        webDriverWait.withTimeout(Duration.ofSeconds(15));
        try{
            webDriverWait.until(waitCondition);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void untilPageLoadComplete(WebDriver driver, Duration timeoutInSeconds){
        System.out.println("Page Load Started "+getCurrentDate());
        waitUntilPageLoad(driver, (d) ->
        {
            Boolean isPageLoaded = (Boolean)((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            if (!isPageLoaded) System.out.println("Document is loading");
            return isPageLoaded;
        }, timeoutInSeconds);
        System.out.println("Page Load Ends "+getCurrentDate());
    }

    public static String getCurrentDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));
        return dtf.format(now) ;
    }


}
