package de.stintlab.centrifuge.services;

import de.stintlab.centrifuge.entities.RaceEntity;
import de.stintlab.centrifuge.entities.RacePlanEntity;
import de.stintlab.centrifuge.repositories.RacePlanRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class RacePlanService {
    private final RacePlanRepository racePlanRepository;

    public Mono<RacePlanEntity> CreateEmptyRacePlan() {
        var emptyRace = new RaceEntity(new Date(), null, 1, 1, 0);
        var racePlan = new RacePlanEntity(UUID.randomUUID(), emptyRace, new ArrayList<>(), null);
        return racePlanRepository.save(racePlan);
    }

    public Mono<RacePlanEntity> GetById(UUID id) {
        return racePlanRepository.findById(id);
    }
}
