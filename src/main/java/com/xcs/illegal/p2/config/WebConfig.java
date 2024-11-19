// 
// Decompiled by Procyon v0.5.36
// 

//package th.co.XCS.ilg.config;
package com.xcs.illegal.p2.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter
{
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins(new String[] { "*" });
    }
}
