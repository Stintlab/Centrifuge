package de.stintlab.centrifuge.endpoints.service;

import de.stintlab.centrifuge.endpoints.model.DriverDto;
import de.stintlab.centrifuge.endpoints.model.DriverPutDto;
import de.stintlab.centrifuge.repository.PlanMongoRepository;
import lombok.AccessLevel;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@CustomLog
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DriverService {
    
    PlanMongoRepository planMongoRepository;
    
    
    public Mono<List<DriverDto>> getDriversForPlan(UUID id) {
        return planMongoRepository.findById(id)
            .map(plan -> plan.getDrivers().stream()
                .map(DriverDto::fromEntity).toList());
    }
    
    public Mono<Void> addDriverToPlan(UUID planId, DriverPutDto dto) {
        return planMongoRepository.findById(planId)
            .flatMap(plan -> {
                plan.getDrivers().add(dto.toEntity());
                return planMongoRepository.save(plan);
            })
            .then();
    }
    
    public Mono<Void> deleteDriverFromPlan(UUID planId, String name) {
        return planMongoRepository.findById(planId)
            .flatMap(plan -> {
                plan.setDrivers(plan.getDrivers().stream()
                    .filter(d -> !d.getName().equals(name))
                    .collect(Collectors.toSet()));
                return planMongoRepository.save(plan);
            })
            .then();
    }
}
