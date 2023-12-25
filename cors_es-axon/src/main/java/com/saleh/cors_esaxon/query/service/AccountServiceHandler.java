package com.saleh.cors_esaxon.query.service;

import com.saleh.cors_esaxon.commonapi.enums.AccountStatus;
import com.saleh.cors_esaxon.commonapi.events.AccountActivatedEvent;
import com.saleh.cors_esaxon.commonapi.events.AccountCreatedEvent;
import com.saleh.cors_esaxon.commonapi.events.AccountCreditedEvent;
import com.saleh.cors_esaxon.commonapi.events.AccountDebitedEvent;
import com.saleh.cors_esaxon.commonapi.queries.GetAccountByIdQuery;
import com.saleh.cors_esaxon.commonapi.queries.GetAllAccountQuery;
import com.saleh.cors_esaxon.query.entities.Account;
import com.saleh.cors_esaxon.query.entities.Operation;
import com.saleh.cors_esaxon.query.enums.OperationType;
import com.saleh.cors_esaxon.query.repositories.AccountRepository;
import com.saleh.cors_esaxon.query.repositories.OperationRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class AccountServiceHandler {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;
    @EventHandler
    public void on( AccountCreatedEvent event){
        log.info("**************************************");
        log.info("AccountCreatedEvent received");
        Account account = new Account();
        account.setId(event.getId());
        account.setCurrency(event.getCurrency());
        account.setBalance(event.getInitialBalance());
        account.setStatus(event.getStatus());
        accountRepository.save(account);

    }
    @EventHandler
    public void on(AccountActivatedEvent event){
        log.info("**************************************");
        log.info("AccountActivatedEvent received");
        Account account = accountRepository.findById(event.getId()).get();
        account.setStatus(event.getStatus());
        accountRepository.save(account);
    }
    @EventHandler
    public void on(AccountDebitedEvent event){
        log.info("**************************************");
        log.info("AccountActivatedEvent received");
        Account account = accountRepository.findById(event.getId()).get();
        Operation operation = new Operation();
        operation.setAmount(event.getAmount());
        operation.setDate(new Date());
        operation.setType(OperationType.DEBIT);
        operation.setAccount(account);
        account.setBalance(account.getBalance()-event.getAmount());
        accountRepository.save(account);
    }
    @EventHandler
    public void on(AccountCreditedEvent event){
        log.info("**************************************");
        log.info("AccountActivatedEvent received");
        Account account = accountRepository.findById(event.getId()).get();
        Operation operation = new Operation();
        operation.setAmount(event.getAmount());
        operation.setDate(new Date());
        operation.setType(OperationType.DEBIT);
        operation.setAccount(account);
        account.setBalance(account.getBalance()+event.getAmount());
        accountRepository.save(account);
    }
    @QueryHandler
    public List<Account> on(GetAllAccountQuery query){
        return accountRepository.findAll();
    }
    @QueryHandler
    public Account on(GetAccountByIdQuery query){
        return accountRepository.findById(query.getId()).get();
    }
}
