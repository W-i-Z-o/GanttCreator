package de.ganttcreator.excel;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Task extends Item {
    private Duration duration;
    private String key;
    private List<String> linkedTasks;
    private int assignee;

    public Task(int id, String key, String name, int outlineLvl, Duration duration, int assignee) {
        this.setId(id);
        this.setKey(key);
        this.setName(name);
        this.setOutlineLvl(outlineLvl);
        this.setDuration(duration);
        this.setLinkedTasks(new ArrayList<String>());
        this.setAssignee(assignee);

    }
    
    public Task(String key, String name, Duration duration, int assignee) {
        this(-1, key, name, 3, duration, assignee);
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public String getDurationString() {
        long seconds = duration.getSeconds();
        long s = seconds % 60;
        long m = (seconds / 60) % 60;
        long h = (seconds / (60 * 60)) % 24;
        return String.format("PT%dH%dM%dS", h, m, s);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<String> getLinkedTasks() {
        return linkedTasks;
    }

    public void setLinkedTasks(List<String> linkedTasks) {
        this.linkedTasks = linkedTasks;
    }

    public int getAssignee() {
        return assignee;
    }

    public void setAssignee(int assignee) {
        this.assignee = assignee;
    }

}
