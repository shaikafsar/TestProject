package com.gt.driver;

import com.gt.config.ConfigFactory;
import org.openqa.selenium.WebDriver;
import java.net.MalformedURLException;
import java.time.Duration;

public final class Driver {
    private Driver(){}

    public static void initiateDriver() throws MalformedURLException {

        String browser = ConfigFactory.getConfig().browser();
        String runmode = ConfigFactory.getConfig().runmode();
        /*if (!System.getenv("runmode").isEmpty()){
            runmode = ConfigFactory.getConfig().runmode();
        }else{
            runmode = ConfigFactory.getConfig().runmode();
        }*/
        if (DriverManager.getDriver()==null) {
            WebDriver driver = DriverFactory.getDriver(browser,runmode);
            DriverManager.setDriver(driver);
            DriverManager.getDriver().manage().window().maximize();
            DriverManager.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigFactory.getConfig().timeout()));
            DriverManager.getDriver().get(ConfigFactory.getConfig().url());
        }
}

public static void quitDriver() {
    if (DriverManager.getDriver() != null) {
        DriverManager.getDriver().quit();
        DriverManager.setDriver(null);
    }

}


}
