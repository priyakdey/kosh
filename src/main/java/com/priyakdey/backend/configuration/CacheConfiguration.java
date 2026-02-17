package com.priyakdey.backend.configuration;

import com.priyakdey.backend.security.StateTokenCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Priyak Dey
 */
@Configuration
public class CacheConfiguration {

    @Bean
    public StateTokenCache stateTokenCache() {
        return new StateTokenCache();
    }

}
