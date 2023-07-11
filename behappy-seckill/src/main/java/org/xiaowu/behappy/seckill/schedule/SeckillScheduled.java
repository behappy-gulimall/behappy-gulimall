package org.xiaowu.behappy.seckill.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.xiaowu.behappy.seckill.service.SeckillService;

import java.util.concurrent.TimeUnit;

import static org.xiaowu.behappy.api.seckill.constant.SeckillConstant.UPLOAD_LOCK;

/**
 * 秒杀商品定时上架
 *  每天晚上3点，上架最近三天需要三天秒杀的商品
 *  当天00:00:00 - 23:59:59
 *  明天00:00:00 - 23:59:59
 *  后天00:00:00 - 23:59:59
 * @author xiaowu
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class SeckillScheduled {

    private final SeckillService seckillService;

    private final RedissonClient redissonClient;


    // TODO 保证幂等性问题
    // @Scheduled(cron = "*/5 * * * * ? ")
    @Scheduled(cron = "0 0 1/1 * * ? ")
    public void uploadSeckillSkuLatest3Days() {
        //1、重复上架无需处理
        log.info("上架秒杀的商品...");

        //分布式锁
        RLock lock = redissonClient.getLock(UPLOAD_LOCK);
        try {
            //加锁
            lock.lock(10, TimeUnit.SECONDS);
            seckillService.uploadSeckillSkuLatest3Days();
        } catch (Exception e) {
            log.error("SeckillScheduled - uploadSeckillSkuLatest3Days: ", e);
        } finally {
            lock.unlock();
        }
    }
}
