package de.StintLab.Centrifuge.endpoints;

import com.mongodb.assertions.Assertions;
import de.StintLab.Centrifuge.testconfig.EnableMongoDbTestcontainer;
import de.stintlab.centrifuge.endpoints.PlanEndpoint;
import de.stintlab.centrifuge.endpoints.model.DriverDto;
import de.stintlab.centrifuge.endpoints.model.PlanDto;
import de.stintlab.centrifuge.endpoints.model.RaceDto;
import de.stintlab.centrifuge.endpoints.model.StintDto;
import de.stintlab.centrifuge.repository.PlanMongoRepository;
import de.stintlab.centrifuge.repository.entities.DriverEntity;
import de.stintlab.centrifuge.repository.entities.PlanEntity;
import de.stintlab.centrifuge.repository.entities.RaceEntity;
import de.stintlab.centrifuge.repository.entities.StintEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebFlux;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@SpringJUnitConfig
@AutoConfigureWebFlux
@AutoConfigureWebTestClient
@EnableMongoDbTestcontainer
class PlanEndpointTest {
    
    @Autowired
    WebTestClient webClient;
    
    @Autowired
    PlanMongoRepository planMongoRepository;
    
    @BeforeEach
    void cleanDb(){
        planMongoRepository.deleteAll().block();
    }
    
    @Test
    void createPlanWorks(){
        var result = webClient.post()
            .uri(uriBuilder -> uriBuilder
                .path(PlanEndpoint.CREATE_PLAN_ENDPOINT_URL)
                .build())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isCreated()
            .expectBody(String.class)
            .returnResult()
            .getResponseBody();
        assertThat(result).isNotBlank();
        Assertions.doesNotThrow(() -> UUID.fromString(result));
    }
    
    @Test
    void createdPlanCanBeRequested(){
        var planId = webClient.post()
            .uri(uriBuilder -> uriBuilder
                .path(PlanEndpoint.CREATE_PLAN_ENDPOINT_URL)
                .build())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isCreated()
            .expectBody(String.class)
            .returnResult()
            .getResponseBody();
        assertThat(planId).isNotBlank();
        Assertions.doesNotThrow(() -> UUID.fromString(planId));
        
        var result = webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path(PlanEndpoint.GET_PLAN_ENDPOINT_URL)
                .build(planId))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody(PlanDto.class)
            .returnResult()
            .getResponseBody();
        
        var expected = PlanDto.builder()
            .race(null)
            .drivers(new HashSet<>())
            .stints(new ArrayList<>())
            .build();
        
        assertThat(result).isNotNull()
            .usingRecursiveComparison()
            .isEqualTo(expected);
    }
    
    @Test
    void filledPlanCanBeRequested(){
        var planId = UUID.randomUUID();
        var driverId = UUID.randomUUID();
        var entity = PlanEntity.builder()
            .planId(planId)
            .stints(List.of(StintEntity.builder()
                    .counter(1)
                    .driverId(driverId)
                    .fuelUsed(94.32)
                    .laps(6)
                    .stintEndDate(LocalDateTime.now().plusMinutes(3).truncatedTo(ChronoUnit.MILLIS))
                    .stintStartTime(LocalDateTime.now().minusDays(1).truncatedTo(ChronoUnit.MILLIS))
                    .refuelTime(Duration.ofSeconds(12))
                    .timeDriven(Duration.ofDays(2))
                    .timeInPitlane(Duration.ofMinutes(1))
                    .totalStintLength(Duration.ofHours(40))
                .build()))
            .race(RaceEntity.builder()
                .fuelTankSize(99.56)
                .raceDuration(Duration.ofDays(120))
                .raceStart(LocalDateTime.now().minusYears(1).truncatedTo(ChronoUnit.MILLIS))
                .timeLostInPitlane(Duration.ofSeconds(50))
                .refuelRate(40)
                .build())
            .drivers(Set.of(
                DriverEntity.builder()
                    .name("driver 1")
                    .averageLapTime(Duration.ofSeconds(21))
                    .fuelConsumption(1.12)
                    .driverId(driverId)
                    .build()))
            .build();
        planMongoRepository.save(entity).block();
        
        var result = webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path(PlanEndpoint.GET_PLAN_ENDPOINT_URL)
                .build(planId))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody(PlanDto.class)
            .returnResult()
            .getResponseBody();
        var driverDto = DriverDto.fromEntity(entity.getDrivers().iterator().next());
        
        var expected = PlanDto.builder()
            .race(RaceDto.fromEntity(entity.getRace()))
            .drivers(Set.of(driverDto))
            .stints(entity.getStints().stream().map(s -> StintDto.fromEntityAndDriverDto(s, driverDto)).collect(Collectors.toList()))
            .build();
        
        assertThat(result).isNotNull()
            .usingRecursiveComparison()
            .ignoringCollectionOrder()
            .isEqualTo(expected);
    }
}



