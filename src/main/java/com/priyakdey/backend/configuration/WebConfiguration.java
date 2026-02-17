package com.priyakdey.backend.configuration;

import com.priyakdey.backend.security.config.AppCorsProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Priyak Dey
 */
@Configuration
public class WebConfiguration {

    @Bean
    public RestClient restClient() {
        return RestClient.builder().build();
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer(AppCorsProperties appCorsProperties,
                                             FooRequestInterceptor fooRequestInterceptor) {
        String[] allowedOrigins = appCorsProperties.getAllowedOrigins();
        String[] allowedHeaders = appCorsProperties.getAllowedHeaders();
        String[] allowedMethods = appCorsProperties.getAllowedMethods();
        String[] exposedHeaders = appCorsProperties.getExposedHeaders();
        boolean allowedCredentials = appCorsProperties.isAllowedCredentials();

        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(allowedOrigins)
                        .allowedHeaders(allowedHeaders)
                        .allowedMethods(allowedMethods)
                        .exposedHeaders(exposedHeaders)
                        .allowCredentials(allowedCredentials);
            }

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(fooRequestInterceptor)
                        .addPathPatterns("/**");
            }
        };
    }


}
