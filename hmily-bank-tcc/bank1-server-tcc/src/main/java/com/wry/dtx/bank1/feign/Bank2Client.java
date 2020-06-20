package com.wry.dtx.bank1.feign;

import org.dromara.hmily.annotation.Hmily;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(value = "bank2-server-tcc",fallback = Bank2ClientFallBack.class)
public interface Bank2Client {

    @GetMapping("/bank2/transfer")
    @Hmily
    String updateAccountBalance(@RequestParam("accountNo") String accountNo, @RequestParam("amount") BigDecimal amount);

}
