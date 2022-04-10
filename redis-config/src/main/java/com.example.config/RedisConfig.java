package com.example.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.nio.charset.Charset;
import java.time.Duration;

/**
 * spring cache 配置
 *
 * @author llin
 * @date 2022/3/31
 **/
@Slf4j
@Configuration
@EnableCaching(proxyTargetClass = true)
public class RedisConfig {
    @Primary
    @Bean(name = "cacheManager")
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        log.info("init redis cache manager ...");
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        redisCacheConfiguration = redisCacheConfiguration.entryTtl(Duration.ofMinutes(30))    //缓存有效期30min
                .disableCachingNullValues() //不保存空值
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(getKeySerializer()))  //key的序列化
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(getValueSerializer()))//value的序列化
        ;

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(redisCacheConfiguration)
                .build();

    }

    private RedisSerializer<String> getKeySerializer() {
        return new StringRedisSerializer(Charset.forName("UTF-8"));
    }

    private RedisSerializer<Object> getValueSerializer() {
        return new GenericFastJsonRedisSerializer();
    }

    @Primary
    @Bean
    public RedisTemplate<String, Object> getRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);


        redisTemplate.setKeySerializer(getKeySerializer());
        redisTemplate.setHashKeySerializer(getKeySerializer());

        redisTemplate.setValueSerializer(getValueSerializer());
        redisTemplate.setHashValueSerializer(getValueSerializer());

        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

}
