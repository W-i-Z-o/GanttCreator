package de.ganttcreator.excel;

import java.util.ArrayList;
import java.util.List;

public class Phase extends Item {
    private List<Task> tasks;

    public Phase(int id, String name) {
        this.setId(id);
        this.setName(name);
        this.setOutlineLvl(2);
        tasks = new ArrayList<Task>();
    }

    public Phase(String name) {
        this(-1, name);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
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
}
