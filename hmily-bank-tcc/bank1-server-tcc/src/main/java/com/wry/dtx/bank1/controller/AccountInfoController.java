package com.wry.dtx.bank1.controller;

import com.wry.dtx.bank1.service.AccountInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;


@RestController
public class AccountInfoController {

    @Resource
    private AccountInfoService accountInfoService;

    /**
     * 转账发起者（张三扣减金额）
     *
     * @return
     */
    @GetMapping(value = "/transfer/{amount}")
    public Object transfer(@PathVariable Double amount) {
        return  accountInfoService.updateAccountBalance("2","1", new BigDecimal(amount));
    }

}