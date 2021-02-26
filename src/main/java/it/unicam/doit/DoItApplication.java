package it.unicam.doit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing
public class DoItApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoItApplication.class, args);
    }
}
