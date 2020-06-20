package com.wry.dtx.bank2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@SpringBootApplication
@EnableDiscoveryClient
@EnableAspectJAutoProxy
@ComponentScan({"org.dromara.hmily","com.wry.dtx.bank2"})
public class Bank2ServerTccApplication {

    public static void main(String[] args) {
        SpringApplication.run(Bank2ServerTccApplication.class, args);
    }

}
