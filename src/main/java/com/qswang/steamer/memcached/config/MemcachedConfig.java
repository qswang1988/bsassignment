package com.qswang.steamer.memcached.config;

import lombok.Data;
import net.spy.memcached.MemcachedClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.InetSocketAddress;

@Data
@Configuration
public class MemcachedConfig {
    private static final Logger logger = LoggerFactory.getLogger(MemcachedConfig.class);

    @Value("${memcached.ip}")
    private String ip;
    @Value("${memcached.port}")
    private String port;

    @Bean
    public MemcachedClient getClient() throws IOException {
        logger.info("memcached client initialized");
        return new MemcachedClient(new InetSocketAddress(ip,Integer.valueOf(port)));
    }
}
