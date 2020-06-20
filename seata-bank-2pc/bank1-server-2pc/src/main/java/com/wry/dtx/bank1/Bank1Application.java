package com.wry.dtx.bank1;


import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.wry.dtx.bank1.feign"})
@EnableAutoDataSourceProxy
public class Bank1Server2pcApplication {

    public static void main(String[] args) {
        SpringApplication.run(Bank1Server2pcApplication.class, args);
    }

}
