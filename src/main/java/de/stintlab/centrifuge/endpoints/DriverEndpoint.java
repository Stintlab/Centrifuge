package de.stintlab.centrifuge.endpoints;

import de.stintlab.centrifuge.endpoints.model.DriverDto;
import de.stintlab.centrifuge.endpoints.model.DriverPutDto;
import de.stintlab.centrifuge.endpoints.service.DriverService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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
    public static final String CREATE_DRIVER_ENDPOINT_URL = "/plan/{planId}/driver";
    public static final String DELETE_DRIVER_ENDPOINT_URL = "/plan/{planId}/driver/{name}";
    
    DriverService driverService;
    
    @Operation(method = "GET", summary = "get all drivers for a plan")
    @GetMapping(value = DRIVERS_ENDPOINT_URL, produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public Mono<List<DriverDto>> getDrivers(@PathVariable @NotNull UUID planId) {
        return driverService.getDriversForPlan(planId);
    }
    
    @Operation(method = "PUT", summary = "add a driver to a plan")
    @PutMapping(value = CREATE_DRIVER_ENDPOINT_URL, produces = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> putDriver(@PathVariable @NotNull UUID planId, @RequestBody @Valid DriverPutDto dto) {
        return driverService.addDriverToPlan(planId, dto);
    }
    
    @Operation(method = "DELETE", summary = "delete a driver from a plan")
    @DeleteMapping(value = DELETE_DRIVER_ENDPOINT_URL, produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteDriver(@PathVariable @NotNull UUID planId, @PathVariable @NotBlank String name) {
        return driverService.deleteDriverFromPlan(planId, name);
    }
}
