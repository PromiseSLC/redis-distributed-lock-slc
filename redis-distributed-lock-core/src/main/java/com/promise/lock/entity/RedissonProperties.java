package com.promise.lock.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author slc
 * @date 2021/7/13 下午6:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EnableConfigurationProperties(RedissonProperties.class)
@ConfigurationProperties(prefix = "redisson.lock.server")
public class RedissonProperties {

  /**
   * redis主机地址，ip：port，有多个用半角逗号分隔
   */
  private String address;

  /**
   * 连接类型，支持standalone-单机节点，sentinel-哨兵，cluster-集群，masterslave-主从
   */
  private String type;

  /**
   * redis 连接密码
   */
  private String password;

  /**
   * 选取那个数据库
   */
  private int database;

}