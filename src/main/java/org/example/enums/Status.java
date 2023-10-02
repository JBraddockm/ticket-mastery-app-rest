package org.example.enums;

public enum Status {
    OPEN("Open"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
