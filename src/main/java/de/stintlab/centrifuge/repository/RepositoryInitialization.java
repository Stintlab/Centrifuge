package de.stintlab.centrifuge.repository;

import com.mongodb.reactivestreams.client.MongoCollection;
import de.stintlab.centrifuge.repository.entities.PlanEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bson.Document;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RepositoryInitialization implements ApplicationListener<ApplicationReadyEvent> {
    
    ReactiveMongoTemplate reactiveMongoTemplate;
    
    @Override
    public void onApplicationEvent(@NonNull ApplicationReadyEvent ignored) {
        reactiveMongoTemplate.getCollectionNames().collectList()
            .flatMapMany(existingCollections -> {
                ArrayList<Mono<MongoCollection<Document>>> creations = new ArrayList<>();
                if (!existingCollections.contains(PlanEntity.COLLECTION_NAME)) {
                    creations.add(reactiveMongoTemplate.createCollection(PlanEntity.class));
                }
                return Flux.concat(creations);
            })
            .blockLast();
    }
}
