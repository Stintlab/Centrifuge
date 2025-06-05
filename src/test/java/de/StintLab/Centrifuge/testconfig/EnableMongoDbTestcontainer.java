package de.StintLab.Centrifuge.testconfig;

import de.stintlab.centrifuge.config.MongoConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({MongoConfig.class, TestcontainerConfiguration.class})
public @interface EnableMongoDbTestcontainer {
}
