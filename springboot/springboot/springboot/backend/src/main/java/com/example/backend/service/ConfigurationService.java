package com.example.backend.service;


import com.example.backend.model.Configuration;
import com.example.backend.repository.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationService {

    private final ConfigurationRepository configurationRepository;

    @Autowired
    public ConfigurationService(ConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
    }

    public Configuration saveConfiguration(Configuration configuration) {
        return configurationRepository.save(configuration);
    }

    public Configuration getConfigurationById(Long id) {
        return configurationRepository.findById(id).orElseThrow(() -> new RuntimeException("Configuration not found"));
    }
}
