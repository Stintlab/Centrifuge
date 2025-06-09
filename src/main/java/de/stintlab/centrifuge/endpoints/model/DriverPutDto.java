package de.stintlab.centrifuge.endpoints.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.stintlab.centrifuge.repository.entities.DriverEntity;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DriverPutDto {
    
    @NotBlank
    String name;
    @Positive
    Double fuelConsumption;
    @NotNull
    Duration averageLapTime;
    
    public DriverEntity toEntity(){
        return DriverEntity.builder()
            .name(name)
            .fuelConsumption(fuelConsumption)
            .averageLapTime(averageLapTime)
            .build();
    }
}
