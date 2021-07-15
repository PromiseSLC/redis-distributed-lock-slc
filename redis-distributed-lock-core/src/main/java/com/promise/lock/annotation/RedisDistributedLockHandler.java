package com.promise.lock.annotation;

import com.promise.lock.RedissonLock;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author slc
 * @date 2021/7/13 下午5:30
 */
@Aspect
@Component
@Slf4j
public class RedisDistributedLockHandler {

  @Autowired
  RedissonLock redissonLock;

  @Around("@annotation(redisDistributedLock)")
  public void around(ProceedingJoinPoint joinPoint, RedisDistributedLock redisDistributedLock) {
    log.info("[开始]执行RedisLock环绕通知,获取Redis分布式锁开始");
    String lockName = redisDistributedLock.value();
    int leaseTime = redisDistributedLock.leaseTime();
    redissonLock.lock(lockName, leaseTime);
    try {
      log.info("获取Redis分布式锁[成功]，加锁完成，开始执行业务逻辑...");
      joinPoint.proceed();
    } catch (Throwable throwable) {
      log.error("获取Redis分布式锁[异常]，加锁失败", throwable);
      throwable.printStackTrace();
    } finally {
      //如果该线程还持有该锁，那么释放该锁。如果该线程不持有该锁，说明该线程的锁已到过期时间，自动释放锁
      if (redissonLock.isHeldByCurrentThread(lockName)) {
        redissonLock.unlock(lockName);
      }
    }
    log.info("释放Redis分布式锁[成功]，解锁完成，结束业务逻辑...");
  }

}
