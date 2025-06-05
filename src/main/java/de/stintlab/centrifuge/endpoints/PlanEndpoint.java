package de.stintlab.centrifuge.endpoints;

import de.stintlab.centrifuge.endpoints.model.PlanDto;
import de.stintlab.centrifuge.endpoints.service.PlanService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@CustomLog
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@OpenAPIDefinition(info = @Info(title = "Plan", version = "v1.0"))
public class PlanEndpoint {
    public static final String CREATE_PLAN_ENDPOINT_URL = "/plan";
    public static final String GET_PLAN_ENDPOINT_URL = CREATE_PLAN_ENDPOINT_URL + "/{planId}";
    
    PlanService planService;
    
    @Operation(method = "GET", summary = "get the entire race plan")
    @GetMapping(value = GET_PLAN_ENDPOINT_URL, produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public Mono<PlanDto> getPlan(@PathVariable @NotBlank UUID planId) {
        return planService.getPlan(planId);
    }
    
    @Operation(method = "POST", summary = "create a new race plan")
    @PostMapping(value = CREATE_PLAN_ENDPOINT_URL, produces = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<String> createPlan() {
        return planService.createPlan();
    }
}
