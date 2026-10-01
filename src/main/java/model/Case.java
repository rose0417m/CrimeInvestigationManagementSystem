package model;

import java.time.LocalDate;

public class Case {

    private int caseId;
    private String caseNumber;
    private String title;
    private String description;
    private String status;
    private LocalDate dateCreated;
    private int detectiveId;

    public Case() {
    }

    public Case(int caseId, String caseNumber, String title,
                String description, String status,
                LocalDate dateCreated, int detectiveId) {

        this.caseId = caseId;
        this.caseNumber = caseNumber;
        this.title = title;
        this.description = description;
        this.status = status;
        this.dateCreated = dateCreated;
        this.detectiveId = detectiveId;
    }

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getDetectiveId() {
        return detectiveId;
    }

    public void setDetectiveId(int detectiveId) {
        this.detectiveId = detectiveId;
    }
}