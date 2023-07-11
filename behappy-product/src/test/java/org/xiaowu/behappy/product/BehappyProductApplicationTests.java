package org.xiaowu.behappy.product;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.xiaowu.behappy.product.entity.SpuInfoEntity;
import org.xiaowu.behappy.product.service.SpuInfoService;

import java.util.List;

@SpringBootTest
class BehappyProductApplicationTests {

    @Autowired
    SpuInfoService spuInfoService;


    /**
     * 数据库数据传输到es
     */
    @Test
    void upProductToEs() {
        List<SpuInfoEntity> spuInfoEntities = spuInfoService.list(new LambdaQueryWrapper<SpuInfoEntity>()
                .select(SpuInfoEntity::getId));
        spuInfoEntities.forEach(spuInfoEntity -> spuInfoService.up(spuInfoEntity.getId()));
    }

}
