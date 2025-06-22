package de.stintlab.centrifuge.repositories;

import de.stintlab.centrifuge.entities.RacePlanEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import java.util.UUID;

public interface RacePlanRepository extends ReactiveMongoRepository<RacePlanEntity, UUID> {
}
