package com.wry.dtx.bank2.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wry.dtx.bank2.entity.AccountInfo;
import com.wry.dtx.bank2.mapper.AccountInfoMapper;
import com.wry.dtx.bank2.service.AccountInfoService;
import io.seata.core.context.RootContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
public class AccountInfoServiceImpl extends ServiceImpl<AccountInfoMapper, AccountInfo> implements AccountInfoService {

    @Resource
    private AccountInfoMapper accountInfoMapper;


    @Override
    @Transactional
    public Boolean transferAccounts(String accountNo, Double amount) {
        if (amount < 200) {
            throw new RuntimeException("bank2 make exception; XID :{" + RootContext.getXID() + "}");
        }
        AccountInfo accountInfo = accountInfoMapper.findByAccountNo(accountNo);
        accountInfo.setAccountBalance(accountInfo.getAccountBalance().add(new BigDecimal(amount)));
        accountInfoMapper.updateById(accountInfo);
        return true;
    }
}
