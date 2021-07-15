package com.promise.lock.strategy.impl;

import com.promise.lock.constant.GlobalConstant;
import com.promise.lock.entity.RedissonProperties;
import com.promise.lock.strategy.RedissonConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.config.Config;

/**
 * @author slc
 * @date 2021/7/13 下午8:41
 */
@Slf4j
public class MasterSlaveConfigImpl implements RedissonConfigService {

  @Override
  public Config createRedissonConfig(RedissonProperties redissonProperties) {
    Config config = new Config();
    try {
      String address = redissonProperties.getAddress();
      String password = redissonProperties.getPassword();
      int database = redissonProperties.getDatabase();
      String[] redisAddr = address.split(",");
      config.useMasterSlaveServers().setDatabase(database);
      String masterAddr = GlobalConstant.REDIS_CONN_PREFIX.getConstantValue() + redisAddr[0];
      config.useMasterSlaveServers().setMasterAddress(masterAddr);
      for (int i = 1; i < redisAddr.length; i++) {
        String slaveAddr = GlobalConstant.REDIS_CONN_PREFIX.getConstantValue() + redisAddr[i];
        config.useMasterSlaveServers().addSlaveAddress(slaveAddr);
      }
      if (StringUtils.isNotBlank(password)) {
        config.useMasterSlaveServers().setDatabase(database);
      }
      log.info("初始化[主从部署]方式Config,redisAddress:" + address);
    } catch (Exception e) {
      log.error("主从部署 Redisson init error", e);
    }
    return config;
  }

}
