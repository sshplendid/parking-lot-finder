package me.shawn.challenge.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.TimeZone;

@SpringBootApplication
public class ProxyApplication {

    private static final Logger log = LoggerFactory.getLogger(ProxyApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ProxyApplication.class, args);
    }

    @Autowired
    ApplicationContext context;

    @Bean
    CommandLineRunner debugForDocker(@Value("${spring.cloud.gateway.routes[0].id}") String id, @Value("${spring.cloud.gateway.routes[0].uri}") String uri) {
        return args -> {
            log.info("Current Time: {}", LocalDateTime.now());
            log.info("TimeZone: {}", TimeZone.getDefault());
            log.info("id: {}, uri: {}", id, uri);
        };
    }

}
