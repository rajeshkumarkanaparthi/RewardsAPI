package com.rk.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.rk.entity.Transaction;
import com.rk.repository.TransactionRepository;
import com.rk.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TransactionsController transactionsController;
    @InjectMocks
    private TransactionService transactionService;
    @Mock
    private TransactionRepository transactionRepository;

    @Test
    public void contextLoads() throws Exception {
        assertThat(transactionsController).isNotNull();
    }

    @Test
    public void shouldReturnOK() throws Exception {
        mockMvc.perform(get("/transactions")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void getAllTransactions() {
        List<Transaction> transactions = Arrays.asList(new Transaction[]{new Transaction(1L, 1001L, new Timestamp(System.currentTimeMillis()), 100)});
        Mockito.when(transactionService.getAllTransactions()).thenReturn(transactions);
        List<Transaction> allTransactions = transactionService.getAllTransactions();
        assertEquals(1, allTransactions.size());
    }
}