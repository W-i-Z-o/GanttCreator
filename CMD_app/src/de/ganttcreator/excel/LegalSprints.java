package de.ganttcreator.excel;

public enum LegalSprints {

    ELAB1("Elab #1"), ELAB2("Elab #2"), CONST1("Const #1"), CONST2("Const #2"), CONST3("Const #3"), CONST4("Const #4");

    private final String name;

    LegalSprints(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
