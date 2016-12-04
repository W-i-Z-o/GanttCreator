package de.ganttcreator.excel;

public enum LegalSprints {
    // @formatter:off
    ELAB1("Elab #1", "2016-11-03T12:00:00", "2016-11-10T12:00:00"), 
    ELAB2("Elab #2", "2016-11-10T12:00:00", "2016-11-17T12:00:00"), 
    CONST1("Const #1", "2016-11-17T12:00:00", "2016-11-24T12:00:00"), 
    CONST2("Const #2", "2016-11-24T12:00:00", "2016-12-01T12:00:00"), 
    CONST3("Const #3", "2016-12-01T12:00:00", "2016-12-08T12:00:00"), 
    CONST4("Const #4", "2016-12-08T12:00:00", "2016-12-15T12:00:00");
    // @formatter:on

    private final String name, start, end;

    LegalSprints(String name, String start, String end) {
        this.name = name;
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

}
