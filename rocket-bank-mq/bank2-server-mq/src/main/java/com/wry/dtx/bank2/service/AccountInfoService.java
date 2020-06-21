package com.wry.dtx.bank2.service;

import com.wry.dtx.bank2.entity.AccountInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wry.dtx.bank2.rocketmq.entity.AccountChangeEvent;

public interface AccountInfoService extends IService<AccountInfo> {

    /**
     * 更新账户，增加金额
     */
    void updateAccountBalance(AccountChangeEvent accountChangeEvent);


}
