package de.stintlab.centrifuge.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@ConfigurationProperties(prefix = "centrifuge.mongo-db")
public class ServiceDbProperties {
}
