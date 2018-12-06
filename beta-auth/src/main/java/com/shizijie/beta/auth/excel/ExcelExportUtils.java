package com.shizijie.beta.auth.excel;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@Slf4j
public class ExcelExportUtils {
    
    public static Workbook createExcel(int startNum, String[] columnTitle, String[] columnKeys, List<Map<String,Object>> dataList,Workbook workbook){
        return createExcel("sheet1",startNum,columnTitle,columnKeys,dataList,workbook);
    }

    public static Workbook createExcel(String sheetName, int startNum, String[] columnTitle, String[] columnKeys, List<Map<String, Object>> dataList, Workbook workbook) {
        if(null==workbook){
            workbook=new Workbook();
        }
        if(startNum==0){
            workbook.createRowTitle(workbook.createSheet(sheetName),columnTitle);
        }
        workbook.createContent(workbook.getSheet(sheetName),startNum+1,columnKeys,dataList);
        return workbook;
    }

    public static Boolean writeExcel(HttpServletResponse response,String fileName, Workbook workbook){
        response.addHeader("Pragma","no-cache");
        response.addHeader("Cache-Control","no-cache");
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("APPLICATION/OCTET-STREAM");
        fileName=fileName+".xlsx";
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setHeader("content-disposition","attachment=\""+fileName+"\"");
        OutputStream outputStream=null;
        boolean result=false;
        try {
            outputStream=new BufferedOutputStream(response.getOutputStream());
            workbook.getWorkbook().write(outputStream);
            outputStream.flush();
            result=true;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(outputStream!=null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return  result;
    }

    public static Boolean writeExcel(String filePath,Workbook workbook){
        OutputStream outputStream=null;
        boolean result=false;
        try {
            outputStream=new BufferedOutputStream(new FileOutputStream(filePath));
            workbook.getWorkbook().write(outputStream);
            outputStream.flush();
            result=true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(outputStream!=null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return  result;
    }
}
