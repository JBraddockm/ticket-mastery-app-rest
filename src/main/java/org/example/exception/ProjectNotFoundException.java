package org.example.exception;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(String message) {
        super(String.format("Project with the code %s could not be found", message));
    }
}
