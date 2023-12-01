package org.example.model;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.common.UserPrincipal;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
  //    @SequenceGenerator(
  //            name = "entity_id_seq",
  //            sequenceName = "entity_id_seq",
  //            allocationSize = 1
  //    )
  //    @Id
  //    @GeneratedValue(
  //            strategy = GenerationType.SEQUENCE,
  //            generator = "entity_id_seq"
  //    )
  //    @Column(
  //            columnDefinition = "SERIAL"
  //    )
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false, updatable = false)
  private Long id;

  @Column(
      name = "created_on",
      updatable = false,
      nullable = false,
      columnDefinition = "TIMESTAMP WITHOUT TIME ZONE",
      length = 50)
  @CreatedDate
  private Instant createdOn;

  @Column(name = "created_by", updatable = false, nullable = false, length = 50)
  @CreatedBy
  private Long createdBy;

  @Column(name = "updated_by")
  @LastModifiedBy
  private Long updatedBy;

  @Column(name = "updated_on", columnDefinition = "TIMESTAMP WITHOUT TIME ZONE", length = 50)
  @LastModifiedDate
  private Instant updatedOn;

  @Column(name ="deleted_on", columnDefinition = "TIMESTAMP WITHOUT TIME ZONE", length = 50)
  private Instant deletedOn;

  @Column(name = "deleted_by", length = 50)
  private Long deletedBy;

  @Column(name = "is_deleted", length = 50)
  private Boolean isDeleted = false;

  @PrePersist
  @PreUpdate
  public void beforeAnyUpdate() {

    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    UserPrincipal user = (UserPrincipal) authentication.getPrincipal();

    if (isDeleted != null && isDeleted) {

      if (deletedBy == null) {
        deletedBy = user.getId();
      }

      if (getDeletedOn() == null) {
        deletedOn = Instant.now();
      }
    }
  }
}
