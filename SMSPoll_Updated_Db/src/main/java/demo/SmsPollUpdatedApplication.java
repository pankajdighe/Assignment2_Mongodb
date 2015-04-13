package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import kafka.*;;

@SpringBootApplication
@EnableScheduling
@ComponentScan
public class SmsPollUpdatedApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmsPollUpdatedApplication.class, args);
    }
}
