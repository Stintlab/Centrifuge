package de.stintlab.centrifuge.config;

import com.mongodb.MongoClientSettings;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.boot.autoconfigure.mongo.MongoClientSettingsBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.ReactiveMongoTransactionManager;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MongoConfig {
    
    @Bean
    public ReactiveMongoTransactionManager transactionManager(ReactiveMongoTemplate reactiveMongoTemplate) {
        return new ReactiveMongoTransactionManager(reactiveMongoTemplate.getMongoDatabaseFactory());
    }
    
    @Bean
    public MongoClientSettingsBuilderCustomizer mongoClientSettingsBuilderCustomizer() {
        var pojoCodecRegistry = CodecRegistries.fromRegistries(
            MongoClientSettings.getDefaultCodecRegistry(),
            CodecRegistries.fromProviders(PojoCodecProvider.builder()
                .automatic(true)
                .build())
        );
        
        return clientSettingsBuilder -> clientSettingsBuilder
            .codecRegistry(pojoCodecRegistry)
            .uuidRepresentation(UuidRepresentation.STANDARD)
            .retryWrites(false)
            .build();
    }
    
    
}
