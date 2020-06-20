package com.wry.dtx.bank1.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wry.dtx.bank1.entity.AccountInfo;
import com.wry.dtx.bank1.feign.Bank2Client;
import com.wry.dtx.bank1.mapper.AccountInfoMapper;
import com.wry.dtx.bank1.service.AccountInfoService;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.Hmily;
import org.dromara.hmily.core.concurrent.threadlocal.HmilyTransactionContextLocal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
@Slf4j
public class AccountInfoServiceImpl extends ServiceImpl<AccountInfoMapper, AccountInfo> implements AccountInfoService {

    @Resource
    private AccountInfoMapper accountInfoMapper;
    @Resource
    private Bank2Client bank2Client;

    /**
     * try幂等校验
     * try悬挂校验
     * 检查余额是否够30元
     * 扣减30元
     * 账户扣款
     *
     * @param receiveAccountNo
     * @param transferAccountNo
     * @param balance
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    //标记了Hmily注解的就是 try 方法，在注解中指定confirm 和cancel 方法
    @Hmily(confirmMethod = "commit", cancelMethod = "rollback")
    public Boolean updateAccountBalance(String receiveAccountNo, String transferAccountNo, BigDecimal balance) {
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("Bnak1  进入 Try 方法执行， transId :" + transId);

        // 人为异常
        // balance>5  1    balance<5  -1  balance=5  0
        if (balance.compareTo(new BigDecimal(5))<0 ){
            throw  new RuntimeException("金额小于5元，转账失败");
        }
        //try幂等校验
        if (accountInfoMapper.isExistTry(transId) > 0) {
            log.info("Bnak1 try 方法已经执行，无需重复执行，transId :" + transId);
            return false;
        }
        //try悬挂校验
        if (accountInfoMapper.isExistCancel(transId) > 0 || accountInfoMapper.isExistConfirm(transId) > 0) {
            log.info("Bnak1 Cancel 或 Confirm  方法已经执行，无需重复执行，transId :" + transId);
            return false;
        }
        //检查余额是否够30元, 扣减30元
        int i = accountInfoMapper.subAccountBalance(transferAccountNo, balance);
        if (i <= 0) {
            throw new RuntimeException("扣减金额失败,transId :" + transId);
        }
        //插入try 执行记录
        accountInfoMapper.addTry(transId);

        //远程调用bank2
        bank2Client.updateAccountBalance(receiveAccountNo, balance);
        return true;
    }

    public Boolean commit(String receiveAccountNo, String transferAccountNo, BigDecimal balance) {
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("Bnak1  进入 Confirm 方法执行， transId :" + transId);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean rollback(String receiveAccountNo, String transferAccountNo, BigDecimal balance) {
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("Bnak1  进入 Cancel 方法执行， transId :" + transId);
        //  cancel幂等校验
        if (accountInfoMapper.isExistCancel(transId) > 0) {
            log.info("Bnak1 Cancel 方法已经执行，无需重复执行，transId :" + transId);
            return false;
        }
        //	cancel空回滚处理
        if (accountInfoMapper.isExistTry(transId) <= 0) {
            log.info("Bnak1 Try 方法没有执行，无需执行 Cancel 方法，transId :" + transId);
            return false;
        }
        //	增加30元
        accountInfoMapper.addAccountBalance(transferAccountNo, balance);

        //插入cancel执行记录
        accountInfoMapper.addCancel(transId);
        return true;
    }
}
