package de.stintlab.centrifuge;

import org.springframework.boot.SpringApplication;

public class TestCentrifugeApplication {

    public static void main(String[] args) {
        SpringApplication.from(CentrifugeApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
