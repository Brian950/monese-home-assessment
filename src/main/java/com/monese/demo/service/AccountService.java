package com.monese.demo.service;

import com.monese.demo.domain.dto.Account;
import javassist.NotFoundException;

public interface AccountService {

    public Account getById(String accountId) throws NotFoundException;

    public void removeFunds(String accountId, long amount) throws NotFoundException;

    void addFunds(String accountId, long amount) throws NotFoundException;

    public String getAccountStatus(String accountId) throws NotFoundException;

    public long getBalanceById(String accountId) throws NotFoundException;

    public long getBalanceByOwnerName(String ownerName) throws NotFoundException;
}
