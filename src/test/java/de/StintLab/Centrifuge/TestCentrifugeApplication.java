package de.StintLab.Centrifuge;

import de.stintlab.centrifuge.CentrifugeApplication;
import org.springframework.boot.SpringApplication;
import org.testcontainers.utility.TestcontainersConfiguration;

public class TestCentrifugeApplication {
    
    public static void main(String[] args) {
        SpringApplication.from(CentrifugeApplication::main).with(TestcontainersConfiguration.class).run(args);
    }
    
}
