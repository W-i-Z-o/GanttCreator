package de.ganttcreator.excel;

import java.util.ArrayList;
import java.util.List;

public class Phase extends Item implements Comparable<Phase> {
    private List<Task> tasks;
    private LegalPhases index;

    public Phase(int id, String name, List<Task> task, LegalPhases index) {
        this.setId(id);
        this.setName(name);
        this.setOutlineLvl(2);
        this.setTasks(task);
        this.setIndex(index);
    }

    public Phase(String name, LegalPhases index) {
        this(-1, name, new ArrayList<Task>(), index);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public LegalPhases getIndex() {
        return index;
    }

    public void setIndex(LegalPhases index) {
        this.index = index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Phase))
            return false;

        Phase that = (Phase) o;
        return this.getName().equals(that.getName());
    }

    @Override
    public int compareTo(Phase o) {
        return this.index.ordinal() - o.index.ordinal();
    }
}
