package com.controller;

import com.dataproviders.DataProviders;
import com.pages.PractiseForm;
import com.relevantcodes.extentreports.ExtentTest;
import com.utils.ExcelReader;
import com.utils.ExtentManager;

import org.testng.annotations.*;
import java.util.HashMap;


@Listeners(com.controller.ListenerClass.class)
public  class BaseTest {

    @Test(description = "Dynamic test Builder",retryAnalyzer = RetryAnalyser.class,dataProvider = "formData",dataProviderClass = DataProviders.class)
    public void fillFormTestCase(@Optional("Test") String testName){

        String testDataSheet="TestData";

        HashMap<String,String> testCases = new ExcelReader().loadTestData_TestName(Runner.WORKBOOK_NAME,Runner.TESTS_SHEET,testName);

        HashMap<String,String> testData = new ExcelReader().loadTestData_TestName(Runner.WORKBOOK_NAME,testDataSheet,testName);
        instantiate(testName);
        PractiseForm fillForm = new PractiseForm(testName,testData);
        fillPractiseForm(testCases,fillForm);

    }

    @Test(description = "Accept Alerts",retryAnalyzer = RetryAnalyser.class,dataProvider = "alerts",dataProviderClass = DataProviders.class)
    public void verifyAlerts(@Optional("Test") String testRunnning){
        //this method dynamically creates the test classes rather than rewritting @Test annotation multiple times for every test case

        String testDataSheet="TestData";

        HashMap<String,String> testCases = new ExcelReader().loadTestData_TestName(Runner.WORKBOOK_NAME,Runner.TESTS_SHEET,testRunnning);

        HashMap<String,String> testData = new ExcelReader().loadTestData_TestName(Runner.WORKBOOK_NAME,testDataSheet,testRunnning);
        instantiate(testRunnning);
        PractiseForm fillForm = new PractiseForm(testRunnning,testData);
        //test case - 2
        //will be considered separate test case as handled from excel sheet
        acceptAlert(testCases,fillForm);

    }

    @Test(description = "verify Hovers",retryAnalyzer = RetryAnalyser.class,dataProvider = "hovers",dataProviderClass = DataProviders.class)
    public void verifyHoverTestCase(@Optional("Test") String testRunnning){
        //this method dynamically creates the test classes rather than rewritting @Test annotation multiple times for every test case

        String testDataSheet="TestData";

        HashMap<String,String> testCases = new ExcelReader().loadTestData_TestName(Runner.WORKBOOK_NAME,Runner.TESTS_SHEET,testRunnning);

        HashMap<String,String> testData = new ExcelReader().loadTestData_TestName(Runner.WORKBOOK_NAME,testDataSheet,testRunnning);
        instantiate(testRunnning);
        PractiseForm fillForm = new PractiseForm(testRunnning,testData);

        //will be considered separate test case  handled from excel sheet
        verifyHoverSuccess(testCases,fillForm);
    }

    @Test(description = "Verify Drag and Drop",retryAnalyzer = RetryAnalyser.class,dataProvider = "dragdrop",dataProviderClass = DataProviders.class)
    public void verifyDragAndDrop(@Optional("Test") String testRunnning){
        //this method dynamically creates the test classes rather than rewritting @Test annotation multiple times for every test case
        String testDataSheet="TestData";

        HashMap<String,String> testCases = new ExcelReader().loadTestData_TestName(Runner.WORKBOOK_NAME,Runner.TESTS_SHEET,testRunnning);

        HashMap<String,String> testData = new ExcelReader().loadTestData_TestName(Runner.WORKBOOK_NAME,testDataSheet,testRunnning);
        instantiate(testRunnning);
        PractiseForm fillForm = new PractiseForm(testRunnning,testData);
        //test-4
        //test drag and drop
        dragAndDrop(testCases,fillForm);


    }

    private void dragAndDrop(HashMap<String, String> testCases, PractiseForm fillForm) {
        instance.getWebDriver().get(testCases.get("URL"));
        fillForm.dragAndDrop();
    }

    private void verifyHoverSuccess(HashMap<String, String> testCases, PractiseForm fillForm) {
        //we can pass from excel as well for a different test case
        instance.getWebDriver().get(testCases.get("URL"));
        fillForm.handleToolTips();

    }

    private void acceptAlert(HashMap<String, String> testCases, PractiseForm fillForm) {
        //we can pass from excel as well for a different test case
        instance.getWebDriver().get(testCases.get("URL"));
        fillForm.acceptAlert();


    }


    @AfterMethod
    public void releaseThreadsAndShutDown(){
        ;//This will also shut down chromedriver.exe from processes
        //removing thread locals from permGen to avoid memory overhead
       flushReporterAndRemoveThreads();
    }

    private void flushReporterAndRemoveThreads() {
        if(Runner.remoteExecution){
            instance.getWebDriver().quit();
            instance.remoteWebDriver.remove();
        }else if(Runner.sauceLab){

            instance.getWebDriver().quit();
            instance.remoteWebDriver.remove();
        }
        else{
            instance.getWebDriver().quit();
            instance.webDriver.remove();
        }

        //flush reports
        repoManager.getReports().endTest(repoManager.getTest());
        repoManager.getReports().flush();
    }


    private void fillPractiseForm(HashMap<String,String> testCases,PractiseForm fillForm) {
        instance.getWebDriver().get(testCases.get("URL"));
        fillForm.fillPractiseForm();
    }


    public void instantiate(@Optional("Test") String testRunnning){
        setBrowser_ReporterForTest(testRunnning);
    }

    private void setBrowser_ReporterForTest(String testRunnning) {
        instance=BrowserFactory.getInstance();
        repoManager = ExtentManager.getInstance();
        repoManager.setReports(testRunnning);
        repoManager.setTest(testRunnning);
        try {
            instance.setWebDriver("chrome");

        }catch(Exception e){
            e.printStackTrace();
        }
    }


    BrowserFactory instance=null;
    ExtentTest test=null;
    ExtentManager repoManager=null;

    private BaseTest(){

    }


}
