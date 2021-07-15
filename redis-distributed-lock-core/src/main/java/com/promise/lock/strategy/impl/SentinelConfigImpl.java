package com.promise.lock.strategy.impl;

import com.promise.lock.constant.GlobalConstant;
import com.promise.lock.entity.RedissonProperties;
import com.promise.lock.strategy.RedissonConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.config.Config;

/**
 * @author slc
 * @date 2021/7/13 下午8:42
 */
@Slf4j
public class SentinelConfigImpl implements RedissonConfigService {

  @Override
  public Config createRedissonConfig(RedissonProperties redissonProperties) {
    Config config = new Config();
    try {
      String address = redissonProperties.getAddress();
      String password = redissonProperties.getPassword();
      int database = redissonProperties.getDatabase();
      config.useSentinelServers().setMasterName("sentinel-master-name");
      config.useSentinelServers().setDatabase(database);
      if (StringUtils.isNotBlank(password)) {
        config.useSentinelServers().setPassword(password);
      }
      String[] redisAddr = address.split(",");
      for (int i = 0; i < redisAddr.length; i++) {
        String sentinelAddr = GlobalConstant.REDIS_CONN_PREFIX.getConstantValue() + redisAddr[i];
        config.useSentinelServers().addSentinelAddress(sentinelAddr);
      }
      log.info("初始化[哨兵部署]方式Config,redisAddress:" + address);
    } catch (Exception e) {
      log.error("哨兵部署 Redisson init error", e);
    }
    return config;
  }

}
