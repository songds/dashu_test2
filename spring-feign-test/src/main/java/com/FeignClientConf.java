
package com;
 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Logger;
 
/**
 *
 * @date 2018-08-10 8:55 ^_^
 */
@Configuration
public class FeignClientConf {
 
    @Bean
    Logger.Level feignLoggerLevel() {
        // 设置日志
        return Logger.Level.FULL;
    }
}