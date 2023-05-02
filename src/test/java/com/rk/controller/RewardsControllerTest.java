package com.rk.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class RewardsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RewardsController rewardsController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(rewardsController).isNotNull();
    }
}