package com.wry.dtx.bank1.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wry.dtx.bank1.entity.AccountInfo;
import com.wry.dtx.bank1.mapper.AccountInfoMapper;
import com.wry.dtx.bank1.rocketmq.entity.AccountChangeEvent;
import com.wry.dtx.bank1.service.AccountInfoService;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class AccountInfoServiceImpl extends ServiceImpl<AccountInfoMapper, AccountInfo> implements AccountInfoService {

    @Resource
    private AccountInfoMapper accountInfoMapper;
    @Resource
    private RocketMQTemplate rocketMQTemplate;
    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void sendUpdateAccountBalance(AccountChangeEvent accountChangeEvent) throws JsonProcessingException {
        //生成Message
        Map<String, String> map = new HashMap();
        map.put("accountChangeEvent", objectMapper.writeValueAsString(accountChangeEvent));
        String jsonStr = objectMapper.writeValueAsString(map);
        Message<String> message = MessageBuilder.withPayload(jsonStr).build();
        /**
         *   String txProducerGroup,    事务组
         *   String destination,        主题
         *   Message<?> message,        消息
         *   Object arg                 参数
         */
        rocketMQTemplate.sendMessageInTransaction("producer_group_txmsg_bank1", "topic_txmsg", message, null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void doUpdateAccountBalance(AccountChangeEvent accountChangeEvent) {
        //幂等控制
        if (accountInfoMapper.isExistTx(accountChangeEvent.getTxNo())) {
            return;
        }
        //扣减金额
        accountInfoMapper.updateAccountBalance(accountChangeEvent.getTransferAccountNo(), accountChangeEvent.getAmount().multiply(new BigDecimal(-1)));
        //记录日志
        accountInfoMapper.addTxLog(accountChangeEvent.getTxNo());

        if (accountChangeEvent.getAmount().compareTo(new BigDecimal(5)) == -1) {
            throw new RuntimeException("转账金额小于5元抛出Exception");
        }
    }
}
