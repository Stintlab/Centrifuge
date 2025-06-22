package de.stintlab.centrifuge.endpoints;

import de.stintlab.centrifuge.endpoints.request.DriverPutRequest;
import de.stintlab.centrifuge.endpoints.request.RacePutRequest;
import de.stintlab.centrifuge.entities.RacePlanEntity;
import de.stintlab.centrifuge.services.RacePlanService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RacePlanEndpoint {
    private static final String BASE = "/plans";
    private static final String POST_RACE_PLAN = "/plans";
    private static final String GET_RACE_PLAN = "/plans/{id}";
    private static final String PUT_RACE = GET_RACE_PLAN + "/race";
    private static final String PUT_DRIVERS = GET_RACE_PLAN + "/drivers";

    private final RacePlanService racePlanService;

    @GetMapping(GET_RACE_PLAN)
    @ResponseStatus(HttpStatus.OK)
    public Mono<RacePlanEntity> GetRacePlan(@PathVariable UUID id) {
        return racePlanService.GetById(id);
    }

    @PostMapping(POST_RACE_PLAN)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<RacePlanEntity> PostRacePlan() {
        return racePlanService.CreateEmptyRacePlan();
    }

    @PutMapping(PUT_DRIVERS)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> PutDrivers(@PathVariable UUID id, @RequestBody @Valid DriverPutRequest[] request) {
        return Mono.empty();
    }

    @PutMapping(PUT_RACE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> PutRace(@PathVariable UUID id, @RequestBody @Valid RacePutRequest request) {
        return Mono.empty();
    }
}
