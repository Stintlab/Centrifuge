package de.stintlab.centrifuge.repository.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RaceEntity {
    LocalDateTime raceStart;
    Duration raceDuration;
    double fuelTankSize;
    double refuelRate;
    Duration timeLostInPitlane;
}
