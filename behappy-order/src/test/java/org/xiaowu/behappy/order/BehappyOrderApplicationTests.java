package org.xiaowu.behappy.order;

import cn.hutool.core.lang.Snowflake;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BehappyOrderApplicationTests {

    @Autowired
    Snowflake snowflake;

    @Test
    void contextLoads() {
        System.out.println(snowflake.nextIdStr());
    }

}
