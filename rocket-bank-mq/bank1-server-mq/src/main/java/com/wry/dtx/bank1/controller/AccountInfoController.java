package com.wry.dtx.bank1.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wry.dtx.bank1.rocketmq.entity.AccountChangeEvent;
import com.wry.dtx.bank1.service.AccountInfoService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.UUID;

@RestController
public class AccountInfoController {

    @Resource
    private AccountInfoService accountInfoService;

    @RequestMapping("/transfer/{amount}")
    public Object transfer(@PathVariable BigDecimal amount) throws JsonProcessingException {
        AccountChangeEvent changeEvent = new AccountChangeEvent();
        changeEvent.setTxNo(UUID.randomUUID().toString());
        changeEvent.setAmount(amount);
        changeEvent.setTransferAccountNo("1");
        changeEvent.setReceiveAccountNo("2");
        accountInfoService.sendUpdateAccountBalance(changeEvent);
        return "SUCCESS";
    }
}
