package com.wry.dtx.bank1.service;

import com.wry.dtx.bank1.entity.AccountInfo;
import com.baomidou.mybatisplus.extension.service.IService;

public interface AccountInfoService extends IService<AccountInfo> {

    Boolean transferAccounts(String receiveAccountNo,String transferAccountNo, Double amount);

}

