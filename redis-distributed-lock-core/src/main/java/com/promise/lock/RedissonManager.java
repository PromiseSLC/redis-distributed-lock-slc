package com.promise.lock;

import com.google.common.base.Preconditions;
import com.promise.lock.constant.RedisConnectionType;
import com.promise.lock.entity.RedissonProperties;
import com.promise.lock.strategy.RedissonConfigService;
import com.promise.lock.strategy.impl.ClusterConfigImpl;
import com.promise.lock.strategy.impl.MasterSlaveConfigImpl;
import com.promise.lock.strategy.impl.SentinelConfigImpl;
import com.promise.lock.strategy.impl.SingleConfigImpl;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.config.Config;

/**
 * @author slc
 * @date 2021/7/13 下午6:37
 */
@Slf4j
public class RedissonManager {

  private Config config;

  private Redisson redisson;

  public RedissonManager() {

  }

  public RedissonManager(RedissonProperties redissonProperties) {
    config = RedissonConfigFactory.getInstance().createConfig(redissonProperties);
    redisson = (Redisson) Redisson.create(config);
  }

  public Redisson getRedisson() {
    return redisson;
  }

  static class RedissonConfigFactory {

    private RedissonConfigFactory() {

    }

    private static volatile RedissonConfigFactory factory = null;

    public static RedissonConfigFactory getInstance() {
      if (factory == null) {
        synchronized (Object.class) {
          if (factory == null) {
            factory = new RedissonConfigFactory();
          }
        }
      }
      return factory;
    }

    Config createConfig(RedissonProperties redissonProperties) {
      Preconditions.checkNotNull(redissonProperties);
      Preconditions.checkNotNull(redissonProperties.getAddress(),
          "redisson.lock.server.address cannot be NULL!");
      Preconditions.checkNotNull(redissonProperties.getType(),
          "redisson.lock.server.password cannot be NULL");
      Preconditions.checkNotNull(redissonProperties.getDatabase(),
          "redisson.lock.server.database cannot be NULL");
      String connectionType = redissonProperties.getType();
      //策略模式
      RedissonConfigService redissonConfigService = null;
      if (RedisConnectionType.SINGLE.getConnectionType().equals(connectionType)) {
        redissonConfigService = new SingleConfigImpl();
      } else if (RedisConnectionType.MASTER_SLAVE.getConnectionType().equals(connectionType)) {
        redissonConfigService = new MasterSlaveConfigImpl();
      } else if (RedisConnectionType.SENTINEL.getConnectionType().equals(connectionType)) {
        redissonConfigService = new SentinelConfigImpl();
      } else if (RedisConnectionType.CLUSTER.getConnectionType().equals(connectionType)) {
        redissonConfigService = new ClusterConfigImpl();
      } else {
        throw new IllegalArgumentException("redis集群类型不存在！当前连接方式:" + connectionType);
      }
      return redissonConfigService.createRedissonConfig(redissonProperties);
    }

  }

}
