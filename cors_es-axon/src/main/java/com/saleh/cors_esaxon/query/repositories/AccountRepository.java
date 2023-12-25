package com.saleh.cors_esaxon.query.repositories;

import com.saleh.cors_esaxon.query.entities.Account;
import com.saleh.cors_esaxon.query.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account,String> {

    List<Object> findAllById(String id);
}
