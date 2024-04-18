package ru.alliedar.pokaznoi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class PokaznoiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PokaznoiApplication.class, args);
    }

}
