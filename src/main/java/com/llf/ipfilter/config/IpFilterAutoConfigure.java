package com.llf.ipfilter.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自动配置参数
 * @author 李良发
 * @version v1.0.0
 * @since 2023/1/31 15:09
 */
@Configuration
@ConditionalOnClass(IpFilterProperties.class)
@EnableConfigurationProperties(IpFilterProperties.class)
public class IpFilterAutoConfigure {
    
    @Bean("ipFilterProperties")
    @ConditionalOnMissingBean
    public IpFilterProperties getIpFilterProperties(IpFilterProperties ipFilterProperties) {
        return ipFilterProperties;
    }
    
}
