package com.hzh.crm.commons.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * @author DAHUANG
 * @date 2022/3/21
 */
public class HSSFUtils {
    /**
     * 判断excel表格中所对应的数据的类型并转换成String
     * @param cell
     * @return
     */
    public static String getCellValueForStr(HSSFCell cell){
        String ret="";
        if (cell.getCellType()==HSSFCell.CELL_TYPE_STRING){
            ret=cell.getStringCellValue();
        }else if (cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
            ret=cell.getNumericCellValue()+"";
        }else if (cell.getCellType()==HSSFCell.CELL_TYPE_BOOLEAN){
            ret=cell.getBooleanCellValue()+"";
        }else if (cell.getCellType()==HSSFCell.CELL_TYPE_FORMULA){
            ret=cell.getCellFormula();
        }else {
            ret="";
        }


        return ret;
    }
}
