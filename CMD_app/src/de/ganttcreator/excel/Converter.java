package de.ganttcreator.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class Converter {
    private File myFile;
    private int rowHead;
    private int colSprint, colLabels, colType, colSummary, colOrigEstimate;
    private List<Sprint> sprints;
    private HSSFWorkbook wb;
    private HSSFSheet sheet;

    public Converter(File myFile, int rowHead, int colSprint, int colLabels, int colType, int colSummary,
            int colOrigEstimate, List<Sprint> sprints) {
        this.setMyFile(myFile);
        this.setRowHead(rowHead);
        this.setColSprint(colSprint);
        this.setColLabels(colLabels);
        this.setColType(colType);
        this.setColSummary(colSummary);
        this.setColOrigEstimate(colOrigEstimate);
        this.setSprints(sprints);

        this.initConverting();
    }

    public Converter(File myFile, int rowHead) {
        this(myFile, rowHead, -1, -1, -1, -1, -1, new ArrayList<Sprint>());
    }
    
    public void doConverting() {
        this.setLabels();
        this.convertData();
        this.endConverting();
    }

    public void initConverting() {
        // File in HSSFWorkbook schreiben
        try {
            FileInputStream in = new FileInputStream(myFile);
            wb = new HSSFWorkbook(in);
            in.close();
            sheet = wb.getSheetAt(0);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void setLabels() {
        for (int i = 0; i < this.sheet.getRow(rowHead).getLastCellNum(); i++) {
            switch (sheet.getRow(3).getCell(i).getStringCellValue()) {
            case "Sprint":
                colSprint = i;
                break;
            case "Labels":
                colLabels = i;
                break;
            case "Issue Type":
                colType = i;
                break;
            case "Summary":
                colSummary = i;
                break;
            case "Original Estimate":
                colOrigEstimate = i;
                break;
            }
        }
    }

    public void convertData() {
        List<Phase> phases = new ArrayList<Phase>();
        for (int i = rowHead + 1; i < sheet.getLastRowNum() - 1; i++) {
            String cell;

            // read Phases
            cell = sheet.getRow(i).getCell(colLabels).toString();
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
            cell = sheet.getRow(i).getCell(colSprint).toString();
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

        System.out.println("--- Sprints:");

        for (Sprint s : sprints) {
            System.out.println(s.getName());
        }

        System.out.println("--- Phases:");

        for (Phase p : phases) {
            System.out.println(p.getName());
        }
    }

    public void endConverting() {
        try {
            // Write Data back in Excel, actualla not neede
            // FileOutputStream out;
            // out = new FileOutputStream(myFile);
            // wb.write(out);
            // out.close();
            wb.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("--- finish ");
    }

    public static void main(String[] args) throws IOException {
        File myDir = new File("C:/Users/Simon/Dropbox/DHBW/Vorlesungen/3. Semester/Softwareentwicklung/gant");
        File myFile = new File(myDir, "JIRA.xls");

        Converter c = new Converter(myFile, 3);
        c.doConverting();
    }

    public File getMyFile() {
        return myFile;
    }

    public void setMyFile(File myFile) {
        this.myFile = myFile;
    }

    public int getRowHead() {
        return rowHead;
    }

    public void setRowHead(int rowHead) {
        this.rowHead = rowHead;
    }

    public int getColSprint() {
        return colSprint;
    }

    public void setColSprint(int colSprint) {
        this.colSprint = colSprint;
    }

    public int getColLabels() {
        return colLabels;
    }

    public void setColLabels(int colLabels) {
        this.colLabels = colLabels;
    }

    public int getColType() {
        return colType;
    }

    public void setColType(int colType) {
        this.colType = colType;
    }

    public int getColSummary() {
        return colSummary;
    }

    public void setColSummary(int colSummary) {
        this.colSummary = colSummary;
    }

    public int getColOrigEstimate() {
        return colOrigEstimate;
    }

    public void setColOrigEstimate(int colOrigEstimate) {
        this.colOrigEstimate = colOrigEstimate;
    }

    public List<Sprint> getSprints() {
        return sprints;
    }

    public void setSprints(List<Sprint> sprints) {
        this.sprints = sprints;
    }

}
