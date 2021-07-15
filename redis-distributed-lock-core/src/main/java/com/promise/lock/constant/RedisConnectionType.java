package com.promise.lock.constant;

/**
 * @author slc
 * @date 2021/7/13 下午7:50
 */
public enum RedisConnectionType {

  SINGLE("single", "单节点部署方式"),

  SENTINEL("sentinel", "哨兵部署方式"),

  CLUSTER("cluster", "集群方式"),

  MASTER_SLAVE("master_slave", "主从部署方式");

  private final String connectionType;
  private final String connectionDesc;

  private RedisConnectionType(String connectionType, String connectionDesc) {
    this.connectionType = connectionType;
    this.connectionDesc = connectionDesc;
  }

  public String getConnectionType() {
    return connectionType;
  }

  public String getConnectionDesc() {
    return connectionDesc;
  }

}