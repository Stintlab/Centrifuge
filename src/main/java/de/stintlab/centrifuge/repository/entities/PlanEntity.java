package de.stintlab.centrifuge.repository.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = PlanEntity.COLLECTION_NAME)
public class PlanEntity {
    public static final String COLLECTION_NAME = "plans";
    
    @Id
    UUID planId;
    Set<DriverEntity> drivers;
    RaceEntity race;
    Set<StintEntity> stints;
    
    @Builder.Default
    @EqualsAndHashCode.Exclude
    LocalDateTime created = LocalDateTime.now(ZoneOffset.UTC);
}


