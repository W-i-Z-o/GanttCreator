package de.ganttcreator.excel;

import java.util.ArrayList;
import java.util.List;

public class Phase extends Item implements Comparable<Phase> {
    private List<Task> tasks;
    private int index;

    public Phase(int id, String name, List<Task> task) {
        this.setId(id);
        this.setName(name);
        this.setOutlineLvl(2);
        this.setTasks(task);
        this.setIndex(LegalPhases.valueOf(name.toUpperCase()).ordinal());
    }

    public Phase(String name) {
        this(-1, name, new ArrayList<Task>());
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
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
        return this.index - o.index;
    }
}
