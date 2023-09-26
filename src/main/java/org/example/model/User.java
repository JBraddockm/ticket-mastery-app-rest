package org.example.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enums.Gender;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class User extends BaseEntity{
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String phoneNumber;
    private Role role;
    private Gender gender;
    private boolean isEnabled;


    public User(Long id, Long insertUserId, Long lastUpdateUserId, LocalDateTime insertDateTime, LocalDateTime lastUpdateDateTime, String firstName, String lastName, String userName, String password, String phoneNumber, Role role, Gender gender, boolean isEnabled) {
        super(id, insertUserId, lastUpdateUserId, insertDateTime, lastUpdateDateTime);
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.gender = gender;
        this.isEnabled = isEnabled;
    }
}
