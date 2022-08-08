import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * 使用poi生成excel文件
 * @author DAHUANG
 * @date 2022/3/19
 */
public class CreateExcel {

    @Test
    public void test01() throws Exception {
        HSSFWorkbook wb=new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("学生表");
        //行号，从0开始，以此增加，写入多少就表示要将数据插入再哪一行
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("学号");
        cell = row.createCell(1);
        cell.setCellValue("姓名");
        cell = row.createCell(2);
        cell.setCellValue("年龄");

        //生成样式对象
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        //使用sheet创建10个HSSFRow对象
        for (int i = 1; i <=10 ; i++) {
            row = sheet.createRow(i);
            cell = row.createCell(0);
            cell.setCellValue(100+i);
            cell = row.createCell(1);
            cell.setCellValue("姓名"+i);
            cell = row.createCell(2);
            cell.setCellValue(20+i);
        }

        //调用工具生成excel文件
        //目录必须创建好，文件可以不存在
        FileOutputStream os = new FileOutputStream("F:\\IdeaServerDirOutPutExcel\\student.xlsx");
        wb.write(os);

        //关闭资源
        os.flush();
        os.close();
        wb.close();
        System.out.println("create OK===========");
    }
}
