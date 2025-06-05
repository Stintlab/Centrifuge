package de.stintlab.centrifuge.endpoints.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.stintlab.centrifuge.repository.entities.StintEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StintDto {
    int counter;
    DriverDto driver;
    LocalDateTime stintStartTime;
    int laps;
    double fuelUsed;
    Duration timeDriven;
    Duration timeInPitlane;
    Duration refuelTime;
    Duration totalStintLength;
    LocalDateTime stintEndDate;
    
    public static StintDto fromEntityAndDriverDto(StintEntity entity, DriverDto driverDto){
        return StintDto.builder()
            .counter(entity.getCounter())
            .driver(driverDto)
            .stintStartTime(entity.getStintStartTime())
            .timeDriven(entity.getTimeDriven())
            .timeInPitlane(entity.getTimeInPitlane())
            .refuelTime(entity.getRefuelTime())
            .totalStintLength(entity.getTotalStintLength())
            .stintEndDate(entity.getStintEndDate())
            .build();
    }
}
