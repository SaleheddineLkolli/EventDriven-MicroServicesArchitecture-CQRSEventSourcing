package com.saleh.cors_esaxon.commnds.controllers.aggragates;

import com.saleh.cors_esaxon.commonapi.commands.CreateAccountCommand;
import com.saleh.cors_esaxon.commonapi.commands.CreditAccountCommand;
import com.saleh.cors_esaxon.commonapi.commands.DebitAccountCommand;
import com.saleh.cors_esaxon.commonapi.enums.AccountStatus;
import com.saleh.cors_esaxon.commonapi.events.AccountActivatedEvent;
import com.saleh.cors_esaxon.commonapi.events.AccountCreatedEvent;
import com.saleh.cors_esaxon.commonapi.events.AccountCreditedEvent;
import com.saleh.cors_esaxon.commonapi.events.AccountDebitedEvent;
import com.saleh.cors_esaxon.commonapi.exceptions.AmountNegativeException;
import com.saleh.cors_esaxon.commonapi.exceptions.BalanceNotSufficentException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId ;
    private double balance ;
    private String currency;
    private AccountStatus status;

    public AccountAggregate() {

    }
    @CommandHandler
    public AccountAggregate(CreateAccountCommand createAccountCommand) {
        if(createAccountCommand.getInitialBalance()<0) throw  new RuntimeException("Impossible ... ");{
            AggregateLifecycle.apply(new AccountCreatedEvent(
                    createAccountCommand.getId(),
                    createAccountCommand.getInitialBalance(),
                    createAccountCommand.getCurrency(),
                    AccountStatus.CREATED
            ));

        }
    }
    @EventSourcingHandler
    public void on(AccountCreatedEvent accountCreatedEvent){
        this.accountId=accountCreatedEvent.getId();
        this.balance=accountCreatedEvent.getInitialBalance();
        this.currency=accountCreatedEvent.getCurrency();
        this.status=AccountStatus.CREATED;
        AggregateLifecycle.apply(new AccountActivatedEvent(
                accountCreatedEvent.getId(),
                AccountStatus.ACTIVATED
        ));
    }
    @EventSourcingHandler
    public void on(AccountActivatedEvent accountActivatedEvent){
        this.status =accountActivatedEvent.getStatus();
    }
    @CommandHandler
    public void handler(CreditAccountCommand command){
        if(command.getAmount()<0) throw  new AmountNegativeException("Amount should not be negative");
        AggregateLifecycle.apply(new AccountCreatedEvent(
                command.getId(),
                command.getAmount(),
                command.getCurrency(),
                AccountStatus.ACTIVATED


        ));
    }
    @EventSourcingHandler
    public void on(AccountCreditedEvent event){
        this.balance+=event.getAmount();
    }

    @CommandHandler
    public void handler(DebitAccountCommand command){
        if(this.balance<command.getAmount()) throw  new BalanceNotSufficentException("Balance not sufficient Exception ==>"+balance);
        AggregateLifecycle.apply(new AccountDebitedEvent(
                command.getId(),
                command.getAmount(),
                command.getCurrency()
        ));
    }
    @EventSourcingHandler
    public void on(AccountDebitedEvent event){
        this.balance-=event.getAmount();
    }
}
