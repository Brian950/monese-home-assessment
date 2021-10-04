package com.monese.demo.service.impl;

import com.monese.demo.domain.dto.Account;
import com.monese.demo.repo.AccountRepository;
import com.monese.demo.service.AccountService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepo;

    // Error messages
    private static final String ACCOUNT_NOT_FOUND_ID = "Could not find account for id: %s";
    private static final String ACCOUNT_NOT_FOUND_NAME = "Could not find account for name: %s";
    // Account statuses
    private static final String POSITIVE = "POSITIVE";
    private static final String EMPTY = "EMPTY";
    private static final String OVERDRAWN = "OVERDRAWN";

    @Override
    public Account getById(String accountId) throws NotFoundException {
        if(accountId != null) {
            Optional<Account> result = accountRepo.findById(accountId);
            if (result.isPresent()) return result.get();
        }
        throw new NotFoundException(String.format(ACCOUNT_NOT_FOUND_ID, accountId));
    }

    @Override
    public void removeFunds(String accountId, long amount) throws NotFoundException {
        Account account = getById(accountId);
        account.setBalance(account.getBalance() - amount);
    }

    @Override
    public void addFunds(String accountId, long amount) throws NotFoundException {
        Account account = getById(accountId);
        account.setBalance(account.getBalance() + amount);
    }

    @Override
    public String getAccountStatus(String id) throws NotFoundException {
        Account account = getById(id);
        if(account.getBalance() > 0) return POSITIVE;
        else if(account.getBalance() < 0) return OVERDRAWN;
        else return EMPTY;
    }

    @Override
    public long getBalanceById(String accountId) throws NotFoundException {
        Optional<Account> result = accountRepo.findById(accountId);
        if(result.isPresent()) return result.get().getBalance();
        else throw new NotFoundException(String.format(ACCOUNT_NOT_FOUND_ID, accountId));
    }

    @Override
    public long getBalanceByOwnerName(String ownerName) throws NotFoundException {
        Optional<Account> result = accountRepo.findByOwner(ownerName);
        if(result.isPresent()) return result.get().getBalance();
        else throw new NotFoundException(String.format(ACCOUNT_NOT_FOUND_NAME, ownerName));
    }
}
