package com.pages;

import com.controller.Runner;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.utils.CommonUtils;
import com.utils.ExtentManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashMap;

import static com.constants.PractiseForm.*;

public class PractiseForm {
    String testCaseRunning;
    ExtentTest test;
    CommonUtils util;
    HashMap<String,String> testData;
    ExtentManager repo;
    public PractiseForm(String testCaseRunning,HashMap<String,String> testData){
        this.testCaseRunning=testCaseRunning;
        this.test= ExtentManager.getInstance().getTest();
        this.util=new CommonUtils();
        this.testData=testData;
        repo =ExtentManager.getInstance();
    }

    public void fillPractiseForm(){
        //		Email	Gender	Mobile	DOB	Hobbies	Picture	CurrentAddress	State	City
        inputFirstName(testData.get("FirstName"));
        inputLastName(testData.get("LastName"));
        inputEmail(testData.get("Email"));
        selectGender(testData.get("Gender"));
        enterMobile(testData.get("Mobile"));
        inputDOB(testData.get("DOB"));
        inputSubject(testData.get("Subject"));

        selectHobbies(testData.get("Hobbies"));
        uploadPicture(testData.get("Picture"));
        inputCurrentAddress(testData.get("CurrentAddress"));
        selectState(testData.get("State"));
        selectCity(testData.get("City"));
        submit();
        closePopUp();

    }

    private void closePopUp() {
        util.explicitWaitForPresenceOfElement(By.id("closeLargeModal"));
        util.findElementAndClick("Close Pop Up",By.id("closeLargeModal"));
    }

    private void submit() {
        util.findElementAndClick("Submit button",By.id(ID_SUBMIT_BUTTON));
    }

    private void selectState(String state) {

        util.selectFromInputSelect(state,By.xpath(XPATH_STATE));
    }
    private void selectCity(String city) {

        util.selectFromInputSelect(city,By.xpath(XPATH_CITY));
    }

    private  void selectHobbies(String hobbies) {

        String[] allHobbies = hobbies.split(",");

        for(String nav:allHobbies){
            util.explicitWaitForPresenceOfElement(By.xpath("//*[contains(text(),'"+nav+"')]/preceding-sibling::input"));
            util.clickByJS(nav,By.xpath("//label[contains(text(),'"+nav+"')]//preceding-sibling::input"));///preceding-sibling::input
        }
    }

    private void inputFirstName(String name){
        util.findElementAndSendKeys(name,name,By.id(ID_FIRSTNAME));
    }
    private void inputLastName(String lastName){
        util.findElementAndSendKeys(lastName,lastName,By.id(ID_LASTNAME));
    }

    private void inputEmail(String email){
        util.findElementAndSendKeys(email,email,By.id(ID_EMAIL));
    }
    private void selectGender(String gender){
        util.clickByActionClass("Gender",By.xpath("//*[@id='genterWrapper']//input[@value='"+gender+"']"));
    }

    private void inputDOB(String dob){
        String month = dob.split(" ")[1];
        String day = dob.split(" ")[0];
        String year = dob.split(" ")[2];

        util.findElementAndClick("Date Picker",By.id(ID_DATE_PICKER));
        util.selectElement_VisibleText(month,By.className(CLASS_MONTH_PICKER),month);
        util.selectElement_VisibleText(year,By.className(CLASS_YEAR_PICKER),year);
        util.findElementAndClick("Day of Date",By.xpath("//div[contains(@class,'"+"react-datepicker__day react-datepicker__day--0"+day+"')]"));
    }

    private void enterMobile(String mobile){
        util.findElementAndSendKeys(mobile,mobile,By.id(ID_MOBILE));
    }
    private void inputSubject(String subject){
        util.clickByActionClass("Interactable page",By.id(ID_SUBJECT_CONTAINER));
        util.explicitWaitForPresenceOfElement(By.xpath("//*[@id='subjectsContainer']/div[1]/div[1]"));
        WebElement ele = util.findAnyWebElement(subject,By.xpath("//div[@id='subjectsContainer']//input"));
        util.sendKeys(ele,subject);
        util.pressKey(ele,"ENTER");
        util.pressKey(ele,"");
    }
    private void uploadPicture(String path){
        //util.setClipboardData(Runner.RESOURCES_PATH+"//"+path+".jpg");
        //util.clickByJS("pic upload",By.id(ID_UPLOAD_PIC));
        util.findElementAndSendKeys("pic upload",Runner.RESOURCES_PATH+"//"+path+".jpg",By.id(ID_UPLOAD_PIC));
        //util.uploadImage(Runner.RESOURCES_PATH+"//"+path+".jpg");

    }
    private void inputCurrentAddress(String adress){
        util.findElementAndSendKeys(adress,adress,By.id(ID_CURRENT_ADDRESS));
    }


    public void acceptAlert(){

        util.findElementAndClick("TimeAlert Alert",By.id(ID_TIMEALERT));
        util.waitForAlertToAppear(6);
        String alertMessage = util.getAlertText();
        if(testData.get("ExpAlertText").equalsIgnoreCase(alertMessage)) {
           repo.reportPass("Alert message captured and matching");
        }else{
            repo.reportFail("Alert message did not match - clicked another alert");
        }
        util.actOnAlert("ACCEPT");
    }

    public void handleToolTips() {

        testHoverOver("toolTipButton");
        testHoverOver("toolTipTextField");//toolTipTextField
    }

    private void testHoverOver(String eleID) {
        util.hoverOverElement("Button tool tip",By.id(eleID));
        String attribute ="aria-describedby";
        util.explicitWaitForPresenceOfElement(By.id(eleID));
        util.sleep(2);
        String attValue =util.getAttributeOfElement(util.findAnyWebElement("Button tool tip",By.id(eleID)),attribute);
        if(attValue!=null){
            repo.reportPass("Hover over is success");
        }else{
            repo.reportFail("Hover over failed");
        }
    }

    public void dragAndDrop() {

        WebElement from = util.findAnyWebElement("Drag Element",By.id("draggable"));
        WebElement to=util.findAnyWebElement("Drop Element",By.id("droppable"));

        util.dragAndDropElement(from,to);
        String isDropSuccess =util.findAnyWebElement("Drop Success Check",By.xpath("//div[@id='droppable']/p")).getText();
        if("Dropped!".equalsIgnoreCase(isDropSuccess)){
            repo.reportPass("Element dropped successfully");
        }else{
            repo.reportFail("Element could not be dropped successfully");
        }
    }
}
