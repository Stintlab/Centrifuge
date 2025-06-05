package de.stintlab.centrifuge.repository;

import de.stintlab.centrifuge.repository.entities.PlanEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import java.util.UUID;

public interface PlanReactiveMongoRepository extends ReactiveMongoRepository<PlanEntity, UUID> {
}
