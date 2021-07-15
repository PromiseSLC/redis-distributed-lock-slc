package com.promise.lock.controller;

import com.promise.lock.RedissonLock;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author application.properties
 * @date 2021/7/14 上午10:33
 */

@RestController
@Slf4j
public class LockController {

  @Resource
  RedissonLock redissonLock;

  private static volatile Integer STOCK = 10;

  private volatile Integer count = 0;

  @GetMapping("lock-decrease-stock")
  public String lockDecreaseStock() throws InterruptedException {
    redissonLock.lock("lock-basic", 10L);
    if (STOCK > 0) {
      STOCK--;
    }
    Thread.sleep(50);
    log.warn("当前库存：{}", STOCK);
    if (redissonLock.isHeldByCurrentThread("lock-basic")) {
      redissonLock.unlock("lock-basic");
    }
    return "LockController lockDecreaseStock";
  }

  @GetMapping("try-lock-decrease-stock")
  public String tryLockDecreaseStock() throws InterruptedException {
    if (redissonLock.tryLock("tryLock-basic", 10L, 5L)) {
      if (STOCK > 0) {
        STOCK--;
      }
      Thread.sleep(50);
      log.warn("当前库存：{}", STOCK);
      redissonLock.unlock("tryLock-basic");
    } else {
      log.error("尝试获取锁失败");
    }
    return "LockController  tryLockDecreaseStock";
  }
}