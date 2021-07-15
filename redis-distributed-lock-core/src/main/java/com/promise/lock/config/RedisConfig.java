package com.promise.lock.config;

import com.alibaba.fastjson.JSONObject;
import com.promise.lock.RedissonLock;
import com.promise.lock.RedissonManager;
import com.promise.lock.entity.RedissonProperties;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * @author slc
 * @date 2021/7/14 上午12:05
 *
 *
 * 尝试去掉
 *  - @ConditionalOnClass(Redisson.class)
 *  - @EnableConfigurationProperties(RedissonProperties.class)
 *  - @ConditionalOnMissingBean
 */
@Slf4j
@Configuration
@ConditionalOnClass(Redisson.class)
@EnableConfigurationProperties(RedissonProperties.class)
public class RedisConfig {

  @Bean
  @ConditionalOnMissingBean
  @Order(value = 2)
  public RedissonLock redissonLock(RedissonManager redissonManager) {
    RedissonLock redissonLock = new RedissonLock(redissonManager);
    log.info("redissonLock 实例化完成");
    return redissonLock;
  }

  @Bean
  @ConditionalOnMissingBean
  @Order(value = 1)
  public RedissonManager redissonManager(RedissonProperties redissonProperties) {
    RedissonManager redissonManager = new RedissonManager(redissonProperties);
    log.info("redisson manager 实例化完成，连接参数为：{}", JSONObject.toJSONString(redissonProperties));
    return redissonManager;
  }

}
