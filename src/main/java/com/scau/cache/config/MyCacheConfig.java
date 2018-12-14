package com.scau.cache.config;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

/**
 * 配置自己的keyGenerator：生成缓存的key
 * 结合@Cacheable中的keyGenerator = 'myKeyGenerator'（生成器的ID）使用
 */
@Configuration
public class MyCacheConfig {
    @Bean
    public KeyGenerator myKeyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                return method.getName() + "[" + objects.toString() + "]";
            }
        };
    }
}
