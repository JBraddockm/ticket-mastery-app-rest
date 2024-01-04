package org.example.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum Gender {
  MALE("Male"),
  FEMALE("Female"),
  OTHER("Other");

  private final String gender;

  Gender(String gender) {
    this.gender = gender;
  }

  @Override
  public String toString() {
    return this.gender;
  }

  @JsonValue
  public String getGender() {
    return this.gender;
  }

  public static Gender fromText(String text) {
    for (Gender r : Gender.values()) {
      if (r.getGender().equals(text)) {
        return r;
      }
    } // TODO Throw a custom exception. Throw 404.
    throw new IllegalArgumentException(
            "Invalid gender value provided. Accepted values are: "
                    + Arrays.toString(Gender.values())
                    + ". Please provide a valid gender.");
  }

}
