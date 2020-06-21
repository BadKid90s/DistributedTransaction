package com.wry.dtx.bank2.rocketmq.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wry.dtx.bank2.rocketmq.entity.AccountChangeEvent;
import com.wry.dtx.bank2.service.AccountInfoService;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Component
@RocketMQMessageListener(consumerGroup = "consumer_group_txmsg_bank2", topic = "topic_txmsg")
public class TxmsgConsumer implements RocketMQListener<String> {
    @Resource
    private AccountInfoService accountInfoService;
    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void onMessage(String jsonStr) {
        try {  //解析消息
            Map map = null;

            map = objectMapper.readValue(jsonStr, Map.class);

            String changeEventStr = map.get("accountChangeEvent").toString();
            AccountChangeEvent accountChangeEvent = objectMapper.readValue(changeEventStr, AccountChangeEvent.class);

            accountInfoService.updateAccountBalance(accountChangeEvent);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
