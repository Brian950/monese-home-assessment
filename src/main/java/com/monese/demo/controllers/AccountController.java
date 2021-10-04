package com.monese.demo.controllers;

import com.monese.demo.domain.ApiConstants;
import com.monese.demo.domain.ApiResponse;
import com.monese.demo.service.AccountService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = { "/account" }, produces="application/json")
public class AccountController {

    @Autowired
    private AccountService accountService;

    Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    @GetMapping("/getBalance")
    public ApiResponse getBalance(@RequestParam String accountId) {
        try {
            return ApiResponse.builder()
                    .status(ApiConstants.SUCCESS)
                    .data(String.valueOf(accountService.getBalanceById(accountId))).build();
        } catch (NotFoundException nfe) {
            LOGGER.error(nfe.getMessage());
            return ApiResponse.builder()
                    .status(ApiConstants.FAILURE)
                    .data(String.valueOf("No account found for this id.")).build();
        }
    }

    @GetMapping("/getBalanceByOwnerName")
    public ApiResponse getBalanceByOwnerName(@RequestParam String accountOwner) {
        try {
            return ApiResponse.builder()
                    .status(ApiConstants.SUCCESS)
                    .data(String.valueOf(accountService.getBalanceByOwnerName(accountOwner))).build();
        } catch (NotFoundException nfe) {
            LOGGER.error(nfe.getMessage());
            return ApiResponse.builder()
                    .status(ApiConstants.FAILURE)
                    .data(nfe.getMessage()).build();
        }
    }

    @GetMapping("/getStatus")
    public ApiResponse getStatus(@RequestParam String accountId) {
        try {
            return ApiResponse.builder()
                    .status(ApiConstants.SUCCESS)
                    .data(accountService.getAccountStatus(accountId)).build();
        } catch (NotFoundException nfe) {
            LOGGER.error(nfe.getMessage());
            return ApiResponse.builder()
                    .status(ApiConstants.FAILURE)
                    .data(String.valueOf("No account found for this id.")).build();
        }
    }
}
