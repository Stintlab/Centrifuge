package de.stintlab.centrifuge.endpoints.response;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class StintGetResponse {
    int counter;
    String driver;
    Date startTime;
    Date endTime;
    int laps;
    double fuelUsed;

    Duration timeDriven;
    Duration timeInPitLane;
    Duration refuelTime;
    Duration totalLength;
}
