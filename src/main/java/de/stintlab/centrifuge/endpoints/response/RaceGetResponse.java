package de.stintlab.centrifuge.endpoints.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.Duration;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RaceGetResponse {
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

