package de.stintlab.centrifuge.endpoints.service;

import de.stintlab.centrifuge.endpoints.model.DriverDto;
import de.stintlab.centrifuge.endpoints.model.PlanDto;
import de.stintlab.centrifuge.endpoints.model.RaceDto;
import de.stintlab.centrifuge.endpoints.model.StintDto;
import de.stintlab.centrifuge.repository.PlanReactiveMongoRepository;
import de.stintlab.centrifuge.repository.entities.DriverEntity;
import de.stintlab.centrifuge.repository.entities.PlanEntity;
import lombok.AccessLevel;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.util.*;
import java.util.stream.Collectors;

@CustomLog
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PlanService {
    
    PlanReactiveMongoRepository planReactiveMongoRepository;
    
    public Mono<PlanDto> getPlan(UUID id) {
        return planReactiveMongoRepository.findById(id)
            .map(planEntity -> {
                var drivers = planEntity.getDrivers().stream()
                    .map(e -> Map.entry(e.getDriverId(), DriverDto.fromEntity(e)))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                
                var race = RaceDto.fromEntity(planEntity.getRace());
                
                var stints = planEntity.getStints().stream()
                    .map(e -> StintDto.fromEntityAndDriverDto(e, drivers.get(e.getDriverId())))
                    .sorted(Comparator.comparingInt(StintDto::getCounter))
                    .toList();
                
                return PlanDto.builder()
                    .drivers(new ArrayList<>(drivers.values()))
                    .race(race)
                    .stints(stints)
                    .build();
            });
    }
    
    public Mono<String> createPlan(){
        return planReactiveMongoRepository.save(
            PlanEntity.builder()
                .planId(UUID.randomUUID())
                .build())
            .map(saved -> saved.getPlanId().toString());
    }
    
    private static class UuidTakenException extends RuntimeException {}
}
