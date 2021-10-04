package com.monese.demo.service.impl;

import com.monese.demo.domain.ApiConstants;
import com.monese.demo.domain.dto.Account;
import com.monese.demo.domain.dto.Transaction;
import com.monese.demo.domain.json.TransactionJson;
import com.monese.demo.repo.TransactionRepository;
import com.monese.demo.service.AccountService;
import com.monese.demo.service.TransactionService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepo;

    @Autowired
    private AccountService accountService;

    public static final String INSUFFICIENT_FUNDS = "Insufficient funds to perform the transaction.";
    public static final String DUPLICATE_ACCOUNT_IDS = "Sender ID is the same as the recipient.";


    @Override
    public List<TransactionJson> getAll() {
        List<Transaction> transactions = transactionRepo.findAll();
        List<TransactionJson> transactionJsonList = new ArrayList<>();
        for(Transaction t : transactions) {
            transactionJsonList.add(TransactionJson.builder()
                    .id(t.getId())
                    .senderId(t.getSender().getId())
                    .recipientId(t.getRecipient().getId())
                    .amount(t.getAmount()).build());
        }
        return transactionJsonList;
    }

    @Transactional
    @Override
    public String performTransaction(TransactionJson transactionJson) throws NotFoundException {
        // Can't transfer money from an account to itself.
        if(transactionJson.getSenderId().equals(transactionJson.getRecipientId()))
            return DUPLICATE_ACCOUNT_IDS;
        // Get the accounts required to verify they exist
        Account sender = accountService.getById(transactionJson.getSenderId());
        Account recipient = accountService.getById(transactionJson.getRecipientId());
        // Only allow the transaction if the senders balance is positive (they may go overdrawn)
        if(sender.getBalance() > 0) {
            accountService.removeFunds(sender.getId(), transactionJson.getAmount());
            accountService.addFunds(recipient.getId(), transactionJson.getAmount());
            transactionRepo.save(Transaction.builder()
                    .sender(sender)
                    .recipient(recipient)
                    .amount(transactionJson.getAmount())
                    .build());
            return ApiConstants.SUCCESS;
        } else {
            return INSUFFICIENT_FUNDS;
        }
    }
}
