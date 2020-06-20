package com.wry.dtx.bank1.service;

import com.wry.dtx.bank1.entity.AccountInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;

public interface AccountInfoService extends IService<AccountInfo> {

    Boolean updateAccountBalance (String receiveAccountNo,String transferAccountNo, BigDecimal balance);
}
