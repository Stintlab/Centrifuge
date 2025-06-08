package de.StintLab.Centrifuge.repository;

import de.StintLab.Centrifuge.testconfig.EnableMongoDbTestcontainer;
import de.stintlab.centrifuge.repository.entities.PlanEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@EnableMongoDbTestcontainer
class RepositoryInitializationTest {
    
    @Autowired
    ReactiveMongoTemplate reactiveMongoTemplate;
    
    @Test
    void testCollectionsAndIndicesCreated(){
        var planCollection = reactiveMongoTemplate.getCollection(PlanEntity.COLLECTION_NAME).block();
        assertThat(planCollection).isNotNull();
    }
}
