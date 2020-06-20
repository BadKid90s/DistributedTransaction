package com.wry.dtx.bank2.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;

/**
    * 账户表
    */
@TableName(value = "account_info")
public class AccountInfo {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 账户姓名
     */
    @TableField(value = "account_name")
    private String accountName;

    /**
     * 账户卡号
     */
    @TableField(value = "account_no")
    private String accountNo;

    /**
     * 账户密码
     */
    @TableField(value = "account_password")
    private String accountPassword;

    /**
     * 账户余额
     */
    @TableField(value = "account_balance")
    private BigDecimal accountBalance;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取账户姓名
     *
     * @return account_name - 账户姓名
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * 设置账户姓名
     *
     * @param accountName 账户姓名
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * 获取账户卡号
     *
     * @return account_no - 账户卡号
     */
    public String getAccountNo() {
        return accountNo;
    }

    /**
     * 设置账户卡号
     *
     * @param accountNo 账户卡号
     */
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    /**
     * 获取账户密码
     *
     * @return account_password - 账户密码
     */
    public String getAccountPassword() {
        return accountPassword;
    }

    /**
     * 设置账户密码
     *
     * @param accountPassword 账户密码
     */
    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    /**
     * 获取账户余额
     *
     * @return account_balance - 账户余额
     */
    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    /**
     * 设置账户余额
     *
     * @param accountBalance 账户余额
     */
    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }
}