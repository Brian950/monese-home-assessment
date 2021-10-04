package com.monese.demo.controllers;

import com.monese.demo.domain.ApiConstants;
import com.monese.demo.domain.ApiResponse;
import com.monese.demo.domain.json.TransactionJson;
import com.monese.demo.service.TransactionService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = { "/transaction" }, produces="application/json")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    Logger LOGGER = LoggerFactory.getLogger(TransactionController.class);

    @GetMapping("/getAll")
    public List<TransactionJson> getAll() {
        return transactionService.getAll();
    }

    @PostMapping("/sendMoney")
    public ApiResponse sendMoney(@RequestBody TransactionJson transactionJson) {
        try {
            String result = transactionService.performTransaction(transactionJson);
            if(result.equals(ApiConstants.SUCCESS)) {
                return ApiResponse.builder().status(ApiConstants.SUCCESS).data("Money sent.").build();
            } else {
                return ApiResponse.builder().status(ApiConstants.FAILURE).data(result).build();
            }
        } catch (NotFoundException nfe) {
            LOGGER.error(nfe.getMessage());
            return ApiResponse.builder().status(ApiConstants.FAILURE).data(nfe.getMessage()).build();
        }
    }
}
