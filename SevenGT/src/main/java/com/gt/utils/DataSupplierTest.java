package com.gt.utils;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

public class DataSupplierTest {

     static List<Map<String, String>> list = new ArrayList();

    @DataProvider
    public static Object[] getData(Method m) {
        String testName = m.getName();
        System.out.println(testName);
        if (list.isEmpty()) {
            list = getTestDetail();
        }
        List<Map<String,String>> smallList = new ArrayList<>();
        for(int i = 0; i<list.size();i++){
            if (list.get(i).get("testname").equalsIgnoreCase(testName)){
                if (list.get(i).get("execution").equalsIgnoreCase("Y")){
                    smallList.add(list.get(i));
                    System.out.println(smallList);
                }
            }
        }
        list.remove(smallList);
        return smallList.toArray();
    }

    public static List<Map<String, String>> getTestDetail() {
        FileInputStream fs = null;
        List<Map<String, String>> list = null;

        try {
            fs = new FileInputStream(new File(".//testData//TestData.xlsx"));
            XSSFWorkbook workbook = new XSSFWorkbook(fs);
            XSSFSheet sheet = null ;
            if (TestNGUtils.ExecutionType.equalsIgnoreCase("Web")){
                sheet = workbook.getSheet("Web");
            }else if (TestNGUtils.ExecutionType.equalsIgnoreCase("Api")){
                sheet = workbook.getSheet("Api");
            }

            int lastRowNum = sheet.getLastRowNum();
            int lastColNum = sheet.getRow(0).getLastCellNum();
            Map<String, String> map = null;
            list = new ArrayList<>();

            for (int i = 1; i <=lastRowNum; i++) {
                map = new HashMap<>();
                for (int j = 0; j < lastColNum; j++) {
                    String key = sheet.getRow(0).getCell(j).getStringCellValue();
                    String value = sheet.getRow(i).getCell(j).getStringCellValue();
                    map.put(key, value);
                }
                list.add(map);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(fs)) {
                try{
                    fs.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return list ;
    }
}