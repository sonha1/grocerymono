package com.tks.grocerymono.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Value("${cloudinary.api-url}")
    private String cloudinaryApiUrl;

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(cloudinaryApiUrl);
    }
}
