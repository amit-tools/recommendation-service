package com.example.recommendation.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/health")
public class DatabaseHealthController {

  private final JdbcTemplate jdbcTemplate;

  public DatabaseHealthController(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @GetMapping("/database")
  public Map<String, Object> databaseHealth() {
    Map<String, Object> response = new LinkedHashMap<>();

    Integer value = jdbcTemplate.queryForObject("SELECT 1", Integer.class);

    response.put("service", "recommendation-service");
    response.put("database", "recommendation_engine");
    response.put("connected", value != null && value == 1);

    return response;
  }
}
