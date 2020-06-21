package com.wry.dtx.bank1.rocketmq.entity;

import java.math.BigDecimal;

public class AccountChangeEvent {
    /**
     * 接收账号
     */
    private String receiveAccountNo;

    /**
     * 转账账号
     */
    private String transferAccountNo;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 事务号
     */
    private String txNo;

    public String getReceiveAccountNo() {
        return receiveAccountNo;
    }

    public void setReceiveAccountNo(String receiveAccountNo) {
        this.receiveAccountNo = receiveAccountNo;
    }

    public String getTransferAccountNo() {
        return transferAccountNo;
    }

    public void setTransferAccountNo(String transferAccountNo) {
        this.transferAccountNo = transferAccountNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTxNo() {
        return txNo;
    }

    public void setTxNo(String txNo) {
        this.txNo = txNo;
    }
}
