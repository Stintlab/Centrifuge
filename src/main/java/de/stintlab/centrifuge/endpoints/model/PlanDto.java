package de.stintlab.centrifuge.endpoints.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.stintlab.centrifuge.repository.entities.RaceEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlanDto {
    List<DriverDto> drivers;
    RaceDto race;
    List<StintDto> stints;
}
