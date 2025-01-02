package com.ortech.bookstore.catalog.config;

import jakarta.validation.constraints.Min;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "catalog")
public record ApplicationProperties(@Min(value = 1) @DefaultValue(value = "10") int pageSize) {}
