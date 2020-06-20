package com.wry.dtx.bank1.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wry.dtx.bank1.entity.AccountInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface AccountInfoMapper extends BaseMapper<AccountInfo> {

   AccountInfo findByAccountNo(@Param("accountNo")String accountNo);


}