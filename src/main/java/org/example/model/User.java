package org.example.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enums.Gender;

@NoArgsConstructor
@Data
@Entity
@Table(
        name = "users"
)
public class User extends BaseEntity{
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String confirmPassword;
    private String phoneNumber;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    private Role role;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private boolean isEnabled;

}
