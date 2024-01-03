package com.marcoecommerce.shop.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration
public class DatabaseContainerConfig {

//    @Bean
//    static MySQLContainer<?> mySQLContainer() {
//        return new MySQLContainer("mysql:8.0")
//                .withDatabaseName("shop");
//    }
//
//    @Bean
//    static GenericContainer<?> redisContainer() {
//        return new GenericContainer<>(DockerImageName.parse("redis:5.0.3-alpine"))
//                .withExposedPorts(6379);
//    }
}
