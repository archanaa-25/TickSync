package com.example.backend.controller;

import com.example.backend.model.Configuration;
import com.example.backend.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/configurations")
public class ConfigurationController {

    private final ConfigurationService configurationService;

    @Autowired
    public ConfigurationController(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    @PostMapping
    public ResponseEntity<Configuration> createConfiguration(@RequestBody Configuration configuration) {
        Configuration savedConfig = configurationService.saveConfiguration(configuration);
        return ResponseEntity.ok(savedConfig);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Configuration> getConfiguration(@PathVariable Long id) {
        Configuration configuration = configurationService.getConfigurationById(id);
        return ResponseEntity.ok(configuration);
    }
}
