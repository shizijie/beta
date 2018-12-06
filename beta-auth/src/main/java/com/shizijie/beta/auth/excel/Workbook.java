package com.shizijie.beta.auth.excel;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Slf4j
public class Workbook {
    private int rowAccess=1000;

    private SXSSFWorkbook workbook=null;

    private CellStyle alignLeft;

    public Workbook(){
        this.workbook=new SXSSFWorkbook(rowAccess);
        this.alignLeft=this.cellTextAlignLeft();
    }

    public CellStyle cellTextAlignLeft() {
        CellStyle cellStyle=this.workbook.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
        return cellStyle;
    }

    public Workbook(File file){
        try {
            this.workbook=new SXSSFWorkbook(new XSSFWorkbook(file),rowAccess);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        this.alignLeft=this.cellTextAlignLeft();
    }

    public Workbook(InputStream inputStream){
        try {
            this.workbook=new SXSSFWorkbook(new XSSFWorkbook(inputStream),rowAccess);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.alignLeft=this.cellTextAlignLeft();
    }

    /**
     * 创建sheet页
     * @param sheetName
     * @return
     */
    public Sheet createSheet(String sheetName){
        return this.workbook.createSheet(sheetName);
    }

    /**
     * 创建sheet头
     * @param sheet
     * @param cellTitle
     */
    public void createRowTitle(Sheet sheet,String[] cellTitle){
        Row titleRow=sheet.createRow(0);
        for (int i=0;i<cellTitle.length;i++){
           sheet.setColumnWidth(i,4000);
           Cell cell=titleRow.createCell(i);
           cell.setCellType(XSSFCell.CELL_TYPE_STRING);
           cell.setCellValue(cellTitle[i]);
        }
    }

    /**
     * sheet添加内容
     * @param sheet
     * @param startRow
     * @param cellName
     * @param dataList
     */
    public void createContent(Sheet sheet, int startRow, String[] cellName, List<Map<String,Object>> dataList){
        if(dataList!=null&&dataList.size()>0 ){
            int size=dataList.size();
            for(int i=0; i<size;i++){
                Row row=sheet.createRow(startRow+i);
                Map map=dataList.get(i);
                int length=cellName.length;
                for(int j=0;j<length;j++){
                    Cell cell=row.createCell(j);
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    Object cellValue=map.get(cellName[j]);
                    cell.setCellValue(cellValue==null?"":cellValue.toString());
                    cell.setCellStyle(alignLeft);
                }
                if(i%rowAccess==0){
                    try {
                        ((SXSSFSheet)sheet).flushRows();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 获取当前sheet
     * @param sheetName
     * @return
     */
    public Sheet getSheet(String sheetName){
        return this.workbook.getSheet(sheetName);
    }

    /**
     * 获取当前workbook
     * @return
     */
    public SXSSFWorkbook getWorkbook(){
        return this.workbook;
    }
}
