package com.wry.dtx.bank2.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wry.dtx.bank2.entity.AccountInfo;

import java.math.BigDecimal;

public interface AccountInfoService extends IService<AccountInfo> {

    Boolean updateAccountBalance (String receiveAccountNo,BigDecimal balance);
}
