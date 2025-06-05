package de.stintlab.centrifuge.endpoints;

import de.stintlab.centrifuge.endpoints.service.DriverService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@CustomLog
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@OpenAPIDefinition(info = @Info(title = "Driver", version = "v1.0"))
public class DriverEndpoint {
    public static final String DRIVERS_ENDPOINT_URL = "/plan/{planId}/drivers";
    
    DriverService driverService;
    
    @Operation(method = "GET", summary = "get all drivers for a plan")
    @GetMapping(value = DRIVERS_ENDPOINT_URL, produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public Mono<List<String>> getDrivers(@PathVariable @NotBlank UUID planId) {
        return driverService.getDriversForPlan(planId);
    }
}
