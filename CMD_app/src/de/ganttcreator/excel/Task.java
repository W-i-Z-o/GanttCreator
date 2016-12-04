package de.ganttcreator.excel;

import java.time.Duration;

public class Task extends Item {
    Duration duration;

    public Task(int id, String name, int outlineLvl, Duration duration) {
        this.setId(id);
        this.setName(name);
        this.setOutlineLvl(outlineLvl);
        this.duration = duration;
    }
    
    public Task(int id, String name, int outlineLvl){
        this(id,name,outlineLvl,null);
    }

    public Task(String name, Duration duration) {
        this(-1, name, 3, duration);
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
}
