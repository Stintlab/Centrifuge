package de.stintlab.centrifuge.entities;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RacePlanEntity {
    @NotNull
    UUID id;
    @NotNull
    RaceEntity race;
    @NotNull
    List<DriverEntity> drivers;
    @Nullable
    List<StintEntity> stints;
}
