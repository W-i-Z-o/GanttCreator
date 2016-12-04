package de.ganttcreator.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class Converter {
    private File myFile;
    private int rowHead;
    private int colSprint, colLabels, colType, colSummary, colOrigEstimate, colKey, colLinkedIssues;
    private List<Sprint> sprints;
    private HSSFWorkbook wb;
    private HSSFSheet sheet;
    private Map<String, Integer> taskToId = new HashMap<String, Integer>();

    public Converter(File myFile, int rowHead, List<Sprint> sprints) {
        this.setMyFile(myFile);
        this.setRowHead(rowHead);
        this.setSprints(sprints);

        this.initConverting();
    }

    public Converter(File myFile, int rowHead) {
        this(myFile, rowHead, new ArrayList<Sprint>());
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
            case "Key":
                colKey = i;
                break;
            case "Linked Issues":
                colLinkedIssues = i;
            }
        }
    }

    public void convertData() {
        boolean legalSprintAndLabel;

        for (int i = rowHead + 1; i < sheet.getLastRowNum() - 1; i++) {
            String cell, sprint, labels, type, summary, key;
            String[] linkedIssues;
            long origEst;
            labels = sheet.getRow(i).getCell(colLabels).toString();
            sprint = sheet.getRow(i).getCell(colSprint).toString();
            summary = sheet.getRow(i).getCell(colSummary).toString();
            key = sheet.getRow(i).getCell(colKey).toString();
            origEst = (long) sheet.getRow(i).getCell(colOrigEstimate).getNumericCellValue();

            // This is useless, because you cant find out how it is
            // linked:
            // related, blocked, ect...
            String li = sheet.getRow(i).getCell(colLinkedIssues).toString();
            if (!li.equals("")) {
                linkedIssues = li.split(", ");

            } else {
                linkedIssues = new String[0];
            }

            // TODO: Tasks über mehrere Sprints, mit mehrern Labels werden
            // ignoriert

            try {
                LegalSprints.valueOf(sprint.replaceAll(" #", "").toUpperCase()).ordinal();
                LegalPhases.valueOf(labels.toUpperCase()).ordinal();
                legalSprintAndLabel = true;
            } catch (IllegalArgumentException e) {
                legalSprintAndLabel = false;
            }

            if (legalSprintAndLabel) {
                // handle Sprint informations
                Sprint s = new Sprint(sprint);
                if (!sprints.contains(s)) {
                    sprints.add(s);
                }
                s = sprints.get(sprints.indexOf(s));

                // handle Phase informations
                Phase p = new Phase(labels);
                if (!s.getPhases().contains(p)) {
                    s.getPhases().add(p);
                }

                // handle Task Informations
                // switch-Statement is to fix our fucke up first Sprint
                // normally you just use origEst from Jira

                switch (key) {
                case "TESB416-46":
                    origEst = 30 * 60;
                    break;
                case "TESB416-47":
                    origEst = 30 * 60;
                    break;
                case "TESB416-48":
                    origEst = 60 * 60;
                    break;
                case "TESB416-49":
                    origEst = 120 * 60;
                    break;
                }

                p = s.getPhases().get(s.getPhases().indexOf(p));
                Task t = new Task(key, summary, Duration.ofSeconds(origEst));

                if (!linkedIssues.equals("")) {
                    for (String issue : linkedIssues) {
                        t.getLinkedTasks().add(issue);
                    }

                    p.getTasks().add(t);
                }
            }
        }

        // Sort and allocate IDs for further use in XML
        System.out.println("--- Sprints:");

        int count = 0;
        Collections.sort(sprints);
        for (Sprint s : sprints) {
            System.out.println(s.getName());

            count++;
            s.setId(count);
            Collections.sort(s.getPhases());

            for (Phase p : s.getPhases()) {
                System.out.println("     " + p.getName());
                count++;
                p.setId(count);
                for (Task t : p.getTasks()) {
                    System.out.println(
                            "          " + t.getName() + ", " + t.getDuration() + ", linked: "
                                    + t.getLinkedTasks().size() + t.getLinkedTasks());
                    count++;
                    t.setId(count);
                    taskToId.put(t.getKey(), t.getId());
                }
            }
        }
    }

    public void endConverting() {
        try {
            // Write Data back in Excel, actualla not neede
            // FileOutputStream out;
            // out = new FileOutputStream(myFile);
            // wb.write(out);
            // out.close();
            XmlGenerator xml = new XmlGenerator(this);
            xml.generateXml();
            wb.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("--- finish");
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

    public Map<String, Integer> getTaskToId() {
        return taskToId;
    }

    public void setTaskToId(Map<String, Integer> taskToId) {
        this.taskToId = taskToId;
    }

}
