package com.llf.ipfilter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 参数配置
 * @author 李良发
 * @version v1.0.0
 * @since 2023/1/31 15:04
 */
@ConfigurationProperties(prefix = "llf.ipfilter")
public class IpFilterProperties {
    
    private String filterType;
    
    private String ips;

    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    public String getIps() {
        return ips;
    }

    public void setIps(String ips) {
        this.ips = ips;
    }
}
