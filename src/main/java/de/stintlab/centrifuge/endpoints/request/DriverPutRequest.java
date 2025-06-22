package de.stintlab.centrifuge.endpoints.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Duration;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DriverPutRequest {
    @NotBlank
    String name;
    @Positive
    Double fuelConsumption;
    @NotNull
    Duration averageLapTime;
}
