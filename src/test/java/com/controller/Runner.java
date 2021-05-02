package com.controller;

import com.utils.ExcelReader;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Runner {

    public static final long IMPLICITWAITTIME =40;
    public static String RESOURCES_PATH=System.getProperty("user.dir")+"//src//main//resources";
    public  static String EXCEL_PATH=RESOURCES_PATH+"//datas";

    public  static String TESTS_SHEET="Tests";
    public static String  WORKBOOK_NAME="Tests";
    public String classContainingTests;
    public String columnHeaderForTest="TestName";//This will be your column header in excel sheet
    public String paramterForTestCase="TestRunning";//Note this should be same as in BaseTest where your @Test annotation is located

    public static String suiteName="MySuite";
    public static int totalThreads=2;
    public static String paramForSheet= "SheetRunning";
    public static boolean remoteExecution=false;
    public static boolean gridLocalExecution=false;
    public static boolean gridDockerExecution=false;
    public static boolean sauceLab=false;
    public static boolean zalenium=false;

    public static void main(String[] args) {

        Runner runner = new Runner();
        //read data from excel sheet
        ExcelReader reader = new ExcelReader();
        ArrayList<HashMap<String,String>> allTests = reader.loadTestData(runner.WORKBOOK_NAME,runner.TESTS_SHEET);
        runner.testRunner(allTests);

    }
    public void testRunner(ArrayList<HashMap<String,String>> testCasesTotal){
        classContainingTests="com.controller.BaseTest";
        //Frame a map of parameters that needs to be passed to tests
        TestNG testng = new TestNG();

        //Set your suite file in a dynamic manner
        XmlSuite suite = new XmlSuite();
        suite.setName(suiteName);
        suite.setThreadCount(totalThreads);

        suite.setParallel(XmlSuite.ParallelMode.METHODS);


            //set your classes, where your @Test annotation are specified
            List<XmlClass> classNames = new ArrayList<>();
            classNames.add(new XmlClass(classContainingTests));

            //Set your tests here, add all the classes created above
            XmlTest test = new XmlTest(suite);
            test.setName("DemoQA");
            test.setXmlClasses(classNames);


        //finally jellify your suite at the end
        List<XmlSuite> suites = new ArrayList<>();
        suites.add(suite);
        testng.setXmlSuites(suites);
        testng.run();




    }
}
