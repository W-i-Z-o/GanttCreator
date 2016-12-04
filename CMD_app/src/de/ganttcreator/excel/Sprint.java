package de.ganttcreator.excel;

import java.util.ArrayList;
import java.util.List;

public class Sprint extends Item {
    private List<Phase> phases;

    public Sprint(int id, String name, List<Phase> phases) {
        this.setId(id);
        this.setName(name);
        this.setOutlineLvl(1);
        phases = new ArrayList<Phase>();
    }

    public Sprint(String name) {
        this(-1, name, null);
    }

    public Sprint(String name, List<Phase> phases) {
        this(-1, name, phases);
    }

    public List<Phase> getPhases() {
        return phases;
    }

    public void setPhases(List<Phase> phases) {
        this.phases = phases;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Sprint))
            return false;

        Sprint that = (Sprint) o;
        return this.getName().equals(that.getName());
    }

}
