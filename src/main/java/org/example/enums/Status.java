package org.example.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;

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

  @JsonValue
  public String getStatus() {
    return this.status;
  }

  public static Status fromText(String text) {
    for (Status r : Status.values()) {
      if (r.getStatus().equals(text)) {
        return r;
      }
    } // TODO Throw a custom exception. Throw 404.
    throw new IllegalArgumentException(
        "Invalid status value provided. Accepted values are: "
            + Arrays.toString(Status.values())
            + ". Please provide a valid status.");
  }
}
