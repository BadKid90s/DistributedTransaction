package com.wry.dtx.bank2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wry.dtx.bank2.entity.AccountInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AccountInfoMapper extends BaseMapper<AccountInfo> {

    AccountInfo findByAccountNo(@Param("accountNo") String accountNo);
}