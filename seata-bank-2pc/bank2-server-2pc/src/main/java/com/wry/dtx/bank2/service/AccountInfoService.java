package com.wry.dtx.bank2.service;

import com.wry.dtx.bank2.entity.AccountInfo;
import com.baomidou.mybatisplus.extension.service.IService;
public interface AccountInfoService extends IService<AccountInfo>{

    Boolean transferAccounts(String accountNo,Double balance);
}
