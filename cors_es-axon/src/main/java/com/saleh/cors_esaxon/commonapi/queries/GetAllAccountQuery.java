package com.saleh.cors_esaxon.commonapi.queries;

import com.saleh.cors_esaxon.query.entities.Account;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;

import java.util.List;

public class GetAllAccountQuery {
    private QueryGateway queryGateway;
    public List<Account> accountList(){
        List<Account> response = queryGateway.query(new GetAllAccountQuery(), ResponseTypes.multipleInstancesOf(Account.class)).join();
        return response;
    }
}
