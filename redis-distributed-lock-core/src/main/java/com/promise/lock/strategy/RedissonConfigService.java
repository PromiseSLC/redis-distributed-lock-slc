package com.promise.lock.strategy;

import com.promise.lock.entity.RedissonProperties;
import org.redisson.config.Config;

/**
 * @author slc
 * @date 2021/7/13 下午8:20
 */
public interface RedissonConfigService {

  /**
   * 根据不同的Redis配置策略创建对应的Config
   * @param redissonProperties
   * @return Config
   */
  Config createRedissonConfig(RedissonProperties redissonProperties);

}