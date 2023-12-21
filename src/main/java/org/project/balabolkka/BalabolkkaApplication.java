package org.project.balabolkka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BalabolkkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BalabolkkaApplication.class, args);
    }

}
