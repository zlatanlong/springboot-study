package top.lclong.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.lclong.config.bean.JWTProperties;

/**
 * @Author: zlatanlong
 * @Date: 2021/4/19 19:25
 */
@Configuration
public class ConfigBeanConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "jwt", ignoreUnknownFields = true)
    public JWTProperties jwtProperties() {
        return new JWTProperties();
    }
}
