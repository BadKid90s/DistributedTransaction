package com.wry.dtx.bank1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableDiscoveryClient
@EnableAspectJAutoProxy
@EnableFeignClients(basePackages = {"com.wry.dtx.bank1.feign"})
@ComponentScan({"org.dromara.hmily","com.wry.dtx.bank1"})
public class Bank1ServerTccApplication {

    public static void main(String[] args) {
        SpringApplication.run(Bank1ServerTccApplication.class, args);
    }

}
