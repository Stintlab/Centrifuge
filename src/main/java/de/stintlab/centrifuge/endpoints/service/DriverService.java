package de.stintlab.centrifuge.endpoints.service;

import de.stintlab.centrifuge.repository.PlanReactiveMongoRepository;
import de.stintlab.centrifuge.repository.entities.DriverEntity;
import lombok.AccessLevel;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@CustomLog
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DriverService {
    
    PlanReactiveMongoRepository planReactiveMongoRepository;
    
    
    public Mono<List<String>> getDriversForPlan(UUID id) {
        return planReactiveMongoRepository.findById(id)
            .map(plan -> plan.getDrivers().stream()
                .map(DriverEntity::getName).toList());
    }
}
