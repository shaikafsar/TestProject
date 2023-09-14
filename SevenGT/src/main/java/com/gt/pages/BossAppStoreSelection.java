package com.gt.pages;

import com.gt.driver.DriverManager;
import com.gt.utils.ReportUtils;
import org.openqa.selenium.By;

import static com.gt.utils.SeleniumUtils.*;

public class BossAppStoreSelection {


    private static final By TEXTBOX_STOREID = By.xpath("//*[@type='text']");
    private static final String HEADINGSUGGESTED_STOREID ="//*[text()='Store ";
    private static final String STR = "']";

    public static void verifyHomePageTitle(String expected){
        ReportUtils.addStepValidation( DriverManager.getDriver().getTitle(), expected);
    }

    public void enterStoreNoAndSearch(String val){
        enterStoreId(val);
        enterKeys();
        clickOnStoreAddress(val);
    }

    private void enterStoreId(String storeId) {
        waitForPageLoad(3000);
        sendKeys(TEXTBOX_STOREID, storeId,"StoreId");
    }

    public void enterKeys(){
        waitForPageLoad(3000);
        keysEnter(TEXTBOX_STOREID, "StoreId");
    }

    private void clickOnStoreAddress(String val) {
        waitForPageLoad(5000);
        click(By.xpath(HEADINGSUGGESTED_STOREID+val+STR)," Suggested StoreId Address");
    }

}
