package com.rk.controller;

import com.rk.entity.Transaction;
import com.rk.service.RewardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class RewardsController {

    private RewardsService rewardsService;

    @Autowired
    public RewardsController(RewardsService rewardsService) {
        this.rewardsService = rewardsService;
    }

    @GetMapping("/rewards")
    public Map<Integer, Map<String, Integer>> getRewards() {
        return rewardsService.getRewards();
    }

    @GetMapping("/rewards/{customerId}")
    public Map<String, Integer> getRewardsForCustomer(@PathVariable Long customerId) {
        return rewardsService.getRewardsForCustomer(customerId);
    }
}
