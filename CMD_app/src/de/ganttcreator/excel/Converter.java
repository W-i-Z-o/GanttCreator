package de.ganttcreator.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class Converter {
    

    public static void main(String[] args) throws IOException {
        File myDir = new File("C:/Users/Simon/Dropbox/DHBW/Vorlesungen/3. Semester/Softwareentwicklung/gant");
        File myFile = new File(myDir, "JIRA.xls");
        
        int i, rowSprint = -1, rowLabels = -1;
        List<Sprint> sprints = new ArrayList<Sprint>();
        List<Phase> phases = new ArrayList<Phase>();

        FileInputStream in = new FileInputStream(myFile);
        HSSFWorkbook wb = new HSSFWorkbook(in);
        in.close();

        HSSFSheet sheet = wb.getSheetAt(0);

        for (i = 0; i < sheet.getRow(3).getLastCellNum(); i++) {
            switch (sheet.getRow(3).getCell(i).getStringCellValue()) {
            case "Sprint":
                rowSprint = i;
                break;
            case "Labels":
                rowLabels = i;
            }
        }

        for (i = 4; i < sheet.getLastRowNum() - 1; i++) {
            String cell;

            // read Phases
            cell = sheet.getRow(i).getCell(rowLabels).toString();
            switch (cell) {
            case "":
                break;
            default:
                Phase p = new Phase(cell);
                if (!phases.contains(p)) {
                    phases.add(p);
                }
            }

            // read Sprints
            cell = sheet.getRow(i).getCell(rowSprint).toString();
            switch (cell) {
            case "":
                break;
            default:
                Sprint s = new Sprint(cell);
                if (!sprints.contains(s)) {
                    sprints.add(s);
                }
            }
        }


        for (Sprint s : sprints) {
            System.out.println(s.getName());
        }

        System.out.println("---");

        for (Phase p : phases) {
            System.out.println(p.getName());
        }

        FileOutputStream out = new FileOutputStream(myFile);
        wb.write(out);
        out.close();
        wb.close();

        System.out.println("--- finish " + i);

    }

}
