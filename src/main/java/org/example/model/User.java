package org.example.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enums.Gender;

@NoArgsConstructor
@Data
@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "user_username_unique",
                        columnNames = "username"
                )
        }
)
public class User extends BaseEntity{
    private String firstName;
    private String lastName;
    @Column(
            name = "username"
    )
    private String username;
    private String password;
    private String confirmPassword;
    private String phoneNumber;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            foreignKey = @ForeignKey(
                    name = "users_role_id_fk"
            )
    )
    private Role role;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private boolean isEnabled;

}
