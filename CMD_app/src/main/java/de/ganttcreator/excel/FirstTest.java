package de.ganttcreator.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class FirstTest {

    public static void main(String[] args) throws IOException {
        File myDir = new File("C:/Users/Simon/Dropbox/DHBW/Vorlesungen/3. Semester/Softwareentwicklung/gant");
        File myFile = new File(myDir, "JIRA.xls");

        FileInputStream in = new FileInputStream(myFile);
        HSSFWorkbook wb = new HSSFWorkbook(in);
        in.close();

        HSSFSheet sheet = wb.getSheetAt(0);

        for (int i = 4; i < sheet.getLastRowNum(); i++) {
            System.out.println(sheet.getRow(i).getCell(1));
        }

        sheet.getRow(0).getCell(0).setCellValue("Deine MUDDA!");

        FileOutputStream out = new FileOutputStream(myFile);
        wb.write(out);
        out.close();
        wb.close();
    }
}
