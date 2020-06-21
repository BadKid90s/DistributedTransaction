package com.wry.dtx.bank1.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wry.dtx.bank1.entity.AccountInfo;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;

@Mapper
public interface AccountInfoMapper extends BaseMapper<AccountInfo> {
    @Update("update account_info set account_balance=account_balance+#{amount} where account_no=#{accountNo} ")
    int updateAccountBalance(@Param("accountNo") String accountNo, @Param("amount") BigDecimal amount);

    @Insert(" insert into de_duplication (tx_no, create_time) VALUES (#{txNo} ,now())")
    int addTxLog(String txNo);

    @Select("select count(1) from de_duplication where tx_no=#{txNo} ")
    boolean isExistTx(String txNo);
}