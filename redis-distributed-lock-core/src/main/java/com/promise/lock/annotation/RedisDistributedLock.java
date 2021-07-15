package com.promise.lock.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author slc
 * @date 2021/7/13 下午5:24
 */

@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RedisDistributedLock {

  /**
   * 锁名称
   * @return
   */
  String value() default "redis-distributed-lock";

  /**
   * 锁有效时间
   * @return
   */
  int leaseTime() default 10;

}
