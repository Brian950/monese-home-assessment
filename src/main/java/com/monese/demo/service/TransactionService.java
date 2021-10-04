package com.monese.demo.service;

import com.monese.demo.domain.json.TransactionJson;
import javassist.NotFoundException;

import java.util.List;

public interface TransactionService {
    public List<TransactionJson> getAll();
    public String performTransaction(TransactionJson transactionJson) throws NotFoundException;
}
