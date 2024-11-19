// 
// Decompiled by Procyon v0.5.36
// 

//package th.co.XCS.ilg.config;

package com.xcs.illegal.p2.config;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.context.annotation.Bean;
import oracle.jdbc.driver.OracleDriver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
public class DatabaseConfig
{
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    
    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(OracleDriver.class.getName());
        ds.setUrl(this.url);
        ds.setUsername(this.username);
        ds.setPassword(this.password);
        return (DataSource)ds;
    }
    
    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(this.dataSource());
    }
    
    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(final DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }
}
