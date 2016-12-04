package de.ganttcreator.excel;

import java.util.ArrayList;
import java.util.List;

public class Sprint extends Item implements Comparable<Sprint> {
    private List<Phase> phases;
    LegalSprints index;

    public Sprint(int id, String name, List<Phase> phases) {
        this.setId(id);
        this.setName(name);
        this.setOutlineLvl(1);
        this.setPhases(phases);
        this.setIndex(LegalSprints.valueOf(name.replaceAll(" #", "").toUpperCase()));
    }

    public Sprint(String name) {
        this(-1, name, new ArrayList<Phase>());
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

    @Override
    public int compareTo(Sprint o) {
        return this.index.ordinal() - o.index.ordinal();
    }

    public LegalSprints getIndex() {
        return index;
    }

    public void setIndex(LegalSprints index) {
        this.index = index;
    }

}
