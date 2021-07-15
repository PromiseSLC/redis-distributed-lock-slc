package com.promise.lock.controller;

import com.promise.lock.annotation.RedisDistributedLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author application.properties
 * @date 2021/7/15 上午10:21
 */
@RestController
@Slf4j
public class AnnotationLockController {

  private static volatile Integer STOCK = 10;

  @GetMapping("annotation-lock")
  @RedisDistributedLock(value = "distributed-redis-lock-of-stock", leaseTime = 10)
  public String annotationLock() throws InterruptedException {
    if (STOCK > 0) {
      STOCK--;
      // 模拟具体业务逻辑
      Thread.sleep(50);
      log.warn("当前库存：{}", STOCK);
    }
    return "annotation-lock-of-decrease-stock";
  }


}
