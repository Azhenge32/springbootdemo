package com.azhen.service;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("example.service")
public class StarterServiceProperties {
    private String config;

    public void setConfig(String config) {
        this.config = config;
    }

    public String getConfig() {
        return config;
    }
}
