package org.example.exception;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(String message) {
        super(String.format("Task with the id %s could not be found", message));
    }
}
