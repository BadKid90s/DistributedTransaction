package com.wry.dtx.bank1.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wry.dtx.bank1.entity.AccountInfo;
import com.wry.dtx.bank1.feign.Bank2Client;
import com.wry.dtx.bank1.mapper.AccountInfoMapper;
import com.wry.dtx.bank1.service.AccountInfoService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
public class AccountInfoServiceImpl extends ServiceImpl<AccountInfoMapper, AccountInfo> implements AccountInfoService {

    @Resource
    private AccountInfoMapper accountInfoMapper;
    @Resource
    private Bank2Client bank2Client;


    @GlobalTransactional(rollbackFor = Exception.class)
    @Transactional
    @Override
    public Boolean transferAccounts(String receiveAccountNo, String transferAccountNo, Double amount) {
        if (amount < 500) {
            throw new RuntimeException("bank1 make exception");
        }
        //张三扣减金额
        AccountInfo accountInfo = accountInfoMapper.findByAccountNo(transferAccountNo);
        accountInfo.setAccountBalance(accountInfo.getAccountBalance().subtract(new BigDecimal(amount)));
        accountInfoMapper.updateById(accountInfo);
        //李四增减金额
        String s = bank2Client.transferAccounts(receiveAccountNo, amount);
        if (s.equals("fallBack")) {
            throw new RuntimeException("转账失败！");
        }
        return true;
    }

}


