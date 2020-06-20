package com.wry.dtx.bank2.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wry.dtx.bank2.entity.AccountInfo;
import com.wry.dtx.bank2.mapper.AccountInfoMapper;
import com.wry.dtx.bank2.service.AccountInfoService;
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


    @Override
    //标记了Hmily注解的就是 try 方法，在注解中指定confirm 和cancel 方法
    @Hmily(confirmMethod = "commit", cancelMethod = "rollback")
    public Boolean updateAccountBalance(String receiveAccountNo, BigDecimal balance) {
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("Bnak2  进入Try方法执行， transId :" + transId);
        return true;
    }

    /**
     * confirm幂等校验
     * 正式增加30元
     *
     * @param receiveAccountNo
     * @param balance
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean commit(String receiveAccountNo, BigDecimal balance) {
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("Bnak2  进入Commit 方法执行， transId :" + transId);
        //  confirm幂等校验
        if (accountInfoMapper.isExistConfirm(transId) > 0) {
            log.info("Bnak1 Cancel 方法已经执行，无需重复执行，transId :" + transId);
            return false;
        }
        //增加30元
        accountInfoMapper.addAccountBalance(receiveAccountNo, balance);
        //插入 Confirm 执行记录
        accountInfoMapper.addConfirm(transId);
        return true;
    }

    public Boolean rollback(String receiveAccountNo, BigDecimal balance) {
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("Bnak2  进入Cancel方法执行， transId :" + transId);
        return true;
    }
}
