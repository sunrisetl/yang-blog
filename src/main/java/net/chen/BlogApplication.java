package net.chen;

import lombok.extern.slf4j.Slf4j;
import net.chen.controller.TestController;
import net.chen.utils.RedisUtil;
import net.chen.utils.SnowflakeIdWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Random;

/**
 * Created by Chen
 * 2020/7/28 23:01
 */
@Slf4j
@EnableSwagger2
@SpringBootApplication
public class BlogApplication  {
  //  public static final Logger log = LoggerFactory.getLogger(BlogApplication.class);
    public static void main(String[] args) {
        log.info("BlogApplication run.....");
        SpringApplication.run(BlogApplication.class,args);

    }

    @Bean
    public SnowflakeIdWorker createIdWorker(){
        return new SnowflakeIdWorker(0,0);
    }
    /** 注入*/
    @Bean
    public BCryptPasswordEncoder createPassword(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public RedisUtil createRedisUtils(){
        return new RedisUtil();
    }
    @Bean
    public Random createRandom(){
        return new Random();
    }



}