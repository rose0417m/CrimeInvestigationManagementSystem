package model;

import java.time.LocalDate;

public class Evidence {

    private int evidenceId;
    private int caseId;
    private String description;
    private String type;
    private String locationFound;
    private LocalDate dateCollected;

    public Evidence() {
    }

    public Evidence(int evidenceId, int caseId,
                    String description, String type,
                    String locationFound,
                    LocalDate dateCollected) {

        this.evidenceId = evidenceId;
        this.caseId = caseId;
        this.description = description;
        this.type = type;
        this.locationFound = locationFound;
        this.dateCollected = dateCollected;
    }

    public int getEvidenceId() {
        return evidenceId;
    }

    public void setEvidenceId(int evidenceId) {
        this.evidenceId = evidenceId;
    }

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocationFound() {
        return locationFound;
    }

    public void setLocationFound(String locationFound) {
        this.locationFound = locationFound;
    }

    public LocalDate getDateCollected() {
        return dateCollected;
    }

    public void setDateCollected(LocalDate dateCollected) {
        this.dateCollected = dateCollected;
    }
}