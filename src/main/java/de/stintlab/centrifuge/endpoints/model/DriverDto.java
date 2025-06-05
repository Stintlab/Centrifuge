package de.stintlab.centrifuge.endpoints.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.stintlab.centrifuge.repository.entities.DriverEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Duration;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DriverDto {
    String name;
    double fuelConsumption;
    Duration averageLapTime;
    
    public static DriverDto fromEntity(DriverEntity entity){
        return DriverDto.builder()
            .name(entity.getName())
            .fuelConsumption(entity.getFuelConsumption())
            .averageLapTime(entity.getAverageLapTime())
            .build();
    }
}
