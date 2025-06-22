package de.stintlab.centrifuge.entities;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Duration;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RaceEntity {
    @Nullable
    Date start;
    @Nullable
    Duration duration;
    @Positive
    double fuelTankSize;
    @Positive
    double refuelRate;
    @PositiveOrZero
    int timeLostServicingCar;
}
