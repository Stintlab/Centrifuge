package de.StintLab.Centrifuge.testconfig;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MongoDBContainer;

@TestConfiguration(proxyBeanMethods = false)
class TestcontainerConfiguration {
    
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest")
        .withReuse(true);
    
    @ServiceConnection
    @Bean(destroyMethod = "stop")
    MongoDBContainer mongoDbContainer() {
        return mongoDBContainer;
    }
    
}
