package com.honsul.stampist.agent.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "stampist")
public class StampistAgentConfig { 
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  
  private String token;
  
  private String serverUrl;
  
  public void setToken(String token) {
    this.token = token;
  }
  
  public String getToken() {
    return this.token;
  }
  
  public void setServerUrl(String serverUrl) {
    this.serverUrl = serverUrl;
  }
  
  public String getServerUrl() {
    return this.serverUrl;
  }
}
