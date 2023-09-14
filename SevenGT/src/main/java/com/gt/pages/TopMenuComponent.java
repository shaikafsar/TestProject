package com.gt.pages;

import com.gt.driver.DriverManager;
import com.gt.utils.SeleniumUtils;
import org.openqa.selenium.By;

import static com.gt.utils.SeleniumUtils.*;

public class TopMenuComponent {

    private static final By lnkAdmin = By.xpath("//*[@class='oxd-main-menu-item active']");

    public void clickAdmin(){
        click(lnkAdmin,"Admin");
    }
}
