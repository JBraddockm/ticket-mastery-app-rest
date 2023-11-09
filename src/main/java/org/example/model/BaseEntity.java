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
public class BaseEntity {
    @SequenceGenerator(
            name = "entity_id_seq",
            sequenceName = "entity_id_seq",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "entity_id_seq"
    )
    @Column(
            columnDefinition = "SERIAL"
    )
    private Long id;

    private Long insertUserId;
    private Long lastUpdateUserId;

    @Column(
            columnDefinition = "TIMESTAMP WITHOUT TIME ZONE"
    )
    private Instant insertDateTime;

    @Column(
            columnDefinition = "TIMESTAMP WITHOUT TIME ZONE"
    )
    private Instant lastUpdateDateTime;
}
