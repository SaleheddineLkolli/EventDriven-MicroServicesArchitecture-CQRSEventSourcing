package com.saleh.cors_esaxon.commonapi.events;

import lombok.Getter;

public class AccountCreditedEvent extends BaseEvent<String>{
   @Getter
   private double amount;
    @Getter
    private String currency;
    protected AccountCreditedEvent(String id, double amount, String currency) {
        super(id);
        this.amount = amount;
        this.currency = currency;
    }
}
