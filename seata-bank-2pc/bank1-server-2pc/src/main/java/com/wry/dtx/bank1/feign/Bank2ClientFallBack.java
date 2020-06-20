package com.wry.dtx.bank1.feign;

import org.springframework.stereotype.Component;

@Component
public class Bank2ClientFallBack  implements Bank2Client {
    @Override
    public String transferAccounts(String accountNo, Double amount) {
        return "fallBack";
    }
}
