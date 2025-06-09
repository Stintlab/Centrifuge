package de.StintLab.Centrifuge.endpoints;

import de.StintLab.Centrifuge.testconfig.EnableMongoDbTestcontainer;
import de.stintlab.centrifuge.endpoints.DriverEndpoint;
import de.stintlab.centrifuge.endpoints.model.DriverDto;
import de.stintlab.centrifuge.endpoints.model.DriverPutDto;
import de.stintlab.centrifuge.repository.PlanMongoRepository;
import de.stintlab.centrifuge.repository.entities.DriverEntity;
import de.stintlab.centrifuge.repository.entities.PlanEntity;
import org.assertj.core.api.InstanceOfAssertFactories;
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
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@SpringJUnitConfig
@AutoConfigureWebFlux
@AutoConfigureWebTestClient
@EnableMongoDbTestcontainer
class DriverEndpointTest {
    @Autowired
    WebTestClient webClient;
    
    @Autowired
    PlanMongoRepository planMongoRepository;
    
    UUID planId = UUID.randomUUID();
    
    PlanEntity emptyPlan = PlanEntity.builder()
        .planId(planId)
        .build();
    
    @BeforeEach
    void cleanDb(){
        planMongoRepository.deleteAll().block();
        planMongoRepository.save(emptyPlan).block();
    }
    
    @Test
    void gettingNoDriversWorks(){
        var result = webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path(DriverEndpoint.DRIVERS_ENDPOINT_URL)
                .build(planId))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(DriverDto.class)
            .returnResult()
            .getResponseBody();
        assertThat(result).isNotNull().isEmpty();
    }
    
    @Test
    void gettingOneDriversWorks(){
        var entity = DriverEntity.builder()
            .name("newDriver")
            .averageLapTime(Duration.ofSeconds(10))
            .fuelConsumption(10.0)
            .build();
        
        var dto = DriverDto.builder()
            .name("newDriver")
            .averageLapTime(Duration.ofSeconds(10))
            .fuelConsumption(10.0)
            .build();
        
        var plan = emptyPlan.toBuilder()
            .drivers(Set.of(entity))
            .build();
        planMongoRepository.save(plan).block();
        
        var result = webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path(DriverEndpoint.DRIVERS_ENDPOINT_URL)
                .build(planId))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(DriverDto.class)
            .returnResult()
            .getResponseBody();
        
        assertThat(result).isNotNull()
            .hasSize(1)
            .containsExactly(dto);
    }
    
    @Test
    void gettingTwoDriversWorks(){
        var entity1 = DriverEntity.builder()
            .name("newDriver1")
            .averageLapTime(Duration.ofSeconds(10))
            .fuelConsumption(10.0)
            .build();
        
        var dto1 = DriverDto.builder()
            .name("newDriver1")
            .averageLapTime(Duration.ofSeconds(10))
            .fuelConsumption(10.0)
            .build();
        
        var entity2 = DriverEntity.builder()
            .name("newDriver2")
            .averageLapTime(Duration.ofSeconds(20))
            .fuelConsumption(20.0)
            .build();
        
        var dto2 = DriverDto.builder()
            .name("newDriver2")
            .averageLapTime(Duration.ofSeconds(20))
            .fuelConsumption(20.0)
            .build();
        
        
        var plan = emptyPlan.toBuilder()
            .drivers(Set.of(entity1, entity2))
            .build();
        planMongoRepository.save(plan).block();
        
        var result = webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path(DriverEndpoint.DRIVERS_ENDPOINT_URL)
                .build(planId))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(DriverDto.class)
            .returnResult()
            .getResponseBody();
        
        assertThat(result).isNotNull()
            .hasSize(2)
            .containsExactlyInAnyOrder(dto1, dto2);
    }
    
    @Test
    void creatingADriverWorks(){
        var newDriver = DriverPutDto.builder()
            .name("newDriver")
            .averageLapTime(Duration.ofSeconds(10))
            .fuelConsumption(10.0)
            .build();
        
        var entity = DriverEntity.builder()
            .name("newDriver")
            .averageLapTime(Duration.ofSeconds(10))
            .fuelConsumption(10.0)
            .build();
        
        webClient.put()
            .uri(uriBuilder -> uriBuilder
                .path(DriverEndpoint.CREATE_DRIVER_ENDPOINT_URL)
                .build(planId))
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(newDriver)
            .exchange()
            .expectStatus()
            .isCreated();
        
        var plan = planMongoRepository.findById(planId).block();
        assertThat(plan).isNotNull()
            .extracting("drivers")
            .asInstanceOf(InstanceOfAssertFactories.COLLECTION)
            .hasSize(1)
            .containsExactly(entity);
    }
    
    @Test
    void gettingTwoDriverWorks(){
        var newDriver = DriverEntity.builder()
            .name("newDriver")
            .averageLapTime(Duration.ofSeconds(10))
            .fuelConsumption(10.0)
            .build();
        
        var newDriver2 = DriverEntity.builder()
            .name("newDriver2")
            .averageLapTime(Duration.ofSeconds(20))
            .fuelConsumption(20.0)
            .build();
        
        var plan = emptyPlan.toBuilder()
            .drivers(Set.of(newDriver, newDriver2))
            .build();
        planMongoRepository.save(plan).block();
        
        webClient.delete()
            .uri(uriBuilder -> uriBuilder
                .path(DriverEndpoint.DELETE_DRIVER_ENDPOINT_URL)
                .build(planId, newDriver2.getName()))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();
        
        var plandb = planMongoRepository.findById(planId).block();
        assertThat(plandb).isNotNull()
            .extracting("drivers")
            .asInstanceOf(InstanceOfAssertFactories.COLLECTION)
            .hasSize(1)
            .containsExactly(newDriver);
    }
}
