package de.ganttcreator.excel;

public class Item {
    private int id, outlineLvl;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOutlineLvl() {
        return outlineLvl;
    }

    public void setOutlineLvl(int outlineLvl) {
        this.outlineLvl = outlineLvl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
