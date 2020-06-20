package com.wry.dtx.bank2.controller;

import com.wry.dtx.bank2.service.AccountInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/bank2")
public class AccountInfoController {

    @Resource
    private AccountInfoService accountInfoService;

    /**
     * 转账接收者 （李四增加金额）
     *
     * @param accountNo
     * @param amount
     * @return
     */
    @GetMapping(value = "/transfer")
    public String transfer(String accountNo, Double amount) {
        Boolean success = accountInfoService.transferAccounts(accountNo, amount);
        return "bank2:"+success;
    }

}