package org.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Data
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
      columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
  private Instant createdOn;

  @Column(name = "created_by", updatable = false, nullable = false)
  private Long createdBy;

  @Column(name = "updated_by")
  private Long updatedBy;

  @Column(name = "updated_on", columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
  private Instant updatedOn;

  private boolean isDeleted = false;

  @PrePersist
  public void prePersist() {
    this.createdBy = 1L;
    this.createdOn = Instant.now();
  }

  @PreUpdate
  private void preUpdate() {
    this.updatedOn = Instant.now();
    this.updatedBy = 1L;
  }
}
