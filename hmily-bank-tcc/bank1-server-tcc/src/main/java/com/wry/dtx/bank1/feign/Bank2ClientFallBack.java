package com.wry.dtx.bank1.feign;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class Bank2ClientFallBack  implements Bank2Client {
    @Override
    public String updateAccountBalance(String accountNo, BigDecimal amount) {
        return "fallBack";
    }
}
