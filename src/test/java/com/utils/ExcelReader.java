package com.utils;

import com.controller.Runner;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ExcelReader {

    public ArrayList<HashMap<String,String>> loadTestData(String workBookName,String sheetName){
        ArrayList<HashMap<String,String>> data = new ArrayList<>();
        HashMap<String,String> tests = null;
        Workbook workbook =null;

        try{
            workbook=new XSSFWorkbook(new FileInputStream(Runner.EXCEL_PATH+"//"+workBookName+".xlsx"));

            Sheet sheet= workbook.getSheet(sheetName);
            Row mainRow = sheet.getRow(0);
            DataFormatter formatData = new DataFormatter();
            //get total filled rows with test cases

            for(int i=1;i<sheet.getPhysicalNumberOfRows();i++){
                tests=new HashMap<>();
                Row currentRow=sheet.getRow(i);
                Iterator<Cell> cellIterator = currentRow.cellIterator();
                int mainRowCell=0;
                while(cellIterator.hasNext()){
                    String rowHeader = formatData.formatCellValue(mainRow.getCell(mainRowCell));
                    Cell currentCell = cellIterator.next();
                    String currentCellValue = formatData.formatCellValue(currentCell);
                    tests.put(rowHeader,currentCellValue);
                    mainRowCell++;
                }
                //add row into list
                data.add(tests);
            }
        }catch(Exception e){
            //put the exception in reporter
            e.printStackTrace();
        }finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return data;
    }

    public HashMap<String, String> loadTestData_TestName(String workBookName, String sheetName, String testName){
        ArrayList<HashMap<String,String>> data = new ArrayList<>();
        HashMap<String,String> tests = new HashMap<>();
        Workbook workbook =null;

        try{
            workbook=new XSSFWorkbook(new FileInputStream(Runner.EXCEL_PATH+"//"+workBookName+".xlsx"));

            Sheet sheet= workbook.getSheet(sheetName);
            Row mainRow = sheet.getRow(0);
            DataFormatter formatData = new DataFormatter();
            //get total filled rows with test cases
            boolean tsFound=false;
            for(int i=1;i<sheet.getPhysicalNumberOfRows();i++){

                Row currentRow=sheet.getRow(i);
                Cell tcCell = currentRow.getCell(0);
                if(tcCell.getStringCellValue().equalsIgnoreCase(testName)) {
                    tsFound=true;
                    Iterator<Cell> cellIterator = currentRow.cellIterator();
                    int mainRowCell = 0;
                    while (cellIterator.hasNext()) {
                        String rowHeader = formatData.formatCellValue(mainRow.getCell(mainRowCell));
                        Cell currentCell = cellIterator.next();
                        String currentCellValue = formatData.formatCellValue(currentCell);
                        tests.put(rowHeader, currentCellValue);
                        mainRowCell++;

                    }

                }
                if(tsFound) {
                    break;
                }
                //add row into list


            }
        }catch(Exception e){
            //put the exception in reporter
            e.printStackTrace();
        }finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return tests;

    }
}
