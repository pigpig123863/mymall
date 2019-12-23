package com.it2windfly.mymall.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis配置类
 * Created by macro on 2019/4/8.
 */
@Configuration
@MapperScan({"com.it2windfly.mymall.mbg.mapper","com.it2windfly.mymall.dao"})
public class MyBatisConfig {
}
