package com.backend.zycus.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "app.security.cors")
public class CorsProperties {
    
    private List<String> allowedOrigins;
    private List<String> allowedMethods = List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS");
//    private List<String> allowedHeaders = List.of("Authorization", "Cache-Control", "Content-Type");
    private List<String> allowedHeaders = List.of("*");// sinnce the above one was giving 403 error in angular
    private List<String> exposedHeaders = List.of("Authorization");
    private Boolean allowCredentials = true;
    private Long maxAge = 3600L; // Cache preflight response for 1 hour

    // Getters and Setters
    public List<String> getAllowedOrigins() { return allowedOrigins; }
    public void setAllowedOrigins(List<String> allowedOrigins) { this.allowedOrigins = allowedOrigins; }
    public List<String> getAllowedMethods() { return allowedMethods; }
    public void setAllowedMethods(List<String> allowedMethods) { this.allowedMethods = allowedMethods; }
    public List<String> getAllowedHeaders() { return allowedHeaders; }
    public void setAllowedHeaders(List<String> allowedHeaders) { this.allowedHeaders = allowedHeaders; }
    public List<String> getExposedHeaders() { return exposedHeaders; }
    public void setExposedHeaders(List<String> exposedHeaders) { this.exposedHeaders = exposedHeaders; }
    public Boolean getAllowCredentials() { return allowCredentials; }
    public void setAllowCredentials(Boolean allowCredentials) { this.allowCredentials = allowCredentials; }
    public Long getMaxAge() { return maxAge; }
    public void setMaxAge(Long maxAge) { this.maxAge = maxAge; }
}
