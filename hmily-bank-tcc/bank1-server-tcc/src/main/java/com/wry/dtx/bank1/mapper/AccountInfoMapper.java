package com.wry.dtx.bank1.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wry.dtx.bank1.entity.AccountInfo;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;

@Mapper
public interface AccountInfoMapper extends BaseMapper<AccountInfo> {

    @Select("select * from account_info  where account_no = #{accountNo,jdbcType=VARCHAR}")
    AccountInfo findByAccountNo(@Param("accountNo") String accountNo);

    @Update("update account_info set account_balance = account_balance - #{balance,jdbcType=DECIMAL} where account_balance >= #{balance,jdbcType=DECIMAL} and account_no = #{accountNo,jdbcType=VARCHAR}")
    int subAccountBalance(@Param("accountNo") String accountNo, @Param("balance") BigDecimal balance);

    @Update("update account_info set account_balance = account_balance + #{balance,jdbcType=DECIMAL} where  account_no = #{accountNo,jdbcType=VARCHAR}")
    int addAccountBalance(@Param("accountNo") String accountNo, @Param("balance") BigDecimal balance);

    @Insert("insert into local_try_log (tx_no, create_time) values (#{txNo} ,now())")
    int addTry(@Param("txNo") String txNo);

    @Insert("insert into local_confirm_log (tx_no, create_time) values (#{txNo} ,now())")
    int addConfirm(@Param("txNo") String txNo);

    @Insert("insert into local_cancel_log (tx_no, create_time) values (#{txNo} ,now())")
    int addCancel(@Param("txNo") String txNo);

    @Select("select count(1) from local_try_log where  tx_no= #{txNo} ")
    int isExistTry(@Param("txNo") String txNo);

    @Select("select count(1) from local_confirm_log where  tx_no= #{txNo} ")
    int isExistConfirm(@Param("txNo") String txNo);

    @Select("select count(1) from local_cancel_log where  tx_no= #{txNo} ")
    int isExistCancel(@Param("txNo") String txNo);
}