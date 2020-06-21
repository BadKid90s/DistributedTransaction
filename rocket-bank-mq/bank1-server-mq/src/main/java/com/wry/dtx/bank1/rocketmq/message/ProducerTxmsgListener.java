package com.wry.dtx.bank1.rocketmq.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wry.dtx.bank1.mapper.AccountInfoMapper;
import com.wry.dtx.bank1.rocketmq.entity.AccountChangeEvent;
import com.wry.dtx.bank1.service.AccountInfoService;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

@Component
@RocketMQTransactionListener(txProducerGroup = "producer_group_txmsg_bank1")//和发消息的生成组一致
public class ProducerTxmsgListener implements RocketMQLocalTransactionListener {

    @Resource
    private AccountInfoService accountInfoService;
    @Resource
    private AccountInfoMapper accountInfoMapper;

    @Resource
    private ObjectMapper objectMapper;

    /**
     * 发送prepare消息成功此方法被回调，该方法用于执行本地事务
     *
     * @param message 回传的消息，利用transactionId即可获取到该消息的唯一Id
     * @param o       调用send方法时传递的参数，当send时候若有额外的参数可以传递到send方法中，这里能获取到
     * @return 返回事务状态，COMMIT：提交 ROLLBACK：回滚 UNKNOW：回调
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        try {
            String payload = new String((byte[]) message.getPayload());
            //解析消息
            Map map = objectMapper.readValue(payload, Map.class);
            String changeEventStr = map.get("accountChangeEvent").toString();
            AccountChangeEvent accountChangeEvent = objectMapper.readValue(changeEventStr, AccountChangeEvent.class);
            //扣减金额
            accountInfoService.doUpdateAccountBalance(accountChangeEvent);
            //提交
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            e.printStackTrace();
            //回滚
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    /**
     * 检查本地事务的执行状态
     *
     * @param message 通过获取transactionId来判断这条消息的本地事务执行状态
     * @return 返回事务状态，COMMIT：提交 ROLLBACK：回滚 UNKNOW：回调
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        try {
            String payload = (String) message.getPayload();
            AccountChangeEvent accountChangeEvent = null;
            //解析消息
            accountChangeEvent = objectMapper.readValue(payload, AccountChangeEvent.class);
            //查询事务日志
            if (accountInfoMapper.isExistTx(accountChangeEvent.getTxNo())) {
                //提交
                return RocketMQLocalTransactionState.COMMIT;
            }
            //回滚
            return RocketMQLocalTransactionState.UNKNOWN;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            //回滚
            return RocketMQLocalTransactionState.UNKNOWN;
        }
    }
}
