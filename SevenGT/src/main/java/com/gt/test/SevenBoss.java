package com.gt.test;


import com.gt.config.ApiConfigFactory;
import com.gt.pages.BossAppStoreSelection;
import com.gt.pages.BossAppLogin;
import com.gt.pages.BossOrderingPlaceOrder;
import com.gt.reports.ExtentLogger;
import com.gt.utils.ApiUtils;
import io.restassured.RestAssured;
import org.testng.annotations.Test;
import java.util.Map;

public class SevenBoss extends BaseTest {

    private static final String BASE_URL = ApiConfigFactory.getConfig().apiBaseUrl();
    private static final String LIST_USERS_ENDPOINT = ApiConfigFactory.getConfig().ListUserEndPoint();

    /*TC01 */
    @Test( dataProvider = "getData",dataProviderClass = com.gt.utils.DataSupplierTest.class)
    public void Login(Map<Object,Object> data) throws InterruptedException {
        BossAppLogin loginPage = new BossAppLogin();
        BossAppStoreSelection homePage = new BossAppStoreSelection();
        BossOrderingPlaceOrder placeOrder = new BossOrderingPlaceOrder();
        ApiUtils apiUtils = new ApiUtils();
        try {
            // Login as a Store Manager
            loginPage.loginApplication(data.get("username").toString(), data.get("password").toString());
            homePage.enterStoreNoAndSearch(data.get("storeid").toString());
            placeOrder.enterStoreCredentials(data.get("storeuserid").toString(),data.get("storepwd").toString());
            homePage.verifyHomePageTitle(data.get("HomePageTitle").toString());

 /*           // Order an Item
            placeOrder.clickOrdering();
            placeOrder.selectItem("fresh bakery");
            placeOrder.enterQuantityAndSubmitOrder("4",data.get("itemname").toString());*/

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("TestCase Failed for - "+data.get("testname").toString() +", Exception Details: "+ e.getMessage());
            ExtentLogger.fail("TestCase Failed - "+data.get("testname")+"\n"+e);
        }
    }

    /* TC02 */
    @Test(dataProvider = "getData", dataProviderClass = com.gt.utils.DataSupplierTest.class)
    public void SevenBossTest1(Map<Object, Object> data) throws InterruptedException {
        BossAppLogin loginPage = new BossAppLogin();
        BossAppStoreSelection homePage = new BossAppStoreSelection();
        BossOrderingPlaceOrder placeOrder = new BossOrderingPlaceOrder();
        ApiUtils apiUtils = new ApiUtils();
        try {
            // Login as a Store Manager
            loginPage.loginApplication(data.get("username").toString(), data.get("password").toString());
            homePage.enterStoreNoAndSearch(data.get("storeid").toString());
            placeOrder.enterStoreCredentials(data.get("storeuserid").toString(),data.get("storepwd").toString());
            homePage.verifyHomePageTitle(data.get("HomePageTitle").toString());

            // Order an Item
            placeOrder.clickOrdering();
            //placeOrder.selectItemType("MultiDay");
            placeOrder.selectItem("fresh bakery");
            placeOrder.enterQuantityAndSubmitOrder("4",data.get("itemname").toString());

            //loginPage.handleYourConnectionNotPrivate();
        }catch (Exception e){
            System.out.println("TestCase Failed for - "+data.get("testname").toString() +", Exception Details: "+ e.getMessage());
            ExtentLogger.fail("TestCase Failed - "+data.get("testname")+"\n"+e);
        }
    }


    // API Sample Test
    @Test( dataProvider = "getData",dataProviderClass = com.gt.utils.DataSupplierTest.class)
    public void SevenBossTest2(Map<Object,Object> data) throws InterruptedException {
        RestAssured.given()
                .queryParam("page", 2)
                .baseUri(BASE_URL)
                .get(LIST_USERS_ENDPOINT)
                .prettyPrint();
    }


}
