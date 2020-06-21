package com.wry.dtx.bank2.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wry.dtx.bank2.entity.AccountInfo;
import com.wry.dtx.bank2.mapper.AccountInfoMapper;
import com.wry.dtx.bank2.rocketmq.entity.AccountChangeEvent;
import com.wry.dtx.bank2.service.AccountInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
public class AccountInfoServiceImpl extends ServiceImpl<AccountInfoMapper, AccountInfo> implements AccountInfoService {

    @Resource
    private AccountInfoMapper accountInfoMapper;

    /**
     * 更新账户增加金额
     *
     * @param accountChangeEvent
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAccountBalance(AccountChangeEvent accountChangeEvent) {
        //幂等校验
        if (accountInfoMapper.isExistTx(accountChangeEvent.getTxNo())) {
            return;
        }
        //扣减金额
        accountInfoMapper.updateAccountBalance(accountChangeEvent.getReceiveAccountNo(), accountChangeEvent.getAmount());
        //添加事务执行日志记录
        accountInfoMapper.addTxLog(accountChangeEvent.getTxNo());
        if (accountChangeEvent.getAmount().compareTo(new BigDecimal(10)) == -1) {
            throw new RuntimeException("转账金额小于10元抛出Exception");
        }
    }
}
