package ru.alliedar.pokaznoi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
public class PokaznoiApplication {

    public static void main(final String[] args) {
        SpringApplication.run(PokaznoiApplication.class, args);
    }

}
