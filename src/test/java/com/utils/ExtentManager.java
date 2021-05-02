package com.utils;

import com.controller.Runner;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.NetworkMode;

public class ExtentManager {
    private ThreadLocal<ExtentReports> reports=new ThreadLocal<>();
    private ThreadLocal<ExtentTest> test=new ThreadLocal<>();
    private ExtentManager(){}
    public static ExtentManager instance;
    public static ExtentManager getInstance(){
        if(instance==null){
            instance=new ExtentManager();
        }
        return instance;
    }

    public ExtentTest getTest() {
        return test.get();
    }

    public void setTest(String testName) {

        this.test.set(this.reports.get().startTest(testName));
    }


    public void setReports(String testRunnning) {
        reports.set(new ExtentReports(Runner.RESOURCES_PATH+"\\reports\\"+testRunnning+"\\"+testRunnning+".html",true, NetworkMode.ONLINE));
    }

    public ExtentReports getReports() {
        return reports.get();
    }

    public void reportPass(String message){
        getTest().log(LogStatus.PASS,message);
    }

    public void reportFail(String message){
        getTest().log(LogStatus.FAIL,message);
    }


}
