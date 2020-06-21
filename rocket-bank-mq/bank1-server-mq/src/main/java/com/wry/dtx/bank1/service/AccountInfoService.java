package com.wry.dtx.bank1.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wry.dtx.bank1.entity.AccountInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wry.dtx.bank1.rocketmq.entity.AccountChangeEvent;

public interface AccountInfoService extends IService<AccountInfo> {

    /**
     * 向MQ 发送转账消息
     */
    void sendUpdateAccountBalance(AccountChangeEvent accountChangeEvent) throws JsonProcessingException;

    /**
     * 更新账户，扣减金额
     */
    void doUpdateAccountBalance(AccountChangeEvent accountChangeEvent);
}
