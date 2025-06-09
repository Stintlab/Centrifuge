package de.stintlab.centrifuge.repository.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;

import java.time.Duration;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DriverEntity {
    UUID driverId;
    String name;
    double fuelConsumption;
    Duration averageLapTime;
}
