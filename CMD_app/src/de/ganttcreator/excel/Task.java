package de.ganttcreator.excel;

import java.time.Duration;

public class Task extends Item {
    Duration duration;
    String key;

    public Task(int id, String key, String name, int outlineLvl, Duration duration) {
        this.setId(id);
        this.setKey(key);
        this.setName(name);
        this.setOutlineLvl(outlineLvl);
        this.duration = duration;
    }
    
    public Task(int id, String key, String name, int outlineLvl) {
        this(id, key, name, outlineLvl, null);
    }

    public Task(String key, String name, Duration duration) {
        this(-1, key, name, 3, duration);
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
}
