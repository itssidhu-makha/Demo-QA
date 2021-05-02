package com.utils;

import com.controller.BrowserFactory;
import com.controller.Runner;
import com.relevantcodes.extentreports.ExtentTest;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class CommonUtils {
    WebDriver driver=null;
    ExtentManager repoInstance;
    public CommonUtils(){

        driver=BrowserFactory.getInstance().getWebDriver();
        repoInstance=ExtentManager.getInstance();

    }

    public WebElement findAnyWebElement(String eleName, By by){
        WebElement ele = driver.findElement(by);
        if(ele!=null){
            repoInstance.reportPass("Element "+eleName+" found successfully");
        }else{
            repoInstance.reportFail("Element "+eleName+" not found ::"+by);
        }
        return ele;
    }

    public void findElementAndSendKeys(String eleName,String value,By by){
        try {
            WebElement ele = findAnyWebElement(eleName, by);
            sendKeys(ele,value);
        }catch (Exception e){
            e.printStackTrace();
            repoInstance.reportFail("Failed to send keys");
        }
    }

    public void sendKeys(WebElement ele, String value) {
        ele.sendKeys(value);
    }

    public void findElementAndSendKeys_clear(String eleName,String value,By by){
        try {
            WebElement ele = findAnyWebElement(eleName, by);
            ele.clear();
            ele.sendKeys(value);
        }catch (Exception e){
            e.printStackTrace();
            repoInstance.reportFail("Failed to send keys");
        }
    }

    public void findElementAndClick(String gender, By by) {
        WebElement ele = findAnyWebElement(gender,by);
        clickElement(ele);
    }

    private void clickElement(WebElement ele) {
        if(ele!=null){
            ele.click();
        }
    }
    public void setClipboardData(String string) {
        //StringSelection is a class that can be used for copy and paste operations.
        StringSelection stringSelection = new StringSelection(string);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
    }

    public void selectFromInputSelect(String state, By xpath) {
        WebElement ele = findAnyWebElement(state,xpath);
        findElementAndSendKeys(state,state,xpath);
        pressKey(ele,"DOWN");
        pressKey(ele,"ENTER");
    }

    public void explicitWaitForPresenceOfElement(By by){
        try {
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
            driver.manage().timeouts().implicitlyWait(Runner.IMPLICITWAITTIME, TimeUnit.SECONDS);
        }catch(Exception e){
            e.getMessage();
            repoInstance.reportFail("Failed to find element after 5 seconds as well "+by);
        }
    }


    public void selectElement_VisibleText(String eleName,By by,String value){
        WebElement ele = findAnyWebElement(eleName,by);
        try {
            Select select = new Select(ele);
            select.selectByVisibleText(value);
        }catch(Exception e){
            e.printStackTrace();
            repoInstance.reportFail("Failed to select element "+eleName);
        }
    }

    public void pressKey(WebElement ele,String key) {
        switch (key){
            case "DOWN":
               ele.sendKeys(Keys.DOWN);
               break;
            case "ENTER":
                ele.sendKeys(Keys.ENTER);
                break;
            default:
                ele.sendKeys(Keys.TAB);
                break;
        }
    }

    public void uploadImage(String path) {
        try{

            Robot robot = new Robot();

            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        }catch (Exception e){
            repoInstance.reportFail("Upload method failed");
            e.printStackTrace();
        }
    }

    public void waitForAlertToAppear(int timeInSeconds){
        driver.manage().timeouts().implicitlyWait(0,TimeUnit.SECONDS);
        new WebDriverWait(driver, Duration.ofSeconds(timeInSeconds))
                .ignoring(NoAlertPresentException.class)
                .until(ExpectedConditions.alertIsPresent());
        driver.manage().timeouts().implicitlyWait(Runner.IMPLICITWAITTIME,TimeUnit.SECONDS);
    }

    public void clickByActionClass(String eleName, By by) {
        try {
            Actions act = new Actions(driver);
            Action eleToClick = act.moveToElement(findAnyWebElement(eleName, by)).click().build();
            eleToClick.perform();
        }catch (Exception e){
            e.printStackTrace();
            repoInstance.reportFail("Could not click by action class "+eleName);
        }
    }

    public void hoverOverElement(String eleName, By by) {
        try {
            Actions act = new Actions(driver);
            act.moveToElement(findAnyWebElement(eleName, by)).build().perform();

        }catch (Exception e){
            e.printStackTrace();
            repoInstance.reportFail("Could not hover by action class "+eleName);
        }
    }

    public void clickByActionClass_Space(String eleName, By by) {
        try {
            Actions act = new Actions(driver);
            Action eleToClick = act.moveToElement(findAnyWebElement(eleName, by)).sendKeys(Keys.SPACE).build();
            eleToClick.perform();
        }catch (Exception e){
            e.printStackTrace();
            repoInstance.reportFail("Could not click by action class "+eleName);
        }
    }

    public void clickByJS(String eleName,By ele){
        WebElement element = driver.findElement(ele);
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", element);
    }


    public void sleep(int i) {
        try {
            Thread.sleep(i*1000);
        }catch (Exception e){

        }
    }

    public String getAlertText(){
        Alert al=null;
        try {

            al= driver.switchTo().alert();
            return al.getText();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public void actOnAlert(String action) {
        try {
            Alert al = driver.switchTo().alert();
            switch (action) {
                case "ACCEPT":
                case "Accept":
                    al.accept();
                    break;

                case "REJECT":
                case "DISMISS":
                    al.dismiss();

                default:
                    al.accept();
                    break;

            }
        }catch (Exception e){
            e.printStackTrace();
            repoInstance.reportFail("Failed to act on alert");
        }

    }

    public String getAttributeOfElement(WebElement ele,String attribute) {
        try{
            return ele.getAttribute(attribute);
        }catch(Exception e){
            e.getMessage();
        }
        return null;
    }

    public void dragAndDropElement(WebElement from, WebElement to) {
        Actions act=new Actions(driver);

        act.dragAndDrop(from, to).build().perform();
    }
}
