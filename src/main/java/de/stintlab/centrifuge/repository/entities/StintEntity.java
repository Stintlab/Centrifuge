package de.stintlab.centrifuge.repository.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StintEntity {
    int counter;
    UUID driverId;
    LocalDateTime stintStartTime;
    int laps;
    double fuelUsed;
    Duration timeDriven;
    Duration timeInPitlane;
    Duration refuelTime;
    Duration totalStintLength;
    LocalDateTime stintEndDate;
}
