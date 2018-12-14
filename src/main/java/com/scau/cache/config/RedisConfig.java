package com.scau.cache.config;

import com.scau.cache.entity.Customer;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.net.UnknownHostException;
import java.time.Duration;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

/**
 * 自定义redis配置类
 */
@Configuration
public class RedisConfig {
    @Bean //自定义缓存操作模版
    public RedisTemplate<Object, Customer> customerRedisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<Object, Customer> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<Customer> serializer = new Jackson2JsonRedisSerializer<>(Customer.class);
        template.setDefaultSerializer(serializer);
        return template;
    }

    @Primary//指定为默认序列化器,springboot2.x的配置方式与springboot1.x不同
    @Bean //自定义RedisCacheManager，以实现对象在缓存中以Json方式存储
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {//这个cacheManager能处理所有object
        //初始化一个RedisCacheWriter
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
        //设置CacheManager的值序列化方式为json序列化
        RedisSerializer<Object> jsonSerializer = new GenericJackson2JsonRedisSerializer();//转成的json里面会标明对象的实际类型
        RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair
                .fromSerializer(jsonSerializer);
        //设置缓存空间的默认失效时间是一天，并且自动清除
        RedisCacheConfiguration defaultCacheConfig=RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(pair).entryTtl(Duration.ofSeconds(864000)).disableCachingNullValues();
        //defaultCacheConfig.disableCachingNullValues();
        //设置缓存名为“teacherCache”的缓存失效时间为一个小时
        RedisCacheConfiguration teacherCacheConfig=RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(pair).entryTtl(Duration.ofSeconds(3600)).disableCachingNullValues();
        //defaultCacheConfig.disableCachingNullValues();
        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
        configMap.put("customerCache",defaultCacheConfig);
        configMap.put("teacherCache",teacherCacheConfig);
        return new RedisCacheManager(redisCacheWriter, defaultCacheConfig,configMap);
    }
}
