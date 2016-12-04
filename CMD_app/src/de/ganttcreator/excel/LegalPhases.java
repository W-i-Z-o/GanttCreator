package de.ganttcreator.excel;

public enum LegalPhases {
    BUSINESS_MODELLING("Business Modelling"), REQUIREMENTS("Requirements"), DESIGN("Design"), IMPLEMENTATION(
            "Implementation"), TESTING("Testing"), DEPLOYMENT("Deployment"), CONFIGURATION_CHANGE_MANAGEMENT(
                    "Configuration & Change Management"), PROJECT_MANAGMENT(
                            "Project Managment"), ENVIRONMENT("Environment"), DOCUMENTATION("Documentation");

    private final String name;

    LegalPhases(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
