package de.stintlab.centrifuge.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Duration;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StintEntity {
    int counter;
    String driver;
    Date startTime;
    Date endTime;
    int laps;
    double fuelUsed;

    Duration timeDriven;
    Duration timeInPitLane;
    Duration refuelTime;
    Duration totalLength;

    StintActualValuesEntity actualValues;
}
