package com.wsq.service;

import lombok.Data;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author wsq
 *         Created by Tp on 2019/5/10.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
//@Slf4j
@Data
public class LoggerTest {

    private final Logger logger = LoggerFactory.getLogger(LoggerTest.class);// 填写当前的类

    @Test
    public void test1(){
        logger.debug("debug......");
        logger.info("info......");
        logger.error("error......");
    }
    @Test
    public void test2(){
//        log.debug("debug......");
//        log.info("info......");
//        log.error("error......");
    }

}
