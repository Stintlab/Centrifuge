package de.StintLab.Centrifuge.repository;

import de.StintLab.Centrifuge.testconfig.EnableMongoDbTestcontainer;
import de.stintlab.centrifuge.repository.PlanReactiveMongoRepository;
import de.stintlab.centrifuge.repository.entities.DriverEntity;
import de.stintlab.centrifuge.repository.entities.PlanEntity;
import de.stintlab.centrifuge.repository.entities.RaceEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@SpringJUnitConfig
@EnableMongoDbTestcontainer
class PlanReactiveMongoRepositoryTest {
    
    @Autowired
    PlanReactiveMongoRepository planReactiveMongoRepository;
    
    @Autowired
    ReactiveMongoTemplate reactiveMongoTemplate;
    
    @Test
    void testStore() {
        DriverEntity driver = DriverEntity.builder()
            .driverId(UUID.randomUUID())
            .averageLapTime(Duration.of(8, ChronoUnit.MINUTES))
            .fuelConsumption(13.65)
            .name("Testdriver")
            .build();
        
        RaceEntity race = RaceEntity.builder()
            .fuelTankSize(98.64)
            .raceDuration(Duration.of(24, ChronoUnit.HOURS))
            .raceStart(LocalDateTime.of(2025, 6, 7, 12, 0, 0, 0))
            .refuelRate(2.23)
            .timeLostInPitlane(Duration.of(25, ChronoUnit.SECONDS))
            .build();
        
        PlanEntity plan = PlanEntity.builder()
            .planId(UUID.randomUUID())
            .drivers(Set.of(driver))
            .race(race)
            .build();
        
        var mono = planReactiveMongoRepository.save(plan);
        var saved = assertDoesNotThrow(() -> mono.block());
        assertThat(saved).isEqualTo(plan);
    }
    
    @Test
    void testFetch() {
        DriverEntity driver = DriverEntity.builder()
            .driverId(UUID.randomUUID())
            .averageLapTime(Duration.of(8, ChronoUnit.MINUTES))
            .fuelConsumption(13.65)
            .name("Testdriver")
            .build();
        
        RaceEntity race = RaceEntity.builder()
            .fuelTankSize(98.64)
            .raceDuration(Duration.of(24, ChronoUnit.HOURS))
            .raceStart(LocalDateTime.of(2025, 6, 7, 12, 0, 0, 0))
            .refuelRate(2.23)
            .timeLostInPitlane(Duration.of(25, ChronoUnit.SECONDS))
            .build();
        
        PlanEntity plan = PlanEntity.builder()
            .planId(UUID.randomUUID())
            .drivers(Set.of(driver))
            .race(race)
            .build();
        
        var saveMono = reactiveMongoTemplate.save(plan);
        assertDoesNotThrow(() -> saveMono.block());
        
        var fetchMono = planReactiveMongoRepository.findById(plan.getPlanId());
        var db = assertDoesNotThrow(() -> fetchMono.block());
        assertThat(db).isEqualTo(plan);
    }
}
