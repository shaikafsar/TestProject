package com.gt.pages;

import com.gt.driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import static com.gt.utils.SeleniumUtils.*;

public class BossOrderingPlaceOrder {
    private static final By TXTBOX_USERNAME = By.name("userId"); // static is declared to have same object used
    private static final By TXTBOX_PASSWORD = By.name("password");
    private static final By BTN_LOGIN = By.xpath("//*[text()='LOGIN']");
    private static final By ORDERING = By.xpath("//a[text()='Ordering']");
    private static final By PLACE_ORDER = By.xpath("//a[text()='Place Order']");
    private static final By DAILY = By.xpath("//*[text()='Daily']");
    private static final By MULTIDAY = By.xpath("//*[text()='Multi Day']");
    private static final By NONDAILY = By.xpath("//*[text()='Non-daily']");
    private static final By ALL = By.xpath("//*[@id='All-desktop']");
    private static final By CARRIED = By.xpath("//*[@id='Carried-desktop']");
    private static final By CONTINUE = By.xpath("//button[@id='btn-next']");
    private static final By CONTINUE2 = By.xpath("//*[text()='SUBMIT']");
    private static final String ITEMNAME = "//*[text()='";
    private static final String STR = "']";

    private static final By UP_ARROW = By.xpath("//i[@class='fa fa-angle-up cat-arrow arrow-expanded']");
    private static final String ORDER_QUANTITY = "//*[@count='";
    private static final String ORDER_QUANTITY_ENDTAG = "']//input" ;
    private static final String ORDER_QUANTITY_INPUT = "[@placeholder='Order']" ;
    private static final By CONFIRMATION_ORDER = By.xpath("//button[text()='Yes, Exit']") ;
    private static final By AI_REPLENISHMENT = By.xpath("//*[text()='AI Replenishment Recap']");

    private static final By TOGGLE= By.xpath("//*[@class='react-toggle react-toggle--checked']");

    private static final By TOGGLE_DISABLE = By.xpath("//*[@class='react-toggle react-toggle--disabled']");
    public void enterStoreCredentials(String uName, String pwd){
        waitForPageLoad(5000);
        setUserName(uName);
        setPassword(pwd);
        clickLogin();
    }



    public void selectItemType(String itemType) {
        unCheckOtherOrderingTypes(itemType);
        selectItemsToOrder("All");   // InProgress - Pass from Excel
    }

    public void selectItem(String itemName){
        selectCategoryAndContinue(itemName);
    }

    public void enterQuantityAndSubmitOrder(String quantity , String itemType) {
       //disableOrderRemaining();
        enterQuantity(quantity,itemType);
        submitOrder();
        //handlePopUp();
        waitForPageLoad(5000);
    }


    private void disableOrderRemaining(){
        waitForPageLoad(10000l);

        if(!DriverManager.getDriver().findElement(TOGGLE_DISABLE).isDisplayed()){
            click(TOGGLE,"Order Remaining Items Only Toggle ");
        }
    }

    public void enterQuantity(String quantity,String ItemType) {
        waitForPageLoad(5000);
        click(UP_ARROW,"UP Arrow");
        int size = DriverManager.getDriver().findElements(By.xpath("//*[starts-with(@class,'item-name ellipsis')]")).size();
        System.out.println("Total Size: "+size);
        boolean status = true ;
        for (int i = 1; i <=size; i++) {
           String str =  DriverManager.getDriver().findElement(By.xpath("//*[@count='"+i+"']//*[starts-with(@class,'item-name ellipsis')]")).getText();
           if (str.equals(ItemType)){
               // Click on Text Box to show the Input
               System.out.println("Item Found , Item Name - "+str);
               status = true;
               By xpath =   By.xpath(ORDER_QUANTITY+i+ORDER_QUANTITY_ENDTAG);
               System.out.println("Xpath: "+xpath);
               click(xpath,"OrderQuantity");
               // Enter the Quantity in InPut Textbox
                By inputXpath =  By.xpath(ORDER_QUANTITY+i+ORDER_QUANTITY_ENDTAG+ORDER_QUANTITY_INPUT);
               System.out.println("inputXpath: "+inputXpath);
                sendKeys(inputXpath,quantity,"OrderQuantity");
                break ;
           }else{
               System.out.println("No Item Found , Item Name - "+str);
               status = false;
           }
        }
        if (!status){
            throw new NoSuchElementException("Item Name not found in UI");
        }else{
            System.out.println("Not True");
        }
    }

    private void submitOrder(){
        try {
            Thread.sleep(10000l);
            click(CONTINUE2,"Submit");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void handlePopUp() {
        waitForPageLoad(5000l);
        click(CONFIRMATION_ORDER,"Page Exit Popup");
    }

    private void unCheckOtherOrderingTypes(String orderingType){
        waitForPageLoad(7000);
        if (orderingType.equalsIgnoreCase("Daily")){
            click(MULTIDAY,"MultiDay");
            click(NONDAILY,"NonDaily");
        }else if(orderingType.equalsIgnoreCase("MultiDay")){
            click(NONDAILY,"NonDaily");
            click(DAILY,"Daily");
        }else if (orderingType.equalsIgnoreCase("NonDaily")) {
            click(MULTIDAY,"MultiDay");
            click(DAILY,"Daily");
        }
    }
    private void selectCategoryAndContinue(String itemName) {
        By categoryXpath = By.xpath(ITEMNAME+itemName+STR);
        try {
            Thread.sleep(5000l);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        click(categoryXpath,itemName);
        click(CONTINUE,"Continue");
    }

    private void selectItemsToOrder(String val) {
        if (val.equalsIgnoreCase("All")){
            click(ALL,"All Option");
        }else{
            click(CARRIED,"Carried Option");
        }
    }

    public void clickOrdering() {
        waitForPageLoad(10000l);
        click(ORDERING,"Ordering");
        click(PLACE_ORDER,"PlaceOrder");
    }

    private void setUserName(String userName) {
        sendKeys(TXTBOX_USERNAME, "40","UserName");
    }

    private void setPassword(String password) {
        sendKeys(TXTBOX_PASSWORD, "243780", "Password");
    }

    public void clickLogin(){
        click(BTN_LOGIN,"Login");
    }

    // This Method is not working - Created on 2023-May10
    private void unCheckOtherOrderingTypes1(String orderingType){
        int size =  DriverManager.getDriver().findElements(By.xpath("//div[@class='cycleType']/label")).size();
        System.out.println("Size :"+size);
        for (int i=0;i<=size;i++){
            String val = DriverManager.getDriver().findElement(By.xpath("//div[@class='cycleType']/label")).getText();
            if (!val.equalsIgnoreCase(orderingType)) {
                DriverManager.getDriver().findElement(By.xpath("//div[@class='cycleType']//*[@type='checkbox']")).click();
            }
        }
    }




}
