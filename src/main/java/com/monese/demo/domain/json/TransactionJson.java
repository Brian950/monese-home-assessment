package com.monese.demo.domain.json;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class TransactionJson {
    private long id;
    private String senderId;
    private String recipientId;
    private long amount;
}
