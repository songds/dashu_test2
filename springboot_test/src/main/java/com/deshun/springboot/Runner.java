package com.deshun.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;


//表明这是一个 Controller
//@Controller

//RestController是一种Rest风格的Controller，可以直接返回对象而不返回视图，返回的对象可以使JSON，XML等
//@RestController

//使用自动配置，主动添加并解析bean，配置文件等信息
//@EnableAutoConfiguration
@SpringBootApplication
@EnableFeignClients
public class Runner {
    public static void main(String[] args) throws Exception {
        //通过SpringApplication的run()方法启动应用，无需额外的配置其他的文件
        SpringApplication.run(Runner.class, args);
    }
    
   
    
}