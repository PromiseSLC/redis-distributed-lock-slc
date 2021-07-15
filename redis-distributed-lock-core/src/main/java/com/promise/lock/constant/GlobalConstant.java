package com.promise.lock.constant;

/**
 * @author slc
 * @date 2019/6/19 下午9:09
 */
public enum GlobalConstant {

  REDIS_CONN_PREFIX("redis://", "Redis地址配置前缀");

  private final String constantValue;
  private final String constantDesc;

  GlobalConstant(String constantValue, String constantDesc) {
    this.constantValue = constantValue;
    this.constantDesc = constantDesc;
  }

  public String getConstantValue() {
    return constantValue;
  }

  public String getConstantDesc() {
    return constantDesc;
  }
}
