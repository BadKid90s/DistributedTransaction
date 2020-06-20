package com.wry.dtx.bank1.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "bank2-server-2pc",fallback = Bank2ClientFallBack.class)
public interface Bank2Client {

    @GetMapping("/bank2/transfer")
    String transferAccounts(@RequestParam("accountNo") String accountNo, @RequestParam("amount") Double amount);

}
