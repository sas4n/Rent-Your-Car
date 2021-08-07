package com.lnu.RentYourCar.ImageStorage;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class ImageStorageConfig implements WebMvcConfigurer {
        private final Environment environment;

        public ImageStorageConfig(Environment environment) {
                this.environment = environment;
        }

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/images/**").addResourceLocations(environment.getProperty("images-path"));
        }
}


