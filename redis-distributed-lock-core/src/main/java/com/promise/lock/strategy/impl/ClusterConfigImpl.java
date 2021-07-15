package com.promise.lock.strategy.impl;

import com.promise.lock.constant.GlobalConstant;
import com.promise.lock.constant.RedisConnectionType;
import com.promise.lock.entity.RedissonProperties;
import com.promise.lock.strategy.RedissonConfigService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.config.Config;

/**
 * @author slc
 * @date 2021/7/13 下午8:21
 */
@Slf4j
public class ClusterConfigImpl implements RedissonConfigService {

  @Override
  public Config createRedissonConfig(RedissonProperties redissonProperties) {
    Config config = new Config();
    try {
      String address = redissonProperties.getAddress();
      String password = redissonProperties.getPassword();
      String[] addressSplit = address.split(",");
      for (int i = 0; i < addressSplit.length; i++) {
        // 这个2000每次都需要加吗?
        config.useClusterServers()
            .setScanInterval(2000)
            .addNodeAddress(GlobalConstant.REDIS_CONN_PREFIX.getConstantValue() + addressSplit[i]);
        if (password != null) {
          config.useClusterServers().setPassword(password);
        }
      }
      log.info("初始化[集群部署]方式Config,redisAddress:" + address);
    } catch (Exception e) {
      log.error("集群部署 Redisson init error", e);
    }
    return config;
  }

}
