package com.priyakdey.backend.configuration;


import com.priyakdey.backend.security.config.AppCorsProperties;
import com.priyakdey.backend.security.config.SessionTokenProperties;
import com.priyakdey.backend.security.config.StateTokenProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Priyak Dey
 */
@Configuration
@EnableConfigurationProperties(value = {StateTokenProperties.class,
        SessionTokenProperties.class, AppCorsProperties.class})
public class PropertiesConfiguration {
}
