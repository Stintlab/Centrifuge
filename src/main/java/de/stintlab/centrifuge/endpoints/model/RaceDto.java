package de.stintlab.centrifuge.endpoints.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.stintlab.centrifuge.repository.entities.RaceEntity;
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
public class RaceDto {
    LocalDateTime raceStart;
    Duration raceDuration;
    double fuelTankSize;
    double refuelRate;
    Duration timeLostInPitlane;
    
    
    public static RaceDto fromEntity(RaceEntity entity){
        return RaceDto.builder()
            .raceStart(entity.getRaceStart())
            .raceDuration(entity.getRaceDuration())
            .fuelTankSize(entity.getFuelTankSize())
            .refuelRate(entity.getRefuelRate())
            .timeLostInPitlane(entity.getTimeLostInPitlane())
            .build();
    }
}
