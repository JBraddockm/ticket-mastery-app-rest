package org.example.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.enums.Gender;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(
    name = "users",
    uniqueConstraints = {
      @UniqueConstraint(name = "user_username_unique", columnNames = "username")
    })
public class User extends BaseEntity {
  private String firstName;
  private String lastName;

  @Column(name = "username")
  private String username;

  @Column(name = "email")
  private String email;

  private String password;

  // TODO Remove confirmPassword field.
  private String confirmPassword;

  private String phoneNumber;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(foreignKey = @ForeignKey(name = "users_role_id_fk"))
  private Role role;

  @Enumerated(EnumType.STRING)
  private Gender gender;

  private boolean isEnabled;
}
