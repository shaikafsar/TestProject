package com.gt.pages;


import com.gt.driver.DriverManager;
import org.openqa.selenium.By;

import java.sql.SQLOutput;
import java.time.Duration;

import static com.gt.utils.SeleniumUtils.*;

public class BossAppLogin {

    private static final By TXTBOX_USERNAME = By.name("username"); // static is declared to have same object used
    private static final By TXTBOX_PASSWROD = By.name("password");
    private static final By BTN_LOGIN = By.xpath("//*[@type='submit']");
    private static final By BTN_DETAILS = By.id("details-button");
    private static final By BTN_PROCEED = By.id("proceed-link");

    public void loginApplication(String userName, String password) {
        handleYourConnectionNotPrivate();
        setUserName(userName);
        setPassword(password);
        clickLogin();
    }

    public void handleYourConnectionNotPrivate() {
            System.out.println("**** Script Started ****");
            click(BTN_DETAILS, "Details");
            click(BTN_PROCEED, "Proceed");
    }

    private void setUserName(String userName) {
        sendKeys(TXTBOX_USERNAME, userName,"UserName");
    }

    private void setPassword(String password) {
        sendKeys(TXTBOX_PASSWROD, password, "Password");
    }

    public void clickLogin(){
        click(BTN_LOGIN,"Login");
    }

}
