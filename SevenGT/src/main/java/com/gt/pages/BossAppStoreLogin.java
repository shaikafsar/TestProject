package com.gt.pages;

import org.openqa.selenium.By;

import static com.gt.utils.SeleniumUtils.*;

public class BossAppStoreLogin {

    private static final By TXTBOX_USERNAME = By.name("userId"); // static is declared to have same object used
    private static final By TXTBOX_PASSWROD = By.name("password");
    private static final By BTN_LOGIN = By.xpath("//*[@type='button']");

    public void enterStoreUserDetails(String val){
        setUserPassword("");
        setUserId("");
    }

    private void setUserPassword(String userName) {
        sendKeys(TXTBOX_PASSWROD, "40","UserName");
    }

    private void setUserId(String userName) {
        sendKeys(TXTBOX_USERNAME, "243780","UserName");
    }
}
