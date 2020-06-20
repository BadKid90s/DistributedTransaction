package com.wry.dtx.bank2.controller;

import com.wry.dtx.bank2.service.AccountInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;


@RestController
public class AccountInfoController {

    @Resource
    private AccountInfoService accountInfoService;

    /**
     * 转账接收者（李四增加金额）
     *
     * @return
     */
    @GetMapping("/bank2/transfer")
    String updateAccountBalance(@RequestParam("accountNo") String accountNo, @RequestParam("amount") BigDecimal amount){
        return accountInfoService.updateAccountBalance(accountNo, amount).toString();
    }


}