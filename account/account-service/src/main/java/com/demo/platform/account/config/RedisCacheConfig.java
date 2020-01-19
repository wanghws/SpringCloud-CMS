package com.demo.platform.account.config;

import com.demo.platform.commons.constants.Constants;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author github.com/wanghws
 * @date 20191010
 */
@EnableCaching
@Configuration
public class RedisCacheConfig {
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory lettuceConnectionFactory) {
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig();
        defaultCacheConfig = defaultCacheConfig
                .entryTtl(Duration.ofSeconds(Constants.DEFAULT_EXPIRE_TIME))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new JdkSerializationRedisSerializer()))
                .disableCachingNullValues();

        Set<String> cacheNames = new HashSet<>();
        cacheNames.add(Constants.CACHE_ACCOUNT);

        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
        configMap.put(Constants.CACHE_ACCOUNT,
                defaultCacheConfig.entryTtl(Duration.ofSeconds(Constants.DEFAULT_EXPIRE_TIME)));

        return RedisCacheManager.builder(lettuceConnectionFactory)
                .cacheDefaults(defaultCacheConfig)
                .initialCacheNames(cacheNames)
                .withInitialCacheConfigurations(configMap)
                .build();
    }

}
