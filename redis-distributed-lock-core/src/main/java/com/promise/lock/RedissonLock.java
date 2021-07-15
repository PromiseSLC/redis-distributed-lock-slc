package com.promise.lock;

import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;

/**
 * @author slc
 * @date 2021/7/13 下午6:35
 */
@Slf4j
public class RedissonLock {

  private RedissonManager redissonManager;

  private Redisson redisson;

  public RedissonLock(RedissonManager redissonManager) {
    this.redissonManager = redissonManager;
    this.redisson = redissonManager.getRedisson();
  }

  public RedissonLock() {

  }

  /**
   * 加锁 + 有效时间
   * @param lockName
   * @param leaseTime
   */
  public void lock(String lockName, long leaseTime) {
    RLock rLock = redisson.getLock(lockName);
    rLock.lock(leaseTime, TimeUnit.SECONDS);
  }

  /**
   * 加锁 + 默认有效15秒
   * @param lockName
   */
  public void lock(String lockName) {
    RLock rLock = redisson.getLock(lockName);
    rLock.lock();
  }

  /**
   * 尝试获取锁,无等待时间
   * @param lockName
   * @param leaseTime
   * @return true or false
   */
  public boolean tryLock(String lockName, long leaseTime) {
    RLock rLock = redisson.getLock(lockName);
    boolean isGetLock = false;
    try {
      isGetLock = rLock.tryLock(leaseTime, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      log.error("尝试获取Redisson分布式异常，lockName=" + lockName, e);
      e.printStackTrace();
      return false;
    }
    return isGetLock;
  }

  /**
   * 尝试获取锁，有等待时间
   * @param lockName
   * @param leaseTime
   * @param waitTime
   * @return
   */
  public boolean tryLock(String lockName, long leaseTime, long waitTime) {
    RLock rLock = redisson.getLock(lockName);
    boolean isGetLock = false;
    try {
      isGetLock = rLock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      log.error("尝试获取Redisson分布式异常，lockName=" + lockName, e);
      e.printStackTrace();
      return false;
    }
    return isGetLock;
  }

  public void unlock(String lockName) {
    redisson.getLock(lockName).unlock();
  }

  public boolean isLock(String lockName) {
    return redisson.getLock(lockName).isLocked();
  }

  public boolean isHeldByCurrentThread(String lockName) {
    return redisson.getLock(lockName).isHeldByCurrentThread();
  }

}
