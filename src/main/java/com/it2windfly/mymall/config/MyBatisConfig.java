package com.it2windfly.mymall.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis配置类
 */
@Configuration
@MapperScan({"com.it2windfly.mymall.mbg.mapper","com.it2windfly.mymall.dao"})
public class MyBatisConfig {
}
