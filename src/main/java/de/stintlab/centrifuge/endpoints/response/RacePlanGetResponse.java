package de.stintlab.centrifuge.endpoints.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RacePlanGetResponse {
    @NotNull
    UUID id;
    @NotNull
    RaceGetResponse race;
    @NotNull
    Collection<DriverGetResponse> drivers;
    @NotNull
    RaceStatisticsGetResponse statistics;
    @NotNull
    List<StintGetResponse> stints;
}
