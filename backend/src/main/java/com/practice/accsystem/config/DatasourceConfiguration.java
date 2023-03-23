package com.practice.accsystem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.practice.accsystem.repository")
@EnableMongoRepositories(basePackages = "com.practice.accsystem.repository.mongo")
public class DatasourceConfiguration {
}
