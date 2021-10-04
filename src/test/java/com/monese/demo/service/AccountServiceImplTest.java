package com.monese.demo.service;

import com.monese.demo.domain.dto.Account;
import com.monese.demo.repo.AccountRepository;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class AccountServiceImplTest {

    @Autowired
    private AccountService accountService;

    @MockBean
    private AccountRepository repo;

    private static final String TEST_ID = "123456789";
    private static final String TEST_NAME = "Brian";
    private static final long TEST_BAL = 1000;

    @Test
    void getById() {
        Optional<Account> optionalAccount = Optional.of(Account.builder()
                .id(TEST_ID).owner(TEST_NAME).balance(TEST_BAL).build());
        when(repo.findById(TEST_ID)).thenReturn(optionalAccount);

        try {
            Account account = accountService.getById(TEST_ID);
            assertEquals(TEST_ID, account.getId());
        } catch (NotFoundException nfe) {
            fail();
        }
    }

    @Test
    void removeFunds() {
        Optional<Account> optionalAccount = Optional.of(Account.builder()
                .id(TEST_ID).owner(TEST_NAME).balance(TEST_BAL).build());
        Account a = optionalAccount.get();
        when(repo.findById(TEST_ID)).thenReturn(optionalAccount);
        try {
            accountService.removeFunds(TEST_ID, 500);
            assertEquals(TEST_BAL - 500, a.getBalance());
        } catch (NotFoundException nfe) {
            fail();
        }
    }

    @Test
    void addFunds() {
        Optional<Account> optionalAccount = Optional.of(Account.builder()
                .id(TEST_ID).owner(TEST_NAME).balance(TEST_BAL).build());
        Account a = optionalAccount.get();
        when(repo.findById(TEST_ID)).thenReturn(optionalAccount);
        try {
            accountService.addFunds(TEST_ID, 500);
            assertEquals(TEST_BAL +  500, a.getBalance());
        } catch (NotFoundException nfe) {
            fail();
        }
    }

    @Test
    void getAccountStatus() {
        Optional<Account> optionalAccount = Optional.of(Account.builder()
                .id(TEST_ID).owner(TEST_NAME).balance(TEST_BAL).build());
        when(repo.findById(TEST_ID)).thenReturn(optionalAccount);
        try {
            //Positive account
            String result = accountService.getAccountStatus(TEST_ID);
            assertEquals("POSITIVE", result);
            // Empty account
            accountService.removeFunds(TEST_ID, TEST_BAL);
            result = accountService.getAccountStatus(TEST_ID);
            assertEquals("EMPTY", result);
            // Overdrawn account
            accountService.removeFunds(TEST_ID, TEST_BAL + 1);
            result = accountService.getAccountStatus(TEST_ID);
            assertEquals("OVERDRAWN", result);
        } catch (NotFoundException nfe) {
            fail();
        }
    }

    @Test
    void getBalanceById() {
        Optional<Account> optionalAccount = Optional.of(Account.builder()
                .id(TEST_ID).owner(TEST_NAME).balance(TEST_BAL).build());
        when(repo.findById(TEST_ID)).thenReturn(optionalAccount);

        try {
            long balance = accountService.getBalanceById(TEST_ID);
            assertEquals(TEST_BAL, balance);
        } catch (NotFoundException nfe) {
            fail();
        }
    }

    @Test
    void getBalanceByOwnerName() {
        Optional<Account> optionalAccount = Optional.of(Account.builder()
                .id(TEST_ID).owner(TEST_NAME).balance(TEST_BAL).build());
        when(repo.findByOwner(TEST_NAME)).thenReturn(optionalAccount);

        try {
            long balance = accountService.getBalanceByOwnerName(TEST_NAME);
            assertEquals(TEST_BAL, balance);
        } catch (NotFoundException nfe) {
            fail();
        }
    }
}