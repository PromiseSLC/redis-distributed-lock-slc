package com.promise.lock.strategy.impl;

import com.promise.lock.constant.GlobalConstant;
import com.promise.lock.entity.RedissonProperties;
import com.promise.lock.strategy.RedissonConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.config.Config;

/**
 * @author slc
 * @date 2021/7/13 下午8:40
 */
@Slf4j
public class SingleConfigImpl implements RedissonConfigService {

  @Override
  public Config createRedissonConfig(RedissonProperties redissonProperties) {
    Config config = new Config();
    try {
      String address = redissonProperties.getAddress();
      int database = redissonProperties.getDatabase();
      String password = redissonProperties.getPassword();
      config.useSingleServer().setAddress(GlobalConstant.REDIS_CONN_PREFIX.getConstantValue() + redissonProperties.getAddress());
      config.useSingleServer().setDatabase(database);
      if (StringUtils.isNotBlank(password)) {
        config.useSingleServer().setPassword(password);
      }
      log.info("初始化[单机部署]方式Config,redisAddress:" + address);
    } catch (Exception e) {
      log.error("单机部署 Redisson init error", e);
    }
    return config;
  }
}
