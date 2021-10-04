package com.monese.demo.integration;

import com.google.gson.Gson;
import com.monese.demo.domain.dto.Account;
import com.monese.demo.domain.json.TransactionJson;
import com.monese.demo.repo.AccountRepository;
import org.hibernate.engine.spi.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebAppConfiguration
@SpringBootTest
public class EndToEndTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private AccountRepository accountRepo;

    private static final String SENDER_ID = "0";
    private static final String RECIPIENT_ID = "1";
    private static final long TRANS_AMOUNT = 500;

    @Transactional
    @Test
    void endToEndIntegrationTest() throws Exception {
        Account sender = accountRepo.getById(SENDER_ID);
        // Set the sender balance to the transaction amount,
        // so it can complete the transaction (getting it positive is sufficient)
        if(sender.getBalance() < TRANS_AMOUNT) {
            sender.setBalance(TRANS_AMOUNT);
        }
        long senderStartingBalance = sender.getBalance();
        Account recipient = accountRepo.getById(RECIPIENT_ID);
        long recipientStartingBalance = recipient.getBalance();
        // Create JSON transaction to send
        TransactionJson json = TransactionJson.builder().senderId(SENDER_ID).recipientId(RECIPIENT_ID).amount(500).build();
        Gson gson = new Gson();
        // Create the MVC request executor & send the post request
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        this.mvc.perform(MockMvcRequestBuilders.post("/transaction/sendMoney")
                .content(gson.toJson(json))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // Verify that the account balances are as expected
        assertEquals(senderStartingBalance - TRANS_AMOUNT, sender.getBalance());
        assertEquals(recipientStartingBalance + TRANS_AMOUNT, recipient.getBalance());
    }
}
