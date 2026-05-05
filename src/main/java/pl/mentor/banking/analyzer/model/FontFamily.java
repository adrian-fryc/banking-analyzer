package pl.mentor.banking.analyzer.model;

public enum FontFamily {
    HELVETICA("Helvetica"),
    COURIER("Courier"),
    TIMES_ROMAN("Times-Roman");

    private final String technicalName;

    FontFamily(String technicalName) {
        this.technicalName = technicalName;
    }

    public String getTechnicalName() {
        return technicalName;
    }
}
